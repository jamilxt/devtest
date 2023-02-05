package co.hishab.devtest.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@ToString
@Getter
@Setter
public class CreatePlayerRequest {
    @NotNull
    private String name;
    @Positive
    private int age;
}
