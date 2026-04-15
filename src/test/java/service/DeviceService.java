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
        testContext.setCreatedDeviceRequest(device);
    }

    public void updateDevice(Device device){
        testContext.setUpdatedDeviceRequest(device);
    }

    public void createDevice(){
        Response rsp = deviceClient.post(testContext.getCreatedDeviceRequest(), DEVICE_ENDPOINT);
        System.out.println("device is " + rsp.body().asString());
        testContext.setLastDeviceResponse(rsp);
        testContext.setCreatedDeviceId(rsp.jsonPath().getString("id"));
    }

    public void getDeviceById(){
        Response rsp = deviceClient.get(DEVICE_ENDPOINT+"/"+testContext.getCreatedDeviceId());
        testContext.setLastDeviceResponse(rsp);
        System.out.println("device is " + rsp.body().asString());
    }

    public void getDevice(){
        Response rsp = deviceClient.get(DEVICE_ENDPOINT);
        testContext.setLastDeviceResponse(rsp);
        System.out.println("device is " + rsp.body().asString());
    }

    public void updateDevice(){
        Response rsp = deviceClient.update(testContext.getUpdatedDeviceRequest(), DEVICE_ENDPOINT+"/"+testContext.getCreatedDeviceId());
        System.out.println("device is " + rsp.body().asString());
        testContext.setLastDeviceResponse(rsp);
    }

    public void deleteDevice() {
        Response rsp;
        if (testContext.getCreatedDeviceId() != null || !testContext.getCreatedDeviceId().isBlank()) {
            rsp = deviceClient.delete(DEVICE_ENDPOINT + "/" + testContext.getCreatedDeviceId());
            testContext.setLastDeviceResponse(rsp);
        }
    }

        public boolean cleanupDevice(){
            Response rsp = null;
            try{
                if(testContext.getCreatedDeviceId()!=null && !testContext.getCreatedDeviceId().isBlank()) {
                    rsp = deviceClient.delete(DEVICE_ENDPOINT + "/" + testContext.getCreatedDeviceId());
                }
            } catch (Exception e){
                return false;
            }

            try{
                if(rsp != null) {
                    int deleteRspCode = rsp.getStatusCode();
                    rsp = deviceClient.get(DEVICE_ENDPOINT+"/"+testContext.getCreatedDeviceId());
                    System.out.println("code " + rsp.getStatusCode()+ " body " + rsp.body().asString());
                    return deleteRspCode == 200;
                }
                }catch (Exception e){
                    return false;
                }
            return false;
        }



}
