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
package de.gandev.modjn;

/**
 *
 * @author ag
 */
public class ModbusConstants {

    public static final int ERROR_OFFSET = 0x80;

    public static final int SYNC_RESPONSE_TIMEOUT = 2000; //milliseconds
    public static final int TRANSACTION_IDENTIFIER_MAX = 100; //affects memory usage of library

    public static final int ADU_MAX_LENGTH = 260;
    public static final int MBAP_LENGTH = 7;

    public static final int DEFAULT_MODBUS_PORT = 502;
    public static final short DEFAULT_PROTOCOL_IDENTIFIER = 0;
    public static final short DEFAULT_UNIT_IDENTIFIER = 255;
}
