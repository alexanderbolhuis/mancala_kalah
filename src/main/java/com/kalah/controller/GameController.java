package com.kalah.controller;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import com.kalah.domain.Player;
import com.kalah.enums.PitType;
import com.kalah.service.BoardService;
import com.kalah.service.GameService;
import com.kalah.service.PitService;
import com.kalah.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private GameService gameService;
    private PlayerService playerService;
    private BoardService boardService;
    private PitService pitService;
    private HttpSession httpSession;

    Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    public GameController(GameService gameService, PlayerService playerService, BoardService boardService,
                          PitService pitService, HttpSession httpSession) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.boardService = boardService;
        this.pitService = pitService;
        this.httpSession = httpSession;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createNewGame() {

        // Logged player
        Player player = playerService.getLoggedInUser();

        // Create new game
        Game game = gameService.createNewGame(player);

        // Create Board
        Board board = boardService.createNewBoard(game);

        // Create Pits 6x6 layout + 2 stores
        pitService.createPit(board, PitType.STORE, 7, 0); // store pos 7
        pitService.createPit(board, PitType.STORE, 14, 0); // store post 14

        // P1 houses
        for (int i = 1; i <= 6; i++) {
            pitService.createPit(board, PitType.HOUSE, i, 6);
        }

        // P2 houses
        for (int i = 8; i <= 13; i++) {
            pitService.createPit(board, PitType.HOUSE, i, 6);
        }

        httpSession.setAttribute("gameId", game.getId());

        logger.info("new game id: " + httpSession.getAttribute("gameId")+ " stored in session" );

        return game;
    }

    @RequestMapping(value = "/join/{id}", method = RequestMethod.POST)
    public Game joinGame(@PathVariable Long id) {
        Player player = playerService.getLoggedInUser();
        Game game = gameService.joinGame(player, id);
        httpSession.setAttribute("gameId", id);

        return game;
    }

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getGamesToJoin() {
        return gameService.getGamesToJoin(playerService.getLoggedInUser());
    }



    @RequestMapping(value = "/player/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getPlayerGames() {
        return gameService.getPlayerGames(playerService.getLoggedInUser());
    }

    @RequestMapping(value = "/{id}")
    public Game getGameProperties(@PathVariable Long id) {

        httpSession.setAttribute("gameId", id);

        return gameService.getGameById(id);
    }
}