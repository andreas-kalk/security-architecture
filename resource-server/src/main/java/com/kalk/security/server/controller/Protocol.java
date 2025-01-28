package com.kalk.security.server.controller;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Protocol {

    public record Product(@JsonProperty("id") UUID id,
                          @JsonProperty("name") String name,
                          @JsonProperty("creator") String creator,
                          @JsonProperty("group") ProductGroup group) {

    }

    public record ProductGroup(@JsonProperty("id") UUID id,
                               @JsonProperty("name") String name) {

    }
}
