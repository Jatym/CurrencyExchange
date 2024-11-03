# CurrencyExchange

## Overview
CurrencyExchange is a Kotlin-based application built using Spring Boot. It is designed to provide currency exchange functionalities with real-time exchange rates.

## Technologies Used
- **Language**: Kotlin
- **Framework**: Spring Boot
- **Build Tools**: Gradle
- **Database**: H2
- **External APIs**: Integration with NBP API

## Test case using IDEA http-client
1. To create user account call:
```http
   POST http://localhost:8080/user-account
   Content-Type: application/json
```
```json
    {
        "firstName": "John",
        "lastName": "Doe",
        "initialBalance" : 10.00
    }
```

    This step will crete bank account for PLN

2. To crete bank account for USD call:
```http
   POST http://localhost:8080/user-account/1/bank-account
   Content-Type: application/json
```
```json
    {
        "initialBalance": 20.00,
        "currency": "USD"
    }
```

3. To buy or sell dollars call:
```http
   POST http://localhost:8080/user-account/1/operation
   Content-Type: application/json
```
```json
    {
        "currency": "USD",
        "action": "BUY",
        "value": 10
    }
```
    In this step you can exchange USD to PLN if action = SELL or exchange PLN to USD if action = BUY

4. To check bank accout balance call:
```http
   GET http://localhost:8080/user-account/1/bank-account/1/balance
```

