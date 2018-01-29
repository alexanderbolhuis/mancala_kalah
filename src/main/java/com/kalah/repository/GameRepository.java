package com.kalah.repository;

import com.kalah.domain.Game;
import com.kalah.enums.GameState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    List<Game> findByGameState(GameState gameState);
}
