package steps;

import assertions.DeviceAssertions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import mapper.DeviceMapper;
import service.DeviceService;

import java.util.Map;

public class DeviceStepDefinitions {

    private DeviceService deviceService;
    private DeviceMapper deviceMapper;
    private DeviceAssertions deviceAssertions;
    private TestContext testContext;

    public DeviceStepDefinitions(DeviceService deviceService,
                                 DeviceMapper deviceMapper,
                                 TestContext testContext,
                                 DeviceAssertions deviceAssertions){
    this.deviceService =deviceService;
    this.deviceMapper = deviceMapper;
    this.testContext = testContext;
    this.deviceAssertions = deviceAssertions;
    }

    @Given("I have a device with details")
    public void setupDeviceData(DataTable data){
        Map<String, String> dataMap = data.asMap();
        deviceService.setupDevice(deviceMapper.mapper(dataMap));
    }

    @When("I create a device")
    public void createDevice(){
        deviceService.createDevice();
    }

    @Then("The device is created successfully")
    public void validateDeviceCreation(){
        deviceAssertions.validateStatusCode();;
        deviceAssertions.validateDeviceCreation();
    }
}
