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
package de.gandev.modjn.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.gandev.modjn.ModbusClient;
import de.gandev.modjn.ModbusServer;
import de.gandev.modjn.entity.exception.ConnectionException;
import de.gandev.modjn.entity.exception.ErrorResponseException;
import de.gandev.modjn.entity.exception.NoResponseException;
import de.gandev.modjn.entity.func.response.WriteMultipleCoilsResponse;
import de.gandev.modjn.example.ClientForTests;
import de.gandev.modjn.example.ServerForTests;

/**
 * 
 * @author Andreas Gabriel <ag.gandev@googlemail.com>
 */
public class ModbusWriteMultipleCoilsTest {

	ModbusClient modbusClient;
	ModbusServer modbusServer;

	@Before
	public void setUp() throws Exception {
		modbusServer = ServerForTests.getInstance().getModbusServer();
		modbusClient = ClientForTests.getInstance().getModbusClient();
	}

	@Test
	public void testWriteMultipleCoils() throws NoResponseException,
			ErrorResponseException, ConnectionException {
		int quantityOfCoils = 10;
		boolean[] coils = new boolean[quantityOfCoils];
		coils[0] = true;
		coils[5] = true;
		coils[8] = true;

		WriteMultipleCoilsResponse writeMultipleCoils = modbusClient
				.writeMultipleCoils(12321, quantityOfCoils, coils);

		assertNotNull(writeMultipleCoils);

		System.out.println(writeMultipleCoils);
	}
}
