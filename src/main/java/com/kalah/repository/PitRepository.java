package com.kalah.repository;

import com.kalah.domain.Board;
import com.kalah.domain.Pit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for @{@link Pit}
 */
@Repository
public interface PitRepository extends CrudRepository<Pit, Long> {
    /**
     * Find Pit by @{@link Board}
     *
     * @param Board @{@link Board} to find pits by
     * @return List of @{@link Pit}
     */
    List<Pit> findByBoardOrderByPositionAsc(Board Board);

    /**
     * Find Pit by @{@link Board} and position
     *
     * @param board @{@link Board} to find by
     * @param position to find by
     * @return @{@link Pit} by board and position
     */
    Pit findByBoardAndPosition(Board board, int position);
}