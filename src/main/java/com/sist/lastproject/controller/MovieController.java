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

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping("/movie")
    public List<Movie> getMovieList(@ModelAttribute("moviePage")MoviePage moviePage) {
        System.out.println(moviePage.getPage());
        PageRequest pageRequest = PageRequest.of(moviePage.getPage(), 12);
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
