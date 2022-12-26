package com.sparta.springweekone.bulletinboard.repository;

import com.sparta.springweekone.bulletinboard.domain.BulletinBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Long> {

}
