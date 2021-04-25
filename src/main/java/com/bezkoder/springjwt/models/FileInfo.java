package com.bezkoder.springjwt.models;
import java.sql.Date;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//@Data
@Getter

@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class FileInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "stagiaire", nullable = true)
	private User stagiaire ;

	@ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)

	@JoinColumn(name = "E_stage_id", nullable = true)
	private User enseig_stage;


	public Float note_jury ;
	public Float note_rap ;
	public Float note_univ ;

		
public String status;
	public String titre;
	public String description;
	
	private String problm;
	public String fonctionnality;
	public String f1;
	public String f2;
	public String f3;
	public String techno;
	public String t1;
	public String t2;
	public String t3;

private LocalDate put;
	public FileInfo(User stagiaire,  String titre, String description, String problm,
			String fonctionnality, 	String f1,String f2,String f3,String techno,String t1,String t2,String t3,LocalDate a) {
		super();
		this.stagiaire = stagiaire;
		this.titre = titre;
		this.description = description;
		this.problm = problm;
		this.fonctionnality = fonctionnality;
		this.f1=f1;
		this.f2=f2;
		this.f3=f3;
		this.techno = techno;
		this.t1=t1;
		this.t2=t2;
		this.t3=t3;
		this.put=a;
	}
	





}
