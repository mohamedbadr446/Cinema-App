package com.example.demo.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Ville {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_ville;
	private String name;
	private double longitude,latitude,atitude;
	@OneToMany(mappedBy = "ville")
	private Collection<Cinema> cinemas;
	
}
