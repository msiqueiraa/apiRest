package com.example.apiRest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.catalina.Session;

import java.math.BigDecimal;

public record ProductRecordDto(@NotBlank String name,@NotNull BigDecimal value) {

}
