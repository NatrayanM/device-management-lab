package assertions;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Device;
import steps.TestContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeviceAssertions {

    private TestContext testContext;

    public DeviceAssertions(TestContext testContext) {
        this.testContext=testContext;
    }

    public void validateStatusCode(){
        Response actualResponse = testContext.getDeviceResponse();
        assertEquals(200, actualResponse.getStatusCode());
    }

    public void validateDeviceCreation(){
        Device expectedRequest = testContext.getDeviceRequest();
        JsonPath jsonRsp = testContext.getDeviceResponse().jsonPath();


        assertEquals(expectedRequest.getName(), jsonRsp.get("name"));
        assertEquals(expectedRequest.getDeviceData().getPrice(), jsonRsp.getDouble("data.price"));
        assertEquals(expectedRequest.getDeviceData().getYear(), jsonRsp.getInt("data.year"));

    }
}
