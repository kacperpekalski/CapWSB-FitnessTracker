package com.capgemini.wsb.fitnesstracker.user.internal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record UserDto(@Nullable Long id, String firstName, String lastName,
               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
               String email) {

    public Long getId() {
        return id;
    }
}