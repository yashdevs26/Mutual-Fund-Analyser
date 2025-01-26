package com.yashdevs.dataScraper.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"meta", "status", "navData"})
public class MFAPIResponseDTO {
	@JsonProperty("meta")
	private Meta fundInfo;
	
	@JsonProperty("data")
	private List<NAVObject> navData;
	
	@JsonProperty("status")
	@JsonIgnore
	private String status;

	public Meta getMeta() {
		return fundInfo;
	}

	public void setMeta(Meta meta) {
		this.fundInfo = meta;
	}

	public List<NAVObject> getNAVObject() {
		return navData;
	}

	public void setNAVObject(List<NAVObject> data) {
		this.navData = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static class Meta {
		
		@JsonProperty("fund_house")
		private String fundHouse;
		
		@JsonProperty("scheme_type")
		private String schemeType;
		
		@JsonProperty("scheme_category")
		private String schemeCategory;
		
		@JsonProperty("scheme_code")
		private Long schemeCode;
		
		@JsonProperty("scheme_name")
		private String schemeName;
		
		@JsonProperty("isin_growth")
		private String isInGrowthNo;
		
		@JsonProperty("isin_div_reinvestment")
		private String isInDivReInvestmentNo;

		public String getFundHouse() {
			return fundHouse;
		}

		public void setFundHouse(String fundHouse) {
			this.fundHouse = fundHouse;
		}

		public String getSchemeType() {
			return schemeType;
		}

		public void setSchemeType(String schemeType) {
			this.schemeType = schemeType;
		}

		public String getSchemeCategory() {
			return schemeCategory;
		}

		public void setSchemeCategory(String schemeCategory) {
			this.schemeCategory = schemeCategory;
		}

		public Long getSchemeCode() {
			return schemeCode;
		}

		public void setSchemeCode(Long schemeCode) {
			this.schemeCode = schemeCode;
		}

		public String getSchemeName() {
			return schemeName;
		}

		public void setSchemeName(String schemeName) {
			this.schemeName = schemeName;
		}

		public String getIsInGrowth() {
			return isInGrowthNo;
		}

		public void setIsInGrowth(String isInGrowth) {
			this.isInGrowthNo = isInGrowth;
		}

		public String getIsInDivReInvestment() {
			return isInDivReInvestmentNo;
		}

		public void setIsInDivReInvestment(String isInDivReInvestment) {
			this.isInDivReInvestmentNo = isInDivReInvestment;
		}

	}

	public static class NAVObject {
		
		@JsonProperty("date")
		private String date;
		
		@JsonProperty("nav")
		private BigDecimal netAssetValue;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public BigDecimal getNav() {
			return netAssetValue;
		}

		public void setNav(BigDecimal nav) {
			this.netAssetValue = nav;
		}

	}

}
