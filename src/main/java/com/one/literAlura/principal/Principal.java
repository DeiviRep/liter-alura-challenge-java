package com.one.literAlura.principal;

import java.util.Scanner;

import com.one.literAlura.model.Bock;
import com.one.literAlura.model.DatosBock;
import com.one.literAlura.repository.BockRepository;
import com.one.literAlura.service.ConsumoAPI;
import com.one.literAlura.service.ConvierteDatos;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final String SLASH = "/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private BockRepository repositorio;

    public Principal(BockRepository repository) {
        this.repositorio = repository;
    }

    // === MENU PRINCIPAL === //
    public void muestraElMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n===== LITER ALURA - BOOK FINDER =====");
            System.out.println("1) Buscar libro por ID");
            System.out.println("2) Guardar libro por ID");
            System.out.println("3) Listar libros guardados");
            System.out.println("0) Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Opción inválida. Intenta nuevamente.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibroPorId();
                case 2 -> guardarLibroPorId();
                case 3 -> listarLibrosGuardados();
                case 0 -> System.out.println("👋 Saliendo... ¡Hasta luego!");
                default -> System.out.println("❌ Opción no válida. Intenta nuevamente.");
            }
        }
    }

    // === FUNCIONES === //

    private DatosBock getDatosBock(String id) {
        try {
            var json = consumoApi.obtenerDatos(URL_BASE + id + SLASH);
            return conversor.obtenerDatos(json, DatosBock.class);
        } catch (Exception e) {
            System.out.println("❌ Error al obtener datos del API.");
            return null;
        }
    }

    private void buscarLibroPorId() {
        System.out.println("\n== BOOK API ==");
        System.out.print("Ingresa el ID del libro: ");
        String id = teclado.nextLine();

        DatosBock datos = getDatosBock(id);
        if (datos == null) {
            System.out.println("❌ No se encontró el libro.");
            return;
        }

        Bock bock = new Bock(datos);
        System.out.println("\n📘 Libro encontrado:");
        System.out.println(bock);
    }

    private void guardarLibroPorId() {
        System.out.println("\n== GUARDAR LIBRO ==");
        System.out.print("Ingresa el ID del libro a guardar: ");
        String id = teclado.nextLine();

        DatosBock datos = getDatosBock(id);
        if (datos == null) {
            System.out.println("❌ No se pudo obtener el libro.");
            return;
        }

        Bock bock = new Bock(datos);
        repositorio.save(bock);

        System.out.println("💾 Libro guardado correctamente:");
        System.out.println(bock);
    }

    private void listarLibrosGuardados() {
        System.out.println("\n== LIBROS GUARDADOS ==");
        var lista = repositorio.findAll();

        if (lista.isEmpty()) {
            System.out.println("📭 No hay libros guardados.");
            return;
        }

        lista.forEach(System.out::println);
    }
}