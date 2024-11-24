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
    void testResetEnergyLogs() {
        EnergyTracker tracker = new EnergyTracker();
        tracker.logEnergyUsage(new SmartHomeSystem()); // Log some usage
        tracker.resetEnergyLogs();
        assertTrue(tracker.getEnergyUsageSummary().isEmpty());
    }
}
