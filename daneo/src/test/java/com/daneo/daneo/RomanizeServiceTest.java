package com.daneo.daneo;

import com.daneo.daneo.romanization.service.RomanizationService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RomanizationServiceTest {

    private final RomanizationService romanizationService = new RomanizationService();

    @Test
    void shouldRomanizeSimpleWord() {
        assertThat(romanizationService.romanize("사과")).isEqualTo("Sagwa");
    }
}