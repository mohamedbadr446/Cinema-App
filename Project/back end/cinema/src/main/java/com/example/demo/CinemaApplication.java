package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.example.demo.model.Film;
import com.example.demo.model.Salle;
import com.example.demo.model.Ticket;
import com.example.demo.service.ICinemaService;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner{

	@Autowired
	private ICinemaService cinemainitservice;
	
	@Autowired
	private RepositoryRestConfiguration rescon;
	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		rescon.exposeIdsFor(Film.class,Salle.class);
		cinemainitservice.initVilles();
		cinemainitservice.initCinemas();
		cinemainitservice.initSalles();
		cinemainitservice.initPlaces();
		cinemainitservice.initSeances();
		cinemainitservice.initCategorie();
		cinemainitservice.initFilms();
		cinemainitservice.initProjections();
		cinemainitservice.initTicket();
	}

}
