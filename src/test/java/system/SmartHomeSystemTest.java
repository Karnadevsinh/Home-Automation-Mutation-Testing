package system;

import devices.Device;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartHomeSystemTest {
    @Test
    void testAddDevice() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device light = new Device("Light", 60);
        system.addDevice(light);
        assertTrue(system.getDevices().contains(light));
    }

    @Test
    void testRemoveDevice() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device fan = new Device("Fan", 75);
        system.addDevice(fan);
        system.removeDevice("Fan");
        assertFalse(system.getDevices().contains(fan));
    }

    @Test
    void testFindDeviceByName() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device heater = new Device("Heater", 1500);
        system.addDevice(heater);
        assertTrue(system.findDeviceByName("Heater").isPresent());
    }

    @Test
    void testControlAllDevices() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device tv = new Device("TV", 100);
        Device ac = new Device("AC", 2000);
        system.addDevice(tv);
        system.addDevice(ac);

        system.controlAllDevices(true);
        assertTrue(tv.isOn());
        assertTrue(ac.isOn());
    }
}
