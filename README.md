***

# LiterAlura – Book Catalog (Java 17)

CLI app built with **Java 17** and **Spring Boot** that consumes **Gutendex** (`https://gutendex.com/`) to search books and persist them in **PostgreSQL** using **Spring Data JPA**.

## Features

*   Search book by **ID** or **Title** (stores the **first** search result).
*   Persist **Books** and **Authors** (Many-to-Many).
*   Store **summary** as `TEXT` (long content).
*   Support **multi-language per book** (`@ElementCollection`) and a **flat primary language** field for easy queries.
*   List:
    *   All saved books
    *   Books by language
    *   Authors (distinct)
    *   Authors alive in a given year
    *   Language count statistics

## Tech Stack

*   Java 17, Spring Boot, Spring Data JPA (Hibernate), PostgreSQL, Jackson, Maven

## Run

```bash
mvn clean package
java -jar target/liter-alura-*.jar
```

## Config (application.properties)

```properties
spring.datasource.url=jdbc:postgresql://<HOST>:5432/<DB>?sslmode=require
spring.datasource.username=<USER>
spring.datasource.password=<PASSWORD>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Schema Notes

*   `books(titulo varchar(500), resumen text, autor varchar(200), lenguaje varchar(10), descargas int)`
*   `bock_languages` via `@ElementCollection`
*   `authors` + `bock_authors` (Many-to-Many)
*   If needed:
    ```sql
    ALTER TABLE books ALTER COLUMN resumen TYPE text;
    ALTER TABLE books ALTER COLUMN titulo TYPE varchar(500);
    ALTER TABLE books ADD COLUMN IF NOT EXISTS autor varchar(200);
    ALTER TABLE books ADD COLUMN IF NOT EXISTS lenguaje varchar(10);
    ALTER TABLE books ADD COLUMN IF NOT EXISTS descargas integer;
    ```

## Menu (Console)

    1) Search book by ID
    2) Save book by ID
    3) List saved books
    4) Search by Title (save first result)
    5) List by Language
    6) List Authors
    7) Authors alive in a given year
    8) Language stats (count)
    0) Exit

## Repositories (examples)

```java
// Books
List<Bock> findByLenguajeIgnoreCase(String lang);
long countByLenguajeIgnoreCase(String lang);

// Authors
@Query("select distinct a from Bock b join b.autores a order by a.nombre asc")
List<Author> findDistinctFromBooks();

@Query("""
       select distinct a from Bock b join b.autores a
       where (a.anoNacimiento is null or a.anoNacimiento <= :year)
         and (a.anoFallecimiento is null or a.anoFallecimiento >= :year)
       order by a.nombre asc
       """)
List<Author> findAliveInYear(@Param("year") int year);
```

## Notes

*   Use **primary language** (flat field) for derived queries and keep the **full language list** for display.
*   If printing collections in `toString`, prefer `EAGER` or use `JOIN FETCH` when listing to avoid `LazyInitializationException`.

***