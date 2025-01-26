package com.yashdevs.microservices.analysisEngine.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yashdevs.dataScraper.entity.MutualFundDataScraperEntity;

@FeignClient(name = "data-scraper"/*, url="localhost:5010"*/) // dynamic load balancing by spring cloud load balancer
public interface FundScraperProxy {
	
	@GetMapping(path = "/data-scraper/", params = {"scheme"})
	public MutualFundDataScraperEntity entryPoint(@RequestParam String scheme);
}
