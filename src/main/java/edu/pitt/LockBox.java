package edu.pitt;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import edu.pitt.input.InputConfiguration;
import edu.pitt.input.NumpadInputConfiguration;
import edu.pitt.input.NumpadInputManager;
import edu.pitt.lock.Lock;
import edu.pitt.lock.MockLock;
import edu.pitt.lock.SoleniodLock;
import edu.pitt.tumbler.Tumbler;
import edu.pitt.tumbler.TumblerConfiguration;
import edu.pitt.tumbler.numpad.NumpadTumbler;
import edu.pitt.tumbler.numpad.NumpadTumblerConfiguration;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class will serve as the initial starting point of lock box application.  It will be responsible for starting
 * up the various Tumblers, Input Servers, and unlocking Finite State Machine.
 */
public class LockBox
{
    public static void main(String[] args) throws Exception
    {
        if (1 != args.length)
        {
            System.out.print(USAGE);
            System.exit(-1);
        }

        String lockFileName = args[0];
        LockConfiguration lockConfig = readLockFile(lockFileName);
        Lock lock = createLock(lockConfig);
        Map<Integer, Tumbler> mappedTumblers = createTumblers(lockConfig.getTumblerConfigurations());
        LockManager lockManager = new LockManager(mappedTumblers, lock);
        createInputs(lockConfig.getInputConfigurations(), lockManager);
    }

    private static Lock createLock(LockConfiguration configuration) throws Exception
    {
        switch(configuration.getLockType())
        {
            case Mock:
                return new MockLock();
            case Servo:
                return new SoleniodLock(configuration.getLockPin());
            default:
                return new MockLock();
        }
    }

    private static Map<Integer, Tumbler> createTumblers(List<TumblerConfiguration> configurations) throws Exception
    {
        Map<Integer, Tumbler> mappedTumblers = new HashMap<>();
        for (TumblerConfiguration config : configurations)
        {
            switch (config.getTumblerType())
            {
                case NumPad:
                    NumpadTumblerConfiguration numConfig =
                            gson.fromJson(config.getTumblerConfig(), NumpadTumblerConfiguration.class);
                    Tumbler numPadTumbler = new NumpadTumbler(numConfig);
                    mappedTumblers.put(config.getTumblerId(), numPadTumbler);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Tumbler Type: " + config.getTumblerType());
            }
        }

        return mappedTumblers;
    }

    private static void createInputs(List<InputConfiguration> configurations, LockManager manager) throws Exception
    {
        for (InputConfiguration config : configurations)
        {
            switch (config.getInputType())
            {
                case Touch:
                    NumpadInputConfiguration inputConfig =
                            gson.fromJson(config.getInputConfig(), NumpadInputConfiguration.class);
                    NumpadInputManager numpad = new NumpadInputManager(inputConfig, manager);
                default:
                    throw new IllegalArgumentException("Invalid Input Type: " + config.getInputType());
            }
        }
    }

    private static LockConfiguration readLockFile(String lockFileName) throws Exception
    {
        try (JsonReader reader = new JsonReader(new FileReader(lockFileName)))
        {
            return gson.fromJson(reader, LockConfiguration.class);
        }
        catch (Exception e)
        {
            throw new Exception("Failed to read file " + lockFileName + " because : " + e.getMessage());
        }
    }

    private static final Gson gson = new Gson();
    private static String USAGE = "Usage: java LockBox <lockfile.json>";
}
