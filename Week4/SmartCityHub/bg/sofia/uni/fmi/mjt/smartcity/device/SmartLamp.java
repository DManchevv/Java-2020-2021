package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;
public class SmartLamp implements SmartDevice{
    private String name;
    private double powerConsumption;
    private LocalDateTime installationDateTime;
    private String Id;
    private static int count = 0;
    public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime){
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationDateTime = installationDateTime;
        this.Id = new StringBuilder().append(DeviceType.LAMP.getShortName()).append("-").append(this.name).append("-").append(count).toString();
        SmartLamp.count++;
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPowerConsumption() {
        return powerConsumption;
    }

    @Override
    public LocalDateTime getInstallationDateTime() {
        return installationDateTime;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.LAMP;
    }

}
