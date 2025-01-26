package com.yashdevs.dataScraper.entity;

public class MutualFundDataScraperEntity {

	private String schemeCode;
	private String fundName;
	private String fundCategory;
	private String currentNav;
	private String currentAum;
	private String expenseRatio;
	private String fundBenchmark;
	private String top5;
	private String top20;
	private String pe;
	private String pb;
	private String alpha;
	private String beta;
	private String sharpie;
	private String sortino;
	private MFAPIResponseDTO mfApiResponseDTO;
	
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getFundCategory() {
		return fundCategory;
	}
	public void setFundCategory(String fundCategory) {
		this.fundCategory = fundCategory;
	}
	public String getCurrentNav() {
		return currentNav;
	}
	public void setCurrentNav(String currentNav) {
		this.currentNav = currentNav;
	}
	public String getCurrentAum() {
		return currentAum;
	}
	public void setCurrentAum(String currentAum) {
		this.currentAum = currentAum;
	}
	public String getFundBenchmark() {
		return fundBenchmark;
	}
	public void setFundBenchmark(String fundBenchmark) {
		this.fundBenchmark = fundBenchmark;
	}
	public String getTop5() {
		return top5;
	}
	public void setTop5(String top5) {
		this.top5 = top5;
	}
	public String getTop20() {
		return top20;
	}
	public void setTop20(String top20) {
		this.top20 = top20;
	}
	public String getPe() {
		return pe;
	}
	public void setPe(String pe) {
		this.pe = pe;
	}
	public String getPb() {
		return pb;
	}
	public void setPb(String pb) {
		this.pb = pb;
	}
	public MFAPIResponseDTO getMfApiResponseDTO() {
		return mfApiResponseDTO;
	}
	public void setMfApiResponseDTO(MFAPIResponseDTO mfApiResponseDTO) {
		this.mfApiResponseDTO = mfApiResponseDTO;
	}
	public String getExpenseRatio() {
		return expenseRatio;
	}
	public void setExpenseRatio(String expenseRatio) {
		this.expenseRatio = expenseRatio;
	}
	public String getAlpha() {
		return alpha;
	}
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}
	public String getBeta() {
		return beta;
	}
	public void setBeta(String beta) {
		this.beta = beta;
	}
	public String getSharpie() {
		return sharpie;
	}
	public void setSharpie(String sharpie) {
		this.sharpie = sharpie;
	}
	public String getSortino() {
		return sortino;
	}
	public void setSortino(String sortino) {
		this.sortino = sortino;
	}
}
