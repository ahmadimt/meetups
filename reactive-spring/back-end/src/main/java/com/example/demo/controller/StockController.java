package com.example.demo.controller;

import com.example.demo.model.Stock;
import com.example.demo.service.StockService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by imteyaz on 05/09/18
 **/
@RestController
@RequestMapping(path = "/stock")
public class StockController {

  private final StockService stockService;

  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Stock> save(@RequestBody Stock stock) {
    return stockService.saveStock(stock);
  }


  @DeleteMapping(path = "/{time}",produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Stock> delete(@PathVariable(value = "time") long time) {
    return stockService.deleteStock(time);
  }

  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Stock> fetchAll() {
    return stockService.all();
  }
}
