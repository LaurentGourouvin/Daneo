package com.daneo.daneo;

import com.daneo.daneo.translation.client.TranslationClient;
import com.daneo.daneo.translation.dto.TranslationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TranslationClientTest {

    @Autowired
    private TranslationClient translationClient;

    @Test
    void callOpenAi() throws JsonProcessingException {
        TranslationResponse result = translationClient.translate("avocat");
        System.out.println(result);
    }
}