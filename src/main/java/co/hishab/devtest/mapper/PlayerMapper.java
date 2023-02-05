package co.hishab.devtest.mapper;

import co.hishab.devtest.model.domain.Player;
import co.hishab.devtest.model.dto.CreatePlayerRequest;
import co.hishab.devtest.persistence.entity.PlayerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    List<Player> mapToDomainList(List<PlayerEntity> playerEntities);

    PlayerEntity mapToEntity(CreatePlayerRequest request);

    Player mapToDomain(PlayerEntity entity);
}
