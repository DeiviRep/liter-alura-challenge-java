package com.one.literAlura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.one.literAlura.model.Bock;

public interface BockRepository extends JpaRepository<Bock, Long> {

    List<Bock> findByLenguajeIgnoreCase(String lenguaje);

    List<Bock> findAllByOrderByTituloAsc();

    long countByLenguajeIgnoreCase(String lenguaje);             // <-- cuenta por un idioma

    @Query("select b.lenguaje, count(b) from Bock b where lower(b.lenguaje) in :codes group by b.lenguaje")
    List<Object[]> countByLenguajes(@Param("codes") List<String> codes);

}
