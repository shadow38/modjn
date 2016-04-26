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
package de.gandev.modjn.entity.datatype;

/**
 * List all the datatype which are serializable 
 * @author shadow38
 *
 */
public enum DataType {

    TWO_BYTE_INT_UNSIGNED(1),
    TWO_BYTE_INT_SIGNED(1),
    TWO_BYTE_BCD(1),

    FOUR_BYTE_INT_UNSIGNED(2),
    FOUR_BYTE_INT_SIGNED(2),
    FOUR_BYTE_INT_UNSIGNED_SWAPPED(2),
    FOUR_BYTE_INT_SIGNED_SWAPPED(2),
    FOUR_BYTE_FLOAT(2),
    FOUR_BYTE_FLOAT_SWAPPED(2),
    FOUR_BYTE_BCD(2),

    EIGHT_BYTE_INT_UNSIGNED(4),
    EIGHT_BYTE_INT_SIGNED(4),
    EIGHT_BYTE_INT_UNSIGNED_SWAPPED(4),
    EIGHT_BYTE_INT_SIGNED_SWAPPED(4),
    EIGHT_BYTE_FLOAT(4),
    EIGHT_BYTE_FLOAT_SWAPPED(4);

    private int registerCount;
    
    private DataType(int pRegisterCount) {
		registerCount = pRegisterCount;
	}

    public int getRegisterCount() {
		return registerCount;
	}
}
