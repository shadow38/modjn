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
package de.gandev.modjn.entity;

import io.netty.buffer.ByteBuf;
import de.gandev.modjn.ModbusConstants;

/**
 *
 * @author ares
 */
public abstract class ModbusFunction {

    public static final short READ_COILS = 0x01;
    public static final short READ_DISCRETE_INPUTS = 0x02;
    public static final short READ_HOLDING_REGISTERS = 0x03;
    public static final short READ_INPUT_REGISTERS = 0x04;
    public static final short WRITE_SINGLE_COIL = 0x05;
    public static final short WRITE_SINGLE_REGISTER = 0x06;
    public static final short WRITE_MULTIPLE_COILS = 0x0F;
    public static final short WRITE_MULTIPLE_REGISTERS = 0x10;
    public static final short READ_FILE_RECORD = 0x14;
    public static final short WRITE_FILE_RECORD = 0x15;
    public static final short MASK_WRITE_REGISTER = 0x16;
    public static final short READ_WRITE_MULTIPLE_REGISTERS = 0x17;
    public static final short READ_FIFO_QUEUE = 0x18;
    public static final short ENCAPSULATED_INTERFACE_TRANSPORT = 0x2B;

    private final short functionCode;

    public ModbusFunction(short functionCode) {
        this.functionCode = functionCode;
    }

    public short getFunctionCode() {
        return functionCode;
    }

    public static boolean isError(short functionCode) {
        return functionCode - ModbusConstants.ERROR_OFFSET >= 0;
    }

    public abstract int calculateLength();

    public abstract ByteBuf encode();

    public abstract void decode(ByteBuf data);
}
