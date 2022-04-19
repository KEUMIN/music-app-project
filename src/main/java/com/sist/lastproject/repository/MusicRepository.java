package com.sist.lastproject.repository;

import com.sist.lastproject.entity.Music;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {
    Music findMusicByNo(Integer no);
    List<Music> findMusicByCommentsContains(@Param("keyword") String keyword);

    @Query(value = "SELECT m.album "
            + "FROM music m", nativeQuery = true)
    List<String> getAlbumNames();

    @Transactional
    @Modifying
    @Query(value = "UPDATE music m SET m.comments = :comments "
            + "WHERE m.album = :album", nativeQuery = true)
    void updateComments(@Param("comments") String comments, @Param("album") String album);

    @Query("SELECT m.comments FROM Music m WHERE m.no = :no")
    String getCommentsByNo(@Param("album") int no);
}
