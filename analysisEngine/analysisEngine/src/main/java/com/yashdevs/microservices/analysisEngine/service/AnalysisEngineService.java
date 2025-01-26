package com.yashdevs.microservices.analysisEngine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yashdevs.dataScraper.constant.NumericConstant;
import com.yashdevs.dataScraper.entity.MutualFundDataScraperEntity;
import com.yashdevs.microservices.analysisEngine.entity.AnalysisEngineTechnicalParamsEntity;
import com.yashdevs.microservices.analysisEngine.entity.MutualFundAnalysisEngineEntity;

@Service
public class AnalysisEngineService {

	@Autowired
	private FundScraperProxy proxy;
	@Autowired
	private DerivationService service;

	public MutualFundAnalysisEngineEntity getScraperProxy(String schemeName) {

		MutualFundAnalysisEngineEntity response = new MutualFundAnalysisEngineEntity();
		AnalysisEngineTechnicalParamsEntity technicals = new AnalysisEngineTechnicalParamsEntity();

		MutualFundDataScraperEntity scrapper = proxy.entryPoint(schemeName);
		if (scrapper != null) {
			
			response.setSchemeCode(scrapper.getSchemeCode());
			response.setFundHouse(scrapper.getMfApiResponseDTO().getMeta().getFundHouse());
			response.setIsInGrowthNo(scrapper.getMfApiResponseDTO().getMeta().getIsInGrowth());
			response.setFundName(scrapper.getFundName());
			response.setFundCategory(scrapper.getFundCategory());
			response.setCurrentNav(scrapper.getCurrentNav());
			response.setCurrentAum(scrapper.getCurrentAum());
			response.setExpenseRatio(scrapper.getExpenseRatio());
			response.setFundBenchmark(scrapper.getFundBenchmark());
			technicals.setAlphaRatio(scrapper.getAlpha());
			technicals.setBetaRatio(scrapper.getBeta());
			technicals.setTop5HoldingsConcentration(scrapper.getTop5());
			technicals.setTop20HoldingsConcentration(scrapper.getTop20());
			technicals.setPriceToEarningRatio(scrapper.getPe());
			technicals.setPriceToBookRatio(scrapper.getPb());
			technicals.setSharpieRatio(scrapper.getSharpie());
			technicals.setSortinoRatio(scrapper.getSortino());
			response.setTechnicals(technicals);
			
			response.setNavObj(scrapper.getMfApiResponseDTO().getNAVObject());
		}
		return response;
	}
	
	public MutualFundAnalysisEngineEntity getTechnicalParams(MutualFundAnalysisEngineEntity response) {

		AnalysisEngineTechnicalParamsEntity technicals = new AnalysisEngineTechnicalParamsEntity();

		try {
			// copying already set values
			technicals.setAlphaRatio(response.getTechnicals().getAlphaRatio());
			technicals.setBetaRatio(response.getTechnicals().getBetaRatio());
			technicals.setTop5HoldingsConcentration(response.getTechnicals().getTop5HoldingsConcentration());
			technicals.setTop20HoldingsConcentration(response.getTechnicals().getTop20HoldingsConcentration());
			technicals.setPriceToEarningRatio(response.getTechnicals().getPriceToEarningRatio());
			technicals.setPriceToBookRatio(response.getTechnicals().getPriceToBookRatio());
			technicals.setSharpieRatio(response.getTechnicals().getSharpieRatio());
			technicals.setSortinoRatio(response.getTechnicals().getSortinoRatio());

			technicals.setAnnualizedReturn(service.calculateAnnualizedReturn(response.getNavObj()));
			technicals.setRollingReturn(service.calculateRollingReturns(response.getNavObj()));
			technicals.setCagr(service.calculateCAGR(response.getNavObj()));
			technicals.setStandardDeviation(service.calculateStandardDeviation(response.getNavObj()));
			technicals.setMovingAverage(service.calculateSMAs(response.getNavObj()));
			technicals.setMaximumDrawdown(service.calculateMaximumDrawdown(response.getNavObj()));
			technicals.setNetReturn(service.calculateNetReturns(response.getNavObj(), Double.parseDouble(response.getExpenseRatio().split("%")[0])));
			technicals.setAnnualisedVolatility(service.calculateAnnualizedVolatility(response.getNavObj()));
			technicals.setMaximumAnnualReturn(service.calculateMAR(response.getNavObj()));
			technicals.setMacd(service.calculateMACD(response.getNavObj(), NumericConstant.MACD_SHORT, 
					NumericConstant.MACD_LONG, NumericConstant.MACD_SIGNAL));
			technicals.setRelativeStrengthIndex(service.calculateRSI(response.getNavObj()));

			response.setTechnicals(technicals);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return response;

	}

	public MutualFundAnalysisEngineEntity getAnalysisEngine(String schemeName) {

		MutualFundAnalysisEngineEntity response = this.getScraperProxy(schemeName.trim());
		this.getTechnicalParams(response);

		return response;

	}

}
