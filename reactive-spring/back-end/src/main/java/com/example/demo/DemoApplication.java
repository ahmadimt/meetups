package com.example.demo;

import com.example.demo.model.Movie;
import com.example.demo.model.MovieEvent;
import com.example.demo.repo.MovieRepo;
import com.example.demo.service.MovieService;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
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
