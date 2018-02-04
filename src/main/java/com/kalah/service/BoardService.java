package com.kalah.service;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import com.kalah.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class to handle @{@link Board} related actions
 */
@Service
@Transactional
public class BoardService {

    private BoardRepository boardRepository;

    /**
     * BoardService constructor
     *
     * @param boardRepository @{@link BoardRepository} dependency
     */
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * Create new board
     *
     * @param game @{@link Game} for which to create Board
     * @return @{@link Board} that has been created
     */
    public Board createNewBoard(Game game) {
        Board board = new Board(game);

        boardRepository.save(board);

        return board;
    }

    /**
     * Retrieve a Board by @{@link Game}
     *
     * @param game @{@link Game} to retrieve Board for
     * @return @{@link Board} matching Game given
     */
    public Board getBoardByGame(Game game) {
        return boardRepository.findByGame(game);
    }

}
