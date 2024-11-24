package devices;

import java.time.Duration;
import java.time.LocalDateTime;

public class Device {
    private String name;
    private double powerConsumption; // in watts
    private boolean isOn;
    private LocalDateTime lastTurnedOnTime;
    private Duration totalRuntime;
    private boolean wasOnLastTime;  // To track if the device was previously ON

    public Device(String name, double powerConsumption) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.isOn = false;
        this.totalRuntime = Duration.ZERO;
        this.wasOnLastTime = false;  // Initially the device is off
    }

    public String getName() {
        return name;
    }

    public double getPowerConsumption() {
        return isOn ? powerConsumption : 0;
    }

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        if (!isOn) {
            isOn = true;
            lastTurnedOnTime = LocalDateTime.now();
            if (!wasOnLastTime) {
                System.out.println(name + " is now ON.");
                wasOnLastTime = true; // Device is now ON
            }
        }
    }

    public void turnOff() {
        if (isOn) {
            isOn = false;
            updateTotalRuntime();
            if (wasOnLastTime) {
                System.out.println(name + " is now OFF.");
                wasOnLastTime = false; // Device is now OFF
            }
        }
    }

    private void updateTotalRuntime() {
        if (lastTurnedOnTime != null) {
            Duration runtime = Duration.between(lastTurnedOnTime, LocalDateTime.now());
            totalRuntime = totalRuntime.plus(runtime);
            lastTurnedOnTime = null;  // Reset the last turned on time
        }
    }

    public double getTotalRuntimeHours() {
        if (isOn && lastTurnedOnTime != null) {
            updateTotalRuntime(); // Update runtime if the device is still on
        }
        return totalRuntime.toHours() + (totalRuntime.toMinutesPart() / 60.0);
    }
}
