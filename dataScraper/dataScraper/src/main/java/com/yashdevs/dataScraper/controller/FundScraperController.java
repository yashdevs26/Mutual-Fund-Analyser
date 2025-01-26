package com.yashdevs.dataScraper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yashdevs.dataScraper.entity.MutualFundDataScraperEntity;
import com.yashdevs.dataScraper.service.FundScraperService;

@RestController
@RequestMapping("/data-scraper")
public class FundScraperController {

	private FundScraperService fundScraperService;

	public FundScraperController(FundScraperService fundScraperService) {
		this.fundScraperService = fundScraperService;
	}
	
	@GetMapping(value = "/", params = {"scheme"})
	public ResponseEntity<MutualFundDataScraperEntity> entryPoint(@RequestParam String scheme) {
		return new ResponseEntity<>(fundScraperService.scrapeData(scheme.trim()), HttpStatus.OK);
	}

	/*
	 * @GetMapping("/{fund}") public ResponseEntity<MutualFundDataScraperEntity>
	 * getFunds(@PathVariable String fund) { return new
	 * ResponseEntity<>(fundScraperService.scrapeGroww(), HttpStatus.OK); }
	 */
}
