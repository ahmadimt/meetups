package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by imteyaz on 05/09/18
 **/
@Getter
@ToString
@Document(collection = "stock")
public class Stock {

  @Id
  @Setter(value = AccessLevel.NONE)
  private String id = UUID.randomUUID().toString();

  private long time = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();

  private double price;

  public Stock() {
  }

  public Stock(double price) {
    this.price = price;
  }

  public Stock(long time, double price) {
    this.time = time;
    this.price = price;
  }
}
