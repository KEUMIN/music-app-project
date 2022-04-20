package com.sist.lastproject.dto;

import com.sist.lastproject.entity.Movie;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovieDTO {
    private Movie movie;
    private String msg;
}
