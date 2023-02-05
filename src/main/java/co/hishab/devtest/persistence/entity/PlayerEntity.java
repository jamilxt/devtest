package co.hishab.devtest.persistence.entity;

import co.hishab.devtest.constant.EntityConstant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = EntityConstant.PLAYER)
public class PlayerEntity extends BaseEntity {
    @Column(unique = true)
    private String name;
    private int age;

    private int score;
}
