package edu.pitt.input;

import com.pi4j.io.spi.*;
import com.pi4j.util.Console;

import java.io.IOException;

public class Adc {
	public static SpiDevice spi = null;

	public Adc() throws IOException{

		spi = SpiFactory.getInstance(SpiChannel.CS0,
			SpiDevice.DEFAULT_SPI_SPEED, //1mhz
			SpiDevice.DEFAULT_SPI_MODE); // mode = 0
	}


	public int read(int channel) throws IOException{
		byte data[] = new byte[]{
			(byte)0b00000001,
			(byte)(0b10000000 | ((channel & 7) << 4)),
			(byte)0b00000000,	
	
		};
		byte[] result = spi.write(data);
		int ret = (result[1] << 8 ) & 0b1100000000;
		ret |= result[2] & 0xff;
		return ret;
	}

}