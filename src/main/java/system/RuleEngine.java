package system;

import java.time.LocalTime;

public class RuleEngine {
    private SmartHomeSystem system;

    public RuleEngine(SmartHomeSystem system) {
        this.system = system;
    }

    public void applyRule(String deviceName, LocalTime onTime, LocalTime offTime) {
        LocalTime now = LocalTime.now();

        if (now.isAfter(onTime) && now.isBefore(offTime)) {
            system.controlDevice(deviceName, true);
        } else {
            system.controlDevice(deviceName, false);
        }
    }
}
