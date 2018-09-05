package com.example.demo.functional;

import com.example.demo.service.MovieService;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Created by imteyaz on 04/09/18
 **/
@Configuration
@EnableWebFlux
public class FunctionalController implements WebFluxConfigurer {

  private final MovieService movieService;

  public FunctionalController(
      MovieService movieService) {
    this.movieService = movieService;
  }

  @Bean
  @CrossOrigin("http://localhost:4200")
  public RouterFunction<ServerResponse> randomNumber() {

    return RouterFunctions.route(RequestPredicates.GET("/functional/randomnumber/time"),
        request -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(movieService.streamOfStreams(),
            Map.class))
        .andRoute(RequestPredicates.OPTIONS("/functional/randomnumber/time"),
            request -> ServerResponse.ok().body(movieService.streamOfStreams(),
                Map.class));
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("http://localhost:4200")
        .allowedMethods("*");
  }
}
