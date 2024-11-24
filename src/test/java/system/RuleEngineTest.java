package system;


import devices.Device;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RuleEngineTest {

    @Test
    void testApplyRule() {
        SmartHomeSystem system = new SmartHomeSystem();
        RuleEngine ruleEngine = new RuleEngine(system);

        Device heater = new Device("Heater", 2000);
        system.addDevice(heater);

        LocalTime now = LocalTime.now();
        ruleEngine.applyRule("Heater", now.minusMinutes(1), now.plusMinutes(1));
        assertTrue(heater.isOn());

        ruleEngine.applyRule("Heater", now.minusMinutes(5), now.minusMinutes(3));
        assertFalse(heater.isOn());
    }

    @Test
    void testApplyRuleWhenDeviceIsOff() {
        SmartHomeSystem system = new SmartHomeSystem();
        RuleEngine ruleEngine = new RuleEngine(system);

        Device fan = new Device("Fan", 75);
        system.addDevice(fan);

        LocalTime now = LocalTime.now();
        ruleEngine.applyRule("Fan", now.minusMinutes(10), now.minusMinutes(5));
        assertFalse(fan.isOn());  // Fan should be off
    }

//    @Test
//    void testRecurringRule() {
//        SmartHomeSystem system = new SmartHomeSystem();
//        Device light = new Device("Living Room Light", 60);
//        system.addDevice(light);
//
//        RuleEngine ruleEngine = new RuleEngine(system);
//        ruleEngine.applyRecurringRule("Living Room Light", LocalTime.of(18, 0), LocalTime.of(23, 0), 1000L);
//
//        // Verify recurring rule by observing console or waiting for period
//    }

    @Test
    void testScheduleDevice() {
        SmartHomeSystem system = new SmartHomeSystem();
        Device fan = new Device("Ceiling Fan", 75);
        system.addDevice(fan);

        RuleEngine ruleEngine = new RuleEngine(system);
        ruleEngine.scheduleTurnOn("Ceiling Fan", LocalTime.of(18, 0));
        ruleEngine.scheduleTurnOff("Ceiling Fan", LocalTime.of(23, 0));

        // Check the console output to verify correct scheduling
    }

    @Test
    void testApplyRuleWithInvalidDevice() {
        SmartHomeSystem system = new SmartHomeSystem();
        RuleEngine ruleEngine = new RuleEngine(system);

        ruleEngine.applyRule("NonExistentDevice", LocalTime.of(18, 0), LocalTime.of(23, 0));

        // Verify no exception is thrown and log message printed
    }

    @Test
    void testApplyMultipleRulesAtOnce() {
        SmartHomeSystem system = new SmartHomeSystem();
        RuleEngine ruleEngine = new RuleEngine(system);

        Device heater = new Device("Heater", 2000);
        Device light = new Device("Light", 60);
        system.addDevice(heater);
        system.addDevice(light);

        ruleEngine.applyRule("Heater", LocalTime.of(6, 0), LocalTime.of(8, 0));
        ruleEngine.applyRule("Light", LocalTime.of(18, 0), LocalTime.of(23, 0));

//        assertTrue(heater.isOn());
        assertFalse(light.isOn());
    }

    @Test
    void testApplyRuleAtExactStartTime() {
        SmartHomeSystem system = new SmartHomeSystem();
        RuleEngine ruleEngine = new RuleEngine(system);

        Device ac = new Device("AC", 1500);
        system.addDevice(ac);

        LocalTime now = LocalTime.now();
        ruleEngine.applyRule("AC", now, now.plusMinutes(1));  // Exact match for "on" time
//        assertTrue(ac.isOn());
    }

    @Test
    void testApplyRuleWithLongTimeInterval() {
        SmartHomeSystem system = new SmartHomeSystem();
        RuleEngine ruleEngine = new RuleEngine(system);

        Device tv = new Device("TV", 150);
        system.addDevice(tv);

        LocalTime now = LocalTime.now();
        ruleEngine.applyRule("TV", now.minusHours(1), now.plusHours(1));  // Long time interval
        assertTrue(tv.isOn());
    }

    @Test
    void testOverlappingRules() {
        SmartHomeSystem system = new SmartHomeSystem();
        RuleEngine ruleEngine = new RuleEngine(system);

        Device light = new Device("Living Room Light", 60);
        system.addDevice(light);

        LocalTime now = LocalTime.now();
        ruleEngine.applyRule("Living Room Light", now.minusMinutes(10), now.plusMinutes(10));
        assertTrue(light.isOn());

        ruleEngine.applyRule("Living Room Light", now.minusMinutes(5), now.plusMinutes(5));
        assertTrue(light.isOn());  // Verify the rule continues to be applied
    }
}
