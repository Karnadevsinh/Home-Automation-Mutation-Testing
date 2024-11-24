package system;

import devices.Device;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmartHomeSystemTest {

    @Test
    void testAddDevice() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device light = new Device("Living Room Light", 60);
        system.addDevice(light);

        // Assert device is added
        assertTrue(system.getDevices().contains(light));
    }

    @Test
    public void testRemoveDevicePreservesOtherDevices() {
        // Arrange
        SmartHomeSystem system = new SmartHomeSystem();
        Device device1 = new Device("Light1", 100);
        Device device2 = new Device("Light2", 100);
        Device device3 = new Device("Light3", 100);
        system.addDevice(device1);
        system.addDevice(device2);
        system.addDevice(device3);

        // Act
        system.removeDevice("Light2");

        // Assert
        List<Device> remainingDevices = system.getDevices();
        assertEquals(2, remainingDevices.size());
        assertTrue(remainingDevices.stream().anyMatch(d -> d.getName().equals("Light1")));
        assertTrue(remainingDevices.stream().anyMatch(d -> d.getName().equals("Light3")));
        assertFalse(remainingDevices.stream().anyMatch(d -> d.getName().equals("Light2")));
    }



    @Test
    void testControlDevice() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device light = new Device("Living Room Light", 60);
        system.addDevice(light);

        // Turn on the device and verify status
        system.controlDevice("Living Room Light", true);
        assertTrue(light.isOn());

        // Turn off the device and verify status
        system.controlDevice("Living Room Light", false);
        assertFalse(light.isOn());
    }

    @Test
    void testPowerUsage() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device heater = new Device("Heater", 2000);
        heater.turnOn();
        system.addDevice(heater);

        // Assert total power usage is correct
        assertEquals(2000, system.calculateTotalPowerUsage());
    }

    @Test
    void testPowerUsageWithMultipleDevices() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device light = new Device("Light", 60);
        Device fan = new Device("Fan", 75);

        light.turnOn();
        fan.turnOn();
        system.addDevice(light);
        system.addDevice(fan);

        // Assert total power usage is correct
        assertEquals(135, system.calculateTotalPowerUsage()); // 60 + 75 = 135
    }
}