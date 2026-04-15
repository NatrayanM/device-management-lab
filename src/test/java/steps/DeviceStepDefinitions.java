package steps;

import assertions.DeviceAssertions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import mapper.DeviceMapper;
import service.DeviceService;

import java.util.HashMap;
import java.util.Map;

public class DeviceStepDefinitions {

    private DeviceService deviceService;
    private DeviceMapper deviceMapper;
    private DeviceAssertions deviceAssertions;

    public DeviceStepDefinitions(DeviceService deviceService,
                                 DeviceMapper deviceMapper,
                                 DeviceAssertions deviceAssertions){
    this.deviceService =deviceService;
    this.deviceMapper = deviceMapper;
    this.deviceAssertions = deviceAssertions;
    }

    @Given("I have a device with details")
    public void setupDeviceData(DataTable data){
        Map<String, String> dataMap = data.asMap();
        deviceService.setupDevice(deviceMapper.mapper(dataMap));
    }

    @Given("A device exists with details")
    public void setupDevice(DataTable data) {
        Map<String, String> dataMap = data.asMap();
        deviceService.setupDevice(deviceMapper.mapper(dataMap));
        deviceService.createDevice();
    }

    @When("I create a device")
    public void createDevice(){
        deviceService.createDevice();
    }

    @When("I delete the device")
    public void deleteDevice(){
        deviceService.deleteDevice();
    }

    @When("I delete a device that doesnt exists in the system")
    public void deleteDeviceByRandomId(){
        deviceService.deleteDeviceByRandomId();
    }

    @Then("The device is created successfully")
    public void validateDeviceCreation(){
        deviceAssertions.validateStatusCode();;
        deviceAssertions.validateDeviceCreation();
    }

    @Then("The listed device matches the created device")
    public void validateListedDeviceAfterCreate(){
        deviceAssertions.validateStatusCode();;
        deviceAssertions.validateListedDeviceAfterCreate();
    }

    @Then("The listed device matches the updated device")
    public void validateListedDevice(){
        deviceAssertions.validateStatusCode();;
        deviceAssertions.validateListedDeviceAfterUpdate();
    }


    @When("I list the device by deviceID")
    public void listDeviceById(){
        deviceService.getDeviceById();
    }

    @When("I list the device by deviceID that doesnt exist in the system")
    public void listDeviceByRandomId(){
        deviceService.getDeviceByRandomId();
    }

    @When("I list all devices")
    public void listDevice(){
        deviceService.getDevice();
    }

    @When("I update the device with new device details")
    public void updateDeviceDetails(DataTable data){
        Map<String, String> dataMap = data.asMap();
        deviceService.updateDevice(deviceMapper.mapper(dataMap));
        deviceService.updateDevice();
    }

    @When("I update device that doesnt exists in the system with new device details")
    public void updateDeviceDetailsByRandomId(DataTable data){
        Map<String, String> dataMap = data.asMap();
        deviceService.updateDevice(deviceMapper.mapper(dataMap));
        deviceService.updateDeviceByRandomId();
    }

    @Then("The device updated successfully")
    public void validateUpdatedDevice(){
        deviceAssertions.validateStatusCode();;
        deviceAssertions.validateDeviceUpdate();
    }

    @Then("The device gets deleted successfully")
    public void validateDeviceDeletion(){
        deviceAssertions.validateStatusCode();;
    }

    @Then("The device is not listed again")
    @Then("No device listed")
    public void deviceNotFound(){
        deviceAssertions.deviceNotFound();
    }

    @Then("No device updated")
    @Then("No device deleted")
    public void deviceNotUpdated(){
        deviceAssertions.deviceNotUpdated();
    }

    @Then("All the devices are listed and matches the expected list schema")
    public void listAllDevices(){
        deviceAssertions.validateStatusCode();
        deviceAssertions.validateDeviceList();
    }

    @Given("I have a device with flexible attributes")
    public void setupFlexibleDevice(DataTable data) {
        Map<String, String> dataMap = data.asMap();
        deviceService.prepareCustomDevicePayload(dataMap);
    }

    @When("I create an empty device")
    public void createEmptyDevice(){
        deviceService.createEmptyDevice();
    }

    @Then("Request is rejected as bad request")
    public void validateDeviceCreationFailureBadRequest(){
        deviceAssertions.verifyBadRequest();
    }

    @Given("I create a flexible device")
    public void createFlexibleDevice() {
        deviceService.createFlexibleDevice();
    }

    @Then("The device is created successfully with all the flexible attributes")
    public void validateFlexibleDeviceCreation(){
        deviceAssertions.validateStatusCode();;
        deviceAssertions.validateFlexibleDeviceCreation();
    }

}
