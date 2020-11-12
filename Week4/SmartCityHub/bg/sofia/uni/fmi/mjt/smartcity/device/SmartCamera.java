package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartCamera implements SmartDevice{
    private String name;
    private double powerConsumption;
    private LocalDateTime installationDateTime;
    private String Id;
    private static int count = 0;
    public SmartCamera(String name, double powerConsumption, LocalDateTime installationDateTime){
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationDateTime = installationDateTime;
        this.Id = new StringBuilder().append(DeviceType.CAMERA.getShortName()).append("-").append(this.name).append("-").append(count).toString();
        SmartCamera.count++;
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
        return DeviceType.CAMERA;
    }

}
