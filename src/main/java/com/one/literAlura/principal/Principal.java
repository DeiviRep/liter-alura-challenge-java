package com.one.literAlura.principal;

import java.util.Scanner;

import com.one.literAlura.model.Bock;
import com.one.literAlura.model.DatosBock;
import com.one.literAlura.model.ListDatosBock;
import com.one.literAlura.repository.BockRepository;
import com.one.literAlura.service.ConsumoAPI;
import com.one.literAlura.service.ConvierteDatos;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final String SLASH = "/";
    private String ID_BOCK = "84";
    private ConvierteDatos conversor = new ConvierteDatos();
    private BockRepository repositorio;
    public Principal(BockRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        // getDatosListBocks();
        buscarLibroPorId();
    }

    private ListDatosBock getDatosListBocks() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println("== BOOKS API ==");
        ListDatosBock datos = conversor.obtenerDatos(json, ListDatosBock.class);
        System.out.println(datos);
        return datos;
    }

    private DatosBock getDatosBock() {
        var json = consumoApi.obtenerDatos(URL_BASE + ID_BOCK + SLASH);
        DatosBock datos = conversor.obtenerDatos(json, DatosBock.class);
        return datos;
    }
    
    private void buscarLibroPorId() {
        System.out.println("== BOOK API ==");
        System.out.println("Ingresa el ID a Buscar");
        ID_BOCK = teclado.nextLine();
        DatosBock datos = getDatosBock();
        Bock bock = new Bock(datos);
        System.out.println(bock);
    }
    
}
