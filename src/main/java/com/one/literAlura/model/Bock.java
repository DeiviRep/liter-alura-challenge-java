package com.one.literAlura.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Bock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false, length = 500)
    private String titulo;
    @Column(columnDefinition = "text")
    private String resumen;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
        CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(
        name = "bock_authors",
        joinColumns = @JoinColumn(name = "bock_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> autores = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "bock_languages", 
        joinColumns = @JoinColumn(name = "bock_id")
    )
    @Column(name = "language")
    private List<String> lenguajes = new ArrayList<>();

    public Bock(){}

    public Bock(DatosBock dto) {
        this.titulo = dto.titulo();        
        this.autores = dto.autores().stream().map(Author::new)
                .collect(Collectors.toCollection(HashSet::new));
        this.resumen = dto.resumen().toString();
        this.lenguajes = dto.lenguaje();
    }

    @Override

    public String toString() {
        return "Bock{" +
               "id=" + Id +
               ", titulo='" + safe(titulo) + '\'' +
               ", resumenLines=" + (resumen == null ? 0 : Arrays.toString(resumen.split("\\R",-1))) +
               ", autores=" + (lenguajes == null ? "[]" : autores) +
               ", lenguajes=" + (lenguajes == null ? "[]" : lenguajes) +
               '}';
    }

    private String safe(String s) { return s == null ? "" : s;}
    
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

        
    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }
}
