package edu.pitt.input.bluetooth;

import com.pi4j.io.serial.*;

import java.io.IOException;

/**
 * A class responsible for monitoring a Bluetooth serial input and forwarding recieved messages to the manager.
 */
public class BluetoothInput
{
    public BluetoothInput(String channelName, BluetoothInputManager manager) throws IOException
    {
        manager_ = manager;

        serial_ = SerialFactory.createInstance();
        serial_.addListener(new BluetoothDataEventListener());

        SerialConfig config = new SerialConfig();
        config.device(channelName);

        serial_.open(config);
    }

    private class BluetoothDataEventListener implements SerialDataEventListener
    {
        @Override
        public void dataReceived(SerialDataEvent event)
        {
            boolean pin = false;
            try
            {
                String data = event.getAsciiString();
                if ("1".equals(data))
                {
                    pin = true;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            manager_.receiveInput(pin);
        }
    }

    private final Serial serial_;
    private final BluetoothInputManager manager_;
}
