package com.yashdevs.microservice.apiGateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/data-scraper/**").uri("lb://data-scraper"))
				.route(p -> p.path("/analysis-engine/**").uri("lb://analysis-engine"))
				.route(p -> p.path("/mf-analysis/**")
						.filters(f -> f.rewritePath(
								"/mf-analysis/(?<segment>.*)",
								"/analysis-engine/${segment}"))
						.uri("lb://analysis-engine"))
				.build();
	}

}
