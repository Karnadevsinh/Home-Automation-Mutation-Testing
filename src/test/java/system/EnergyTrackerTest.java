package system;

import devices.Device;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class EnergyTrackerTest {

    @Test
    void testEnergyUsageLogging() {
        // Create the system and energy tracker
        SmartHomeSystem system = new SmartHomeSystem();
        EnergyTracker tracker = new EnergyTracker(system);

        // Add devices
        Device light = new Device("Light", 60);
        Device fan = new Device("Fan", 75);
        system.addDevice(light);
        system.addDevice(fan);

        // Turn on both devices
        light.turnOn();
        fan.turnOn();

        // Capture the output of printEnergyUsage
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Log and print energy usage
        tracker.printEnergyUsage();

        // Reset System.out to original
        System.setOut(originalOut);

        // Validate the output contains expected information
        String output = outputStream.toString();
        assertTrue(output.contains("Energy usage per device:"));
        assertTrue(output.contains("Light: 60.0 watts."));
        assertTrue(output.contains("Fan: 75.0 watts."));

        // Check if the total power usage is correct (light + fan = 135 watts)
        assertEquals(135, system.calculateTotalPowerUsage());
    }

}
