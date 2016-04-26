package de.gandev.modjn.example;

import de.gandev.modjn.ModbusClient;
import de.gandev.modjn.ModbusConstants;
import de.gandev.modjn.entity.datatype.DataType;
import de.gandev.modjn.entity.datatype.DataTypeEncoderDecoderUtils;
import de.gandev.modjn.entity.exception.ConnectionException;
import de.gandev.modjn.entity.exception.ErrorResponseException;
import de.gandev.modjn.entity.exception.NoResponseException;
import de.gandev.modjn.entity.func.response.ReadCoilsResponse;
import de.gandev.modjn.entity.func.response.ReadDiscreteInputsResponse;
import de.gandev.modjn.entity.func.response.ReadInputRegistersResponse;

public class SimpleClientExample {

	public static void main(String[] args) {
		new SimpleClientExample();
	}

	public SimpleClientExample() {
		super();
		
		ModbusClient client = new ModbusClient("localhost",  ModbusConstants.DEFAULT_MODBUS_PORT, (short)2);
		try {
			client.setup();
			
			ReadInputRegistersResponse response = client.readInputRegisters(2, 4);
			short[] values = response.getInputRegisters();
			
			System.out.println(DataTypeEncoderDecoderUtils.shortsToValue(DataType.FOUR_BYTE_FLOAT_SWAPPED, values));
			
			response = client.readInputRegisters(0, 4);
			values = response.getInputRegisters();
			
			System.out.println(DataTypeEncoderDecoderUtils.shortsToValue(DataType.FOUR_BYTE_FLOAT, values));
			
			
		} catch (NoResponseException | ErrorResponseException | ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
