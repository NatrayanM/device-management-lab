package steps;

import io.restassured.response.Response;
import model.Device;

public class TestContext {

    private Device deviceRequest;
    private Response deviceResponse;
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public Device getDeviceRequest() {
        return deviceRequest;
    }

    public Response getDeviceResponse() {
        return deviceResponse;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceRequest(Device deviceRequest) {
        this.deviceRequest = deviceRequest;
    }

    public void setDeviceResponse(Response deviceResponse) {
        this.deviceResponse = deviceResponse;
    }
}
