package com.example.homeautomation;

import devices.Device;
import system.SmartHomeSystem;
import system.RuleEngine;
import system.EnergyTracker;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        SmartHomeSystem homeSystem = new SmartHomeSystem();

        Device light = new Device("Living Room Light", 60);
        Device fan = new Device("Ceiling Fan", 75);
        Device heater = new Device("Water Heater", 2000);

        homeSystem.addDevice(light);
        homeSystem.addDevice(fan);
        homeSystem.addDevice(heater);

        RuleEngine ruleEngine = new RuleEngine(homeSystem);
        EnergyTracker energyTracker = new EnergyTracker(homeSystem);

        ruleEngine.applyRule("Living Room Light", LocalTime.of(18, 0), LocalTime.of(23, 0));
        ruleEngine.applyRule("Water Heater", LocalTime.of(6, 0), LocalTime.of(8, 0));

        energyTracker.printEnergyUsage();

        homeSystem.controlDevice("Ceiling Fan", true);
        energyTracker.printEnergyUsage();
    }
}
