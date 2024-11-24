package system;

import devices.Device;

import java.util.HashMap;
import java.util.Map;

public class EnergyTracker {
    private Map<String, Double> energyUsageLog;

    public EnergyTracker() {
        this.energyUsageLog = new HashMap<>();
    }

    // Log energy usage for each device in the system
    public void logEnergyUsage(SmartHomeSystem system) {
        for (Device device : system.getDevices()) {
            double consumption = device.getPowerConsumption();
            energyUsageLog.put(device.getName(),
                    energyUsageLog.getOrDefault(device.getName(), 0.0) + consumption);
        }
    }

    // Print total energy usage for all devices
    public void printEnergyUsage() {
        energyUsageLog.forEach((deviceName, usage) ->
                System.out.println(deviceName + ": " + usage + " watts."));
    }

    // Get a summary of energy usage for all devices
    public Map<String, Double> getEnergyUsageSummary() {
        return new HashMap<>(energyUsageLog);
    }

    // Reset the energy usage log
    public void resetEnergyLogs() {
        energyUsageLog.clear();
        System.out.println("Energy logs have been reset.");
    }

    // Log energy usage for a specific device
    public void logEnergyUsageForDevice(Device device) {
        double consumption = device.getPowerConsumption();
        energyUsageLog.put(device.getName(),
                energyUsageLog.getOrDefault(device.getName(), 0.0) + consumption);
        System.out.println("Logged " + consumption + " watts for device " + device.getName());
    }

    // Calculate total energy consumed by all devices
    public double getTotalEnergyConsumption() {
        return energyUsageLog.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    // Set energy usage log for specific devices
    public void setEnergyUsageForDevice(String deviceName, double usage) {
        energyUsageLog.put(deviceName, usage);
        System.out.println("Energy usage for " + deviceName + " set to " + usage + " watts.");
    }

    // Print a detailed summary of the energy usage for the entire system
    public void printDetailedEnergySummary() {
        double totalConsumption = getTotalEnergyConsumption();
        System.out.println("Energy Usage Summary:");
        energyUsageLog.forEach((deviceName, usage) ->
                System.out.println("Device: " + deviceName + " | Usage: " + usage + " watts"));
        System.out.println("Total energy consumption: " + totalConsumption + " watts.");
    }

    // Check if any device exceeds a certain energy usage threshold
    public void checkEnergyThreshold(double threshold) {
        energyUsageLog.forEach((deviceName, usage) -> {
            if (usage > threshold) {
                System.out.println("Energy threshold exceeded for " + deviceName + ": " + usage + " watts.");
            }
        });
    }
}
