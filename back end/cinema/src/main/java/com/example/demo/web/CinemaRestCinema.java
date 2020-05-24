package com.example.demo.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.FilmRep;
import com.example.demo.dao.TicketRep;
import com.example.demo.model.Film;
import com.example.demo.model.Ticket;

import lombok.Data;

@RestController
@CrossOrigin("*")
public class CinemaRestCinema {

	@Autowired
	private FilmRep filmr;

	@Autowired
	private TicketRep ticketr;

	@GetMapping(path = "/ImageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable(name = "id") Long id) throws IOException {
		Film film = filmr.findById(id).get();
		String photo = film.getPhoto();
		File file = new File(System.getProperty("user.home") + "/cinema/images/" + photo);
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path);

	}

	@PostMapping("/PayerTicket")
	@Transactional
	public List<Ticket> payerTicket(@RequestBody TicketForm ticketForm) {
		List<Ticket> listTickets=new ArrayList<>();
		ticketForm.getTickets().forEach(idticket -> {
			Ticket ticket=ticketr.findById(idticket).get();
			ticket.setNomclient(ticketForm.getNomClient());
			ticket.setReservee(true);
			ticketr.save(ticket);
			listTickets.add(ticket);
		});

		return listTickets;

	}

}

@Data
class TicketForm {

	private String nomClient;
	private int codePayement;
	private List<Long> tickets = new ArrayList<>();
}
