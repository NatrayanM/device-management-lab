Feature: Device API validation

  @device
  Scenario:
  Given I have a device with details
    | name         | Apple MacBook Pro 16 |
    | year         | 2019                 |
    | price        | 1849.99              |
    | hardDiskSize | 1 TB                 |
  When I create a device
  Then The device is created successfully
