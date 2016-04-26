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

import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.gandev.modjn.ModbusConstants;
import de.gandev.modjn.ModbusServer;
import de.gandev.modjn.entity.ModbusFrame;
import de.gandev.modjn.entity.ModbusFunction;
import de.gandev.modjn.entity.ModbusHeader;
import de.gandev.modjn.entity.exception.ModbusException;
import de.gandev.modjn.entity.func.ModbusExceptionCode;
import de.gandev.modjn.entity.func.ModbusExceptionResponse;
import de.gandev.modjn.entity.func.WriteSingleCoil;
import de.gandev.modjn.entity.func.WriteSingleRegister;
import de.gandev.modjn.entity.func.request.ReadCoilsRequest;
import de.gandev.modjn.entity.func.request.ReadDiscreteInputsRequest;
import de.gandev.modjn.entity.func.request.ReadHoldingRegistersRequest;
import de.gandev.modjn.entity.func.request.ReadInputRegistersRequest;
import de.gandev.modjn.entity.func.request.WriteMultipleCoilsRequest;
import de.gandev.modjn.entity.func.request.WriteMultipleRegistersRequest;
import de.gandev.modjn.entity.func.response.ReadCoilsResponse;
import de.gandev.modjn.entity.func.response.ReadDiscreteInputsResponse;
import de.gandev.modjn.entity.func.response.ReadHoldingRegistersResponse;
import de.gandev.modjn.entity.func.response.ReadInputRegistersResponse;
import de.gandev.modjn.entity.func.response.WriteMultipleCoilsResponse;
import de.gandev.modjn.entity.func.response.WriteMultipleRegistersResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author ares
 */
public abstract class ModbusRequestHandler extends
		SimpleChannelInboundHandler<ModbusFrame> {

	private static final Logger logger = Logger
			.getLogger(ModbusRequestHandler.class.getSimpleName());
	private ModbusServer server;

	public void setServer(ModbusServer server) {
		this.server = server;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.warning(cause.getLocalizedMessage());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		server.removeClient(ctx.channel());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		server.addClient(ctx.channel());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame frame)
			throws Exception {
		
		Channel channel = ctx.channel();

		ModbusFunction function = frame.getFunction();

		ModbusFunction response = null;

		int slaveId = frame.getHeader().getUnitIdentifier();
		InetSocketAddress remoteAddress = (InetSocketAddress) channel.remoteAddress();
		
		ModbusRequestContext context = new ModbusRequestContext();
		context.setRemoteAddress(remoteAddress);
		context.setSlaveId(slaveId);
		
		logger.log(Level.FINER, function.toString());

		try {
			if (function instanceof WriteSingleCoil) {
				WriteSingleCoil request = (WriteSingleCoil) function;

				response = writeSingleCoil(context, request);
			} else if (function instanceof WriteSingleRegister) {
				WriteSingleRegister request = (WriteSingleRegister) function;

				response = writeSingleRegister(context, request);
			} else if (function instanceof ReadCoilsRequest) {
				ReadCoilsRequest request = (ReadCoilsRequest) function;

				response = readCoilsRequest(context, request);
			} else if (function instanceof ReadDiscreteInputsRequest) {
				ReadDiscreteInputsRequest request = (ReadDiscreteInputsRequest) function;

				response = readDiscreteInputsRequest(context, request);
			} else if (function instanceof ReadInputRegistersRequest) {
				ReadInputRegistersRequest request = (ReadInputRegistersRequest) function;

				response = readInputRegistersRequest(context, request);
			} else if (function instanceof ReadHoldingRegistersRequest) {
				ReadHoldingRegistersRequest request = (ReadHoldingRegistersRequest) function;

				response = readHoldingRegistersRequest(context, request);
			} else if (function instanceof WriteMultipleRegistersRequest) {
				WriteMultipleRegistersRequest request = (WriteMultipleRegistersRequest) function;

				response = writeMultipleRegistersRequest(context, request);
			} else if (function instanceof WriteMultipleCoilsRequest) {
				WriteMultipleCoilsRequest request = (WriteMultipleCoilsRequest) function;

				response = writeMultipleCoilsRequest(context, request);
			} else {
				throw new ModbusException(ModbusExceptionCode.ILLEGAL_FUNCTION);
			}
		} catch (ModbusException e) {
			response = new ModbusExceptionResponse(
					(short) (function.getFunctionCode() | ModbusConstants.ERROR_OFFSET),
					e.getExceptionCode());
		}

		ModbusHeader header = new ModbusHeader(frame.getHeader()
				.getTransactionIdentifier(), frame.getHeader()
				.getProtocolIdentifier(), response.calculateLength(), frame
				.getHeader().getUnitIdentifier());

		ModbusFrame responseFrame = new ModbusFrame(header, response);

		channel.write(responseFrame);
	}

	protected abstract WriteSingleCoil writeSingleCoil(ModbusRequestContext pContext,
			WriteSingleCoil request) throws ModbusException;

	protected abstract WriteSingleRegister writeSingleRegister(ModbusRequestContext pContext,
			WriteSingleRegister request) throws ModbusException;

	protected abstract ReadCoilsResponse readCoilsRequest(ModbusRequestContext pContext,
			ReadCoilsRequest request) throws ModbusException;

	protected abstract ReadDiscreteInputsResponse readDiscreteInputsRequest(ModbusRequestContext pContext, ReadDiscreteInputsRequest request)
			throws ModbusException;

	protected abstract ReadInputRegistersResponse readInputRegistersRequest(ModbusRequestContext pContext, ReadInputRegistersRequest request)
			throws ModbusException;

	protected abstract ReadHoldingRegistersResponse readHoldingRegistersRequest(ModbusRequestContext pContext, ReadHoldingRegistersRequest request)
			throws ModbusException;

	protected abstract WriteMultipleRegistersResponse writeMultipleRegistersRequest(ModbusRequestContext pContext, WriteMultipleRegistersRequest request)
			throws ModbusException;

	protected abstract WriteMultipleCoilsResponse writeMultipleCoilsRequest(ModbusRequestContext pContext, WriteMultipleCoilsRequest request)
			throws ModbusException;
}
