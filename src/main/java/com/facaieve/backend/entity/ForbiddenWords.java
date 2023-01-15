package com.facaieve.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum ForbiddenWords {

    FW1("씨발"),
    FW2("개새끼"),
    FW3("병신"),
    FW4("개새끼"),


    ;
    private final String badWord;

}
