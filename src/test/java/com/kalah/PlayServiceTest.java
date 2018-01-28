package com.kalah;

import com.kalah.domain.Board;
import com.kalah.domain.Game;
import com.kalah.domain.Player;
import com.kalah.enums.GameState;
import com.kalah.service.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PlayServiceTest {

    @Mock
    private BoardService boardServiceMock;

    @Mock
    private PitService pitServiceMock;

    @Mock
    private GameService gameServiceMock;

    @Mock
    private PlayerService playerServiceMock;

    @InjectMocks
    private PlayService playService = new PlayService();

    @Before
    public void init() { }

    @Test
    public void testIsTurn() {
        Game gameMock = mock(Game.class);
        Player playerMock = mock(Player.class);

        when(gameMock.getPlayerTurn()).thenReturn(playerMock);
        boolean isTurn = playService.isTurn(gameMock, playerMock);

        assertTrue(isTurn);
    }

    @Test
    public void testIsNotTurn() {
        Game gameMock = mock(Game.class);
        Player playerMock = mock(Player.class);
        Player otherPlayerMock = mock(Player.class);

        when(gameMock.getPlayerTurn()).thenReturn(otherPlayerMock);
        boolean isTurn = playService.isTurn(gameMock, playerMock);

        assertFalse(isTurn);
    }

    @Test
    public void testGetPlayerOneScore() {
        Game gameMock = mock(Game.class);
        Player playerMock = mock(Player.class);
        Board boardMock = mock(Board.class);

        int score = 10;

        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(gameMock.getFirstPlayer()).thenReturn(playerMock);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(eq(boardMock), eq(7))).thenReturn(score);

        int result = playService.getScore(gameMock, playerMock);

        assertEquals(result, score);
    }

    @Test
    public void testGetPlayerTwoScore() {
        Game gameMock = mock(Game.class);
        Player playerMock = mock(Player.class);
        Player playerOneMock = mock(Player.class);
        Board boardMock = mock(Board.class);

        int score = 10;

        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(gameMock.getFirstPlayer()).thenReturn(playerOneMock);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(eq(boardMock), eq(14))).thenReturn(score);

        int result = playService.getScore(gameMock, playerMock);

        assertEquals(result, score);
    }

    @Test
    public void testDoMovePlayerOneNoCapture() {
        Game gameMock = mock(Game.class);
        Player playerMock = mock(Player.class);
        Board boardMock = mock(Board.class);

        int position = 1;

        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(gameMock.getPlayerTurn()).thenReturn(playerMock);
        when(gameMock.getFirstPlayer()).thenReturn(playerMock);
        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(boardMock, position)).thenReturn(1);


        Board b2 = playService.doMove(gameMock, playerMock, position);

        // Verify empty
        verify(pitServiceMock).updatePitNumberOfStones(boardMock, position, 0);

        // Verify add stone for every pit
        verify(pitServiceMock, times(1)).updatePitNumberOfStonesByOne(eq(boardMock), anyInt());
    }

    @Test
    public void testDoMovePlayerOneCapture() {
        Game gameMock = mock(Game.class);
        Player playerMock = mock(Player.class);
        Board boardMock = mock(Board.class);

        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(gameMock.getPlayerTurn()).thenReturn(playerMock);
        when(gameMock.getFirstPlayer()).thenReturn(playerMock);
        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(eq(boardMock), eq(6))).thenReturn(10);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(eq(boardMock), eq(3))).thenReturn(1);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(eq(boardMock), eq((14-3)))).thenReturn(3);

        Board b2 = playService.doMove(gameMock, playerMock, 6);

        // Verify empty
        verify(pitServiceMock).updatePitNumberOfStones(boardMock, 6, 0);

        // Verify add stone for every pit
        verify(pitServiceMock, times(10)).updatePitNumberOfStonesByOne(eq(boardMock), anyInt());

        // Verify capture
        verify(pitServiceMock).updatePitNumberOfStones(boardMock, 3, 0);
        verify(pitServiceMock).updatePitNumberOfStones(boardMock, (14-3), 0);
        verify(pitServiceMock).updatePitNumberOfStonesByAmount(boardMock, 7, 4);
    }


    @Test
    public void testDoMovePlayerTwoNoCapture() {
        Game gameMock = mock(Game.class);
        Player playerMock = mock(Player.class);
        Player otherPlayerMock = mock(Player.class);
        Board boardMock = mock(Board.class);

        int position = 8;

        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(gameMock.getPlayerTurn()).thenReturn(playerMock);
        when(gameMock.getFirstPlayer()).thenReturn(otherPlayerMock);
        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(boardMock, position)).thenReturn(1);


        Board b2 = playService.doMove(gameMock, playerMock, position);

        // Verify empty
        verify(pitServiceMock).updatePitNumberOfStones(boardMock, position, 0);

        // Verify add stone for every pit
        verify(pitServiceMock, times(1)).updatePitNumberOfStonesByOne(eq(boardMock), anyInt());
    }

    @Test
    public void testDoMovePlayerTwoCapture() {
        Game gameMock = mock(Game.class);
        Player playerMock = mock(Player.class);
        Player otherPlayerMock = mock(Player.class);
        Board boardMock = mock(Board.class);

        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(gameMock.getPlayerTurn()).thenReturn(playerMock);
        when(gameMock.getFirstPlayer()).thenReturn(otherPlayerMock);
        when(boardServiceMock.getBoardByGame(gameMock)).thenReturn(boardMock);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(eq(boardMock), eq(13))).thenReturn(10);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(eq(boardMock), eq(10))).thenReturn(1);
        when(pitServiceMock.getPitNumberOfStonesByBoardAndPosition(eq(boardMock), eq((14-10)))).thenReturn(3);

        Board b2 = playService.doMove(gameMock, playerMock, 13);

        // Verify empty
        verify(pitServiceMock).updatePitNumberOfStones(boardMock, 13, 0);

        // Verify add stone for every pit
        verify(pitServiceMock, times(10)).updatePitNumberOfStonesByOne(eq(boardMock), anyInt());

        // Verify capture
        verify(pitServiceMock).updatePitNumberOfStones(boardMock, 10, 0);
        verify(pitServiceMock).updatePitNumberOfStones(boardMock, (14-10), 0);
        verify(pitServiceMock).updatePitNumberOfStonesByAmount(boardMock, 14, 4);
    }

    // TODO: Test for game finished

    @After
    public void tearDown() {
    }
}
