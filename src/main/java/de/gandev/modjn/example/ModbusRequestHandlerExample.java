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
package de.gandev.modjn.example;

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
import de.gandev.modjn.handler.ModbusRequestContext;
import de.gandev.modjn.handler.ModbusRequestHandler;

/**
 * 
 * @author ares
 */
public class ModbusRequestHandlerExample extends ModbusRequestHandler {

	@Override
	protected WriteSingleCoil writeSingleCoil(ModbusRequestContext pContext,
			WriteSingleCoil request) {
		return request;
	}

	@Override
	protected WriteSingleRegister writeSingleRegister(
			ModbusRequestContext pContext, WriteSingleRegister request) {
		return request;
	}

	@Override
	protected ReadCoilsResponse readCoilsRequest(ModbusRequestContext pContext,
			ReadCoilsRequest request) {
		boolean[] coils = new boolean[request.getQuantityOfCoils()];
		coils[0] = true;
		coils[5] = true;
		coils[8] = true;

		return new ReadCoilsResponse(coils);
	}

	@Override
	protected ReadDiscreteInputsResponse readDiscreteInputsRequest(
			ModbusRequestContext pContext, ReadDiscreteInputsRequest request) {
		boolean[] coils = new boolean[request.getQuantityOfCoils()];
		coils[0] = true;
		coils[5] = true;
		coils[8] = true;

		return new ReadDiscreteInputsResponse(coils);
	}

	@Override
	protected ReadInputRegistersResponse readInputRegistersRequest(
			ModbusRequestContext pContext, ReadInputRegistersRequest request) {
		short[] registers = new short[request.getQuantityOfInputRegisters()];
		registers[0] = (short) 0xFFFF;
		registers[1] = (short) 0xF0F0;
		registers[2] = (short) 0x0F0F;

		return new ReadInputRegistersResponse(registers);
	}

	@Override
	protected ReadHoldingRegistersResponse readHoldingRegistersRequest(
			ModbusRequestContext pContext, ReadHoldingRegistersRequest request) {
		short[] registers = new short[request.getQuantityOfInputRegisters()];
		registers[0] = (short) 0xFFFF;
		registers[1] = (short) 0xF0F0;
		registers[2] = (short) 0x0F0F;

		return new ReadHoldingRegistersResponse(registers);
	}

	@Override
	protected WriteMultipleRegistersResponse writeMultipleRegistersRequest(
			ModbusRequestContext pContext, WriteMultipleRegistersRequest request) {
		return new WriteMultipleRegistersResponse(request.getStartingAddress(),
				request.getQuantityOfRegisters());
	}

	@Override
	protected WriteMultipleCoilsResponse writeMultipleCoilsRequest(
			ModbusRequestContext pContext, WriteMultipleCoilsRequest request) {
		return new WriteMultipleCoilsResponse(request.getStartingAddress(),
				request.getQuantityOfOutputs());
	}

}
