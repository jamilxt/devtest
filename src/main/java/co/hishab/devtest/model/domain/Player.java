package co.hishab.devtest.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class Player {
    private long id;
    private String name;
    private int age;
    private int score;
}
