package service;

import client.DeviceClient;
import io.restassured.response.Response;
import model.Device;
import steps.TestContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeviceService {

    private final DeviceClient deviceClient;
    private final TestContext testContext;
    private static final String DEVICE_ENDPOINT = "/objects";

    public DeviceService(DeviceClient deviceClient, TestContext testContext){
    this.deviceClient = deviceClient;
    this.testContext = testContext;
    }

    public void setupDevice(Device device){
        testContext.setDeviceRequest(device);
    }

    public void createDevice(){
        Response rsp = deviceClient.post(testContext.getDeviceRequest(), DEVICE_ENDPOINT);
        System.out.println("device is " + rsp.body().asString());
        testContext.setDeviceResponse(rsp);
        testContext.setDeviceId(rsp.jsonPath().getString("id"));
    }

    public void getDevice(String deviceId){
        Response rsp = deviceClient.get(DEVICE_ENDPOINT+"/"+deviceId);
        System.out.println("device is " + rsp.body().asString());
    }

    public void updateDevice(){
        Response rsp = deviceClient.update(testContext.getDeviceRequest(), DEVICE_ENDPOINT);
        System.out.println("device is " + rsp.body().asString());
    }

    public void deleteDevice() {
        Response rsp;
        if (testContext.getDeviceId() != null || !testContext.getDeviceId().isBlank()) {
            rsp = deviceClient.delete(DEVICE_ENDPOINT + "/" + testContext.getDeviceId());
            testContext.setDeviceResponse(rsp);
        }
    }

        public boolean cleanupDevice(){
            Response rsp = null;
            if(testContext.getDeviceId()!=null || !testContext.getDeviceId().isBlank()) {
                rsp = deviceClient.delete(DEVICE_ENDPOINT + "/" + testContext.getDeviceId());
            }
            try{
                int rspCode = rsp.getStatusCode();
                rsp = deviceClient.get(DEVICE_ENDPOINT+"/"+testContext.getDeviceId());
                System.out.println("code " + rsp.getStatusCode()+ "body " + rsp.body().asString());
                return rspCode == 200;
            } catch (Exception e){
                return false;
            }
    }

}
