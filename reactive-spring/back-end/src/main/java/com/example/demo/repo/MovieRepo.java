package com.example.demo.repo;

import com.example.demo.model.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface MovieRepo extends ReactiveMongoRepository<Movie, String> {

}
