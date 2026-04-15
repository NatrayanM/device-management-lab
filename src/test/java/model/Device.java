package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Device {

    private String name;

    @JsonProperty("data")
    private DeviceData deviceData;

    public String getName(){
        return name;
    }

    public Device setName(String name){
        this.name = name;
        return this;
    }

    public DeviceData getDeviceData(){
        return deviceData;
    }

    public Device setDeviceData(DeviceData deviceData){
        this.deviceData = deviceData;
        return this;
    }
}
