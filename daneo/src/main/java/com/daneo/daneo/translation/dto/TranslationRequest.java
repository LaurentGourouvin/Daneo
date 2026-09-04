package com.daneo.daneo.translation.dto;

import jakarta.validation.constraints.NotBlank;

public record TranslationRequest(
        @NotBlank String frenchTerm
) {
}
