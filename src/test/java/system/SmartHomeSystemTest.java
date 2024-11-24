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

    @Test
    void testControlAllDevices1() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device light = new Device("Light", 60);
        Device fan = new Device("Fan", 75);
        system.addDevice(light);
        system.addDevice(fan);

        system.controlAllDevices(true);
        assertTrue(light.isOn() && fan.isOn());

        system.controlAllDevices(false);
        assertFalse(light.isOn() || fan.isOn());
    }

    @Test
    void testLogs() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device heater = new Device("Heater", 1500);
        system.addDevice(heater);

        system.controlDevice("Heater", true);
        system.controlDevice("Heater", false);

        assertFalse(system.getLogs().isEmpty());
    }

    @Test
    void testGenerateStatusReport() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device fridge = new Device("Fridge", 300);
        system.addDevice(fridge);
        fridge.turnOn();

        system.generateStatusReport(); // Verify via console
    }

    @Test
    void testResetSystem() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device ac = new Device("AC", 2000);
        system.addDevice(ac);

        system.resetSystem();
        assertTrue(system.getDevices().isEmpty());
    }

    @Test
    void testDeviceNotFound() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device ac = new Device("AC", 2000);
        system.addDevice(ac);

        system.removeDevice("NonExistentDevice");

//        assertFalse(system.getDevices().contains(ac)); // AC should still exist
    }

    @Test
    void testAddMultipleDevices() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device light = new Device("Light", 60);
        Device fan = new Device("Fan", 75);
        system.addDevice(light);
        system.addDevice(fan);

        assertTrue(system.getDevices().contains(light));
        assertTrue(system.getDevices().contains(fan));
    }

    @Test
    void testSystemStateAfterReset() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device light = new Device("Light", 60);
        system.addDevice(light);

        system.resetSystem();

        assertTrue(system.getDevices().isEmpty());
    }

    @Test
    void testRemoveDeviceThatDoesNotExist() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device fan = new Device("Fan", 75);
        system.addDevice(fan);

        system.removeDevice("NonExistentDevice");

        // Check that removing a non-existent device doesn't affect the system
        assertTrue(system.getDevices().contains(fan));
    }
}
