package steps;

import client.DeviceClient;
import io.cucumber.java.After;
import io.cucumber.java.hu.De;
import service.DeviceService;

public class DeviceHooks {

    private final DeviceService deviceService;

    public DeviceHooks(DeviceService deviceService){
    this.deviceService =deviceService;
    }

    @After
    public void deviceCleanup(){
        if(deviceService.cleanupDevice())
            System.out.println("device cleaned up successfully");
        else System.out.println("device cleanup failed");
    }
}
