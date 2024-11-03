# CurrencyExchange

## Overview
CurrencyExchange is a Kotlin-based application built using Spring Boot. It is designed to provide efficient and secure currency exchange functionalities with real-time data updates.

## Features
- **Real-time currency conversion**: Utilize live exchange rates for accurate currency conversions.
- **Comprehensive currency support**: Exchange between a wide variety of global currencies.
- **Robust backend**: Built with Spring Boot for reliable performance.
- **Secure and scalable**: Designed with security best practices and scalability in mind.
- **User-friendly interface**: Clean and straightforward UI for seamless interaction.

## Installation

To set up and run the CurrencyExchange application locally:

1. Clone the repository:
    ```bash
    git clone https://github.com/Jatym/CurrencyExchange.git
    ```

2. Navigate to the project directory:
    ```bash
    cd CurrencyExchange
    ```

3. Build the project using Gradle or Maven:
    ```bash
    ./gradlew build
    ```
   or
    ```bash
    mvn clean install
    ```

4. Run the application:
    ```bash
    ./gradlew bootRun
    ```
   or
    ```bash
    java -jar build/libs/currency-exchange-0.0.1-SNAPSHOT.jar
    ```

## Usage

1. Access the application by navigating to `http://localhost:8080` in your web browser.
2. Use the interface to select currencies and input the amount for conversion.
3. View real-time conversion results and manage transactions as needed.

## Technologies Used
- **Language**: Kotlin
- **Framework**: Spring Boot
- **Build Tools**: Gradle or Maven
- **Database**: H2 / PostgreSQL (for storing exchange history or transaction logs)
- **External APIs**: Integration with services like [Open Exchange Rates](https://openexchangerates.org/) for currency data.

## Contributing
Contributions are encouraged! Please:

1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add an AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Create a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact
For questions or feedback, feel free to reach out:

- **Author**: [Jatym](https://github.com/Jatym)
- **Email**: your-email@example.com

## Acknowledgements
- Thanks to Spring Boot and Kotlin for providing robust and modern development frameworks.
- Special appreciation for currency data providers like [Open Exchange Rates](https://openexchangerates.org/).

---

Thank you for using CurrencyExchange! We hope it makes currency management easier and more efficient.