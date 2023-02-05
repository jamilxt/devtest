package co.hishab.devtest.service;

import co.hishab.devtest.config.GameConfig;
import co.hishab.devtest.model.domain.Player;
import co.hishab.devtest.service.dice.DiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @InjectMocks
    private GameService gameService;
    @Mock
    GameConfig gameConfig;
    @Mock
    PlayerService playerService;
    @Mock
    DiceService diceService;

    @Test
    void When_GameWith2Players_Expected_2ndPlayerWin() {
        List<Player> playerList =
                List.of(
                        new Player().setName("A").setAge(12).setId(1L),
                        new Player().setName("B").setAge(12).setId(2L)
                );
        when(gameConfig.getWinningScore()).thenReturn(25);
        when(playerService.getPlayers()).thenReturn(playerList);
        when(diceService.rollDice())
                .thenReturn(
                        3, 6, 4, 4, 3, 4,
                        1, 5, 1, 2, 3, 5, 6, 5, 4, 4,
                        3, 3, 2, 1, 2, 6,
                        3, 4, 4, 4, 6, 6
                );
        gameService.startGame();

        Assertions.assertEquals(gameService.winnerPlayerId, 2L);
    }

    @Test
    void When_GameWith3Players_Expected_3rdPlayerWin() {
        List<Player> playerList =
                List.of(
                        new Player().setName("A").setAge(12).setId(1L),
                        new Player().setName("B").setAge(12).setId(2L),
                        new Player().setName("C").setAge(12).setId(3L)
                );
        when(gameConfig.getWinningScore()).thenReturn(25);
        when(playerService.getPlayers()).thenReturn(playerList);
        when(diceService.rollDice())
                .thenReturn(
                        3, 6, 4, 4, 3, 4,
                        1, 5, 1, 2, 3, 5, 6, 5, 4, 4,
                        3, 3, 2, 1, 2, 6,
                        3, 4, 4, 4, 6, 6
                );
        gameService.startGame();

        Assertions.assertEquals(gameService.winnerPlayerId, 3L);
    }

    @Test
    void When_GameWith3PlayersRollingDice_Expected_3rdPlayerWin() {
        List<Player> playerList =
                List.of(
                        new Player().setName("John").setAge(12).setId(1L),
                        new Player().setName("Levi").setAge(12).setId(2L),
                        new Player().setName("Cloe").setAge(12).setId(3L)
                );
        when(gameConfig.getWinningScore()).thenReturn(25);
        when(playerService.getPlayers()).thenReturn(playerList);
        when(diceService.rollDice())
                .thenReturn(
                        1, 1, 3, 2, 4, 2, 2, 3, 3, 4, 3, 2, 1, 5, 6,
                        6, 5, 3, 1, 1, 1, 4, 4, 2, 5, 4, 4, 3, 6, 6, 5, 5, 5, 3,
                        1, 3, 3, 3, 4, 5, 6, 4, 5, 4, 1, 6, 2, 4, 1, 6, 5, 6, 6,
                        4, 3, 1, 4, 5, 4, 1, 2, 1, 5, 5, 5, 1, 1, 6, 2, 2, 2, 6,
                        6, 1, 3
                );
        gameService.startGame();

        Assertions.assertEquals(gameService.winnerPlayerId, 3L);
    }

    @Test
    void When_GameWith3Players_Expected_4thPlayerWin() {
        List<Player> playerList =
                List.of(
                        new Player().setName("A").setAge(12).setId(1L),
                        new Player().setName("B").setAge(12).setId(2L),
                        new Player().setName("C").setAge(12).setId(3L),
                        new Player().setName("D").setAge(12).setId(4L)
                );
        when(gameConfig.getWinningScore()).thenReturn(25);
        when(playerService.getPlayers()).thenReturn(playerList);
        when(diceService.rollDice())
                .thenReturn(
                        3, 6, 4, 4, 3, 4,
                        1, 5, 1, 2, 3, 5, 6, 5, 4, 4,
                        3, 3, 2, 1, 2, 6,
                        3, 4, 4, 4, 6, 6
                );
        gameService.startGame();

        Assertions.assertEquals(gameService.winnerPlayerId, 4L);
    }
}
