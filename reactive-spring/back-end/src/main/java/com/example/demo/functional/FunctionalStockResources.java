package com.example.demo.functional;


import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Created by imteyaz on 06/09/18
 **/
@EnableWebFlux
@Configuration
public class FunctionalStockResources implements WebFluxConfigurer {

  private static final String URL_PATTERN = "/functional/stock";

  private final StockService stockService;

  @Autowired
  public FunctionalStockResources(StockService stockService) {
    this.stockService = stockService;
  }

  @Bean
  @CrossOrigin("http://localhost:4200")
  public RouterFunction<ServerResponse> randomNumber() {

    // TODO implement API to save details of Stock
    // TODO implement API to update details of Stock
    // TODO implement API to delete a Stock
    // TODO implement API to get list of Stocks
    return null;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedMethods("*");
  }
}
