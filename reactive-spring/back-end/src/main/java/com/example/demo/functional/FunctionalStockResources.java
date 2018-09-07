package com.example.demo.functional;


import com.example.demo.model.Stock;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
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

    return RouterFunctions.route(RequestPredicates.GET(URL_PATTERN),
        request -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
            .body(stockService.all(),
                Stock.class))
        .andRoute(RequestPredicates.OPTIONS(URL_PATTERN),
            request -> ServerResponse.ok()
                .body(stockService.all(),
                    Stock.class))
        .andRoute(RequestPredicates.POST(URL_PATTERN),
            request -> ServerResponse.ok()
                .body(request.bodyToMono(Stock.class).flatMap(stockService::saveStock),
                    Stock.class))
        .andRoute(RequestPredicates.PUT(URL_PATTERN),
            request -> ServerResponse.ok()
                .body(request.bodyToMono(Stock.class).flatMap(stockService::saveStock),
                    Stock.class))
        .andRoute(RequestPredicates.DELETE(URL_PATTERN + "/{time}"),
            request -> ServerResponse.ok()
                .body(stockService.deleteStock(Long.parseLong(request.pathVariable("time"))),
                    Stock.class));
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedMethods("*");
  }
}
