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

  @device @get
  Scenario: List all devices
    When I list all devices
    Then All the devices are listed correctly


