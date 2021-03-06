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

import de.gandev.modjn.entity.func.ModbusExceptionCode;

/**
 * Manage a modbus exception which can be sent to the modbus client.
 */
public class ModbusException extends Exception {

	/**  */
	private static final long serialVersionUID = 1501303165557579387L;

	/** modbus error. */
	private ModbusExceptionCode error;
	
	/**
	 * Constructor.
	 * 
	 * @param error modbus error
	 */
	public ModbusException(ModbusExceptionCode error) {
		super();
		this.error = error;
	}

	/**
	 * The exception code associated to the exception. Exception code is sent in the modubs frame after the function code.
	 * @return exception code
	 */
	public short getExceptionCode(){
		return error.getCode();
	}
}
