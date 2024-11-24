package devices;

public class Device {
    private String name;
    private double powerConsumption;
    private boolean isOn;

    public Device(String name, double powerConsumption) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.isOn = false;
    }

    public String getName() {
        return name;
    }

    public double getPowerConsumption() {
        return isOn ? powerConsumption : 0.0;
    }

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        this.isOn = true;
    }

    public void turnOff() {
        this.isOn = false;
    }

    public void toggle() {
        this.isOn = !this.isOn;
    }
}
