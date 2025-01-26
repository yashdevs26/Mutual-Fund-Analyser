package com.yashdevs.microservices.analysisEngine.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"top5HoldingsConcentration", "top20HoldingsConcentration", "priceToEarningRatio", "priceToBookRatio", 
	"annualizedReturn", "cagr", "alphaRatio", "betaRatio", "standardDeviation", "sharpieRatio", "sortinoRatio", 
	"maximumDrawdown", "netReturn", "annualisedVolatility", "maximumAnnualReturn", "rollingReturn", "movingAverage", 
	"relativeStrengthIndex", "macd"})
public class AnalysisEngineTechnicalParamsEntity {

	private String top5HoldingsConcentration;
	private String top20HoldingsConcentration;
	private String priceToEarningRatio;
	private String priceToBookRatio;
	private String annualizedReturn;
	private Map<String, String> rollingReturn;
	private String cagr;
	private String alphaRatio;
	private String betaRatio;
	@JsonProperty("oneYearStandardDeviation")
	private String standardDeviation;
	private String sharpieRatio;
	private String sortinoRatio;
	private Map<String, List<Double>> movingAverage;
	private String maximumDrawdown;
	@JsonProperty("netReturnWithoutExpenses")
	private String netReturn;
	@JsonProperty("oneYearAnnualisedVolatility")
	private String annualisedVolatility;
	private Map<String, List<Double>> relativeStrengthIndex;
	private Map<String, List<Double>> macd;
	@JsonIgnore
	private List<Double> rSquaredRatio;
	@JsonIgnore
	private String maximumAnnualReturn;
	
	public String getTop5HoldingsConcentration() {
		return top5HoldingsConcentration;
	}
	public void setTop5HoldingsConcentration(String top5HoldingsConcentration) {
		this.top5HoldingsConcentration = top5HoldingsConcentration;
	}
	public String getTop20HoldingsConcentration() {
		return top20HoldingsConcentration;
	}
	public void setTop20HoldingsConcentration(String top20HoldingsConcentration) {
		this.top20HoldingsConcentration = top20HoldingsConcentration;
	}
	public String getPriceToEarningRatio() {
		return priceToEarningRatio;
	}
	public void setPriceToEarningRatio(String priceToEarningRatio) {
		this.priceToEarningRatio = priceToEarningRatio;
	}
	public String getPriceToBookRatio() {
		return priceToBookRatio;
	}
	public void setPriceToBookRatio(String priceToBookRatio) {
		this.priceToBookRatio = priceToBookRatio;
	}
	public String getAnnualizedReturn() {
		return annualizedReturn;
	}
	public void setAnnualizedReturn(String annualizedReturn) {
		this.annualizedReturn = annualizedReturn;
	}
	public Map<String, String> getRollingReturn() {
		return rollingReturn;
	}
	public void setRollingReturn(Map<String, String> rollingReturn) {
		this.rollingReturn = rollingReturn;
	}
	public String getCagr() {
		return cagr;
	}
	public void setCagr(String cagr) {
		this.cagr = cagr;
	}
	public String getAlphaRatio() {
		return alphaRatio;
	}
	public void setAlphaRatio(String alphaRatio) {
		this.alphaRatio = alphaRatio;
	}
	public String getBetaRatio() {
		return betaRatio;
	}
	public void setBetaRatio(String betaRatio) {
		this.betaRatio = betaRatio;
	}
	public String getStandardDeviation() {
		return standardDeviation;
	}
	public void setStandardDeviation(String standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	public String getSharpieRatio() {
		return sharpieRatio;
	}
	public void setSharpieRatio(String sharpieRatio) {
		this.sharpieRatio = sharpieRatio;
	}
	public String getSortinoRatio() {
		return sortinoRatio;
	}
	public void setSortinoRatio(String sortinoRatio) {
		this.sortinoRatio = sortinoRatio;
	}
	public Map<String, List<Double>> getMovingAverage() {
		return movingAverage;
	}
	public void setMovingAverage(Map<String, List<Double>> movingAverage) {
		this.movingAverage = movingAverage;
	}
	public String getMaximumDrawdown() {
		return maximumDrawdown;
	}
	public void setMaximumDrawdown(String maximumDrawdown) {
		this.maximumDrawdown = maximumDrawdown;
	}
	public String getNetReturn() {
		return netReturn;
	}
	public void setNetReturn(String netReturn) {
		this.netReturn = netReturn;
	}
	public String getAnnualisedVolatility() {
		return annualisedVolatility;
	}
	public void setAnnualisedVolatility(String annualisedVolatility) {
		this.annualisedVolatility = annualisedVolatility;
	}
	public Map<String, List<Double>> getRelativeStrengthIndex() {
		return relativeStrengthIndex;
	}
	public void setRelativeStrengthIndex(Map<String, List<Double>> relativeStrengthIndex) {
		this.relativeStrengthIndex = relativeStrengthIndex;
	}
	public String getMaximumAnnualReturn() {
		return maximumAnnualReturn;
	}
	public void setMaximumAnnualReturn(String maximumAnnualReturn) {
		this.maximumAnnualReturn = maximumAnnualReturn;
	}
	public Map<String, List<Double>> getMacd() {
		return macd;
	}
	public void setMacd(Map<String, List<Double>> macd) {
		this.macd = macd;
	}
	public List<Double> getrSquaredRatio() {
		return rSquaredRatio;
	}
	public void setrSquaredRatio(List<Double> rSquaredRatio) {
		this.rSquaredRatio = rSquaredRatio;
	}
	
	
}
