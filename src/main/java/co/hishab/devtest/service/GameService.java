package co.hishab.devtest.service;

import co.hishab.devtest.config.GameConfig;
import co.hishab.devtest.exception.custom.AlreadyExistsException;
import co.hishab.devtest.exception.custom.InsufficientException;
import co.hishab.devtest.exception.custom.LimitExceedException;
import co.hishab.devtest.model.GameStatus;
import co.hishab.devtest.model.domain.Player;
import co.hishab.devtest.model.dto.Scoreboard;
import co.hishab.devtest.service.dice.DiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameService {
    public static final String INSUFFICIENT_PLAYERS = "Insufficient players! " +
            "Minimum %s players are required to start the game. " +
            "Current players: %s";
    public static final String THE_GAME_IS_GOING_ON = "The game is going on... The scoreboard is available to check";
    public static final String THE_GAME_IS_FINISHED = "The game is finished. Please restart the application to play a new game";

    LinkedHashMap<Long, Player> playersScoreById;
    Map<Long, Player> gainedStartingPoint;
    long winnerPlayerId;
    Player winnerPlayer;
    Scoreboard scoreboard;
    private final GameConfig gameConfig;

    private final PlayerService playerService;
    private final DiceService diceService;

    public GameService(GameConfig gameConfig, PlayerService playerService, @Qualifier("hishabDiceService") DiceService diceService) {
        this.gameConfig = gameConfig;
        this.playersScoreById = new LinkedHashMap<>();
        this.scoreboard = new Scoreboard();
        this.playerService = playerService;
        this.diceService = diceService;
    }

    @Async
    public void startGame() {
        log.info("Starting game...");
        scoreboard.setStatus(GameStatus.ONGOING);
        this.processGame();
    }

    private void processGame() {
        List<Player> playersFromDB = playerService.getPlayers();

        playersScoreById = this.getPlayersScoreById(playersFromDB);
        gainedStartingPoint = new HashMap<>();

        boolean gameOver = false;
        int playerPosition = 0;

        while (!gameOver) {
            this.delayPlayerTurns();

            var playerId = playersFromDB.get(playerPosition).getId();

            var player = playersScoreById.get(playerId);
            boolean extraThrow = false;

            var diceValue = diceService.rollDice();
            if (!this.playerGotStartingPoint(playerId)) {
                if (diceValue == 6) {
                    log.info("Player Name: {}, Total Score: {},  Current Value of Dice: {}", player.getName(), player.getScore(), diceValue);

                    diceValue = diceService.rollDice();
                    switch (diceValue) {
                        case 4:
                            log.info("Player Name: {}, Total Score: {},  Current Value of Dice: {}", player.getName(), player.getScore(), diceValue);
                            break;
                        case 6:
                            extraThrow = true;
                        default:
                            this.updatePlayerScore(playerId, diceValue, true);
                            gainedStartingPoint.put(playerId, player);
                            log.info("Player Name: {}, Total Score: {},  Current Value of Dice: {} [Starting Point]".concat(extraThrow ? " [Extra Throw]" : ""), player.getName(), player.getScore(), diceValue);
                    }
                }
            } else {
                switch (diceValue) {
                    case 4:
                        this.updatePlayerScore(playerId, diceValue, false);
                        break;
                    case 6:
                        extraThrow = true;
                    default:
                        this.updatePlayerScore(playerId, diceValue, true);
                }
            }
            log.info("Player Name: {}, Total Score: {},  Current Value of Dice: {}".concat(extraThrow ? " [Extra Throw]" : ""), player.getName(), player.getScore(), diceValue);

            if (this.isPlayerWon(player.getScore())) {
                winnerPlayerId = playerId;
                winnerPlayer = playersScoreById.get(playerId);
                log.info("""
                        \n--------------------------------------------
                         Winner: Player Name: {}, Total Score: {}    
                        --------------------------------------------
                        """, winnerPlayer.getName(), winnerPlayer.getScore());
                gameOver = true;
                scoreboard.setStatus(GameStatus.FINISHED);
                this.saveFinalizedScoreToDb(playersScoreById);
            }

            if (!extraThrow) {
                playerPosition = this.getNextPlayerPosition(playerPosition, playersFromDB.size());
            }
        }
    }

    private void delayPlayerTurns() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Scoreboard getGameScoreboard() {
        List<Player> players = new ArrayList<>();
        playersScoreById.forEach((id, player) -> players.add(player));
        scoreboard.setPlayers(players);
        if (winnerPlayer != null) {
            scoreboard.setWinner(winnerPlayer);
        }
        return scoreboard;
    }

    private LinkedHashMap<Long, Player> getPlayersScoreById(List<Player> playersFromDB) {
        return playersFromDB.stream()
                .collect(Collectors.toMap(Player::getId, player -> player, (a, b) -> b, LinkedHashMap::new));
    }

    private boolean playerGotStartingPoint(long playerId) {
        return gainedStartingPoint.containsKey(playerId);
    }

    private boolean isPlayerWon(int playerScore) {
        return playerScore >= gameConfig.getWinningScore();
    }

    public void validateStartGame() {
        long playerCount = playerService.getPlayerCount();
        if (playerCount < gameConfig.getMinPlayer()) {
            throw new InsufficientException(String.format(INSUFFICIENT_PLAYERS, gameConfig.getMinPlayer(), playerCount));
        }
        switch (scoreboard.getStatus()) {
            case ONGOING -> throw new AlreadyExistsException(THE_GAME_IS_GOING_ON);
            case FINISHED -> throw new LimitExceedException(THE_GAME_IS_FINISHED);
        }
    }

    private int getNextPlayerPosition(int currentPlayerPosition, int totalPlayers) {
        return (currentPlayerPosition + 1) % totalPlayers;
    }

    private void updatePlayerScore(long playerId, int diceValue, boolean increase) {
        var player = playersScoreById.get(playerId);
        var playerScore = player.getScore();
        playerScore = increase ? player.getScore() + diceValue : player.getScore() - diceValue;
        player.setScore(playerScore);
        playersScoreById.put(playerId, player);
    }

    private void saveFinalizedScoreToDb(LinkedHashMap<Long, Player> playersScoreById) {
        playersScoreById.forEach((id, player) -> playerService.updatePlayerScore(id, player.getScore()));
    }
}
