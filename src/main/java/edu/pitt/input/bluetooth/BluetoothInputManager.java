package edu.pitt.input.bluetooth;

import edu.pitt.LockManager;
import edu.pitt.tumbler.pin.PinTumblerMessage;

import java.io.IOException;

/**
 * This class is responsible for configuring the BluetoothInput and then passing the result back to the LockManager.
 */
public class BluetoothInputManager
{
    public BluetoothInputManager(int tumblerId, BluetoothInputConfiguration config, LockManager lockManager) throws IOException
    {
        tumblerId_ = tumblerId;
        System.out.println("Configured Tumbler ID : " + tumblerId_ + " : " + config.getTumbler());
        lockManager_ = lockManager;

        input_ = new BluetoothInput(config.getSerialChannel(), this);
    }

    public void receiveInput(boolean pin)
    {
        PinTumblerMessage message = new PinTumblerMessage(tumblerId_, pin);
        lockManager_.receiveMessage(message);
    }

    private final int tumblerId_;

    private final BluetoothInput input_;
    private final LockManager lockManager_;
}
