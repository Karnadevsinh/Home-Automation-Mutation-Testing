package system;

import devices.Device;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SmartHomeSystem {
    private List<Device> devices;
    private Map<String, List<Device>> groups;
    private double energyThreshold;
    private List<String> logs;

    public SmartHomeSystem() {
        this.devices = new ArrayList<>();
        this.groups = new HashMap<>();
        this.energyThreshold = Double.MAX_VALUE;
        this.logs = new ArrayList<>();
    }

    public void addDevice(Device device) {
        devices.add(device);
        logs.add("Device added: " + device.getName());
        System.out.println(device.getName() + " added to the system.");
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void removeDevice(String deviceName) {
        devices.removeIf(device -> device.getName().equalsIgnoreCase(deviceName));
        logs.add("Device removed: " + deviceName);
        System.out.println(deviceName + " removed from the system.");
    }

    public void controlDevice(String deviceName, boolean turnOn) {
        devices.stream()
                .filter(device -> device.getName().equalsIgnoreCase(deviceName))
                .findFirst()
                .ifPresentOrElse(
                        device -> {
                            if (turnOn) device.turnOn();
                            else device.turnOff();
                            logs.add("Device " + deviceName + " turned " + (turnOn ? "ON" : "OFF"));
                            System.out.println(deviceName + " turned " + (turnOn ? "ON" : "OFF") + ".");
                        },
                        () -> System.out.println("Device " + deviceName + " not found.")
                );
    }

    public Optional<Device> findDeviceByName(String deviceName) {
        return devices.stream()
                .filter(device -> device.getName().equalsIgnoreCase(deviceName))
                .findFirst();
    }

    public double calculateTotalPowerUsage() {
        return devices.stream()
                .filter(Device::isOn)
                .mapToDouble(Device::getPowerConsumption)
                .sum();
    }

    public void createGroup(String groupName, Device... devicesInGroup) {
        groups.put(groupName, Arrays.asList(devicesInGroup));
        logs.add("Group created: " + groupName);
        System.out.println("Group '" + groupName + "' created.");
    }

    public void controlGroup(String groupName, boolean turnOn) {
        List<Device> groupDevices = groups.getOrDefault(groupName, Collections.emptyList());
        groupDevices.forEach(device -> controlDevice(device.getName(), turnOn));
        logs.add("Group " + groupName + " turned " + (turnOn ? "ON" : "OFF"));
    }

    public void scheduleDevice(String deviceName, LocalTime startTime, LocalTime endTime) {
        Timer timer = new Timer();

        LocalDate today = LocalDate.now();
        ZonedDateTime startDateTime = startTime.atDate(today).atZone(ZoneId.systemDefault());
        ZonedDateTime endDateTime = endTime.atDate(today).atZone(ZoneId.systemDefault());

        TimerTask turnOnTask = new TimerTask() {
            @Override
            public void run() {
                controlDevice(deviceName, true);
            }
        };
        TimerTask turnOffTask = new TimerTask() {
            @Override
            public void run() {
                controlDevice(deviceName, false);
            }
        };

        timer.schedule(turnOnTask, Date.from(startDateTime.toInstant()));
        timer.schedule(turnOffTask, Date.from(endDateTime.toInstant()));

        logs.add("Scheduled device: " + deviceName + " from " + startTime + " to " + endTime);
        System.out.println("Scheduled " + deviceName + " from " + startTime + " to " + endTime);
    }

    public void setEnergyThreshold(double threshold) {
        this.energyThreshold = threshold;
        logs.add("Energy threshold set to: " + threshold);
    }

    public boolean isThresholdExceeded() {
        return calculateTotalPowerUsage() > energyThreshold;
    }

    public void checkMaintenanceReminders() {
        devices.forEach(device -> {
            if (device.getTotalRuntimeHours() > 100) {
                logs.add("Maintenance alert: " + device.getName() + " requires service.");
                System.out.println("Maintenance alert: " + device.getName() + " has run for "
                        + device.getTotalRuntimeHours() + " hours.");
            }
        });
    }

    public void executeVoiceCommand(String command) {
        if (command.startsWith("Turn on")) {
            String deviceName = command.substring(8);
            controlDevice(deviceName, true);
        } else if (command.startsWith("Turn off")) {
            String deviceName = command.substring(9);
            controlDevice(deviceName, false);
        } else {
            logs.add("Unrecognized command: " + command);
            System.out.println("Unrecognized command: " + command);
        }
    }

    public void controlAllDevices(boolean turnOn) {
        devices.forEach(device -> controlDevice(device.getName(), turnOn));
        logs.add("All devices turned " + (turnOn ? "ON" : "OFF"));
    }

    public void resetSystem() {
        devices.clear();
        groups.clear();
        logs.add("System reset performed.");
        System.out.println("System has been reset.");
    }

    public void generateStatusReport() {
        System.out.println("SmartHomeSystem Status Report:");
        devices.forEach(device -> {
            System.out.println(" - Device: " + device.getName() +
                    " | Power: " + device.getPowerConsumption() +
                    "W | Status: " + (device.isOn() ? "ON" : "OFF"));
        });
        System.out.println("Total Power Usage: " + calculateTotalPowerUsage() + "W");
        System.out.println("Energy Threshold: " + (isThresholdExceeded() ? "Exceeded" : "Within Limit"));
    }

    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    public void printLogs() {
        System.out.println("System Logs:");
        logs.forEach(System.out::println);
    }
}
