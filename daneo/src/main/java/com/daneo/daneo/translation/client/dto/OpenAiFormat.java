package com.daneo.daneo.translation.client.dto;

import com.fasterxml.jackson.databind.JsonNode;

public record OpenAiFormat(String type, String name, Boolean strict, JsonNode schema) {}
