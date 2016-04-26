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
package de.gandev.modjn.entity.exception;

/**
 * Manage all the supported modbus error.
 */
public enum ModbusExceptionCode {

	ILLEGAL_FUNCTION((short) 0x01), 
	ILLEGAL_DATA_ADDRESS((short) 0x02), 
	ILLEGAL_DATA_VALUE((short) 0x03), 
	SLAVE_DEVICE_FAILURE((short) 0x04), 
	ACKNOWLEDGE((short) 0x05), 
	SLAVE_DEVICE_BUSY((short) 0x06), 
	MEMORY_PARITY_ERROR((short) 0x08), 
	GATEWAY_PATH_UNAVAILABLE((short) 0x0A), 
	GATEWAY_TARGET_DEVICE_FAILED_TO_RESPOND((short) 0x0B);

	/** modbus frame code. */
	private short code;

	/** Constructor.
	 * 
	 * @param code modbus frame error code.
	 */
	private ModbusExceptionCode(short code) {
		this.code = code;
	}

	/**
	 * Get the modbus frame error code.
	 * @return modbus error code
	 */
	public short getCode() {
		return code;
	}

}
