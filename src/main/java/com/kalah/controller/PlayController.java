package com.kalah.controller;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import com.kalah.domain.Pit;
import com.kalah.domain.Player;
import com.kalah.service.BoardService;
import com.kalah.service.GameService;
import com.kalah.service.PitService;
import com.kalah.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    HttpSession httpSession;

    Logger logger = LoggerFactory.getLogger(PlayController.class);

    @RequestMapping(value = "/move/{position}", method = RequestMethod.POST)
    public Game doMove(@PathVariable int position) {
        // Get info
        Player player = playerService.getLoggedInUser();
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);
        Board board = boardService.getBoardByGame(game);

        // Check if turn
        // TODO: Move GameLogic out of controller
        if (game.getPlayerTurn.equals(player)) {


        }
    }
}
