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

import static de.gandev.modjn.ModbusConstants.MBAP_LENGTH;
import de.gandev.modjn.entity.ModbusFrame;
import de.gandev.modjn.entity.ModbusFunction;
import de.gandev.modjn.entity.ModbusHeader;
import de.gandev.modjn.entity.func.ModbusExceptionResponse;
import de.gandev.modjn.entity.func.request.ReadCoilsRequest;
import de.gandev.modjn.entity.func.response.ReadCoilsResponse;
import de.gandev.modjn.entity.func.request.ReadDiscreteInputsRequest;
import de.gandev.modjn.entity.func.response.ReadDiscreteInputsResponse;
import de.gandev.modjn.entity.func.request.ReadHoldingRegistersRequest;
import de.gandev.modjn.entity.func.response.ReadHoldingRegistersResponse;
import de.gandev.modjn.entity.func.request.ReadInputRegistersRequest;
import de.gandev.modjn.entity.func.response.ReadInputRegistersResponse;
import de.gandev.modjn.entity.func.request.WriteMultipleCoilsRequest;
import de.gandev.modjn.entity.func.response.WriteMultipleCoilsResponse;
import de.gandev.modjn.entity.func.request.WriteMultipleRegistersRequest;
import de.gandev.modjn.entity.func.response.WriteMultipleRegistersResponse;
import de.gandev.modjn.entity.func.WriteSingleCoil;
import de.gandev.modjn.entity.func.WriteSingleRegister;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 *
 * @author ag
 */
public class ModbusDecoder extends ByteToMessageDecoder {

    private final boolean serverMode;

    public ModbusDecoder(boolean serverMode) {
        this.serverMode = serverMode;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        if (buffer.capacity() < MBAP_LENGTH + 1 /*Function Code*/) {
            return;
        }

        ModbusHeader mbapHeader = ModbusHeader.decode(buffer);

        short functionCode = buffer.readUnsignedByte();

        ModbusFunction function = null;
        switch (functionCode) {
            case ModbusFunction.READ_COILS:
                if (serverMode) {
                    function = new ReadCoilsRequest();
                } else {
                    function = new ReadCoilsResponse();
                }
                break;
            case ModbusFunction.READ_DISCRETE_INPUTS:
                if (serverMode) {
                    function = new ReadDiscreteInputsRequest();
                } else {
                    function = new ReadDiscreteInputsResponse();
                }
                break;
            case ModbusFunction.READ_INPUT_REGISTERS:
                if (serverMode) {
                    function = new ReadInputRegistersRequest();
                } else {
                    function = new ReadInputRegistersResponse();
                }
                break;
            case ModbusFunction.READ_HOLDING_REGISTERS:
                if (serverMode) {
                    function = new ReadHoldingRegistersRequest();
                } else {
                    function = new ReadHoldingRegistersResponse();
                }
                break;
            case ModbusFunction.WRITE_SINGLE_COIL:
                function = new WriteSingleCoil();
                break;
            case ModbusFunction.WRITE_SINGLE_REGISTER:
                function = new WriteSingleRegister();
                break;
            case ModbusFunction.WRITE_MULTIPLE_COILS:
                if (serverMode) {
                    function = new WriteMultipleCoilsRequest();
                } else {
                    function = new WriteMultipleCoilsResponse();
                }
                break;
            case ModbusFunction.WRITE_MULTIPLE_REGISTERS:
                if (serverMode) {
                    function = new WriteMultipleRegistersRequest();
                } else {
                    function = new WriteMultipleRegistersResponse();
                }
                break;
        }

        if (ModbusFunction.isError(functionCode)) {
            function = new ModbusExceptionResponse(functionCode);
        } else if (function == null) {
            function = new ModbusExceptionResponse(functionCode, (short) 1);
        }

        function.decode(buffer.readBytes(buffer.readableBytes()));

        ModbusFrame frame = new ModbusFrame(mbapHeader, function);

        out.add(frame);
    }
}
