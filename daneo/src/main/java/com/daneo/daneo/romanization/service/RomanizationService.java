package com.daneo.daneo.romanization.service;

import net.crizin.KoreanRomanizer;
import org.springframework.stereotype.Service;

@Service
public class RomanizationService {
    public String romanize(String korean) {
        return KoreanRomanizer.romanize(korean);
    }
}
