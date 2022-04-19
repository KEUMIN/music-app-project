package com.sist.lastproject.controller;

import com.sist.lastproject.dto.Comment;
import com.sist.lastproject.entity.Music;
import com.sist.lastproject.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MusicRepository musicRepository;

    @RequestMapping("/")
    public String getMainPage() {
        return "main";
    }

    @PostMapping("/keyword")
    public String getKeywordMusicList(@ModelAttribute("comment")Comment comment, Model model) {
        List<Music> musicList = musicRepository.findMusicByCommentsContains(comment.getKeyword());
        model.addAttribute("musicList", musicList);
        return "fragments/custom";
    }
}
