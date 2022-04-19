package com.sist.lastproject.service;

import com.sist.lastproject.dto.MusicDTO;
import com.sist.lastproject.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    public MusicDTO getMusicDTO(int no) {
        MusicDTO musicDTO = new MusicDTO();
        musicDTO.buildMusicDTO(musicRepository.findMusicByNo(no));
        return musicDTO;
    }
}
