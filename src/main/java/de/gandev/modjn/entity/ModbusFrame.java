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
 * @author ag
 */
public class ModbusFrame {

    private final ModbusHeader header;
    private final ModbusFunction function;

    public ModbusFrame(ModbusHeader header, ModbusFunction function) {
        this.header = header;
        this.function = function;
    }

    public ModbusHeader getHeader() {
        return header;
    }

    public ModbusFunction getFunction() {
        return function;
    }

    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeBytes(header.encode());
        buf.writeBytes(function.encode());

        return buf;
    }

    @Override
    public String toString() {
        return "ModbusFrame{" + "header=" + header + ", function=" + function + '}';
    }
}
