package system;

import devices.Device;
import java.util.ArrayList;
import java.util.List;

public class SmartHomeSystem {
    private List<Device> devices;

    public SmartHomeSystem() {
        this.devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        devices.add(device);
//        System.out.println(device.getName() + " added to the system.");
    }

    public void removeDevice(String deviceName) {
        devices.removeIf(device -> device.getName().equalsIgnoreCase(deviceName));
//        System.out.println(deviceName + " removed from the system.");
    }

    public void controlDevice(String deviceName, boolean turnOn) {
        for (Device device : devices) {
            if (device.getName().equalsIgnoreCase(deviceName)) {
                if (turnOn) {
                    device.turnOn();
                } else {
                    device.turnOff();
                }
                return;
            }
        }
//        System.out.println("Device " + deviceName + " not found.");
    }

    public double calculateTotalPowerUsage() {
        return devices.stream().mapToDouble(Device::getPowerConsumption).sum();
    }

    public List<Device> getDevices() {
        return devices;
    }
}
