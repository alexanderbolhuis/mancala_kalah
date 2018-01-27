package com.kalah.repository;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
    public Board findByGame(Game game);
}