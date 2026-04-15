Feature: Device API validation

  @device @create
  Scenario: Create a new device
  Given I have a device with details
    | name         | Apple MacBook Pro 16 |
    | year         | 2019                 |
    | price        | 1849.99              |
  When I create a device
  Then The device is created successfully
  #These 2 steps are required to ensure the device is persisted correctly post update
  # The update response may not guarantee the device is persisted correctly.
  And I list the device by deviceID
  Then The listed device matches the created device

  @device @get
  Scenario: List a new device
    Given A device exists with details
      | name         | Apple MacBook Pro 15 |
      | year         | 2016                 |
      | price        | 1649.99              |
    When I list the device by deviceID
    Then The listed device matches the created device

  @device @update
  Scenario: Update an existing device with new device details
    Given A device exists with details
      | name         | Apple MacBook Pro 16 |
      | year         | 2019                 |
      | price        | 1849.99              |
    When I update the device with new device details
      | name         | Apple MacBook Pro 17 |
      | year         | 2026                 |
      | price        | 2049.99              |
    Then The device updated successfully
    #These 2 steps are required to ensure the device is persisted correctly post update
    # The update response may not guarantee the device is persisted correctly.
    And I list the device by deviceID
    Then The listed device matches the updated device

  @device @delete
  Scenario: Delete an existing device
    Given A device exists with details
      | name         | Apple MacBook Pro 16 |
      | year         | 2019                 |
      | price        | 1849.99              |
    When I delete the device
    Then The device gets deleted successfully
    And I list the device by deviceID
    And The device is not listed again

  #Observed that GET on /devices returns a default list of devices and not the one's
  #created. Those device objects had different shapes. So went with schema validation
  @device @get
  Scenario: List all devices
    When I list all devices
    Then All the devices are listed and matches the expected list schema

  #this test is just to prove that the data object within the Device object is flexible
  # and accepts any valid json structure
  @device @create
  Scenario: Create new device with flexible device data
    Given I have a device with flexible attributes
      | name         | Apple MacBook Pro 16 |
      | year         | 2019                 |
      | price        | 1849.99              |
      | cpumodel     | Intel core i9        |
      | storage      | 1TB                  |
    When I create a flexible device
    Then The device is created successfully with all the flexible attributes

  #Attempted to check if the device creation API validates the payload
  #Manual testing proved that the API literally accepts any payload even an empty object
  #Maynot be meaningful to add a test for a functionality that is not supported by the API
  @device @create
  Scenario: Create a empty device
    When I create an empty device
    Then Request is rejected as bad request

  @device @get
  Scenario: List a device that does not exist
    When I list the device by deviceID that doesnt exist in the system
    Then No device listed

  @device @update
  Scenario: update a device that does not exist
    When I update device that doesnt exists in the system with new device details
      | name         | Apple MacBook Pro 17 |
      | year         | 2026                 |
      | price        | 2049.99              |
    Then No device updated

  @device @delete
  Scenario: Delete a device that does not exist
    When I delete a device that doesnt exists in the system
    Then No device deleted



