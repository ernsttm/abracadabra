package edu.pitt.input;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

public class AdcTests
{

    @Test
    public void AdcTest() throws IOException 
    {
        Adc adc = new Adc(0);
	    for(int i=0;i<10;i++){
            try
            {
                System.out.println(adc.read());
                Thread.sleep(500);
            }
            catch (Exception e)
            {
                fail(e.getMessage());
            }
	    }
    }
}