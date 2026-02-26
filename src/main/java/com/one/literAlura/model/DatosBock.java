package com.one.literAlura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBock(
    @JsonAlias("id") Integer Id,    
    @JsonAlias("title") String titulo,
    @JsonAlias("authors") List<DatosAuthor> autores,
    @JsonAlias("summaries") List<String> resumen,
    @JsonAlias("languages") List<String> lenguaje

) {

}
