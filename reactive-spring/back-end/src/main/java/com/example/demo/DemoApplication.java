package com.example.demo;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  CommandLineRunner run(MovieRepo movieRepo) {
    return args -> {
      movieRepo.deleteAll().subscribe(null, null, () -> {
        Stream.of("Hum he rahi pyaar ke", "Hum apke hai kaun", "Andaz apana apana", "Bhoot",
            "Dangal", "Drushyam", "Kahani", "Ashi hi banava banvi", "B.A. pass",
            "Mene gandhi ko nahi mara", "Dil to pagal hai")
            .map(name -> new Movie(UUID.randomUUID().toString(), name, randomGenre()))
            .forEach(m -> movieRepo.save(m).subscribe(System.out::println));
      });
    };
  }

  private String randomGenre() {
    String[] genres = "Comedy,tragedy,horror,action,drama".split(",");
    return genres[new Random().nextInt(genres.length)];
  }

}

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
class MovieEvent {

  private Movie movie;

  private String user;

  private Date date;
}

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
class Movie {

  @Id
  private String id;

  private String name;

  private String genre;
}

interface MovieRepo extends ReactiveMongoRepository<Movie, String> {

}

@Service
class MovieService {

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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/movies")
class MovieController {

  private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping
  public Flux<Movie> getAll() {
    return movieService.getAll();
  }

  @GetMapping(value = "/{id}")
  public Mono<Movie> getMovie(@PathVariable String id) {
    return movieService.get(id);
  }

  @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<MovieEvent> getEvent(@PathVariable String id){
    return movieService.get(id)
        .flatMapMany(movie -> movieService.streamOfStreams(movie));
  }

  @GetMapping(value = "/randomnumber/time", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Map> getTimeAndNumber(){
    return movieService.streamOfStreams();
  }
}
