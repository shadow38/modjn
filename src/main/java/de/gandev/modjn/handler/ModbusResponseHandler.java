/*
 * Copyright 2012 modjn Project
 *
 * The modjn Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package de.gandev.modjn.handler;

import de.gandev.modjn.ModbusConstants;
import de.gandev.modjn.entity.ModbusFrame;
import de.gandev.modjn.entity.exception.ErrorResponseException;
import de.gandev.modjn.entity.exception.NoResponseException;
import de.gandev.modjn.entity.func.ModbusExceptionResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ares
 */
public abstract class ModbusResponseHandler extends SimpleChannelInboundHandler<ModbusFrame> {

    private static final Logger logger = Logger.getLogger(ModbusResponseHandler.class.getSimpleName());
    private final Map<Integer, ModbusFrame> responses = new HashMap<>(ModbusConstants.TRANSACTION_IDENTIFIER_MAX);

    public ModbusFrame getResponse(int transactionIdentifier)
            throws NoResponseException, ErrorResponseException {

        long timeoutTime = System.currentTimeMillis() + ModbusConstants.SYNC_RESPONSE_TIMEOUT;
        ModbusFrame frame;
        do {
            frame = responses.get(transactionIdentifier);
        } while (frame == null && (timeoutTime - System.currentTimeMillis()) > 0);

        if (frame != null) {
            responses.remove(transactionIdentifier);
        }

        if (frame == null) {
            throw new NoResponseException();
        } else if (frame.getFunction() instanceof ModbusExceptionResponse) {
            throw new ErrorResponseException((ModbusExceptionResponse) frame.getFunction());
        }

        return frame;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(Level.SEVERE, cause.getLocalizedMessage());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame response) throws Exception {
        responses.put(response.getHeader().getTransactionIdentifier(), response);
        newResponse(response);

    }

    public abstract void newResponse(ModbusFrame frame);
}
