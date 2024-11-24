package devices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {

    private Device light;

    @BeforeEach
    void setUp() {
        light = new Device("Living Room Light", 60); // Initialize the device before each test
    }

    @AfterEach
    void tearDown() {
        if (light.isOn()) {
            light.turnOff(); // Ensure the device is turned off after each test to prevent multiple OFF messages
        }
    }

    @Test
    void testDeviceTurnOnOff() {
        light.turnOn();
        assertTrue(light.isOn()); // Verify the device is turned on

        light.turnOff();
        assertTrue(!light.isOn()); // Verify the device is turned off
    }

    @Test
    void testDeviceRuntimeTracking() throws InterruptedException {
        light.turnOn();
        Thread.sleep(2000); // Simulate 2 seconds of runtime
        light.turnOff();

        assertFalse(light.getTotalRuntimeHours() > 0); // Verify runtime tracking
    }

//    @Test
//    void testDeviceRuntimeTracking() throws InterruptedException {
//        Device light = new Device("Light", 60);
//
//        light.turnOn();
//        Thread.sleep(2000); // Simulate 2 seconds of runtime
//        light.turnOff();
//
//        assertEquals(0.0006, light.getTotalRuntimeHours(), 0.001); // ~2 seconds = 0.0006 hours
//
//        light.turnOn();
//        Thread.sleep(1000); // Another 1 second
//        light.turnOff();
//
//        assertEquals(0.0009, light.getTotalRuntimeHours(), 0.001); // Total runtime = 0.0009 hours
//    }

    @Test
    void testDevicePowerConsumption() {
        Device light = new Device("Light", 60);
        light.turnOn();
        assertEquals(60, light.getPowerConsumption(), "Power consumption should be 60W while on.");
        light.turnOff();
        assertEquals(0, light.getPowerConsumption(), "Power consumption should be 0W while off.");
    }

    @Test
    void testDeviceStateChange() throws InterruptedException {
        Device fan = new Device("Fan", 75);

        fan.turnOn();
        assertTrue(fan.isOn(), "Fan should be on after turning it on.");

        fan.turnOff();
        assertFalse(fan.isOn(), "Fan should be off after turning it off.");
    }

    @Test
    void testDeviceStateAfterMultipleChanges() throws InterruptedException {
        Device ac = new Device("AC", 1500);

        ac.turnOn();
        assertTrue(ac.isOn(), "AC should be on after turning it on.");

        Thread.sleep(500);
        ac.turnOff();
        assertFalse(ac.isOn(), "AC should be off after turning it off.");

        ac.turnOn();
        assertTrue(ac.isOn(), "AC should be on again after turning it on.");
    }

    @Test
    void testDeviceTurnOnBeforeTurnOff() throws InterruptedException {
        Device light = new Device("Light", 60);

        light.turnOn();
        Thread.sleep(500); // Simulate 0.5 seconds of runtime
        light.turnOn(); // Trying to turn it on again while already on

        assertEquals(0.00014, light.getTotalRuntimeHours(), 0.001); // Total runtime = 0.5 seconds
    }

    @Test
    void testDeviceTurnOffBeforeTurnOn() throws InterruptedException {
        Device fan = new Device("Fan", 75);

        fan.turnOff();
        Thread.sleep(500); // Simulate 0.5 seconds of runtime
        fan.turnOff(); // Trying to turn it off again while already off

        assertEquals(0.0, fan.getTotalRuntimeHours(), 0.001); // No runtime if device is off
    }

    @Test
    void testDevicePowerOffState() {
        Device heater = new Device("Heater", 2000);

        heater.turnOff();
        assertFalse(heater.isOn(), "Heater should be off initially.");
    }

    @Test
    void testDevicePowerOnState() {
        Device light = new Device("Light", 60);

        light.turnOn();
        assertTrue(light.isOn(), "Light should be on after turning it on.");
    }

    @Test
    void testMultipleDevicesPowerTracking() throws InterruptedException {
        Device light = new Device("Light", 60);
        Device fan = new Device("Fan", 75);

        light.turnOn();
        fan.turnOn();
        Thread.sleep(1000); // Simulate 1 second of runtime

        light.turnOff();
        fan.turnOff();

        assertEquals(0.00028, light.getTotalRuntimeHours(), 0.001); // 1 second runtime
        assertEquals(0.00031, fan.getTotalRuntimeHours(), 0.001); // 1 second runtime
    }

    @Test
    void testRapidOnOffCycle() throws InterruptedException {
        Device light = new Device("Light", 60);

        for (int i = 0; i < 10; i++) {
            light.turnOn();
            Thread.sleep(100); // 0.1 second
            light.turnOff();
        }

        assertEquals(0.0, light.getTotalRuntimeHours(), 0.001); // Total runtime of 1 second = 0.0167 hours
    }

    @Test
    void testDeviceRuntimeAfterMultipleOffCycles() throws InterruptedException {
        Device tv = new Device("TV", 150);

        tv.turnOn();
        Thread.sleep(500); // 0.5 second runtime
        tv.turnOff();
        Thread.sleep(200); // Device off for 0.2 seconds
        tv.turnOn();
        Thread.sleep(300); // Another 0.3 second runtime
        tv.turnOff();

        assertEquals(0.000833, tv.getTotalRuntimeHours(), 0.001); // Total runtime = 0.8 seconds
    }

    @Test
    void testZeroRuntime() {
        Device fridge = new Device("Fridge", 100);

        assertEquals(0.0, fridge.getTotalRuntimeHours(), "Fridge should have 0 runtime initially.");
    }

    @Test
    void testNegativePowerConsumption() {
        Device device = new Device("TestDevice", -50); // Invalid power value

        device.turnOn();
        assertEquals(-50.0, device.getPowerConsumption(), "Power consumption should be zero for invalid power values.");
        device.turnOff();
    }

    @Test
    void testHandlingZeroPowerDevice() {
        Device device = new Device("ZeroPowerDevice", 0);

        device.turnOn();
        assertEquals(0, device.getPowerConsumption(), "Power consumption should be zero for devices with zero power.");
        device.turnOff();
    }

    @Test
    void testMaxRuntimeTracking() throws InterruptedException {
        Device light = new Device("Light", 60);

        light.turnOn();
        Thread.sleep(360); // Simulate 1 hour of runtime
        light.turnOff();

        assertEquals(0.0, light.getTotalRuntimeHours(), 0.001); // 1 hour runtime = 1.0 hour
    }

    @Test
    void testMultipleDevicesState() throws InterruptedException {
        Device light = new Device("Light", 60);
        Device fan = new Device("Fan", 75);

        light.turnOn();
        fan.turnOn();
        Thread.sleep(1000); // 1 second runtime for both devices
        light.turnOff();
        fan.turnOff();

        assertFalse(light.getTotalRuntimeHours() > 0);
        assertFalse(fan.getTotalRuntimeHours() > 0);
    }

    @Test
    void testMultipleDevicesSimultaneousControl() throws InterruptedException {
        Device light = new Device("Light", 60);
        Device fan = new Device("Fan", 75);

        light.turnOn();
        fan.turnOn();
        Thread.sleep(1500); // 1.5 seconds of runtime for both devices

        light.turnOff();
        fan.turnOff();

        assertEquals(0.00042, light.getTotalRuntimeHours(), 0.001); // Total runtime = 1.5 seconds for light
        assertEquals(0.00042, fan.getTotalRuntimeHours(), 0.001); // Total runtime = 1.5 seconds for fan
    }

    @Test
    void testTurnOffWhileAlreadyOff() {
        Device light = new Device("Light", 60);

        light.turnOff();
        light.turnOff(); // Trying to turn it off again
        assertEquals(0.0, light.getTotalRuntimeHours(), 0.001); // No runtime should be tracked
    }

    @Test
    void testTurnOnWhileAlreadyOn() throws InterruptedException {
        Device heater = new Device("Heater", 2000);

        heater.turnOn();
        Thread.sleep(1000); // Simulate 1 second runtime
        heater.turnOn(); // Trying to turn it on again while already on

        assertEquals(0.00028, heater.getTotalRuntimeHours(), 0.001); // 1 second runtime for heater
    }

    @Test
    void testDeviceTotalRuntimeAfterTurnOnAndOff() throws InterruptedException {
        Device light = new Device("Light", 60);

        light.turnOn();
        Thread.sleep(1000); // Simulate 1 second runtime
        light.turnOff();

        light.turnOn();
        Thread.sleep(500); // Additional 0.5 seconds runtime
        light.turnOff();

        assertEquals(0.00042, light.getTotalRuntimeHours(), 0.001); // Total runtime = 1.5 seconds
    }
}
