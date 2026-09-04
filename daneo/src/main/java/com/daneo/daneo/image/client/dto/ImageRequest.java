package com.daneo.daneo.image.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImageRequest(String model, String prompt, int n, String size, String quality, String background,
                           @JsonProperty("output_format") String outputFormat) {
}