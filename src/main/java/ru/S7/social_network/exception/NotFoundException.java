package ru.S7.social_network.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotFoundException extends Exception {
    private final String message;
}
