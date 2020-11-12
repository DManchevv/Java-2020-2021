package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SmartCityHub {
    private Set<SmartDevice> devices;
    public SmartCityHub() {
        this.devices = new LinkedHashSet<>();
    }

    /**
     * Adds a @device to the SmartCityHub.
     *
     * @throws IllegalArgumentException in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
     */
    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
        if (device == null) throw new IllegalArgumentException();
        if (devices.contains(device)) throw new DeviceAlreadyRegisteredException("Device already registered!");
        devices.add(device);
    }

    /**
     * Removes the @device from the SmartCityHub.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException in case the @device is not found.
     */
    public void unregister(SmartDevice device) throws DeviceNotFoundException {
        if (device == null) throw new IllegalArgumentException();
        if (!devices.contains(device)) throw new DeviceNotFoundException("Device not found!");
        devices.remove(device);
    }

    /**
     * Returns a SmartDevice with an ID @id.
     *
     * @throws IllegalArgumentException in case @id is null.
     * @throws DeviceNotFoundException in case device with ID @id is not found.
     */
    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        if (id == null) throw new IllegalArgumentException();
        for (SmartDevice currentDevice : devices){
            if (currentDevice.getId().equals(id)) return currentDevice;
        }
        throw new DeviceNotFoundException("Device not found!");
    }

    /**
     * Returns the total number of devices with type @type registered in SmartCityHub.
     *
     * @throws IllegalArgumentException in case @type is null.
     */
    public int getDeviceQuantityPerType(DeviceType type) {
        if (type == null) throw new IllegalArgumentException();
        else if (devices.isEmpty()) {
            return 0;
        }
        else {
            int total = 0;
            for(SmartDevice currentDevice : devices){
                if (currentDevice.getType().equals(type)){
                    total++;
                }
            }
            return total;
        }
    }

    /**
     * Returns a collection of IDs of the top @n devices which consumed
     * the most power from the time of their installation until now.
     *
     * The total power consumption of a device is calculated by the hours elapsed
     * between the two LocalDateTime-s: the installation time and the current time (now)
     * multiplied by the stated nominal hourly power consumption of the device.
     *
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     * @throws IllegalArgumentException in case @n is a negative number.
     */

    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if (n < 0) throw new IllegalArgumentException();
        else {
            Map<String, Double> powerConsumption = new HashMap<>();
            LocalDateTime today = LocalDateTime.now();
            for (SmartDevice currentDevice : devices) {
                powerConsumption.put(currentDevice.getId(), Duration.between(currentDevice.getInstallationDateTime(), today).toHours() * currentDevice.getPowerConsumption());
            }
            Map<String, Double> sortedMap = new HashMap<>();
            sortedMap = powerConsumption.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, HashMap::new));
            Set<String> result = new HashSet<>();
            int counter = 0;
            for (Map.Entry<String, Double> a : sortedMap.entrySet()){
                if (counter == n) break;
                result.add(a.getKey());
                counter++;
            }
            return result;
        }
    }

    /**
     * Returns a collection of the first @n registered devices, i.e the first @n that were added
     * in the SmartCityHub (registration != installation).
     *
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */

    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        if (n < 0) throw new IllegalArgumentException();
        else if (n >= devices.size()) {
            return devices;
        }
        else {
            Set<SmartDevice> firstDevices = new HashSet<>();
            int counter = 0;
            for (SmartDevice currentDevice : devices) {
                if (counter == n) break;
                firstDevices.add(currentDevice);
                counter++;
            }
            return firstDevices;
        }
    }
}
