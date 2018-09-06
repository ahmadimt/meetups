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
    Mono<Stock> stockRepoByTime = stockRepo.findByTime(stockMono.getTime());
    return stockRepoByTime.defaultIfEmpty(stockMono)
        .doOnNext(stock -> stockRepo.save(Stock.from(stock, stockMono)).subscribe());
  }

  public Mono<Stock> deleteStock(long time) {
    return stockRepo.findByTime(time)
        .doOnNext(stock -> stockRepo.deleteById(stock.getId()).subscribe());
  }

  public Flux<Stock> all() {
    return stockRepo.findAll();
  }
}
