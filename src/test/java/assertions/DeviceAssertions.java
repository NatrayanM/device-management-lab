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

    private TestContext testContext;

    public DeviceAssertions(TestContext testContext) {
        this.testContext=testContext;
    }

    public void validateStatusCode(){
        Response actualResponse = testContext.getLastDeviceResponse();
        assertEquals(200, actualResponse.getStatusCode());
    }

    public void validateDeviceCreation(){
        Device expectedRequest = testContext.getCreatedDeviceRequest();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();

        validateDeviceData(expectedRequest, jsonRsp);

        assertNotNull(jsonRsp.get("createdAt"));
        checkTimeStamp(((Number) jsonRsp.get("createdAt")).longValue());

    }

    public void validateDeviceUpdate(){
        Device expectedRequest = testContext.getUpdatedDeviceRequest();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();


        validateDeviceData(expectedRequest, jsonRsp);

        assertEquals(testContext.getCreatedDeviceId(), jsonRsp.getString("id"));
        assertNotNull(jsonRsp.get("updatedAt"));
        checkTimeStamp(((Number) jsonRsp.get("updatedAt")).longValue());
    }

    public void validateListedDeviceAfterCreate(){
        Device expectedRequest = testContext.getCreatedDeviceRequest();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();
        assertEquals(testContext.getCreatedDeviceId(), jsonRsp.getString("id"));
        validateDeviceData(expectedRequest, jsonRsp);

    }

    public void validateListedDeviceAfterUpdate(){
        Device expectedRequest = testContext.getUpdatedDeviceRequest();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();
        assertEquals(testContext.getCreatedDeviceId(), jsonRsp.getString("id"));
        validateDeviceData(expectedRequest, jsonRsp);

    }

    public void validateDeviceList(){
        Response actualResponse = testContext.getLastDeviceResponse();
        List<Object> devices = testContext.getLastDeviceResponse().jsonPath().getList("$");

        assertFalse(devices.isEmpty());

        actualResponse.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schema/DeviceSchema.json"));
    }

    public void deviceNotFound(){
        Response actualResponse = testContext.getLastDeviceResponse();
        System.out.println(actualResponse.asString());
        assertEquals(404, actualResponse.getStatusCode());
        assertTrue(actualResponse.body().asString().contains("not found"));
    }

    public void verifyBadRequest(){
        Response actualResponse = testContext.getLastDeviceResponse();
        System.out.println(actualResponse.asString());
        assertEquals(400, actualResponse.getStatusCode());
        assertTrue(actualResponse.body().asString().contains("body is missing"));
    }

    public void deviceNotUpdated(){
        Response actualResponse = testContext.getLastDeviceResponse();
        System.out.println(actualResponse.asString());
        assertEquals(404, actualResponse.getStatusCode());
        assertTrue(actualResponse.body().asString().contains("doesn't exist"));
    }
    public void validateFlexibleDeviceCreation(){
        Map<String, Object> expectedRequest = testContext.getCustomRequestPayload();
        JsonPath jsonRsp = testContext.getLastDeviceResponse().jsonPath();

        assertEquals(expectedRequest.get("name"), jsonRsp.getString("name"));
        @SuppressWarnings("unchecked")
        Map<String, Object> expectedDeviceData = (Map<String, Object>) expectedRequest.get("data");

        for (Map.Entry<String, Object> entry : expectedDeviceData.entrySet()) {
            String key = entry.getKey();
            Object expectedValue = entry.getValue();
            Object actualValue = jsonRsp.get("data.'" + key + "'");
            //assertEquals(expectedValue, actualValue);
            assertEquals(String.valueOf(expectedValue), String.valueOf(actualValue));
        }
        assertNotNull(jsonRsp.get("createdAt"));
        checkTimeStamp(((Number) jsonRsp.get("createdAt")).longValue());

    }

    public void validateDeviceData(Device expectedRequest, JsonPath jsonRsp) {
        assertEquals(expectedRequest.getName(), jsonRsp.getString("name"));
        assertEquals(expectedRequest.getDeviceData().getPrice(), jsonRsp.getDouble("data.price"));
        assertEquals(expectedRequest.getDeviceData().getYear(), jsonRsp.getInt("data.year"));

    }

    public void checkTimeStamp(Long time){

        long now = System.currentTimeMillis();
        long diff = now - time;
        assertTrue(Duration.ofMillis(diff).toSeconds() < 5);
    }


}
