package system;

import devices.Device;

import java.util.HashMap;
import java.util.Map;

public class EnergyTracker {
    private Map<String, Double> energyUsageLog;

    public EnergyTracker() {
        this.energyUsageLog = new HashMap<>();
    }

    public void logEnergyUsage(SmartHomeSystem system) {
        for (Device device : system.getDevices()) {
            double consumption = device.getPowerConsumption();
            energyUsageLog.put(device.getName(),
                    energyUsageLog.getOrDefault(device.getName(), 0.0) + consumption);
        }
    }

    public void printEnergyUsage() {
        energyUsageLog.forEach((deviceName, usage) ->
                System.out.println(deviceName + ": " + usage + " watts."));
    }

    public Map<String, Double> getEnergyUsageSummary() {
        return new HashMap<>(energyUsageLog);
    }

    public void resetEnergyLogs() {
        energyUsageLog.clear();
    }
}
