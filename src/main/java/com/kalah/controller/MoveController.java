package com.kalah.controller;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import com.kalah.domain.Player;
import com.kalah.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class MoveController {

    private GameService gameService;
    private PlayerService playerService;
    private PlayService playService;
    private HttpSession httpSession;

    private Logger logger = LoggerFactory.getLogger(MoveController.class);

    @Autowired
    public MoveController(GameService gameService, PlayerService playerService,
                          PlayService playService, HttpSession httpSession) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.playService = playService;
        this.httpSession = httpSession;
    }

    @MessageMapping("/move/{position}")
    @SendTo("/moved")
    public Board doMove(@PathVariable int position) {
        // Get info
        Player player = playerService.getLoggedInUser();
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);

        return playService.doMove(game, player, position);
    }
}
