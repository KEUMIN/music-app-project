package com.sist.lastproject.repository;

import com.sist.lastproject.entity.Music;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {
    // 페이지 나누기
    @Query(value="SELECT *"
            + "FROM music ORDER BY no ASC "
            + "LIMIT 20 OFFSET :start", nativeQuery = true)
    public List<Music> musicListData(@Param("start") Integer start);

    @Query(value = "SELECT CEIL(COUNT(*)/20) FROM music", nativeQuery = true)
    public int musicTotalPage();

    @Query(value = "SELECT *"
            + "FROM music WHERE no=:no", nativeQuery = true)
    public Music musicDetailData(@Param("no") Integer no);
}
