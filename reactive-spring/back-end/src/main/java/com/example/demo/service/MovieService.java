package com.example.demo.service;


import com.example.demo.model.Movie;
import com.example.demo.model.MovieEvent;
import com.example.demo.repo.MovieRepo;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by imteyaz on 04/09/18
 **/
@Service
public class MovieService {

  private final MovieRepo movieRepo;

  MovieService(MovieRepo movieRepo) {
    this.movieRepo = movieRepo;
  }

  public Flux<Movie> getAll() {
    return movieRepo.findAll();
  }

  public Mono<Movie> get(String id) {
    return movieRepo.findById(id);
  }

  public Flux<MovieEvent> streamOfStreams(Movie movie) {
    Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
    Flux<MovieEvent> movieEvents = Flux
        .fromStream(Stream.generate(() -> new MovieEvent(movie, randomUser(), new Date())));
    return Flux.zip(interval, movieEvents).map(objects -> {
      System.out.println("Inside stream of stream with 1st stream as "+ objects.getT1());
      return objects.getT2();
    });
  }

  public Flux<Map> streamOfStreams(){
    Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
    Flux<Map> timeAndNumber = Flux
        .fromStream(Stream.generate(MovieService::get));

    return  Flux.zip(interval, timeAndNumber).map(objects -> {
      System.out.println("Inside stream of stream with 1st stream as "+ objects.getT1());
      return objects.getT2();
    });
  }

  private static Map<String, Object> get() {
    Map<String, Object> map = new HashMap<>();
    map.put("number", new Random().nextInt(1000000));
    map.put("time", new Date());
    return map;
  }

  private String randomUser() {
    String[] users = "Nilesh,Sharddha,Sharvaree,Neela,Narayan".split(",");
    return users[new Random().nextInt(users.length)];
  }
}
