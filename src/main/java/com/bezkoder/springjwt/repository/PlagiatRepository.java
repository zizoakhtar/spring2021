package com.bezkoder.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.bezkoder.springjwt.models.Plagiat;

@Component
@RepositoryRestResource
@CrossOrigin("*")
public interface PlagiatRepository extends JpaRepository<Plagiat, Long> {
}