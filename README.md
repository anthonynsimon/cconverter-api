# cconverter-api
[![Build Status](https://travis-ci.org/anthonynsimon/cconverter-api.svg?branch=master)](https://travis-ci.org/anthonynsimon/cconverter-api/builds) 

API server for conversion and exchange rate querying.

## Setup

Simply clone the repo and start up the server using the gradle task `bootRun`. It will install the necessary dependencies.

```bash
$ git clone https://github.com/anthonynsimon/cconverter-api.git
$ cd cconverter-api
$ ./gradlew bootRun
```

Requires Java 1.8.

## Usage

The following URLs assume that the server is running on `localhost:8080`.

### Convert between currencies

To convert from a base currency to any other supported currency:

```
GET /api/convert?from={BASE_CURRENCY}&to={TARGET_CURRENCY}&amount={PRICE}
```
Example:

```
GET http://localhost:8080/api/convert?from=EUR&to=JPY&amount=110.1591
```

```json
{
  "fromCurrency": "EUR",
  "toCurrency": "JPY",
  "amountToConvert": 110.1591,
  "conversionResult": 13193.7554,
  "exchangeRate": 119.77
}
```

### Query Exchange Rates

To query rates for any supported base currency:

```
GET /api/rates/{CURRENCY_CODE}
```
Example:

```
GET http://localhost:8080/api/rates/eur
```

```json
{
  "base": "EUR",
  "date": "2017-02-21",
  "rates": {
    "AUD": 1.3762,
    "BGN": 1.9558,
    "BRL": 3.2623,
    "CAD": 1.384,
    "CHF": 1.0639,
    "CNY": 7.2545,
    "CZK": 27.021,
    "DKK": 7.4333,
    "GBP": 0.8486,
    "HKD": 8.1783,
    "HRK": 7.452,
    "HUF": 306.9,
    "IDR": 14091,
    "ILS": 3.9011,
    "INR": 70.53,
    "JPY": 119.77,
    "KRW": 1208.7,
    "MXN": 21.528,
    "MYR": 4.6973,
    "NOK": 8.8138,
    "NZD": 1.4757,
    "PHP": 52.99,
    "PLN": 4.3064,
    "RON": 4.5151,
    "RUB": 60.839,
    "SEK": 9.469,
    "SGD": 1.4989,
    "THB": 36.916,
    "TRY": 3.8213,
    "USD": 1.0537,
    "ZAR": 13.867
  }
}

```


It currently supports the following currency codes (case insensitive):

`AUD`, `BGN`, `BRL`, `CAD`, `CHF`, `CNY`, `CZK`, `DKK`, `GBP`, `HKD`, `HRK`, `HUF`,
`IDR`, `ILS`, `INR`, `JPY`, `KRW`, `MXN`, `MYR`, `NOK`, `NZD`, `USD`, `PHP`, `PLN`,
`RON`, `RUB`, `SEK`, `SGD`, `THB`, `TRY`, `ZAR`, `EUR`.

## Exchange Rate Source

Thanks to [fixer.io](http://fixer.io/) for providing the public API to query exchange rates.