package com.sist.lastproject.controller;

import com.sist.lastproject.entity.Music;
import com.sist.lastproject.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private MusicRepository musicRepository;

    private static final int POSTS_PER_PAGE = 20;
    private static final int PAGES_PER_BLOCK = 5;

    @GetMapping
    public String getMusic(@RequestParam(required = false) Integer curPage, Model model) {
        if (curPage == null) curPage = 1;
        int start = (curPage - 1) * 20;
        int totPages = musicRepository.musicTotalPage();
        int startPage = ((curPage - 1) / PAGES_PER_BLOCK * PAGES_PER_BLOCK) + 1;
        int endPage = ((curPage - 1) / PAGES_PER_BLOCK * PAGES_PER_BLOCK) + PAGES_PER_BLOCK;

        if (endPage > totPages) endPage = totPages;

        List<Music> musicList = musicRepository.musicListData(start);
        List<Integer> pageNums = IntStream.rangeClosed(startPage, endPage)
                .boxed().collect(Collectors.toList());
        model.addAttribute("content", "music/list");
        model.addAttribute("musicList", musicList);
        model.addAttribute("pageNums", pageNums);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totPages", totPages);
        return "music/list";
    }

    @GetMapping("/{no}")
    public String getMusicDetail(@PathVariable("no") int no, Model model) {
        Music music = musicRepository.musicDetailData(no);
        model.addAttribute("music", music);
        model.addAttribute("content", "music/detail");
        return "music/detail";
    }
}
