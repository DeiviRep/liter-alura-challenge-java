package com.one.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBock(
    @JsonAlias("id") Integer Id,
    @JsonAlias("title") String titulo
) {

}
