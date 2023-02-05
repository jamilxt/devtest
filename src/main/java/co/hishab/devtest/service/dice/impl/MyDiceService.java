package co.hishab.devtest.service.dice.impl;

import co.hishab.devtest.service.dice.DiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyDiceService implements DiceService {

    @Override
    public int rollDice() {
        return (int) (Math.random() * 6 + 1);
    }
}
