package system;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class RuleEngine {
    private SmartHomeSystem system;
    private Timer timer;

    public RuleEngine(SmartHomeSystem system) {
        this.system = system;
        this.timer = new Timer();
    }

    // Apply rule for a device within a given time range
    public void applyRule(String deviceName, LocalTime onTime, LocalTime offTime) {
        LocalTime now = LocalTime.now();

        if (now.isAfter(onTime) && now.isBefore(offTime)) {
            system.controlDevice(deviceName, true);
        } else {
            system.controlDevice(deviceName, false);
        }
    }

    // Schedule a device to turn on at a specific time
    public void scheduleTurnOn(String deviceName, LocalTime onTime) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                system.controlDevice(deviceName, true);
            }
        }, java.sql.Time.valueOf(onTime));
        System.out.println("Scheduled to turn on " + deviceName + " at " + onTime);
    }

    // Schedule a device to turn off at a specific time
    public void scheduleTurnOff(String deviceName, LocalTime offTime) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                system.controlDevice(deviceName, false);
            }
        }, java.sql.Time.valueOf(offTime));
        System.out.println("Scheduled to turn off " + deviceName + " at " + offTime);
    }

    // Apply a recurring rule for turning on and off devices at specified times
    public void applyRecurringRule(String deviceName, LocalTime onTime, LocalTime offTime, long period) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LocalTime now = LocalTime.now();
                if (now.isAfter(onTime) && now.isBefore(offTime)) {
                    system.controlDevice(deviceName, true);
                } else {
                    system.controlDevice(deviceName, false);
                }
            }
        }, java.sql.Time.valueOf(onTime), period);
        System.out.println("Recurring rule applied for " + deviceName + " from " + onTime + " to " + offTime);
    }

    // Stop all scheduled tasks (like turning off devices)
    public void cancelScheduledTasks() {
        timer.cancel();
        System.out.println("All scheduled tasks have been canceled.");
    }

    // Change the scheduled times for a device
    public void changeScheduledTimes(String deviceName, LocalTime newOnTime, LocalTime newOffTime) {
        cancelScheduledTasks();
        applyRule(deviceName, newOnTime, newOffTime);
        System.out.println("New times set for " + deviceName + ": " + newOnTime + " to " + newOffTime);
    }
}
