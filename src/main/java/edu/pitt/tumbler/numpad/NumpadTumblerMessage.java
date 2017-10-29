package edu.pitt.tumbler.numpad;

import com.google.gson.annotations.SerializedName;
import edu.pitt.tumbler.TumblerMessage;

/**
 * A standard message to send/receive Number Pad tumbler updates.
 */
public class NumpadTumblerMessage extends TumblerMessage
{
    /**
     * @return the id of the key that was pressed.
     */
    public int getKeyId()
    {
        return keyId_;
    }

    @SerializedName("keyId")
    private int keyId_;
}
