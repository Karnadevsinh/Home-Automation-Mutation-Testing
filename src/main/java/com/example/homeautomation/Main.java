package com.example.homeautomation;

import devices.Device;
import system.SmartHomeSystem;
import system.RuleEngine;
import system.EnergyTracker;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        SmartHomeSystem homeSystem = new SmartHomeSystem();

        // Add devices
        Device light = new Device("Living Room Light", 60);
        Device fan = new Device("Ceiling Fan", 75);
        Device heater = new Device("Water Heater", 2000);

        homeSystem.addDevice(light);
        homeSystem.addDevice(fan);
        homeSystem.addDevice(heater);

        RuleEngine ruleEngine = new RuleEngine(homeSystem);
        EnergyTracker energyTracker = new EnergyTracker();

        // Device scheduling
        System.out.println("Scheduling devices...");
        homeSystem.scheduleDevice("Living Room Light", LocalTime.of(18, 0), LocalTime.of(22, 0));

        // Apply rules
        ruleEngine.applyRule("Water Heater", LocalTime.of(6, 0), LocalTime.of(8, 0));

        // Group operations
        homeSystem.createGroup("Living Room Devices", light, fan);
        homeSystem.controlGroup("Living Room Devices", true); // Turn on all devices in the group

        // Monitor energy usage
        energyTracker.logEnergyUsage(homeSystem);
        energyTracker.printEnergyUsage();

        // Threshold alerts
        homeSystem.setEnergyThreshold(100);
        if (homeSystem.isThresholdExceeded()) {
            System.out.println("Energy threshold exceeded! Please turn off some devices.");
        }

        // Maintenance reminders
        homeSystem.checkMaintenanceReminders();

        // Voice commands
        System.out.println("Voice command: Turn off the Ceiling Fan");
        homeSystem.executeVoiceCommand("Turn off the Ceiling Fan");

        // Shut down system
        homeSystem.controlAllDevices(false);
        System.out.println("System shut down.");
    }
}
