package com.example.demo.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "p1",types = {com.example.demo.model.Projection.class})
public interface ProjectionProj {

	public long getId_projection();
	public double getPrix();
	public Date getdateprojection();
	public Salle getSalle();
	public Film getFilm();
	public Seance getSeance();
	public Collection<Ticket> getTickets();
}
