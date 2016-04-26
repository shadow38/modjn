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

import io.netty.buffer.ByteBuf;

/**
 *
 * @author ares
 */
public class WriteSingleCoil extends AbstractFunction {

    //outputAddress;
    //outputValue;
    private boolean state;

    public WriteSingleCoil() {
        super(WRITE_SINGLE_COIL);
    }

    public WriteSingleCoil(int outputAddress, boolean state) {
        super(WRITE_SINGLE_COIL, outputAddress, state ? 0xFF00 : 0x0000);

        this.state = state;
    }

    public int getOutputAddress() {
        return address;
    }

    @Override
    public void decode(ByteBuf data) {
        super.decode(data);

        state = value == 0xFF00;
    }

    public boolean isState() {
        return state;
    }

    @Override
    public String toString() {
        return "WriteSingleCoil{" + "outputAddress=" + address + ", state=" + state + '}';
    }
}
