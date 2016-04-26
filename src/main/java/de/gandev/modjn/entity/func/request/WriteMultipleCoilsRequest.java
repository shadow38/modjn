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
package de.gandev.modjn.entity.func.request;

import de.gandev.modjn.entity.func.AbstractFunction;
import io.netty.buffer.ByteBuf;

/**
 * 
 * @author Andreas Gabriel <ag.gandev@googlemail.com>
 */
public class WriteMultipleCoilsRequest extends AbstractFunction {

	// startingAddress = 0x0000 to 0xFFFF
	// quantityOfOutputs = 1 - 1968 (0x07B0)
	private short byteCount;
	private boolean[] outputsValue;

	public WriteMultipleCoilsRequest() {
		super(WRITE_MULTIPLE_COILS);
	}

	public WriteMultipleCoilsRequest(int startingAddress,
			int quantityOfOutputs, boolean[] outputsValue) {
		super(WRITE_MULTIPLE_COILS, startingAddress, quantityOfOutputs);

		// maximum of 1968 bits
		if (outputsValue.length > 246) {
			throw new IllegalArgumentException();
		}

		this.byteCount = (short) outputsValue.length;
		this.outputsValue = outputsValue;
	}

	public short getByteCount() {
		return byteCount;
	}

	public boolean[] getOutputsValue() {
		return outputsValue;
	}

	public int getQuantityOfOutputs() {
		return value;
	}

	public int getStartingAddress() {
		return address;
	}

	@Override
	public int calculateLength() {
		return super.calculateLength() + 1 + byteCount;
	}

	@Override
	public ByteBuf encode() {
		ByteBuf buf = super.encode();

		buf.writeByte(byteCount);
		for (boolean bool : outputsValue) {
			buf.writeByte(bool ? 1 : 0);
		}

		return buf;
	}

	@Override
	public void decode(ByteBuf data) {
		super.decode(data);

		byteCount = data.readUnsignedByte();

		outputsValue = new boolean[byteCount];
		for (int i = 0; i < byteCount; i++) {

			byte b = data.readByte();
			if (b == 0){
				outputsValue[i] = false;
			}
			else{
				outputsValue[i] = true;
			}
		}
	}

	@Override
	public String toString() {
		return "WriteMultipleCoilsRequest{" + "startingAddress=" + address
				+ ", quantityOfOutputs=" + value + ", byteCount=" + byteCount
				+ ", outputsValue=" + outputsValue + '}';
	}
}
