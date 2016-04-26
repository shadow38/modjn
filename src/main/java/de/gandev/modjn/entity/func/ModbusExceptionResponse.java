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
package de.gandev.modjn.entity.func;

import de.gandev.modjn.entity.ModbusFunction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author Andreas Gabriel <ag.gandev@googlemail.com>
 */
public class ModbusExceptionResponse extends ModbusFunction {

    private short exceptionCode;
    private String exceptionMessage;

    public ModbusExceptionResponse(short functionCode) {
        super(functionCode);
    }

    public ModbusExceptionResponse(short functionCode, short exceptionCode) {
        super(functionCode);
        this.exceptionCode = exceptionCode;

    }

    private void setExceptionMessage(short exceptionCode) {
        this.exceptionMessage = ModbusExceptionCode.getByCode(exceptionCode) != null ?ModbusExceptionCode.getByCode(exceptionCode).toString() : "UNDEFINED ERROR";
    }

    public short getExceptionCode() {
        return exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    @Override
    public int calculateLength() {
        return 1 + 1;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeByte(exceptionCode);

        return buf;
    }

    @Override
    public void decode(ByteBuf data) {
        exceptionCode = data.readUnsignedByte();

        setExceptionMessage(exceptionCode);
    }

    @Override
    public String toString() {
        return "ModbusError{" + "exceptionCode=" + exceptionCode + ", exceptionMessage=" + exceptionMessage + '}';
    }
}
