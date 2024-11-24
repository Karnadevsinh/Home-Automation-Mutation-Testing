package devices;

public class Device {
    private String name;
    private boolean status;
    private double powerConsumption;

    public Device(String name, double powerConsumption) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.status = false;
    }

    public String getName() {
        return name;
    }

    public boolean isOn() {
        return status;
    }

    public void turnOn() {
        status = true;
        System.out.println(name + " is turned ON.");
    }

    public void turnOff() {
        status = false;
        System.out.println(name + " is turned OFF.");
    }

    public double getPowerConsumption() {
        return status ? powerConsumption : 0;
    }
}
