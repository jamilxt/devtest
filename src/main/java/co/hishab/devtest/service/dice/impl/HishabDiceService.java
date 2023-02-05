package co.hishab.devtest.service.dice.impl;

import co.hishab.devtest.exception.custom.NotFoundException;
import co.hishab.devtest.model.dto.RollDiceResponse;
import co.hishab.devtest.service.dice.DiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class HishabDiceService implements DiceService {
    public static final String DICE_SPOTS_ARE_NOT_VISIBLE = "Dice spots are not visible. Please try again later.";
    public static final String ROLL_DICE_API = "http://developer-test.hishab.io/api/v1/roll-dice";
    private final RestTemplate restTemplate;

    @Override
    public int rollDice() {
        var rollDiceResponseEntity = restTemplate.getForEntity(ROLL_DICE_API, RollDiceResponse.class);
        if (!rollDiceResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new NotFoundException("Hishab's dice not working. Try using my dice.");
        }
        var rollDiceResponse = rollDiceResponseEntity.getBody();
        if (rollDiceResponse == null) {
            throw new NotFoundException(DICE_SPOTS_ARE_NOT_VISIBLE);
        }
        return rollDiceResponse.getScore();
    }
}
