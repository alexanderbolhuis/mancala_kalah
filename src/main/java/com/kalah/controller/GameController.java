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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Class to handle @{@link Game} related REST calls
 */
@RestController
@RequestMapping("/game")
public class GameController {

    private GameService gameService;
    private PlayerService playerService;
    private BoardService boardService;
    private PitService pitService;
    private HttpSession httpSession;
    private SimpMessagingTemplate template;


    private final Logger logger = LoggerFactory.getLogger(GameController.class);


    /**
     * GameController constructor
     *
     * @param gameService @{@link GameService} dependency
     * @param playerService @{@link PlayerService} dependency
     * @param boardService @{@link BoardService} dependency
     * @param pitService @{@link PitService} dependency
     * @param httpSession @{@link HttpSession} dependency
     * @param template @{@link SimpMessagingTemplate} dependency
     */
    @Autowired
    public GameController(GameService gameService, PlayerService playerService, BoardService boardService,
                          PitService pitService, HttpSession httpSession, SimpMessagingTemplate template) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.boardService = boardService;
        this.pitService = pitService;
        this.httpSession = httpSession;
        this.template = template;
    }

    /**
     * REST endpoint to create a new Game
     *
     * @return @{@link Game} instance of the newly created game.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createNewGame() {
        logger.info("Creating new game");

        // Retrieve Logged player
        Player player = playerService.getLoggedInUser();

        // Create a new game
        Game game = gameService.createNewGame(player);

        // Create the game Board
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

        // Store game id in HttpSession
        httpSession.setAttribute("gameId", game.getId());

        // Log the created game
        logger.info("new game id: " + httpSession.getAttribute("gameId")+ " stored in session" );

        // Notify players in Lobby of update over socket
        template.convertAndSend("/update/lobby", "update");

        return game;
    }

    /**
     * REST endpoint to join a existing Game
     *
     * @param id of the game to join
     * @return @{@link Game} just joined
     */
    @RequestMapping(value = "/join/{id}", method = RequestMethod.POST)
    public Game joinGame(@PathVariable Long id) {
        logger.info("Joining game");

        // Get logged in player
        Player player = playerService.getLoggedInUser();

        // Retrieve game to join and join
        Game game = gameService.joinGame(player, id);

        // Store game id in HttpSession
        httpSession.setAttribute("gameId", id);

        // Notify lobby and game of status update over socket
        template.convertAndSend("/update/join/" + game.getId().toString(), "joined");
        template.convertAndSend("/update/lobby", "updated");

        logger.info("existing game id: " + httpSession.getAttribute("gameId")+ " stored in session" );

        return game;
    }

    /**
     * REST endpoint to get games to join
     *
     * @return List of @{@link Game} able to join
     */
    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getGamesToJoin() {
        logger.info("Getting games to Join");

        return gameService.getGamesToJoin(playerService.getLoggedInUser());
    }


    /**
     * REST endpoint to get current active games for player
     *
     * @return List of @{@link Game} that are active for player
     */
    @RequestMapping(value = "/player/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getPlayerGames() {
        logger.info("Getting active game for player");

        return gameService.getPlayerGames(playerService.getLoggedInUser());
    }

    /**
     * REST endpoint to switch to game by id
     *
     * @param id of the game to retrieve
     * @return @{@link Game} of the game to switch to
     */
    @RequestMapping(value = "/{id}")
    public Game getGameProperties(@PathVariable Long id) {
        logger.info("Switching to game");

        // Set game id in HttpSession
        httpSession.setAttribute("gameId", id);

        logger.info("existing game id: " + httpSession.getAttribute("gameId")+ " stored in session" );

        return gameService.getGameById(id);
    }
}