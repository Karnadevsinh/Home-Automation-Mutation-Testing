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
}

