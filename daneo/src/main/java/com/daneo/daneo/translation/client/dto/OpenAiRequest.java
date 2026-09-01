package com.daneo.daneo.translation.client.dto;

import java.util.List;

public record OpenAiRequest(String model, List<OpenAiInput> input, OpenAiText text) {
}
