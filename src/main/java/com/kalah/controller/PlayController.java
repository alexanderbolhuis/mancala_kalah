package com.kalah.controller;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import com.kalah.domain.Player;
import com.kalah.enums.GameState;
import com.kalah.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Class to handle gameplay related REST calls
 */
@RestController
@RequestMapping("/play")
public class PlayController {

    private GameService gameService;
    private PlayerService playerService;
    private BoardService boardService;
    private PlayService playService;
    private HttpSession httpSession;
    private SimpMessagingTemplate template;

    private final Logger logger = LoggerFactory.getLogger(PlayController.class);

    /**
     * PlayController constructor
     *
     * @param gameService @{@link GameService} dependency
     * @param playerService @{@link PlayerService} dependency
     * @param boardService @{@link BoardService} dependency
     * @param playService @{@link PlayService} dependency
     * @param httpSession @{@link HttpSession} dependency
     * @param template @{@link SimpMessagingTemplate} dependency
     */
    @Autowired
    public PlayController(GameService gameService, PlayerService playerService,
                          BoardService boardService, PlayService playService,
                          HttpSession httpSession, SimpMessagingTemplate template) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.boardService = boardService;
        this.playService = playService;
        this.httpSession = httpSession;
        this.template = template;
    }

    /**
     * REST endpoint to do a player move
     *
     * @param position the position of the house to start move from
     * @return @{@link Board} instance of the current board layout
     */
    @RequestMapping(value = "/move/{position}", method = RequestMethod.POST)
    public Board doMove(@PathVariable int position) {
        logger.info("Starting move for Player");

        // Get info
        Player player = playerService.getLoggedInUser();
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);

        // Do move
        Board board = playService.doMove(game, player, position);

        // Notify players of board change
        template.convertAndSend("/update/position/" + gameId.toString(), "moved");

        return board;
    }

    /**
     * REST endpoint to get the current players turn
     *
     * @return @{@link Player} instance of the player turn
     */
    @RequestMapping(value = "/turn", produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getPlayerTurn() {
        logger.info("Getting player turn");

        // Get info
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);

        // Return turn
        return game.getPlayerTurn();
    }

    /**
     * REST endpoint to get the logged in player's score
     *
     * @return @{@link Integer} of the score
     */
    @RequestMapping(value = "/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getScore() {
        logger.info("Getting player score");

        // Get Info
        Player player = playerService.getLoggedInUser();
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);

        // Return score
        return playService.getScore(game, player);
    }

    /**
     * REST endpoint to get the game state
     *
     * @return @{@link GameState} of the current game
     */
    @RequestMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameState getState() {
        logger.info("Getting game state");

        // Get info
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);

        // Return state
        return game.getGameState();
    }

    /**
     * REST endpoint to get the board info
     *
     * @return @{@link Board} of the current board
     */
    @RequestMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
    public Board getBoard() {
        logger.info("Retrieving game board");

        // Get info
        Long gameId = (Long) httpSession.getAttribute("gameId");
        Game game = gameService.getGameById(gameId);
        Board board = boardService.getBoardByGame(game);

        // Return board
        return board;
    }
}
