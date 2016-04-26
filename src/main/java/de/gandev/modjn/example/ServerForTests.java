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
package de.gandev.modjn.example;

import de.gandev.modjn.ModbusServer;
import de.gandev.modjn.entity.exception.ConnectionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ares
 */
public class ServerForTests {

    private final ModbusServer modbusServer;

    private ServerForTests() {
        modbusServer = new ModbusServer(30502); //ModbusConstants.MODBUS_DEFAULT_PORT);
        try {
            modbusServer.setup(new ModbusRequestHandlerExample());
        } catch (ConnectionException ex) {
            Logger.getLogger(ServerForTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModbusServer getModbusServer() {
        return modbusServer;
    }

    public static ServerForTests getInstance() {
        return ServerForTestsHolder.INSTANCE;
    }

    private static class ServerForTestsHolder {

        private static final ServerForTests INSTANCE = new ServerForTests();
    }
}
