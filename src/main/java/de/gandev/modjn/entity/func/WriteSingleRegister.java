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

/**
 *
 * @author ares
 */
public class WriteSingleRegister extends AbstractFunction {

    //registerAddress;
    //registerValue;
    public WriteSingleRegister() {
        super(WRITE_SINGLE_REGISTER);
    }

    public WriteSingleRegister(int outputAddress, short value) {
        super(WRITE_SINGLE_REGISTER, outputAddress, value);
    }

    public int getRegisterAddress() {
        return address;
    }

    public short getRegisterValue() {
        return (short) value;
    }

    @Override
    public String toString() {
        return "WriteSingleInputRegister{" + "registerAddress=" + address + ", registerValue=" + value + '}';
    }
}
