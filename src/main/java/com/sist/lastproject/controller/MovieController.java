package com.sist.lastproject.controller;

import com.sist.lastproject.dto.MovieDTO;
import com.sist.lastproject.dto.MoviePage;
import com.sist.lastproject.entity.Movie;
import com.sist.lastproject.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movie")
    public List<Movie> getMovieList() {
        PageRequest pageRequest = PageRequest.of(0, 12);
        Page<Movie> movies = movieRepository.findAll(pageRequest);
        return movies.getContent();
    }
    @PostMapping("/movie")
    public List<Movie> postMovieList(@RequestParam("page")Integer page) {
        System.out.println("received page number: " +page);
        PageRequest pageRequest = PageRequest.of(page, 12);
        Page<Movie> movies = movieRepository.findAll(pageRequest);
        movies.stream().forEach(m -> System.out.println(m.getTitle()));
        return movies.getContent();
    }

    @RequestMapping("/movie/{mno}")
    public MovieDTO getMovieDetail(@PathVariable("mno") Integer mno) {
        Optional<Movie> movie = movieRepository.findById(mno);
        MovieDTO movieDTO = new MovieDTO();
        if (!movie.isEmpty()) {
            movieDTO.setMovie(movie.get());
            movieDTO.setMsg("success");
        } else {
            movieDTO.setMsg("null");
        }
        return movieDTO;
    }
}
