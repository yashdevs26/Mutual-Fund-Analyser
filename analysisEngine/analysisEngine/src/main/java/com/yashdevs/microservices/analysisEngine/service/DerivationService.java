package com.yashdevs.microservices.analysisEngine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yashdevs.dataScraper.constant.NumericConstant;
import com.yashdevs.dataScraper.entity.MFAPIResponseDTO.NAVObject;

@Service
public class DerivationService {

	// 1. Annualized Returns
	public String calculateAnnualizedReturn(List<NAVObject> navObj) throws ParseException {
		if (navObj.isEmpty())
			return NumericConstant.DEFAULT_ZERO; // Handle empty data

		double startNav = navObj.get(0).getNav().doubleValue(); // First NAV
		double endNav = navObj.get(navObj.size() - 1).getNav().doubleValue(); // Last NAV

		// Calculate time difference in years
		long timeDiff = NumericConstant.DATE_DD_MM_YYYY.parse(navObj.get(navObj.size() - 1).getDate()).getTime()
				- NumericConstant.DATE_DD_MM_YYYY.parse(navObj.get(0).getDate()).getTime();
		double years = timeDiff / (1000.0 * 60 * 60 * 24 * 365);

		return BigDecimal.valueOf(Math.pow(endNav / startNav, 1 / years) - 1).multiply(BigDecimal.valueOf(100))
				.setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN).toString() + "%"; // Annualized return formula
	}

	// 2. Rolling Returns
	public Map<String, String> calculateRollingReturns(List<NAVObject> navObj) throws ParseException {
		Map<String, String> rollingReturns = new HashMap<>();
		List<Integer> rollingPeriods = new ArrayList<>(NumericConstant.ROLLING_RETURN_DAYS);
		for (int rollingPeriodInDays : rollingPeriods) {
			for (int i = 0; i <= navObj.size() - rollingPeriodInDays; i++) {
				double startNav = navObj.get(i).getNav().doubleValue(); // NAV at start of period
				double endNav = navObj.get(i + rollingPeriodInDays - 1).getNav().doubleValue(); // NAV at end of period
				long timeDiff = NumericConstant.DATE_DD_MM_YYYY.parse(navObj.get(i + rollingPeriodInDays - 1).getDate())
						.getTime() - NumericConstant.DATE_DD_MM_YYYY.parse(navObj.get(i).getDate()).getTime();
				double years = timeDiff / (1000.0 * 60 * 60 * 24 * 365); // Convert milliseconds to years

				String rollingReturn = BigDecimal.valueOf(Math.pow(endNav / startNav, 1 / years) - 1)
						.multiply(BigDecimal.valueOf(100))
						.setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN).toString() + "%"; // rolling return
				rollingReturns.put("Rolling Return for " + rollingPeriodInDays + " days", rollingReturn);
			}
		}

		// Sort by days in ascending order
		Map<String, String> sortedRollingReturns = rollingReturns.entrySet().stream().sorted((entry1, entry2) -> {
			int days1 = Integer.parseInt(entry1.getKey().replaceAll("[^0-9]", ""));
			int days2 = Integer.parseInt(entry2.getKey().replaceAll("[^0-9]", ""));
			return Integer.compare(days1, days2);
		}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		return sortedRollingReturns;
	}

	// 3. CAGR (Compound Annual Growth Rate)
	public String calculateCAGR(List<NAVObject> navObj) throws ParseException {
		if (navObj.isEmpty())
			return NumericConstant.DEFAULT_ZERO; // Handle empty data

		double startNav = navObj.get(0).getNav().doubleValue(); // First NAV
		double endNav = navObj.get(navObj.size() - 1).getNav().doubleValue(); // Last NAV

		// Calculate time difference in years
		long timeDiff = NumericConstant.DATE_DD_MM_YYYY.parse(navObj.get(navObj.size() - 1).getDate()).getTime()
				- NumericConstant.DATE_DD_MM_YYYY.parse(navObj.get(0).getDate()).getTime();
		double years = timeDiff / (1000.0 * 60 * 60 * 24 * 365);

		return BigDecimal.valueOf(Math.pow(endNav / startNav, 1 / years) - 1).multiply(BigDecimal.valueOf(100))
				.setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN).toString() + "%"; // CAGR formula
	}

	// 4. Standard Deviation
	public String calculateStandardDeviation(List<NAVObject> navObj) {
	    if (navObj.isEmpty())
	        return NumericConstant.DEFAULT_ZERO; // Handle empty data

	    // Limit to the first 504 records (or less if there are fewer than 504)
	    List<NAVObject> limitedNavObj = navObj.subList(0, Math.min(NumericConstant.ANNUAL_TRADE_DAYS, navObj.size()));

	    // Calculate mean NAV
	    double mean = limitedNavObj.stream().mapToDouble(obj -> obj.getNav().doubleValue()).average().orElse(0.0);

	    // Calculate variance
	    double variance = limitedNavObj.stream().mapToDouble(obj -> Math.pow(obj.getNav().doubleValue() - mean, 2)).sum()
	            / limitedNavObj.size();  // Use size of the limited list

	    return BigDecimal.valueOf(Math.sqrt(variance)).setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN)
	            .toString(); // Standard deviation
	}

	/*// 5. Sharpe Ratio
	public String calculateSharpeRatio(List<NAVObject> navObj, double riskFreeRate)
			throws NumberFormatException, ParseException {
		double averageReturn = Double.parseDouble(calculateAnnualizedReturn(navObj)); // Expected return
		double standardDeviation = Double.parseDouble(calculateStandardDeviation(navObj)); // Risk (volatility)
	
		return BigDecimal.valueOf((averageReturn - riskFreeRate) / standardDeviation)
				.setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN).toString(); // Sharpe ratio formula
	}*/

	// 6. Moving Averages (SMA for 50, 200, 500, 700 days)
	public Map<String, List<Double>> calculateSMAs(List<NAVObject> navObj) {
		Map<String, List<Double>> smaMap = new HashMap<>();
		List<Integer> periods = new ArrayList<>(NumericConstant.SMA_DAYS);
		int maxDisplayCount = NumericConstant.SMA_MAX_PLOT_POINT; // Number of SMA values to display

		for (int period : periods) {
			List<Double> smaList = new ArrayList<>();
			for (int i = 0; i <= navObj.size() - period; i++) {
				double sum = 0;
				// Sum up NAVs for the given period
				for (int j = i; j < i + period; j++) {
					sum += navObj.get(j).getNav().doubleValue();
				}
				smaList.add(BigDecimal.valueOf(sum / period)
						.setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN).doubleValue()); // Calculate average (SMA)
			}

			// Sample the SMA values to reduce the list size to maxDisplayCount
			List<Double> sampledSMAList = new ArrayList<>();
			int step = Math.max(1, smaList.size() / maxDisplayCount);
			for (int i = 0; i < smaList.size() && sampledSMAList.size() < maxDisplayCount; i += step) {
				sampledSMAList.add(smaList.get(i));
			}

			smaMap.put("SMA for " + period + " days", sampledSMAList); // Store sampled SMA for the period
		}

		// Sort the SMA map by period in ascending order
		Map<String, List<Double>> sortedSmaMap = smaMap.entrySet().stream().sorted((entry1, entry2) -> {
			int period1 = Integer.parseInt(entry1.getKey().replaceAll("[^0-9]", ""));
			int period2 = Integer.parseInt(entry2.getKey().replaceAll("[^0-9]", ""));
			return Integer.compare(period1, period2);
		}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return sortedSmaMap;
	}

	// 7. Maximum Drawdown
	public String calculateMaximumDrawdown(List<NAVObject> navObj) {
		double maxDrawdown = 0.0;
		double peak = navObj.get(0).getNav().doubleValue(); // Initialize peak as the first NAV

		for (NAVObject obj : navObj) {
			if (obj.getNav().doubleValue() > peak) {
				peak = obj.getNav().doubleValue(); // Update peak if a new high is found
			}
			double drawdown = (peak - obj.getNav().doubleValue()) / peak; // Calculate drawdown
			if (drawdown > maxDrawdown) {
				maxDrawdown = drawdown; // Update maximum drawdown if current is larger
			}
		}
		return BigDecimal.valueOf(maxDrawdown).setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN)
				.toString();
	}

	// 8. Net Returns with Expense Ratio
	public String calculateNetReturns(List<NAVObject> navObj, double expenseRatio)
			throws NumberFormatException, ParseException {
		double annualizedReturn = Double.parseDouble(calculateAnnualizedReturn(navObj).split("%")[0]) / 100; // Annualized return
		return BigDecimal.valueOf(annualizedReturn - (expenseRatio / 100)).multiply(BigDecimal.valueOf(100))
				.setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN).toString() + "%"; // Subtract expense ratio
	}

	// 9. Annualized Volatility
	public String calculateAnnualizedVolatility(List<NAVObject> navObj) throws NumberFormatException, ParseException {

	    // Calculate daily standard deviation for the limited data
	    double dailyVolatility = Double.parseDouble(calculateStandardDeviation(navObj)); 

	    // Calculate annualized volatility (248 trading days per year)
	    return BigDecimal.valueOf(dailyVolatility * Math.sqrt(NumericConstant.ANNUAL_TRADE_DAYS))
	            .setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN)
	            .toString(); // Return as percentage
	}

	// 10. RSI (Relative Strength Index)
	public Map<String, List<Double>> calculateRSI(List<NAVObject> navObj) {
		Map<String, List<Double>> rsiMap = new HashMap<>();
		List<Integer> periods = new ArrayList<>(NumericConstant.RSI_DAYS);
		int maxDisplayCount = 15; // Number of RSI values to display

		for (int period : periods) {
			List<Double> rsiList = new ArrayList<>();

			for (int i = period; i < navObj.size(); i++) {
				double gain = 0.0;
				double loss = 0.0;

				// Calculate gains and losses over the period
				for (int j = i - period; j < i; j++) {
					double change = navObj.get(j + 1).getNav().doubleValue() - navObj.get(j).getNav().doubleValue();
					if (change > 0) {
						gain += change;
					} else {
						loss -= change;
					}
				}

				double avgGain = gain / period; // Average gain
				double avgLoss = loss / period; // Average loss
				double rs = avgLoss == 0 ? 100 : avgGain / avgLoss; // Relative strength
				double rsi = BigDecimal.valueOf(100 - (100 / (1 + rs)))
						.setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN).doubleValue(); // RSI formula
				rsiList.add(rsi);
			}

			// Sample the RSI values to reduce the list size to maxDisplayCount
			List<Double> sampledRSI = new ArrayList<>();
			int step = Math.max(1, rsiList.size() / maxDisplayCount);
			for (int i = 0; i < rsiList.size() && sampledRSI.size() < maxDisplayCount; i += step) {
				sampledRSI.add(rsiList.get(i));
			}

			rsiMap.put("RSI for " + period + " days", sampledRSI); // Store sampled RSI for the period
		}

		// Sort the RSI map by period in ascending order
		Map<String, List<Double>> sortedRsiMap = rsiMap.entrySet().stream().sorted((entry1, entry2) -> {
			int period1 = Integer.parseInt(entry1.getKey().replaceAll("[^0-9]", ""));
			int period2 = Integer.parseInt(entry2.getKey().replaceAll("[^0-9]", ""));
			return Integer.compare(period1, period2);
		}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return sortedRsiMap;
	}

	// 11. MACD (Moving Average Convergence Divergence)
	public Map<String, List<Double>> calculateMACD(List<NAVObject> navObj, int shortPeriod, int longPeriod,
			int signalPeriod) {
		List<Double> macdLine = new ArrayList<>();
		List<Double> histogram = new ArrayList<>();
		Map<String, List<Double>> macdMap = new HashMap<>();

		// Calculate short-term and long-term EMAs
		List<Double> shortEMA = calculateEMA(navObj, shortPeriod);
		List<Double> longEMA = calculateEMA(navObj, longPeriod);

		// Calculate MACD line (difference between short and long EMAs)
		for (int i = 0; i < shortEMA.size(); i++) {
			macdLine.add(shortEMA.get(i) - longEMA.get(i));
		}

		// Calculate signal line (EMA of MACD line)
		List<Double> signalLine = calculateEMAFromList(macdLine, signalPeriod);

		// Calculate histogram (difference between MACD and signal line)
		for (int i = 0; i < signalLine.size(); i++) {
			histogram.add(BigDecimal.valueOf(macdLine.get(i) - signalLine.get(i))
					.setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN).doubleValue());
		}

		// Sample the MACD histogram to reduce the list size
		int maxDisplayCount = NumericConstant.SMA_MAX_PLOT_POINT; // Number of MACD histogram values to display
		List<Double> sampledHistogram = new ArrayList<>();
		int step = Math.max(1, histogram.size() / maxDisplayCount);
		for (int i = 0; i < histogram.size() && sampledHistogram.size() < maxDisplayCount; i += step) {
			sampledHistogram.add(histogram.get(i));
		}
		macdMap.put(NumericConstant.MACD_SHORT + "D Short, " + NumericConstant.MACD_LONG + "D Long, " 
				+ NumericConstant.MACD_SIGNAL + "D Signal", sampledHistogram);
		return macdMap; // Return only sampled MACD histogram values
	}

	//WIP: unavailability of riskFreeRate and market returns
	/*// 12. Alpha Calculation -- 
	public static double calculateAlpha(List<NAVObject> navObj, double beta, double riskFreeRate, double marketReturn) {
		double portfolioReturn = calculateAnnualizedReturn(navObj);
		return portfolioReturn - (riskFreeRate + beta * (marketReturn - riskFreeRate)); // Alpha formula
	}
	
	// 13. Beta Calculation
	public static double calculateBeta(List<NAVObject> navObj, List<Double> marketReturns) {
		List<Double> portfolioReturns = calculateDailyReturns(navObj);
		double covariance = calculateCovariance(portfolioReturns, marketReturns);
		double marketVariance = calculateVariance(marketReturns);
		return covariance / marketVariance; // Beta formula
	}
	
	// 14. Sortino Ratio
	public static double calculateSortinoRatio(List<NAVObject> navObj, double riskFreeRate) {
		double portfolioReturn = calculateAnnualizedReturn(navObj);
		double downsideDeviation = calculateDownsideDeviation(navObj, riskFreeRate);
		return (portfolioReturn - riskFreeRate) / downsideDeviation; // Sortino formula
	}
	
	// 16. Treynor Ratio
	public static double calculateTreynorRatio(List<NAVObject> navObj, double beta, double riskFreeRate) {
		double portfolioReturn = calculateAnnualizedReturn(navObj);
		return (portfolioReturn - riskFreeRate) / beta; // Treynor formula
	}
	
	// 17. Jensen's Alpha
	public static double calculateJensensAlpha(List<NAVObject> navObj, double beta, double riskFreeRate,
			double marketReturn) {
		double portfolioReturn = calculateAnnualizedReturn(navObj);
		return portfolioReturn - (riskFreeRate + beta * (marketReturn - riskFreeRate)); // Jensen's alpha formula
	}*/

	/*// 18. R-squared
	public static double calculateRSquared(List<NAVObject> navObj, List<Double> marketReturns) {
		List<Double> portfolioReturns = calculateDailyReturns(navObj);
		double covariance = calculateCovariance(portfolioReturns, marketReturns);
		double portfolioVariance = calculateVariance(portfolioReturns);
		double marketVariance = calculateVariance(marketReturns);
	
		return Math.pow(covariance, 2) / (portfolioVariance * marketVariance); // R-squared formula
	}*/

	// 19. MAR (Maximum Annual Return)
	public String calculateMAR(List<NAVObject> navObj) throws ParseException {
		double maxReturn = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < navObj.size() - 1; i++) {
			for (int j = i + 1; j < navObj.size(); j++) {
				long timeDiff = NumericConstant.DATE_DD_MM_YYYY.parse(navObj.get(j).getDate()).getTime()
						- NumericConstant.DATE_DD_MM_YYYY.parse(navObj.get(i).getDate()).getTime();
				double years = timeDiff / (1000.0 * 60 * 60 * 24 * 365);
				double annualizedReturn = Math.pow(
						navObj.get(j).getNav().doubleValue() / navObj.get(i).getNav().doubleValue(), 1 / years) - 1;
				maxReturn = Math.max(maxReturn, annualizedReturn);
			}
		}
		return BigDecimal.valueOf(maxReturn).multiply(BigDecimal.valueOf(100))
				.setScale(NumericConstant.DEFAULT_SCALE, RoundingMode.HALF_EVEN).toString() + "%";
	}
	/*
		// Helper: Calculate daily returns
		private static List<Double> calculateDailyReturns(List<NAVObject> navObj) {
			List<Double> dailyReturns = new ArrayList<>();
			for (int i = 1; i < navObj.size(); i++) {
				double dailyReturn = (navObj.get(i).getNav() - navObj.get(i - 1).getNav()) / navObj.get(i - 1).getNav();
				dailyReturns.add(dailyReturn);
			}
			return dailyReturns;
		}*/

	/*// Helper: Calculate covariance
	private static double calculateCovariance(List<Double> list1, List<Double> list2) {
		double mean1 = list1.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
		double mean2 = list2.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
	
		double covariance = 0.0;
		for (int i = 0; i < list1.size(); i++) {
			covariance += (list1.get(i) - mean1) * (list2.get(i) - mean2);
		}
		return covariance / list1.size();
	}*/

	/*// Helper: Calculate variance
	private static double calculateVariance(List<Double> list) {
		double mean = list.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
		return list.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / list.size();
	}*/

	/*// Helper: Calculate downside deviation
	private static double calculateDownsideDeviation(List<NAVObject> navObj, double riskFreeRate) {
		double meanReturn = calculateAnnualizedReturn(navObj);
		double downsideVariance = 0.0;
		for (NAVObject obj : navObj) {
			double deviation = obj.getNav() - riskFreeRate;
			if (deviation < 0) {
				downsideVariance += Math.pow(deviation, 2);
			}
		}
		return Math.sqrt(downsideVariance / navObj.size());
	}*/

	// Helper function to calculate EMA (Exponential Moving Average)
	private static List<Double> calculateEMA(List<NAVObject> navObj, int period) {
		List<Double> ema = new ArrayList<>();
		double multiplier = 2.0 / (period + 1); // Multiplier for EMA
		double prevEma = navObj.get(0).getNav().doubleValue(); // Start EMA with the first NAV
		ema.add(prevEma);

		for (int i = 1; i < navObj.size(); i++) {
			double currentEma = (navObj.get(i).getNav().doubleValue() - prevEma) * multiplier + prevEma;
			ema.add(currentEma);
			prevEma = currentEma; // Update previous EMA
		}

		// Reduce EMA list to values by sampling
		int maxDisplayCount = NumericConstant.SMA_MAX_PLOT_POINT; // Number of EMA values to display
		List<Double> sampledEMA = new ArrayList<>();
		int step = Math.max(1, ema.size() / maxDisplayCount);
		for (int i = 0; i < ema.size() && sampledEMA.size() < maxDisplayCount; i += step) {
			sampledEMA.add(ema.get(i));
		}

		return sampledEMA; // Return only sampled EMA values
	}

	// Helper function to calculate EMA from a list of values
	private static List<Double> calculateEMAFromList(List<Double> values, int period) {
		List<Double> ema = new ArrayList<>();
		double multiplier = 2.0 / (period + 1); // Multiplier for EMA
		double prevEma = values.get(0); // Start EMA with the first value
		ema.add(prevEma);

		for (int i = 1; i < values.size(); i++) {
			double currentEma = (values.get(i) - prevEma) * multiplier + prevEma;
			ema.add(currentEma);
			prevEma = currentEma; // Update previous EMA
		}

		// Reduce EMA list to values by sampling
		int maxDisplayCount = NumericConstant.SMA_MAX_PLOT_POINT; // Number of EMA values to display
		List<Double> sampledEMA = new ArrayList<>();
		int step = Math.max(1, ema.size() / maxDisplayCount);
		for (int i = 0; i < ema.size() && sampledEMA.size() < maxDisplayCount; i += step) {
			sampledEMA.add(ema.get(i));
		}

		return sampledEMA; // Return only sampled EMA values
	}
}
