package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CategorieRep;
import com.example.demo.dao.CinemaRep;
import com.example.demo.dao.FilmRep;
import com.example.demo.dao.PlaceRep;
import com.example.demo.dao.ProjectionRep;
import com.example.demo.dao.SalleRep;
import com.example.demo.dao.SeanceRep;
import com.example.demo.dao.TicketRep;
import com.example.demo.dao.VilleRep;
import com.example.demo.model.Categorie;
import com.example.demo.model.Cinema;
import com.example.demo.model.Film;
import com.example.demo.model.Place;
import com.example.demo.model.Projection;
import com.example.demo.model.Salle;
import com.example.demo.model.Seance;
import com.example.demo.model.Ticket;
import com.example.demo.model.Ville;

@Service
@Transactional
public class CinemaInitService implements ICinemaService {

	@Autowired
	private VilleRep villeR;

	@Autowired
	private CinemaRep cinemaR;

	@Autowired
	private SalleRep salleR;

	@Autowired
	private PlaceRep placeR;

	@Autowired
	private CategorieRep categorieR;

	@Autowired
	private FilmRep filmR;

	@Autowired
	private SeanceRep seanceR;

	@Autowired
	private TicketRep ticketR;

	@Autowired
	private ProjectionRep projectionR;

	@Override
	public void initVilles() {
		Stream.of("Casablanca", "Marakech", "Rabat", "Tanget").forEach(v -> {
			Ville ville = new Ville();
			ville.setName(v);
			villeR.save(ville);
		});
	}

	@Override
	public void initCinemas() {
		villeR.findAll().forEach(v -> {
			Stream.of("Megarama", "Linxe", "IMAX", "Rielto", "Rif").forEach(namecin -> {
				Cinema cinema = new Cinema();
				cinema.setName(namecin);
				cinema.setNbrsalle(3 + (int) (Math.random() * 7));
				cinema.setVille(v);
				cinemaR.save(cinema);
			});
		});
	}

	@Override
	public void initSalles() {
		cinemaR.findAll().forEach(cinema -> {
			for (int i = 0; i < cinema.getNbrsalle(); i++) {
				Salle salle = new Salle();
				salle.setName("Salle" + (i + 1));
				salle.setCinema(cinema);
				salle.setNbrplace(20 + (int) (Math.random() * 10));
				salleR.save(salle);
			}
		});
	}

	@Override
	public void initPlaces() {
		salleR.findAll().forEach(salle -> {
			for (int i = 0; i < salle.getNbrplace(); i++) {
				Place place = new Place();
				place.setNumero(i + 1);
				place.setSalle(salle);
				placeR.save(place);
			}
		});
	}

	@Override
	public void initSeances() {
		Stream.of("14:30", "16:00", "17:45", "18:00").forEach(s -> {
			Seance seance = new Seance();
			try {
				seance.setHeurdebut(new SimpleDateFormat("HH:MM").parse(s));
				seanceR.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void initCategorie() {
		Stream.of("Drama", "Action", "Seance Fiction", "Histoire", "Entier").forEach(cat -> {
			Categorie categorie = new Categorie();
			categorie.setName(cat);
			categorieR.save(categorie);
		});
	}

	@Override
	public void initFilms() {
		double[] duree = { 1.45, 2.30, 2.43, 1, 3.20 };
		List<Categorie> cat = categorieR.findAll();
		Stream.of("Breaking Bad", "Matrix", "hannibal", "Chernobil", "Better Call Soul").forEach(f -> {
			Film film = new Film();
			film.setTitre(f);
			film.setDuree(duree[new Random().nextInt(duree.length)]);
			film.setPhoto(f.replace(" ", "") + ".jpeg");
			film.setCategorie(cat.get(new Random().nextInt(cat.size())));
			filmR.save(film);
		});
	}

	@Override
	public void initProjections() {
		List<Film> films = filmR.findAll();
		double[] prix = { 30, 40, 50, 55, 60 };
		villeR.findAll().forEach(ville -> {
			ville.getCinemas().forEach(cinema -> {
				cinema.getSalles().forEach(salle -> {
					int index = new Random().nextInt(films.size());
					Film film = films.get(index);
					seanceR.findAll().forEach(seance -> {
						Projection projection = new Projection();
						projection.setDateprojection(new Date());
						projection.setFilm(film);
						projection.setPrix(prix[new Random().nextInt(prix.length)]);
						projection.setSalle(salle);
						projection.setSeance(seance);
						projectionR.save(projection);
					});

				});

			});
		});
	}

	@Override
	public void initTicket() {
		projectionR.findAll().forEach(projection -> {
			projection.getSalle().getPlace().forEach(place -> {
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(projection.getPrix());
				ticket.setProjection(projection);
				ticket.setReservee(false);

				ticketR.save(ticket);
			});
		});
	}

}
