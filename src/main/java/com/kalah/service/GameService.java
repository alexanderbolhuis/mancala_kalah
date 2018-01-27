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

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createNewGame(Player player) {
        // Create game and set variables
        Game game  = new Game();
        game.setFirstPlayer(player);
        game.setPlayerTurn(player);
        updateGameState(game, GameState.WAIT_FOR_PLAYER);

        // Save game
        gameRepository.save(game);

        return game;
    }

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

    public Game switchTurn(Player player, Long gameId) {
        // Find game by id
        Game game = getGameById(gameId);

        // Set player turn
        game.setPlayerTurn(player);

        // save game
        gameRepository.save(game);

        return game;
    }

    // TODO: Check if needed
    public Game updateGameState(Game game, GameState gameState) {
        Game g = getGameById(game.getId());
        g.setGameState(gameState);

        return g;
    }

    // TODO: Check Long int cast
    public Game getGameById(Long id) {
        return gameRepository.findOne(id);
    }

    public List<Game> getGamesToJoin(Player player) {
        return gameRepository.findByGameState(GameState.WAIT_FOR_PLAYER)
                .stream().filter(
                        game -> game.getFirstPlayer() != player
                ).collect(Collectors.toList());
    }

    public List<Game> getPlayerGames(Player player) {
        return gameRepository.findByGameState(GameState.IN_PROGRESS)
                .stream().filter(
                        game -> game.getFirstPlayer() == player
                ).collect(Collectors.toList());
    }

}
