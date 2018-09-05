package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.MovieEvent;
import com.example.demo.service.MovieService;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by imteyaz on 05/09/18
 **/
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
        .flatMapMany(movieService::streamOfStreams);
  }

  @GetMapping(value = "/randomnumber/time", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Map> getTimeAndNumber(){
    return movieService.streamOfStreams();
  }
}
