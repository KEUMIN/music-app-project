package com.sist.lastproject.controller;

import com.sist.lastproject.dto.MusicDTO;
import com.sist.lastproject.entity.Music;
import com.sist.lastproject.repository.MusicRepository;
import com.sist.lastproject.service.MusicService;
import com.sist.lastproject.util.MelonBot;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Controller
@RequestMapping("/music")
public class MusicController {
    private MusicRepository musicRepository;
    private MusicService musicService;

    private static final int POSTS_PER_PAGE = 20;
    private static final int PAGES_PER_BLOCK = 5;

    @GetMapping
    public String getMusic(@RequestParam(defaultValue = "1") Integer curPage, Model model) {
        int startPage = ((curPage - 1) / PAGES_PER_BLOCK * PAGES_PER_BLOCK) + 1;
        int endPage = ((curPage - 1) / PAGES_PER_BLOCK * PAGES_PER_BLOCK) + PAGES_PER_BLOCK;

        // 첫번째 파라미터에는 post의 인덱스가 아닌 페이지의 인덱스를 넣는다.
        PageRequest pageRequest = PageRequest.of(curPage-1, POSTS_PER_PAGE);
        Page<Music> page = musicRepository.findAll(pageRequest);
        int totPages = page.getTotalPages();

        if (endPage > totPages) endPage = totPages;

        model.addAttribute("musicList", page.getContent());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totPages", totPages);
        return "music/list";
    }

    @GetMapping("/{no}")
    public String getMusicDetail(@PathVariable("no") int no, Model model) {
        MusicDTO music = musicService.getMusicDTO(no);
        model.addAttribute("music", music);
        return "music/detail";
    }

    @GetMapping("/add")
    @ResponseBody
    public String addComments() {
        List<String> albumNames = musicRepository.getAlbumNames();
        MelonBot bot1 = new MelonBot();
        for (int i = 93; i < albumNames.size(); i++) {
            String album = albumNames.get(i);
            String comment = null;
            if (musicRepository.getCommentsByNo(i+1) != null) continue;

            try {
                comment = bot1.activateBot(album);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // init KeywordExtractor
            KeywordExtractor ke = new KeywordExtractor();
            // extract keywords
            KeywordList kl = ke.extractKeyword(comment, true);
            List<Keyword> keywords = kl.stream()
                    .sorted(Comparator.comparing(Keyword::getCnt).reversed())
                    .limit(20)
                    .collect(Collectors.toList());
            StringBuffer comments = new StringBuffer();
            keywords.stream().forEach(k -> comments.append(k.getString() + "%" + k.getCnt() + "/"));

            musicRepository.updateComments(comments.toString(), album);
            System.out.println(comments.toString());
        }
        bot1.closeDriver();
        return "Ok";
    }
}
