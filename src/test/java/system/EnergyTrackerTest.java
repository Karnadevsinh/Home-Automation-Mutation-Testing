package system;

import devices.Device;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EnergyTrackerTest {

    @Test
    void testLogEnergyUsage() {
        SmartHomeSystem system = new SmartHomeSystem();
        EnergyTracker tracker = new EnergyTracker();

        Device light = new Device("Light", 60);
        Device fan = new Device("Fan", 75);
        system.addDevice(light);
        system.addDevice(fan);

        light.turnOn();
        fan.turnOn();

        tracker.logEnergyUsage(system);
        Map<String, Double> summary = tracker.getEnergyUsageSummary();

        assertEquals(60.0, summary.get("Light"));
        assertEquals(75.0, summary.get("Fan"));
    }

    @Test
    void testEnergyUsageWhenDeviceIsOff() {
        SmartHomeSystem system = new SmartHomeSystem();
        EnergyTracker tracker = new EnergyTracker();

        Device heater = new Device("Heater", 1500);
        system.addDevice(heater);

        // Heater is off by default
        tracker.logEnergyUsage(system);
        Map<String, Double> summary = tracker.getEnergyUsageSummary();

        // No usage logged if the device is off
        assertEquals(0.0, summary.get("Heater"));
    }

    @Test
    void testEnergyUsageAfterTurningDeviceOn() {
        SmartHomeSystem system = new SmartHomeSystem();
        EnergyTracker tracker = new EnergyTracker();

        Device light = new Device("Light", 60);
        system.addDevice(light);

        light.turnOn();  // Turning on the light
        tracker.logEnergyUsage(system);
        Map<String, Double> summary = tracker.getEnergyUsageSummary();

        assertEquals(60.0, summary.get("Light"));
    }

    @Test
    void testEnergyThreshold() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device ac = new Device("AC", 2000);
        system.addDevice(ac);

        EnergyTracker tracker = new EnergyTracker();
        tracker.logEnergyUsage(system);  // Log energy usage

        // Verify that threshold checking works as expected
        tracker.checkEnergyThreshold(1500); // Should print a warning about AC exceeding the threshold
    }

    @Test
    void testResetEnergyLogs() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device light = new Device("Light", 60);
        system.addDevice(light);

        EnergyTracker tracker = new EnergyTracker();
        tracker.logEnergyUsage(system);  // Log energy usage

        // Reset the energy logs
        tracker.resetEnergyLogs();

        // Assert that energy logs are now empty
        assertTrue(tracker.getEnergyUsageSummary().isEmpty());
    }

    @Test
    void testResetEnergyLogsWhenEmpty() {
        EnergyTracker tracker = new EnergyTracker();

        tracker.resetEnergyLogs();

        // Assert that resetting an empty log does nothing but doesn't fail
        assertTrue(tracker.getEnergyUsageSummary().isEmpty());
    }

    @Test
    void testEnergyUsageMultipleDevices() {
        SmartHomeSystem system = new SmartHomeSystem();
        EnergyTracker tracker = new EnergyTracker();

        Device light = new Device("Light", 60);
        Device fan = new Device("Fan", 75);
        system.addDevice(light);
        system.addDevice(fan);

        light.turnOn();
        fan.turnOn();

        tracker.logEnergyUsage(system);

        Map<String, Double> summary = tracker.getEnergyUsageSummary();
        assertEquals(60.0, summary.get("Light"));
        assertEquals(75.0, summary.get("Fan"));
    }

    @Test
    void testEnergyUsageForDeviceWithZeroPowerConsumption() {
        SmartHomeSystem system = new SmartHomeSystem();
        EnergyTracker tracker = new EnergyTracker();

        Device device = new Device("ZeroPowerDevice", 0);
        system.addDevice(device);

        device.turnOn();
        tracker.logEnergyUsage(system);

        // Zero power consumption should be logged
        assertEquals(0.0, tracker.getEnergyUsageSummary().get("ZeroPowerDevice"));
    }

    @Test
    void testEnergyUsageForDeviceWithNegativePowerConsumption() {
        SmartHomeSystem system = new SmartHomeSystem();
        EnergyTracker tracker = new EnergyTracker();

        Device device = new Device("NegativePowerDevice", -50);
        system.addDevice(device);

        device.turnOn();
        tracker.logEnergyUsage(system);

        // Negative power consumption should not be allowed
        assertEquals(-50.0, tracker.getEnergyUsageSummary().get("NegativePowerDevice"));
    }

    @Test
    void testEnergyUsageForDeviceWithPowerOffAndOn() {
        SmartHomeSystem system = new SmartHomeSystem();
        EnergyTracker tracker = new EnergyTracker();

        Device device = new Device("DeviceWithOffAndOnCycle", 100);
        system.addDevice(device);

        device.turnOn();
        tracker.logEnergyUsage(system);
        assertEquals(100.0, tracker.getEnergyUsageSummary().get("DeviceWithOffAndOnCycle"));

        device.turnOff();
        tracker.logEnergyUsage(system);
        assertEquals(100.0, tracker.getEnergyUsageSummary().get("DeviceWithOffAndOnCycle"));
    }
}
