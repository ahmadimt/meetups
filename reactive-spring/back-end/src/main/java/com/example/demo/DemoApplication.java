package com.example.demo;

import com.example.demo.model.Movie;
import com.example.demo.repo.MovieRepo;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  CommandLineRunner run(MovieRepo movieRepo) {
    return args -> {
      movieRepo.deleteAll().subscribe(null, null, () -> Stream.of("Hum he rahi pyaar ke", "Hum apke hai kaun", "Andaz apana apana", "Bhoot",
          "Dangal", "Drushyam", "Kahani", "Ashi hi banava banvi", "B.A. pass",
          "Mene gandhi ko nahi mara", "Dil to pagal hai")
          .map(name -> new Movie(UUID.randomUUID().toString(), name, randomGenre()))
          .forEach(m -> movieRepo.save(m).subscribe(System.out::println)));
    };
  }

  private String randomGenre() {
    String[] genres = "Comedy,tragedy,horror,action,drama".split(",");
    return genres[new Random().nextInt(genres.length)];
  }

}

