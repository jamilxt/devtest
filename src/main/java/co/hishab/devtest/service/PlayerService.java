package co.hishab.devtest.service;

import co.hishab.devtest.config.GameConfig;
import co.hishab.devtest.exception.custom.AlreadyExistsException;
import co.hishab.devtest.exception.custom.LimitExceedException;
import co.hishab.devtest.exception.custom.NotFoundException;
import co.hishab.devtest.mapper.PlayerMapper;
import co.hishab.devtest.model.domain.Player;
import co.hishab.devtest.model.dto.CreatePlayerRequest;
import co.hishab.devtest.persistence.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerService {
    public static final String MAXIMUM_PLAYER_LIMIT_EXCEED = "Maximum player limit (%s) exceed!";
    public static final String PLAYER_ALREADY_EXIST = "Player with name (%s) is already exist. Please try another name.";
    public static final String PLAYER_NOT_FOUND = "Player not found";
    private final GameConfig gameConfig;
    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;

    public List<Player> getPlayers() {
        return playerMapper.mapToDomainList(playerRepository.findAll());
    }

    public long createPlayer(CreatePlayerRequest request) {
        log.info("Creating a player: {}", request);
        this.validateCreatePlayer(request);
        var playerEntity = playerMapper.mapToEntity(request);
        var savedPlayerEntity = playerRepository.save(playerEntity);
        return savedPlayerEntity.getId();
    }

    private void validateCreatePlayer(CreatePlayerRequest request) {
        if (this.getPlayerCount() >= gameConfig.getMaxPlayer()) {
            throw new LimitExceedException(String.format(MAXIMUM_PLAYER_LIMIT_EXCEED, gameConfig.getMaxPlayer()));
        }
        if (this.isPlayerExist(request.getName())) {
            throw new AlreadyExistsException(String.format(PLAYER_ALREADY_EXIST, request.getName()));
        }
    }

    public long getPlayerCount() {
        return playerRepository.count();
    }

    private boolean isPlayerExist(String playerName) {
        return playerRepository.existsByName(playerName);
    }

    public void updatePlayerScore(Long id, int score) {
        var playerEntity = playerRepository.findById(id).orElseThrow(() -> new NotFoundException(PLAYER_NOT_FOUND));
        playerEntity.setScore(score);
        playerRepository.save(playerEntity);
    }
}
