package system;

import devices.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SmartHomeSystem {
    private List<Device> devices;

    public SmartHomeSystem() {
        this.devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void removeDevice(String deviceName) {
        devices.removeIf(device -> device.getName().equalsIgnoreCase(deviceName));
    }

    public void controlDevice(String deviceName, boolean turnOn) {
        devices.stream()
                .filter(device -> device.getName().equalsIgnoreCase(deviceName))
                .findFirst()
                .ifPresent(device -> {
                    if (turnOn) {
                        device.turnOn();
                    } else {
                        device.turnOff();
                    }
                });
    }

    public double calculateTotalPowerUsage() {
        return devices.stream().mapToDouble(Device::getPowerConsumption).sum();
    }

    public List<Device> getDevices() {
        return devices;
    }

    public Optional<Device> findDeviceByName(String deviceName) {
        return devices.stream()
                .filter(device -> device.getName().equalsIgnoreCase(deviceName))
                .findFirst();
    }

    public List<Device> getActiveDevices() {
        return devices.stream().filter(Device::isOn).toList();
    }

    public void controlAllDevices(boolean turnOn) {
        devices.forEach(device -> {
            if (turnOn) {
                device.turnOn();
            } else {
                device.turnOff();
            }
        });
    }
}
