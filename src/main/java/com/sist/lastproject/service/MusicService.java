package com.sist.lastproject.service;

import com.sist.lastproject.dto.MusicDTO;
import com.sist.lastproject.repository.MusicRepository;
import com.sist.lastproject.util.KeywordParser;
import com.sist.lastproject.util.MelonBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private KeywordParser keywordParser;

    public MusicDTO getMusicDTO(int no) {
        MusicDTO musicDTO = new MusicDTO();
        musicDTO.buildMusicDTO(musicRepository.findMusicByNo(no));
        return musicDTO;
    }

    public void addComments() {
        List<String> albumNames = musicRepository.getAlbumNames();

        MelonBot bot1 = new MelonBot();

        for (int i = 0; i < albumNames.size(); i++) {
            String album = albumNames.get(i);
            String comment = null;

            // 멜론 사이트 특성상 앨범에 댓글을 달기 때문에 같은 앨범일 경우 중복 무시
            if (musicRepository.getCommentsByNo(i+1) != null) continue;

            try {
                comment = bot1.activateBot(album);
            } catch (Exception e) {
                e.printStackTrace();
            }

            musicRepository.updateComments(keywordParser.parseWords(comment), album);
        }
        bot1.closeDriver();
    }
}
