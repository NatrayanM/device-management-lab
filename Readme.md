# Device Management Lab

## Overview

This project automates functional testing of a Device Management API using a BDD approach.
The solution validates core CRUD operations (Create, Read, Update, Delete) and focuses on:

Handling multiple API calls within a scenario
Sharing data between steps
Validating responses using JSONPath
Handling error cases and API-specific behavior

## Tech stack

Java
Maven
Cucumber (BDD)
JUnit
Rest Assured
Jackson (for request mapping)

## How to run tests
To run all tests
mvn clean test

To run based on tags
mvn clean test '-Dcucumber.filter.tags=@create'

## Scope
The following endpoints are covered:
POST /objects → Create a device
GET /objects/{id} → Retrieve a device by ID
GET /objects → Retrieve all devices
PUT /objects/{id} → Update a device
DELETE /objects/{id} → Delete a device

Implemented Test Scenarios
Happy Path
Create a new device
Retrieve a device by ID
Update an existing device
Delete an existing device
Verify deleted device is no longer retrievable
List all devices
Additional Functional Coverage
Create a device with flexible/dynamic attributes
Edge / Error Scenarios
Retrieve a non-existing device
Update a non-existing device
Delete a non-existing device

## Setup and Cleanup
Device IDs are stored in context after creation
Cleanup is handled post-scenario
Deletion is verified by doing retrieval post delete

## Known API Behaviors / Limitations
The API accepts highly flexible payloads, including arbitrary JSON structures
GET /objects returns a preconfigured dataset and does not reflect newly created devices
Payload validation is minimal; invalid or empty payloads may still be accepted

These behaviors influenced the design of test cases and assertions.