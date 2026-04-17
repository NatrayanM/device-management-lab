package assertions;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Device;
import steps.TestContext;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class DeviceAssertions {

    private final TestContext testContext;
    private  final int  RSP_CODE = 200;
    public DeviceAssertions(TestContext testContext) {
        this.testContext=testContext;
    }

    public void validateStatusCode(){
        Response actualResponse = testContext.getLastDeviceResponse();
        assertEquals(RSP_CODE, actualResponse.getStatusCode(), "Invalid status code: expected " + RSP_CODE + " received " + actualResponse.getStatusCode());
    }

    public void validateDeviceCreation(){
        Device expectedRequest = testContext.getCreatedDeviceRequest();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();

        validateDeviceData(expectedRequest, jsonRsp);

        assertNotNull(jsonRsp.get("createdAt"), "Response missing the device creation time");
        checkTimeStamp(((Number) jsonRsp.get("createdAt")).longValue());

    }

    public void validateDeviceUpdate(){
        Device expectedRequest = testContext.getUpdatedDeviceRequest();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();


        validateDeviceData(expectedRequest, jsonRsp);

        assertEquals(testContext.getCreatedDeviceId(), jsonRsp.getString("id"), "Device Id mismatch between create and update");
        assertNotNull(jsonRsp.get("updatedAt"), "Response missing the device update time");
        checkTimeStamp(((Number) jsonRsp.get("updatedAt")).longValue());
    }

    public void validateListedDeviceAfterCreate(){
        Device expectedRequest = testContext.getCreatedDeviceRequest();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();
        assertEquals(testContext.getCreatedDeviceId(), jsonRsp.getString("id"), "Device id mismatch");
        validateDeviceData(expectedRequest, jsonRsp);

    }

    public void validateListedDeviceAfterUpdate(){
        Device expectedRequest = testContext.getUpdatedDeviceRequest();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();
        assertEquals(testContext.getCreatedDeviceId(), jsonRsp.getString("id"), "Device Id mismatch");
        validateDeviceData(expectedRequest, jsonRsp);

    }

    public void validateDeviceList(){
        Response actualResponse = testContext.getLastDeviceResponse();
        List<Object> devices = testContext.getLastDeviceResponse().jsonPath().getList("$");

        assertFalse(devices.isEmpty());

        actualResponse.then()
                .log().ifValidationFails()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schema/DeviceSchema.json"));

    }

    public void deviceNotFound(){
        Response actualResponse = testContext.getLastDeviceResponse();
        System.out.println(actualResponse.asString());
        assertEquals(404, actualResponse.getStatusCode(), "Invalid status code: expected 404 "   + " received " + actualResponse.getStatusCode());
        assertTrue(actualResponse.body().asString().contains("not found")); //can make the test flaky if the response body changes
    }

    public void verifyBadRequest(){
        Response actualResponse = testContext.getLastDeviceResponse();
        System.out.println(actualResponse.asString());
        assertEquals(400, actualResponse.getStatusCode(), "Invalid status code: expected 404 "   + " received " + actualResponse.getStatusCode());
        assertTrue(actualResponse.body().asString().contains("body is missing")); //can make the test flaky if the response body changes
    }

    public void deviceNotUpdated(){
        Response actualResponse = testContext.getLastDeviceResponse();
        System.out.println(actualResponse.asString());
        assertEquals(404, actualResponse.getStatusCode(), "Invalid status code: expected 404 "   + " received " + actualResponse.getStatusCode());
        assertTrue(actualResponse.body().asString().contains("doesn't exist")); //can make the test flaky if the response body changes
    }
    public void validateFlexibleDeviceCreation(){
        Map<String, Object> expectedRequest = testContext.getCustomRequestPayload();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();

        assertEquals(expectedRequest.get("name"), jsonRsp.getString("name"), "Invalid value for attribute name: Expected "+ expectedRequest.get("name") + " Received " + jsonRsp.getString("name"));
        @SuppressWarnings("unchecked")
        Map<String, Object> expectedDeviceData = (Map<String, Object>) expectedRequest.get("data");

        for (Map.Entry<String, Object> entry : expectedDeviceData.entrySet()) {
            String key = entry.getKey();
            Object expectedValue = entry.getValue();
            Object actualValue = jsonRsp.get("data.'" + key + "'");
            //assertEquals(expectedValue, actualValue);
            assertEquals(String.valueOf(expectedValue), String.valueOf(actualValue), "Invalid value for attribute " + key + " Expected "+ String.valueOf(expectedValue) + " Received " + String.valueOf(expectedValue));
        }
        assertNotNull(jsonRsp.get("createdAt"), "Response missing creation time");
        checkTimeStamp(((Number) jsonRsp.get("createdAt")).longValue());

    }

    public void validateDeviceData(Device expectedRequest, JsonPath jsonRsp) {
        assertEquals(expectedRequest.getName(), jsonRsp.getString("name"), "Invalid value for attribute name Expected "+ expectedRequest.getName() + " Received " + jsonRsp.getString("name"));
        assertEquals(expectedRequest.getDeviceData().getPrice(), jsonRsp.getDouble("data.price"), "Invalid value for attribute price Expected "+ expectedRequest.getDeviceData().getPrice() + " Received " + jsonRsp.getDouble("data.price"));
        assertEquals(expectedRequest.getDeviceData().getYear(), jsonRsp.getInt("data.year"), "Invalid value for attribute year Expected "+ expectedRequest.getDeviceData().getYear() + " Received " + jsonRsp.getInt("data.year"));

    }

    public void checkTimeStamp(Long time){

        long now = System.currentTimeMillis();
        long diff = now - time;
        assertTrue(Duration.ofMillis(diff).toSeconds() < 5, "device creation/update time is not recent enough");
    }


}
