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
public class Util {

	public static String getBinaryString(short byteCount, boolean[] coilStatus) {
		StringBuilder bitString = new StringBuilder();
		int bitCount = 0;
		for (int i = byteCount * 8 - 1; i >= 0; i--) {
			boolean state = coilStatus[i];
			bitString.append(state ? '1' : '0');

			bitCount++;
			if (bitCount == 8 && i > 0) {
				bitCount = 0;
				bitString.append("#");
			}
		}
		return bitString.toString();
	}
}
