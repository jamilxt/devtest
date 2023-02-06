package co.hishab.devtest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "board-game")
public class GameConfig {
    private int maxPlayer;
    private int minPlayer;
    private int winningScore;
    private boolean delayPlayerTurn;
}
