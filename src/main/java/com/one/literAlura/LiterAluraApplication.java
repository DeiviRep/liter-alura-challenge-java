package com.one.literAlura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.one.literAlura.principal.Principal;
import com.one.literAlura.repository.AuthorRepository;
import com.one.literAlura.repository.BockRepository;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner{

	@Autowired
	private BockRepository repository;
	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, authorRepository);
		principal.muestraElMenu();
	}

}
