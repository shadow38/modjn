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

import de.gandev.modjn.entity.func.ModbusExceptionResponse;

/**
 *
 * @author Andreas Gabriel <ag.gandev@googlemail.com>
 */
public class ErrorResponseException extends Exception {

	private static final long serialVersionUID = -2127215680991545040L;
	
	private short functionCode;
	private short exceptionCode;

    public ErrorResponseException(ModbusExceptionResponse function) {
        super(function.toString());
        
       functionCode = function.getFunctionCode();
       exceptionCode = function.getExceptionCode();
    }
    
    public short getFunctionCode() {
		return functionCode;
	}
    
    public int getExceptionCode() {
		return exceptionCode;
	}
}
