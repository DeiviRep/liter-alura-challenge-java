package com.one.literAlura.principal;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.one.literAlura.model.Bock;
import com.one.literAlura.model.DatosBock;
import com.one.literAlura.model.GutendexResponse;
import com.one.literAlura.repository.AuthorRepository;
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
    private AuthorRepository authorRepo;

    public Principal(BockRepository repository, AuthorRepository authorRepository) {
        this.repositorio = repository;
        this.authorRepo = authorRepository;
    }

    // === MENU PRINCIPAL === //
    public void muestraElMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n===== LITER ALURA - BOOK FINDER =====");
            System.out.println("1) Buscar libro por ID");
            System.out.println("2) Guardar libro por ID");
            System.out.println("3) Listar libros guardados");            
            System.out.println("4) Buscar por Título (guarda primer resultado)");
            System.out.println("5) Listar por Idioma");
            System.out.println("6) Listar Autores (de libros guardados)");
            System.out.println("7) Listar Autores vivos en un año");
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
                case 4 -> buscarYGuardarPorTitulo();
                case 5 -> listarPorIdioma();
                case 6 -> listarAutores();
                case 7 -> listarAutoresVivosEnAnio();
                case 0 -> System.out.println("👋 Saliendo... ¡Hasta luego!");
                default -> System.out.println("❌ Opción no válida. Intenta nuevamente.");
            }
        }
    }

    // === FUNCIONES === //

    private DatosBock getDatosBockPorId(String id) {
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

        DatosBock datos = getDatosBockPorId(id);
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

        DatosBock datos = getDatosBockPorId(id);
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

    private void buscarYGuardarPorTitulo() {
        System.out.println("\n== BUSCAR POR TÍTULO ==");
        System.out.print("Ingresa el título o parte del título: ");
        String titulo = teclado.nextLine();

        try {
            String url = URL_BASE + "?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            var json = consumoApi.obtenerDatos(url);
            var resp = conversor.obtenerDatos(json, GutendexResponse.class);

            if (resp == null || resp.results() == null || resp.results().isEmpty()) {
                System.out.println("📭 Sin resultados para: " + titulo);
                return;
            }

            var dto = resp.results().get(0); // primer resultado
            var bock = new Bock(dto);
            repositorio.save(bock);
            System.out.println("✅ Guardado primer resultado:");
            System.out.println(bock);
        } catch (Exception e) {
            System.out.println("❌ Error buscando por título: " + e.getMessage());
        }
    }

    private void listarPorIdioma() {
        System.out.println("\n== LISTAR POR IDIOMA ==");
        System.out.print("Ingresa el código de idioma (ej: en, es, fr): ");
        var code = teclado.nextLine().trim();

        var lista = repositorio.findByLenguajeIgnoreCase(code);
        if (lista.isEmpty()) {
            System.out.println("📭 No hay libros guardados en el idioma: " + code);
            return;
        }
        lista.forEach(System.out::println);
    }

    private void listarAutores() {
        System.out.println("\n== AUTORES (de libros guardados) ==");
        var autores = authorRepo.findDistinctFromBooks();
        if (autores.isEmpty()) {
            System.out.println("📭 No hay autores asociados a libros guardados.");
            return;
        }
        autores.forEach(System.out::println); // usa Author.toString() simple que ya hicimos
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("\n== AUTORES VIVOS EN UN AÑO ==");
        System.out.print("Ingresa el año (ej: 1900): ");
        int year;
        try {
            year = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Año inválido.");
            return;
        }

        var autores = authorRepo.findAliveInYear(year);
        if (autores.isEmpty()) {
            System.out.println("📭 No hay autores vivos en el año: " + year);
            return;
        }
        autores.forEach(System.out::println);
    }

}