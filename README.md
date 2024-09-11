# Currency Converter App

This is a Currency Converter App that allows users to convert a wide range of currencies. The app utilizes the Fixer API to provide up-to-date exchange rates.

## Features

- **Currency Conversion**: Convert between a wide range of currencies.
- **Night Mode**: Toggle between day and night modes via the settings.
- **Conversion History**: Save and maintain conversion history in the database. Records are stored uniquely based on currency pairs, and amounts are updated over time.

## Getting Started

### Prerequisites

- **API Key**: The app uses the Fixer API. You need an API key to access the conversion rates. The API key should be saved in the `secrets.properties` file. [Fixer API Documentation](https://fixer.io/documentation)

### Steps to Use the App

1. **Open the App**: Launch the application on your device.
2. **Choose Currencies**: Select the currencies you want to convert between.
3. **Enter Amount**: Input the amount you wish to convert. The conversion will automatically be calculated and displayed.

### Night Mode

- **Enable Night Mode**: Navigate to the settings to enable or disable night mode according to your preference.

### Conversion History

- The app tracks conversion operations and stores them in the database.
- **Unique Records**: Each conversion is saved only once per currency pair. Amounts are updated as needed.

## Screenshots

![Home ](https://github.com/mohamedkhidr/Currency-converter/blob/master/imgs/IMG-20240911-WA0002.jpg)
![History](https://github.com/mohamedkhidr/Currency-converter/blob/master/imgs/IMG-20240911-WA0003.jpg)
![Settings](https://github.com/mohamedkhidr/Currency-converter/blob/master/imgs/IMG-20240911-WA0004.jpg)

## Installation

Clone the repository and build the project:

```bash
git clone (https://github.com/mohamedkhidr/Currency-converter)
cd Currency-converter
./gradlew build


Made by Me (Mohamed khidr)
