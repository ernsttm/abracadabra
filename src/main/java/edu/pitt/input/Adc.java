package edu.pitt.input;

import com.pi4j.io.spi.*;
import com.pi4j.util.Console;

import java.io.IOException;

public class Adc {
	public static SpiDevice spi = null;
	private final int channelNumber_;

	public Adc(int channelNumber) throws IOException{
		channelNumber_ = channelNumber;

		spi = SpiFactory.getInstance(SpiChannel.getByNumber(0),
			SpiDevice.DEFAULT_SPI_SPEED, //1mhz
			SpiDevice.DEFAULT_SPI_MODE); // mode = 0
	}


	public int read() throws IOException{
		byte data[] = new byte[]{
			(byte)0b00000001,
			(byte)(0b10000000 | ((channelNumber_ & 7) << 4)),
			(byte)0b00000000,	
	
		};
		byte[] result = spi.write(data);
		int ret = (result[1] << 8 ) & 0b1100000000;
		ret |= result[2] & 0xff;
		return ret;
	}

}