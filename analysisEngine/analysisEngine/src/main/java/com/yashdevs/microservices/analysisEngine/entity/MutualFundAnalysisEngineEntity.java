package com.yashdevs.microservices.analysisEngine.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yashdevs.dataScraper.entity.MFAPIResponseDTO.NAVObject;

public class MutualFundAnalysisEngineEntity {

	private String schemeCode;
	private String fundHouse;
	private String isInGrowthNo;
	@JsonIgnore
	private String isInDivReInvestmentNo;
	private String fundName;
	private String fundCategory;
	private String currentNav;
	private String currentAum;
	private String expenseRatio;
	private String fundBenchmark;
	private AnalysisEngineTechnicalParamsEntity technicals;
	@JsonIgnore
	private List<NAVObject> navObj;
	
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode.trim();
	}
	public String getFundHouse() {
		return fundHouse;
	}
	public void setFundHouse(String fundHouse) {
		this.fundHouse = fundHouse.trim();
	}
	public String getIsInGrowthNo() {
		return isInGrowthNo;
	}
	public void setIsInGrowthNo(String isInGrowthNo) {
		this.isInGrowthNo = isInGrowthNo.trim();
	}
	public String getIsInDivReInvestmentNo() {
		return isInDivReInvestmentNo;
	}
	public void setIsInDivReInvestmentNo(String isInDivReInvestmentNo) {
		this.isInDivReInvestmentNo = isInDivReInvestmentNo.trim();
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName.trim();
	}
	public String getFundCategory() {
		return fundCategory;
	}
	public void setFundCategory(String fundCategory) {
		this.fundCategory = fundCategory.trim();
	}
	public String getCurrentNav() {
		return currentNav;
	}
	public void setCurrentNav(String currentNav) {
		this.currentNav = currentNav.trim();
	}
	public String getCurrentAum() {
		return currentAum;
	}
	public void setCurrentAum(String currentAum) {
		this.currentAum = currentAum.trim();
	}
	public String getExpenseRatio() {
		return expenseRatio;
	}
	public void setExpenseRatio(String expenseRatio) {
		this.expenseRatio = expenseRatio.trim();
	}
	public String getFundBenchmark() {
		return fundBenchmark;
	}
	public void setFundBenchmark(String fundBenchmark) {
		this.fundBenchmark = fundBenchmark.trim();
	}
	public AnalysisEngineTechnicalParamsEntity getTechnicals() {
		return technicals;
	}
	public void setTechnicals(AnalysisEngineTechnicalParamsEntity technicals) {
		this.technicals = technicals;
	}
	public List<NAVObject> getNavObj() {
		return navObj;
	}
	public void setNavObj(List<NAVObject> navObj) {
		this.navObj = navObj;
	}
	
	
	
	
	
}
