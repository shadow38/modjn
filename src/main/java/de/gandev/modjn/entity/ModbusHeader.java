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
import io.netty.buffer.Unpooled;

/**
 *
 * @author ares
 */
public class ModbusHeader {

    private final int transactionIdentifier;
    private final int protocolIdentifier;
    private final int length;
    private final short unitIdentifier;

    public ModbusHeader(int transactionIdentifier, int protocolIdentifier, int pduLength, short unitIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
        this.protocolIdentifier = protocolIdentifier;
        this.length = pduLength + 1; //+ unit identifier
        this.unitIdentifier = unitIdentifier;
    }

    public int getLength() {
        return length;
    }

    public int getProtocolIdentifier() {
        return protocolIdentifier;
    }

    public int getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public short getUnitIdentifier() {
        return unitIdentifier;
    }

    public static ModbusHeader decode(ByteBuf buffer) {
        return new ModbusHeader(buffer.readUnsignedShort(),
                buffer.readUnsignedShort(),
                buffer.readUnsignedShort(),
                buffer.readUnsignedByte());
    }

    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeShort(transactionIdentifier);
        buf.writeShort(protocolIdentifier);
        buf.writeShort(length);
        buf.writeByte(unitIdentifier);

        return buf;
    }

    @Override
    public String toString() {
        return "ModbusHeader{" + "transactionIdentifier=" + transactionIdentifier + ", protocolIdentifier=" + protocolIdentifier + ", length=" + length + ", unitIdentifier=" + unitIdentifier + '}';
    }
}
