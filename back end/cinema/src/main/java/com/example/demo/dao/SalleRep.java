package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.demo.model.Cinema;
import com.example.demo.model.Film;
import com.example.demo.model.Projection;
import com.example.demo.model.Salle;

@RepositoryRestResource
@CrossOrigin("*")
public interface SalleRep extends JpaRepository< Salle,Long>{

}