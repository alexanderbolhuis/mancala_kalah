package com.kalah.service;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import com.kalah.domain.Player;
import com.kalah.enums.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayService {

    private GameService gameService;
    private BoardService boardService;
    private PitService pitService;

    // Game constants
    private static final int NR_OF_PITS = 14;
    private static final int P1_LOWER_BOUNDARY = 1;
    private static final int P1_UPPER_BOUNDARY = 6;
    private static final int P2_LOWER_BOUNDARY = 8;
    private static final int P2_UPPER_BOUNDARY = 13;
    private static final int P1_STORE = 7;
    private static final int P2_STORE = 14;

    @Autowired
    public PlayService(GameService gameService, BoardService boardService, PitService pitService) {
        this.gameService = gameService;
        this.boardService = boardService;
        this.pitService = pitService;
    }

    public boolean isTurn(Game game, Player player) {
        return game.getPlayerTurn() == player;
    }

    public Board doMove(Game game, Player player, int position) {
        Board board = boardService.getBoardByGame(game);
        if(isTurn(game, player)) {
            // P1
            if(player == game.getFirstPlayer()) {
                board = handleFirstPlayerMove(game, position);
            }
            // P2
            else {
                board = handleSecondPlayerMove(game, position);
            }
        }

        return board;
    }

    public int getScore(Game game, Player player) {
        Board board = boardService.getBoardByGame(game);
        if(player == game.getFirstPlayer()) {
            return pitService.getPitNumberOfStonesByBoardAndPosition(board, P1_STORE);
        }
        // P2
        else {
            return pitService.getPitNumberOfStonesByBoardAndPosition(board, P2_STORE);
        }
    }

    public Board handleFirstPlayerMove(Game game, int position) {
        // Get Board
        Board board = boardService.getBoardByGame(game);

        // Validate pit position is >= 1 and <= 6
        if(position >= P1_LOWER_BOUNDARY && position <= P1_UPPER_BOUNDARY) {
            // Do Move
            int index = sowSeeds(board, position, P2_UPPER_BOUNDARY, false);

            // Check capture
            checkCapture(board, index, P1_LOWER_BOUNDARY, P1_UPPER_BOUNDARY, P1_STORE);

            // Check game finished
            boolean isFinished = checkFinished(board);

            if(isFinished) {
                emptyAllPits(board);
                gameService.updateGameState(game, GameState.FINISHED);
            }

            // Check turn
            if (!isFinished && index != P1_STORE) {
                // Switch turn
                gameService.switchTurn(game.getSecondPlayer(), game.getId());
            }
        }

        return board;
    }

    public Board handleSecondPlayerMove(Game game, int position) {
        // Get Board
        Board board = boardService.getBoardByGame(game);

        // Validate pit position is >= 8 and <= 13
        if(position >= P2_LOWER_BOUNDARY && position <= P2_UPPER_BOUNDARY) {
            // Do Move
            int index = sowSeeds(board, position, P2_STORE, true);

            // Check capture
            checkCapture(board, index, P2_LOWER_BOUNDARY, P2_UPPER_BOUNDARY, P2_STORE);

            // Check game finished
            boolean isFinished = checkFinished(board);

            if(isFinished) {
                emptyAllPits(board);
                gameService.updateGameState(game, GameState.FINISHED);
            }

            // Check turn
            if (!isFinished && index != P2_STORE) {
                // Switch turn
                gameService.switchTurn(game.getFirstPlayer(), game.getId());
            }
        }

        return board;
    }

    public int sowSeeds(Board board, int position, int upper, boolean skipP1Store) {
        // Get nr of stones from startPit and empty
        int amount = pitService.getPitNumberOfStonesByBoardAndPosition(board, position);
        pitService.updatePitNumberOfStones(board, position, 0);

        // Start on pos + 1
        int index = position + 1;

        // Start Sowing
        while (amount != 0) {
            if(index > upper) {
                index = P1_LOWER_BOUNDARY;
            }
            else if (skipP1Store && index == P1_STORE) {
                // Skip P1 store
                index = P2_LOWER_BOUNDARY;
            }

            // Add stone for every pit
            pitService.updatePitNumberOfStonesByOne(board, index);

            index++;
            amount--;
        }

        index--; // Save last position checked
        return index;
    }

    public void checkCapture(Board board, int index, int lower, int upper, int store) {
        if(index >= lower && index <= upper
                && pitService.getPitNumberOfStonesByBoardAndPosition(board, index) == 1) {
            // Capture stones across
            int indexAcross = NR_OF_PITS - index;
            int amountAcross = pitService.getPitNumberOfStonesByBoardAndPosition(board, indexAcross);

            if(amountAcross > 0) {
                pitService.updatePitNumberOfStones(board, indexAcross, 0);
                pitService.updatePitNumberOfStones(board, index, 0);

                pitService.updatePitNumberOfStonesByAmount(board, store, (amountAcross + 1));
            }
        }
    }

    public boolean checkFinished(Board board) {
        boolean isFinished = true;
        for (int i = P1_LOWER_BOUNDARY; i <= P1_UPPER_BOUNDARY; i++) {
            if (pitService.getPitNumberOfStonesByBoardAndPosition(board, i) > 0) {
                isFinished = false;
                break;
            }
        }

        for (int i = P2_LOWER_BOUNDARY; i <= P2_UPPER_BOUNDARY; i++) {
            if (pitService.getPitNumberOfStonesByBoardAndPosition(board, i) > 0) {
                isFinished = false;
                break;
            }
        }
        return isFinished;
    }

    public void emptyAllPits(Board board) {
        for (int i = P1_LOWER_BOUNDARY; i <= P1_UPPER_BOUNDARY; i++) {
            int tmpAmount = pitService.getPitNumberOfStonesByBoardAndPosition(board, i);
            if (tmpAmount > 0) {
                pitService.updatePitNumberOfStones(board, i, 0);
                pitService.updatePitNumberOfStonesByAmount(board, P1_STORE, tmpAmount);
            }
        }

        for (int i = P2_LOWER_BOUNDARY; i <= P2_UPPER_BOUNDARY; i++) {
            int tmpAmount = pitService.getPitNumberOfStonesByBoardAndPosition(board, i);
            if (tmpAmount > 0) {
                pitService.updatePitNumberOfStones(board, i, 0);
                pitService.updatePitNumberOfStonesByAmount(board, P2_STORE, tmpAmount);
            }
        }
    }
}
