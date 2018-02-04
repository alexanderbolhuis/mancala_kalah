package com.kalah.repository;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for @{@link Board}
 */
@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
    /**
     * Find a board by instance of @{@link Game}
     *
     * @param game @{@link Game} instance
     * @return @{@link Board} instance
     */
    public Board findByGame(Game game);
}