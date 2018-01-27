package com.kalah.service;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import com.kalah.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board createNewBoard(Game game) {
        Board board = new Board();
        board.setGame(game);

        boardRepository.save(board);

        return board;
    }

    public Board getBoardByGame(Game game) {
        return boardRepository.findByGame(game);
    }

}
