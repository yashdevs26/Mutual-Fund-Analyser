# Mutual Fund Analyzer

Mutual Fund Analyzer is a **proof-of-concept (PoC) application** consisting of multiple microservices that analyze and provide data pertaining to mutual funds. This project demonstrates the integration of various technologies and concepts in microservices architecture.

## Overview

The microservices in this application work together to provide:
1. **Fundamental Information**: Using a web scraper and REST APIs, the application fetches key details such as:
   - Mutual Fund Scheme Details (code, ISIN, category, benchmark, etc)
   - Asset under Management
   - Important data driven by fund holdings (p/e, p/b, holding concentration, etc)
   - Historical NAVs (Net Asset Values) over 1000 active trading days of India
2. **Technical Analysis**: An analysis engine calculates technical parameters over different time periods and delivers the cumulative result as a JSON response.
   - Standard Deviation
   - CAGR
   - Expense Adjusted Returns
   - RR
   - MACD
   - RSI
   - MDD
   - EMA
   - Analytical Ratios
   - and many more...

### How It Works
- Accepts a `scheme` as a URL parameter, where the input format is `<fund provider> <fund type>` (e.g., "hdfc mid cap").
- Scrapes and retrieves fundamental data from online sources like Groww and uses open source API for historical NAVs.
- Performs computations for technical parameters and returns the results in JSON format.

---

## Key Features
- **Microservices Integration**: Demonstrates the communication and coordination between multiple RESTful APIs.
- **Custom Mathematical Calculations**: All analysis and computational logic is hand-written, utilizing complex data structures, iteration techniques, and advanced data handling methods. The computations involve:
  - Aggregation of historical NAVs over time to calculate returns.
  - Moving averages and trends for performance evaluation.
  - Percentage-based gain/loss calculations across different timeframes.
  - Indicators like RSI (Relative Strength Index), MACD (Moving Average Convergence Divergence), and Sharpe ratio.
- **Service Discovery**: Uses Eureka Naming Server for dynamic service registration and lookup.
- **API Gateway**: Configured with custom filters and route locators for request routing.
- **Dynamic Load Balancing**: Achieved via Feign Client for better scalability.

---

## Next Steps
The following enhancements are planned:
1. **Spring Cloud Config Server Integration**: Centralized configuration for properties shared across all microservices.
2. **Error Handling**: Improved error responses inspired by the Opti Tracker Application.
3. **Code Optimization**: Refinements to data structures and iteration logic for better performance.
4. **Vaadin Integration**: Adding a backend server layer for data visualization using Vaadin.

---

## Setup Instructions
1. Clone the repository:  
   ```bash
   git clone https://github.com/yourusername/mutual-fund-analyzer.git

2. Navigate to the project directory and build the services using Maven.
3. Start the Eureka Naming Server, followed by the API Gateway.
4. Launch each microservice and access the API at the gateway endpoint.

5. Example Request
   ```bash
   GET localhost:8765/mf-analysis/?scheme=hdfc mid cap


## JSON Response Example (Full Data)

```json
{
    "schemeCode": "145206",
    "fundHouse": "Tata Mutual Fund",
    "isInGrowthNo": "INF277K011O1",
    "fundName": "Tata Small Cap Fund Direct Growth",
    "fundCategory": "Equity Scheme - Small Cap Fund",
    "currentNav": "₹42.47",
    "currentAum": "₹9,699.24Cr",
    "expenseRatio": "0.35%",
    "fundBenchmark": "NIFTY Smallcap 250 Total Return Index",
    "technicals": {
        "top5HoldingsConcentration": "19%",
        "top20HoldingsConcentration": "54%",
        "priceToEarningRatio": "7.14",
        "priceToBookRatio": "3.74",
        "annualizedReturn": "31.92%",
        "cagr": "31.92%",
        "alphaRatio": "7.14",
        "betaRatio": "0.70",
        "oneYearStandardDeviation": "4.70",
        "sharpieRatio": "1.34",
        "sortinoRatio": "2.31",
        "maximumDrawdown": "0.73",
        "netReturnWithoutExpenses": "31.57%",
        "oneYearAnnualisedVolatility": "74.02",
        "rollingReturn": {
            "Rolling Return for 22 days": "40.01%",
            "Rolling Return for 66 days": "59.95%",
            "Rolling Return for 110 days": "118.94%",
            "Rolling Return for 176 days": "88.34%",
            "Rolling Return for 264 days": "61.02%",
            "Rolling Return for 396 days": "37.35%",
            "Rolling Return for 548 days": "31.88%",
            "Rolling Return for 792 days": "33.40%"
        },
        "movingAverage": {
            "SMA for 50 days": [
                46.41,
                47.97,
                45.26,
                40.37,
                36.32,
                35.28,
                32.36,
                30.97,
                28.47,
                25.67,
                25.6,
                25.35,
                23.9,
                21.4,
                21.93,
                22.55,
                23.09,
                21.92,
                20.55,
                17.2
            ],
            "SMA for 200 days": [
                44.64,
                42.53,
                40.07,
                37.24,
                34.84,
                33.02,
                31.26,
                29.27,
                27.88,
                26.67,
                25.58,
                24.54,
                23.78,
                22.98,
                22.54,
                22.28,
                22.32,
                22.2,
                21.59,
                20.15
            ],
            "SMA for 500 days": [
                36.28,
                35.31,
                34.21,
                33.04,
                31.85,
                30.7,
                29.54,
                28.61,
                27.81,
                27.09,
                26.45,
                25.84,
                25.33,
                24.84,
                24.33,
                23.86,
                23.37,
                22.86,
                22.36,
                21.87
            ],
            "SMA for 700 days": [
                32.62,
                32.12,
                31.57,
                31.05,
                30.55,
                30.01,
                29.47,
                28.94,
                28.46,
                27.97,
                27.48,
                27.07,
                26.67,
                26.26,
                25.88,
                25.48,
                25.04,
                24.62,
                24.19,
                23.76
            ]
        },
        "relativeStrengthIndex": {
            "RSI for 30 days": [
                72.06,
                52.68,
                29.08,
                45.25,
                29.99,
                34.39,
                12.92,
                54.3,
                47.31,
                30.01,
                64.37,
                64.16,
                42.92,
                45.25,
                9.63
            ],
            "RSI for 90 days": [
                57.58,
                43.5,
                30.77,
                38.75,
                36.03,
                38.92,
                21.4,
                43.77,
                45.69,
                33.92,
                47.68,
                56.04,
                52.85,
                43.14,
                26.83
            ],
            "RSI for 200 days": [
                46.54,
                40.81,
                37.08,
                41.8,
                40.59,
                33.34,
                39.41,
                38.79,
                41.07,
                46.11,
                45.6,
                47.43,
                51.33,
                45.62,
                37.15
            ],
            "RSI for 500 days": [
                43.49,
                40.44,
                38.74,
                38.54,
                37.35,
                39.52,
                40.45,
                41.57,
                41.73,
                43.87,
                43.82,
                44.11,
                43.9,
                43.47,
                43.41
            ],
            "RSI for 700 days": [
                43.45,
                41.89,
                42.32,
                41.81,
                40.83,
                41.3,
                41.28,
                41.16,
                43.6,
                41.95,
                42.17,
                41.18,
                41.01,
                40.92,
                40.5
            ]
        },
        "macd": {
            "15D Short, 45D Long, 12D Signal": [
                0.0,
                0.15,
                -0.14,
                -0.85,
                -1.03,
                -0.16,
                -0.64,
                -0.02,
                -0.2,
                -0.39,
                0.56,
                0.1,
                -0.08,
                0.02,
                0.31,
                1.04,
                0.14,
                -0.12,
                -0.44,
                -0.4
            ]
        }
    }
}


