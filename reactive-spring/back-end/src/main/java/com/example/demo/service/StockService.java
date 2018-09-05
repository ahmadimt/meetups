package com.example.demo.service;

import com.example.demo.model.Stock;
import com.example.demo.repo.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by imteyaz on 05/09/18
 **/
@Service
public class StockService {

  private final StockRepo stockRepo;

  @Autowired
  public StockService(StockRepo stockRepo) {
    this.stockRepo = stockRepo;
  }

  public Mono<Stock> saveStock(Stock stockMono) {
    return stockRepo.save(stockMono);
  }

  public Flux<Stock> all() {
    return stockRepo.findAll();
  }
}
