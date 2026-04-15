package service;

import client.DeviceClient;
import io.restassured.response.Response;
import model.Device;
import steps.TestContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public void getDeviceByRandomId(){
        Response rsp = deviceClient.get(DEVICE_ENDPOINT+"/"+ UUID.randomUUID());
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

    public void updateDeviceByRandomId(){
        Response rsp = deviceClient.update(testContext.getUpdatedDeviceRequest(), DEVICE_ENDPOINT+"/"+UUID.randomUUID());
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

    public void deleteDeviceByRandomId() {
        Response rsp = deviceClient.delete(DEVICE_ENDPOINT + "/" + UUID.randomUUID());
        testContext.setLastDeviceResponse(rsp);

    }

    public void createFlexibleDevice(){
        Response rsp = deviceClient.post(testContext.getCustomRequestPayload(), DEVICE_ENDPOINT);
        System.out.println("device is " + rsp.body().asString());
        testContext.setLastDeviceResponse(rsp);
        testContext.setCreatedDeviceId(rsp.jsonPath().getString("id"));
    }

    public void createEmptyDevice(){
        Response rsp = deviceClient.post(DEVICE_ENDPOINT);
        System.out.println("device is " + rsp.body().asString());
        testContext.setLastDeviceResponse(rsp);
    }

    public void prepareCustomDevicePayload(Map<String, String> input) {

        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        if (input.containsKey("name")){
            payload.put("name", input.get("name"));
        }

        data.put("year", Integer.parseInt(input.get("year")));
        data.put("price", Double.parseDouble(input.get("price")));

        for (Map.Entry<String, String> entry : input.entrySet()) {
            String key = entry.getKey();
            if (key.equals("name") || key.equals("year") || key.equals("price")) {
                continue;
            }
            data.put(key, entry.getValue());
        }
        payload.put("data", data);
        testContext.setCustomRequestPayload(payload);
        System.out.println(testContext.getCustomRequestPayload().keySet());
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
