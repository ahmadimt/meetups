package com.example.demo;

import com.example.demo.model.Stock;
import com.example.demo.repo.StockRepo;
import com.example.demo.service.StockService;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

/**
 * Created by imteyaz on 05/09/18
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RandomStockGenerator {

  @Autowired
  private StockService stockRepo;

  @Test
  public void generateAndSaveRandomStock() {
    IntStream.range(1, 100).forEach(value -> {
      Mono<Stock> stockMono = stockRepo
          .saveStock(new Stock(ThreadLocalRandom.current().nextDouble(1, 100)));
      stockMono.subscribe(System.out::println);
    });
  }
}
