package steps;

import io.restassured.response.Response;
import model.Device;

public class TestContext {

    private Device createdDeviceRequest;
    private Device updatedDeviceRequest;
    private Response lastDeviceResponse;
    private String createdDeviceId;

    public String getCreatedDeviceId() {
        return createdDeviceId;
    }

    public Device getCreatedDeviceRequest() {
        return createdDeviceRequest;
    }

    public Device getUpdatedDeviceRequest() {
        return updatedDeviceRequest;
    }

    public Response getLastDeviceResponse() {
        return lastDeviceResponse;
    }

    public void setCreatedDeviceId(String createdDeviceId) {
        this.createdDeviceId = createdDeviceId;
    }

    public void setCreatedDeviceRequest(Device createdDeviceRequest) {
        this.createdDeviceRequest = createdDeviceRequest;
    }

    public void setUpdatedDeviceRequest(Device updatedDeviceRequest) {
        this.updatedDeviceRequest = updatedDeviceRequest;
    }

    public void setLastDeviceResponse(Response lastDeviceResponse) {
        this.lastDeviceResponse = lastDeviceResponse;
    }
}
