package com.yashdevs.microservices.analysisEngine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yashdevs.microservices.analysisEngine.entity.MutualFundAnalysisEngineEntity;
import com.yashdevs.microservices.analysisEngine.service.AnalysisEngineService;

@RestController
@RequestMapping("/analysis-engine")
public class AnalysisEngineController {

	private AnalysisEngineService analysisEngineService;
	
	public AnalysisEngineController(AnalysisEngineService service) {
		this.analysisEngineService = service;
	}
	
	 @GetMapping(path = "/", params = {"scheme"})
	    public ResponseEntity<MutualFundAnalysisEngineEntity> getDetails(@RequestParam String scheme) {
		 
	        return new ResponseEntity<>(analysisEngineService.getAnalysisEngine(scheme), HttpStatus.OK);
	    }
	
	
}
