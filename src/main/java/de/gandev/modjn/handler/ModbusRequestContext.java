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
package de.gandev.modjn.handler;

import java.net.InetSocketAddress;

public final class ModbusRequestContext {

	private InetSocketAddress remoteAddress;
	private int slaveId;
	
	public ModbusRequestContext() {
		super();
	}
	
	public void setRemoteAddress(InetSocketAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	
	public InetSocketAddress getRemoteAddress() {
		return remoteAddress;
	}
	
	public int getSlaveId() {
		return slaveId;
	}
	
	public void setSlaveId(int pSlaveId) {
		this.slaveId = pSlaveId;
	}
	
}
