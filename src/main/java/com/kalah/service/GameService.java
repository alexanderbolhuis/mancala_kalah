package com.kalah.service;

import com.kalah.domain.Game;
import com.kalah.domain.Player;
import com.kalah.enums.GameState;
import com.kalah.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for @{@link Game} related actions
 */
@Service
@Transactional
public class GameService {

    private GameRepository gameRepository;

    /**
     * GameService constructor
     *
     * @param gameRepository @{@link GameRepository} dependency
     */
    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Function to create a new game
     *
     * @param player @{@link Player} that created the game
     * @return @{@link Game} newly created Game
     */
    public Game createNewGame(Player player) {
        // Create game and set variables
        Game game  = new Game(player, player, GameState.WAIT_FOR_PLAYER);

        // Save game
        gameRepository.save(game);

        return game;
    }

    /**
     * Function to join a Game
     *
     * @param player @{@link Player} that wants to join the game
     * @param gameId The id of the Game to join
     * @return @{@link Game} that was joined
     */
    public Game joinGame(Player player, Long gameId) {
        // Find game by id
        Game game = getGameById(gameId);

        // Set Second player
        game.setSecondPlayer(player);

        // Update gamestate
        updateGameState(game, GameState.IN_PROGRESS);

        // Save game
        gameRepository.save(game);

        return game;
    }

    /**
     * Function to change the player's turn
     *
     * @param player @{@link Player} to switch turn to
     * @param gameId The id of the Game
     * @return @{@link Game} that the turn changed for
     */
    public Game switchTurn(Player player, Long gameId) {
        // Find game by id
        Game game = getGameById(gameId);

        // Set player turn
        game.setPlayerTurn(player);

        // save game
        gameRepository.save(game);

        return game;
    }

    /**
     * Function to update the game state
     *
     * @param game @{@link Game} to update for
     * @param gameState @{@link GameState} to change to
     * @return @{@link Game} that was changed
     */
    public Game updateGameState(Game game, GameState gameState) {
        // Find game by id
        Game g = getGameById(game.getId());

        // Change state
        g.setGameState(gameState);

        // save game
        gameRepository.save(g);

        return g;
    }

    /**
     * Function to retrieve game by id
     *
     * @param id of the game to retrieve
     * @return @{@link Game} that has been retrieved
     */
    public Game getGameById(Long id) {
        return gameRepository.findOne(id);
    }

    /**
     * Function to retrieve the games that are joinable
     *
     * @param player @{@link Player} to look for
     * @return List of @{@link Game} to join
     */
    public List<Game> getGamesToJoin(Player player) {
        return gameRepository.findByGameState(GameState.WAIT_FOR_PLAYER)
                .stream().filter(
                        game -> game.getFirstPlayer() != player
                ).collect(Collectors.toList());
    }

    /**
     * Function to retrieve active games for player
     *
     * @param player @{@link Player} to look for
     * @return List of @{@link Game} that are active for player
     */
    public List<Game> getPlayerGames(Player player) {
        return gameRepository.findByGameState(GameState.IN_PROGRESS)
                .stream().filter(
                        game -> (game.getFirstPlayer() == player ||
                                game.getSecondPlayer() == player)

                ).collect(Collectors.toList());
    }

}
