package com.one.literAlura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.one.literAlura.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNombreAndAnoNacimientoAndAnoFallecimiento(String nombre, Integer anoNacimiento, Integer anoFallecimiento);

    @Query("""
           select distinct a
           from Bock b
           join b.autores a
           order by a.nombre asc
           """)
    List<Author> findDistinctFromBooks();
    
    @Query("""
           select distinct a
           from Bock b
           join b.autores a
           where (a.anoNacimiento is null or a.anoNacimiento <= :year)
             and (a.anoFallecimiento is null or a.anoFallecimiento >= :year)
           order by a.nombre asc
           """)
    List<Author> findAliveInYear(@Param("year") int year);

}
