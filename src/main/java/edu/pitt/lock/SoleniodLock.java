package edu.pitt.lock;

import com.pi4j.io.gpio.*;

/**
 * An implementation of the lock interface, which physically manages the soleniod, using GPIO pins.
 */ 
 public class SoleniodLock implements Lock
 {
	 final GpioPinDigitalOutput lockPin;

	 public SoleniodLock(int pinNumber)
	 {
		GpioController gpio = GpioFactory.getInstance();
		lockPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinNumber), "Soleniod", PinState.LOW);
		lockPin.setShutdownOptions(true, PinState.LOW);
    lockPin.low();
	 }
	 
	 @Override
	 public void lock()
	 {
		lockPin.low();
		
		// Add a new task to relock the lock 
		Thread thread = new Thread(new Runnable
		{
			@Override
			public void run()
			{
				Thread.sleep(5000);
				unlock();
			}
		});
		
		thread.start();
	 }
	 
	 @Override
	 public void unlock()
	 {
		lockPin.high();
	 }
 }
