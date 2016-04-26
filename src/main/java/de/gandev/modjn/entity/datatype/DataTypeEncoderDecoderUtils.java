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

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class DataTypeEncoderDecoderUtils {

	private DataTypeEncoderDecoderUtils() {

	}

    /**
     * Converts data from the short array into a java value according the requested datatype
     * @param pDataType data type
     * @param pData shorts to convert
     * @return the converted data
     */
    public static Number shortsToValue(DataType pDataType, short[] pData) {
    	
    	ByteBuffer bb = ByteBuffer.allocate(pData.length * 2);
    	bb.asShortBuffer().put(pData);
    	
    	return bytesToValue(pDataType, bb.array());
    }
	
	
    /**
     * Converts data from the byte array into a java value according the requested datatype
     * @param pDataType data type
     * @param pData bytes to convert
     * @return the converted data
     */
    public static Number bytesToValue(DataType pDataType, byte[] pData) {

        StringBuilder sb;
        byte[] b9;
        
        switch(pDataType)
        {
        // 2 bytes
        case TWO_BYTE_INT_UNSIGNED:
        	return new Integer(((pData[0] & 0xff) << 8) | (pData[1] & 0xff));
        case TWO_BYTE_INT_SIGNED:
        return new Short((short)(((pData[0] & 0xff) << 8) | (pData[1] & 0xff)));
        case TWO_BYTE_BCD: 
            sb = new StringBuilder();
            for (int i=0; i<2; i++) {
                sb.append(bcdNibbleToInt(pData[i], true));
                sb.append(bcdNibbleToInt(pData[i], false));
            }
            return Short.parseShort(sb.toString());
        
            // 4 bytes
        case FOUR_BYTE_INT_UNSIGNED:
        return new Long(
                ((long)((pData[0] & 0xff)) << 24) | ((long)((pData[1] & 0xff)) << 16) | 
                ((long)((pData[2] & 0xff)) << 8) | ((pData[3] & 0xff)));
        case FOUR_BYTE_INT_SIGNED:
        return new Integer(
                ((pData[0] & 0xff) << 24) | ((pData[1] & 0xff) << 16) | 
                ((pData[2] & 0xff) << 8) | (pData[3] & 0xff));
        case  FOUR_BYTE_INT_UNSIGNED_SWAPPED:
        return new Long(
                ((long)((pData[2] & 0xff)) << 24) | ((long)((pData[3] & 0xff)) << 16) | 
                ((long)((pData[0] & 0xff)) << 8) | ((pData[1] & 0xff)));
        case FOUR_BYTE_INT_SIGNED_SWAPPED:
        return new Integer(
                ((pData[2] & 0xff) << 24) | ((pData[3] & 0xff) << 16) | 
                ((pData[0] & 0xff) << 8) | (pData[1] & 0xff));
        case FOUR_BYTE_FLOAT:
        return Float.intBitsToFloat(
                ((pData[0] & 0xff) << 24) | ((pData[1] & 0xff) << 16) | 
                ((pData[2] & 0xff) << 8) | (pData[3] & 0xff));
        case FOUR_BYTE_FLOAT_SWAPPED:
        return Float.intBitsToFloat(
                ((pData[2] & 0xff) << 24) | ((pData[3] & 0xff) << 16) | 
                ((pData[0] & 0xff) << 8) | (pData[1] & 0xff));
        case FOUR_BYTE_BCD: 
            sb = new StringBuilder();
            for (int i=0; i<4; i++) {
                sb.append(bcdNibbleToInt(pData[i], true));
                sb.append(bcdNibbleToInt(pData[i], false));
            }
            return Integer.parseInt(sb.toString());
            
            // 8 bytes
        case EIGHT_BYTE_INT_UNSIGNED: 
            b9 = new byte[9];
            System.arraycopy(pData, 0, b9, 1, 8);
            return new BigInteger(b9);
        case EIGHT_BYTE_INT_SIGNED:
        return new Long(
                ((long)((pData[0] & 0xff)) << 56) | ((long)((pData[1] & 0xff)) << 48) | 
                ((long)((pData[2] & 0xff)) << 40) | ((long)((pData[3] & 0xff)) << 32) |
                ((long)((pData[4] & 0xff)) << 24) | ((long)((pData[5] & 0xff)) << 16) | 
                ((long)((pData[6] & 0xff)) << 8) | ((pData[7] & 0xff)));
        case EIGHT_BYTE_INT_UNSIGNED_SWAPPED:
            b9 = new byte[9];
            b9[1] = pData[6];
            b9[2] = pData[7];
            b9[3] = pData[4];
            b9[4] = pData[5];
            b9[5] = pData[2];
            b9[6] = pData[3];
            b9[7] = pData[0];
            b9[8] = pData[1];
            return new BigInteger(b9);
            
        case EIGHT_BYTE_INT_SIGNED_SWAPPED:
        return new Long(
                ((long)((pData[6] & 0xff)) << 56) | ((long)((pData[7] & 0xff)) << 48) | 
                ((long)((pData[4] & 0xff)) << 40) | ((long)((pData[5] & 0xff)) << 32) |
                ((long)((pData[2] & 0xff)) << 24) | ((long)((pData[3] & 0xff)) << 16) | 
                ((long)((pData[0] & 0xff)) << 8) | ((pData[1] & 0xff)));
        case EIGHT_BYTE_FLOAT:
        return Double.longBitsToDouble(
                ((long)((pData[0] & 0xff)) << 56) | ((long)((pData[1] & 0xff)) << 48) | 
                ((long)((pData[2] & 0xff)) << 40) | ((long)((pData[3] & 0xff)) << 32) | 
                ((long)((pData[4] & 0xff)) << 24) | ((long)((pData[5] & 0xff)) << 16) | 
                ((long)((pData[6] & 0xff)) << 8) | ((pData[7] & 0xff)));
        case EIGHT_BYTE_FLOAT_SWAPPED:
        return Double.longBitsToDouble(
                ((long)((pData[6] & 0xff)) << 56) | ((long)((pData[7] & 0xff)) << 48) | 
                ((long)((pData[4] & 0xff)) << 40) | ((long)((pData[5] & 0xff)) << 32) | 
                ((long)((pData[2] & 0xff)) << 24) | ((long)((pData[3] & 0xff)) << 16) | 
                ((long)((pData[0] & 0xff)) << 8) | ((pData[1] & 0xff)));
        
        default:
        throw new RuntimeException("Unsupported data type: "+ pDataType);
        }      
    }
    
    private static int bcdNibbleToInt(byte b, boolean high) {
        int n;
        if (high)
            n = (b >> 4) & 0xf;
        else
            n = b & 0xf;
        if (n > 9)
            n = 0;
        return n;
    }
	
	
    /**
     * Convert a java value to its modbus representation according to its datatype. 1 short = content of 1 word.
     * @param pDataType modbus datatype representation
     * @param pValue value to convert
     * @return content of the registers
     */
	public static short[] valueToShorts(DataType pDataType, Number pValue) {

		long l;
		int i;
		short s;

		switch (pDataType) {
		
		// 2 bytes
		case TWO_BYTE_INT_UNSIGNED:
		case TWO_BYTE_INT_SIGNED:
			return new short[] { pValue.shortValue() };
		case TWO_BYTE_BCD:
			s = pValue.shortValue();
			return new short[] { (short) ((((s / 1000) % 10) << 12)
					| (((s / 100) % 10) << 8) | (((s / 10) % 10) << 4) | (s % 10)) };

			// 4 bytes
		case FOUR_BYTE_INT_UNSIGNED:
		case FOUR_BYTE_INT_SIGNED:
			i = pValue.intValue();
			return new short[] { (short) (i >> 16), (short) i };
		case FOUR_BYTE_INT_UNSIGNED_SWAPPED:
		case FOUR_BYTE_INT_SIGNED_SWAPPED:
			i = pValue.intValue();
			return new short[] { (short) i, (short) (i >> 16) };
		case FOUR_BYTE_FLOAT:
			i = Float.floatToIntBits(pValue.floatValue());
			return new short[] { (short) (i >> 16), (short) i };
		case FOUR_BYTE_FLOAT_SWAPPED:
			i = Float.floatToIntBits(pValue.floatValue());
			return new short[] { (short) i, (short) (i >> 16) };
		case FOUR_BYTE_BCD:
			i = pValue.intValue();
			return new short[] {
					(short) ((((i / 10000000) % 10) << 12)
							| (((i / 1000000) % 10) << 8)
							| (((i / 100000) % 10) << 4) | ((i / 10000) % 10)),
					(short) ((((i / 1000) % 10) << 12)
							| (((i / 100) % 10) << 8) | (((i / 10) % 10) << 4) | (i % 10)) };

			// 8 bytes
		case EIGHT_BYTE_INT_UNSIGNED:
		case EIGHT_BYTE_INT_SIGNED:
			l = Float.floatToIntBits(pValue.floatValue());
			return new short[] { (short) (l >> 48), (short) (l >> 32),
					(short) (l >> 16), (short) l };
		case EIGHT_BYTE_INT_UNSIGNED_SWAPPED:
		case EIGHT_BYTE_INT_SIGNED_SWAPPED:
			l = Float.floatToIntBits(pValue.floatValue());
			return new short[] { (short) l, (short) (l >> 16),
					(short) (l >> 32), (short) (l >> 48) };
		case EIGHT_BYTE_FLOAT:
			l = Double.doubleToLongBits(pValue.doubleValue());
			return new short[] { (short) (l >> 48), (short) (l >> 32),
					(short) (l >> 16), (short) l };
		case EIGHT_BYTE_FLOAT_SWAPPED:
			l = Double.doubleToLongBits(pValue.doubleValue());
			return new short[] { (short) l, (short) (l >> 16),
					(short) (l >> 32), (short) (l >> 48) };
		default:
			throw new RuntimeException("Unsupported data type: " + pDataType);
		}
	}

}
