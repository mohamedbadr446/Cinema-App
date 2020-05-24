package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketproj",types = Ticket.class )
public interface TicketProje {

	public Long getId_ticket();
	public String getNomclient();
	public double getPrix();
	public Integer getCodepayement();
	public boolean getReservee();
	public Place getPlace();
	
}
