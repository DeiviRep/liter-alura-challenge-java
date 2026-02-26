package com.one.literAlura.principal;

import com.one.literAlura.model.ListDatosBock;
import com.one.literAlura.repository.BockRepository;
import com.one.literAlura.service.ConsumoAPI;
import com.one.literAlura.service.ConvierteDatos;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private BockRepository repositorio;
    public Principal(BockRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        getDatosSerie();
    }

    private void getDatosSerie() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println("== BOOKS API ==");
        var datos = conversor.obtenerDatos(json, ListDatosBock.class);
        System.out.println(datos);
    }
    
}
