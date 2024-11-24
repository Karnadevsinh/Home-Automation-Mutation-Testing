package system;

import devices.Device;

import java.util.HashMap;
import java.util.Map;
public class EnergyTracker {
    private SmartHomeSystem system;
    private Map<String, Double> energyUsageLog;

    public EnergyTracker(SmartHomeSystem system) {
        this.system = system;
        this.energyUsageLog = new HashMap<>();
    }

    public void logEnergyUsage() {
        for (Device device : system.getDevices()) {
            double consumption = device.getPowerConsumption();
            energyUsageLog.put(device.getName(), energyUsageLog.getOrDefault(device.getName(), 0.0) + consumption);
        }
    }

    public void printEnergyUsage() {
        logEnergyUsage();
        System.out.println("Energy usage per device:");
        for (Map.Entry<String, Double> entry : energyUsageLog.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " watts.");
        }
    }
}
