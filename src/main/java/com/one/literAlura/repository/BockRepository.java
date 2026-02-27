package com.one.literAlura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.one.literAlura.model.Bock;

public interface BockRepository extends JpaRepository<Bock, Long> {

    List<Bock> findByLenguajeIgnoreCase(String lenguaje);

    List<Bock> findAllByOrderByTituloAsc();

}
