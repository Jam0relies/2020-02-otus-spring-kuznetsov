package ru.otus.homework10.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddAuthorRequestDto {
    @NotEmpty
    private String name;
}
