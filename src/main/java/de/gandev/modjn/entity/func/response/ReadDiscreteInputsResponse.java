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
package de.gandev.modjn.entity.func.response;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

import de.gandev.modjn.entity.ModbusFunction;

/**
 * 
 * @author Andreas Gabriel <ag.gandev@googlemail.com>
 */
public class ReadDiscreteInputsResponse extends ModbusFunction {

	private short byteCount;
	private boolean[] inputStatus;

	public ReadDiscreteInputsResponse() {
		super(READ_DISCRETE_INPUTS);
	}

	public ReadDiscreteInputsResponse(boolean[] inputStatus) {
		super(READ_DISCRETE_INPUTS);

		// maximum of 2000 bits
		if (inputStatus.length > 250) {
			throw new IllegalArgumentException();
		}

		this.byteCount = (short) Math.ceil((double)inputStatus.length / 8);
		this.inputStatus = inputStatus;
	}

	public boolean[] getInputStatus() {
		return inputStatus;
	}

	public short getByteCount() {
		return byteCount;
	}

	@Override
	public int calculateLength() {
		return 1 + 1 + byteCount;
	}

	@Override
	public ByteBuf encode() {
		ByteBuf buf = Unpooled.buffer(calculateLength());
		buf.writeByte(getFunctionCode());
		buf.writeByte(byteCount);
		
		for(int i=0;i<byteCount;i++){
			short val =0;
			short mask =0x01;
			
			for (int j = 0; j < 8; j++) {
				
				if(i*8+j >= inputStatus.length )
					break;
				if(inputStatus[i*8+j] ){
					val|=mask;
				}
				mask<<=1;
			}
			buf.writeByte(val);
		}
		return buf;
	}

	@Override
	public void decode(ByteBuf data) {
		byteCount = data.readUnsignedByte();

		// TODO we are reading all the bits from all the given byte.
		// we may add some false values at the end of the boolean array.
		// for example, if we ask for 2 bits, we receive an entire byte, so we decode 8 bits from the response.
		// the 2 first are filled with the actual response and the 6 other bits are filled with false.
		inputStatus = new boolean[byteCount*8];
		
		for (int i = 0; i < byteCount; i++) {
			
			short val = data.readByte();
			short mask =0x01;
			
			for (int j = 0; j < 8; j++) {
				if ((val & mask) == 0) {
					inputStatus[i*8+j] = false;
				} else {
					inputStatus[i*8+j] = true;
				}
				mask<<=1;
			}
		}
	}

	@Override
	public String toString() {
		return "ReadDiscreteInputsResponse{" + "byteCount=" + byteCount
				+ ", coilStatus=" + Arrays.toString(inputStatus) + '}';
	}
}
