package com.kalah.repository;

import com.kalah.domain.Game;
import com.kalah.enums.GameState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for @{@link Game}
 */
@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    /**
     * Find game by @{@link GameState}
     *
     * @param gameState @{@link GameState} to find by
     * @return List of @{@link Game} with given GameState
     */
    List<Game> findByGameState(GameState gameState);
}
