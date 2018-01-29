package com.kalah.controller;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import com.kalah.domain.Pit;
import com.kalah.domain.Player;
import com.kalah.enums.GameState;
import com.kalah.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/play")
public class PlayController {

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    @Autowired
    BoardService boardService;

    @Autowired
    PitService pitService;

    @Autowired
    PlayService playService;

    @Autowired
    HttpSession httpSession;

    Logger logger = LoggerFactory.getLogger(PlayController.class);

    @RequestMapping(value = "/move/{position}", method = RequestMethod.POST)
    public Board doMove(@PathVariable int position) {
        // Get info
        Player player = playerService.getLoggedInUser();
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);

        return playService.doMove(game, player, position);
    }

    @RequestMapping(value = "/turn", produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getPlayerTurn() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);

        return game.getPlayerTurn();
    }

    @RequestMapping(value = "/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getScore() {
        Player player = playerService.getLoggedInUser();
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);

        return playService.getScore(game, player);
    }

    @RequestMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameState getState() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);

        return game.getGameState();
    }

    @RequestMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pit> getBoard() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);
        Board board = boardService.getBoardByGame(game);

        return pitService.getPitsByBoardOrderedPosition(board);
    }
}
