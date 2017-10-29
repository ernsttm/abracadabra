package edu.pitt.lock;

import com.pi4j.io.gpio.*;

/**
 * An implementation of the lock interface, which physically manages the soleniod, using GPIO pins.
 */ 
 public class SoleniodLock implements Lock
 {
	 final GpioPinDigitalOutput lockPin;
	 public SoleniodLock()
	 {
		GpioController gpio = GpioFactory.getInstance();
		lockPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Soleniod", PinState.LOW);
		lockPin.setShutdownOptions(true, PinState.LOW)
	 }
	 
	 @Override
	 public void lock()
	 {
		 lockPin.low();
	 }
	 
	 @Override
	 public void unlock()
	 {
		 lockPin.high();
	 }
 }