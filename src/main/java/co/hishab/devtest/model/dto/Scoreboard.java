package co.hishab.devtest.model.dto;

import co.hishab.devtest.model.GameStatus;
import co.hishab.devtest.model.domain.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
public class Scoreboard {
    private GameStatus status;
    private List<Player> players;
    private Player winner;

    public Scoreboard() {
        this.status = GameStatus.NOT_STARTED;
        this.players = new ArrayList<>();
    }
}
