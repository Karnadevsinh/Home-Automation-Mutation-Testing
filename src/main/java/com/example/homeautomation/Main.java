package com.example.homeautomation;

import devices.Device;
import system.SmartHomeSystem;
import system.RuleEngine;
import system.EnergyTracker;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        // Initialize SmartHomeSystem
        SmartHomeSystem homeSystem = new SmartHomeSystem();

        // Create devices
        Device light = new Device("Living Room Light", 60);
        Device fan = new Device("Ceiling Fan", 75);
        Device heater = new Device("Water Heater", 2000);

        // Add devices to the system
        homeSystem.addDevice(light);
        homeSystem.addDevice(fan);
        homeSystem.addDevice(heater);

        // Initialize RuleEngine and EnergyTracker
        RuleEngine ruleEngine = new RuleEngine(homeSystem);
        EnergyTracker energyTracker = new EnergyTracker();

        // Apply rules for automatic device control
        System.out.println("Applying rules...");
        ruleEngine.applyRule("Living Room Light", LocalTime.of(18, 0), LocalTime.of(23, 0));
        ruleEngine.applyRule("Water Heater", LocalTime.of(6, 0), LocalTime.of(8, 0));

        // Log and print initial energy usage
        System.out.println("\nInitial energy usage:");
        energyTracker.logEnergyUsage(homeSystem);
        energyTracker.printEnergyUsage();

        // Manually control a device
        System.out.println("\nTurning on the Ceiling Fan...");
        homeSystem.controlDevice("Ceiling Fan", true);

        // Log and print updated energy usage
        System.out.println("\nUpdated energy usage:");
        energyTracker.logEnergyUsage(homeSystem);
        energyTracker.printEnergyUsage();

        // Show active devices
        System.out.println("\nActive devices:");
        homeSystem.getActiveDevices().forEach(device ->
                System.out.println(device.getName() + " is ON"));

        // Turn off all devices and show the final state
        System.out.println("\nTurning off all devices...");
        homeSystem.controlAllDevices(false);

        System.out.println("All devices are now OFF.");
    }
}
