package com.one.literAlura.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "authors")
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK interna

    @Column(nullable = false)
    private String nombre;

    @Column(name = "ano_nacimiento")
    private Integer anoNacimiento;

    @Column(name = "ano_fallecimiento")
    private Integer anoFallecimiento;

    public Author() {}

    public Author(DatosAuthor da) {
        this.nombre = da.nombre();
        this.anoNacimiento = da.anoDesde();
        this.anoFallecimiento = da.anoHasta();
    }
    
    @Override
    public String toString() {
        return "Author{" +
               "nombre='" + nombre + '\'' +
               ", anoNacimiento=" + anoNacimiento +
               ", anoFallecimiento=" + anoFallecimiento +
               '}';
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getAnoNacimiento() { return anoNacimiento; }
    public void setAnoNacimiento(Integer anoNacimiento) { this.anoNacimiento = anoNacimiento; }

    public Integer getAnoFallecimiento() { return anoFallecimiento; }
    public void setAnoFallecimiento(Integer anoFallecimiento) { this.anoFallecimiento = anoFallecimiento; }

    // Para que el Set no duplique autores "iguales"
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author a)) return false;
        return Objects.equals(nombre, a.nombre)
            && Objects.equals(anoNacimiento, a.anoNacimiento)
            && Objects.equals(anoFallecimiento, a.anoFallecimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, anoNacimiento, anoFallecimiento);
    }

}
