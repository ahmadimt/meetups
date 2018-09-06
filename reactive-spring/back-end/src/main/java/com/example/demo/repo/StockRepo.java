package com.example.demo.repo;

import com.example.demo.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Created by imteyaz on 05/09/18
 **/

public interface StockRepo extends ReactiveMongoRepository<Stock, String> {

  Mono<Stock> findByTime(long time);

}
