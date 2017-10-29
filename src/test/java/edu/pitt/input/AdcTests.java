package edu.pitt.input;

import com.pi4j.io.spi.*;
import com.pi4j.util.Console;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.IOException;

public class AdcTests
{

    @Test
    public void AdcTest() throws IOException 
    {

	Adc adc = new Adc();
	for(int i=0;i<10;i++){
	    try
            {
                System.out.println(adc.read(0));
		Thread.sleep(500);
            }
            catch (Exception e)
            {
                fail(e.getMessage());
            }
	}
	
	

    }
}