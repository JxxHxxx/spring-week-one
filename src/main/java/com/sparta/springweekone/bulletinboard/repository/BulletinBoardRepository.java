package com.sparta.springweekone.bulletinboard.repository;

import com.sparta.springweekone.bulletinboard.entity.BulletinBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Long> {

    List<BulletinBoard> findAllByOrderByCreateAtDesc();
    BulletinBoard findByNickname(String nickname);
}
