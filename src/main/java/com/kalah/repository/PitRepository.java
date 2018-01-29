package com.kalah.repository;

import com.kalah.domain.Board;
import com.kalah.domain.Pit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PitRepository extends CrudRepository<Pit, Long> {
    List<Pit> findByBoard(Board Board);
    List<Pit> findByBoardOrderByPositionAsc(Board Board);
    Pit findByBoardAndPosition(Board board, int position);
}