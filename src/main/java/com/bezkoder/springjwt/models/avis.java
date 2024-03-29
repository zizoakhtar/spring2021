
package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@Entity
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "avis")
public class avis implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_avis;

    private String Description ;
    private  String aviscanner ;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name ="User_id")
    private User useravis ;
    private String type;
    private LocalDate date;
    private String objet;
    
    public Long getId_avis() {
        return id_avis;
    }

    public void setId_avis(Long id_avis) {
        this.id_avis = id_avis;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public User getUseravis() {
        return useravis;
    }

    public void setUseravis(User useravis) {
        this.useravis = useravis;
    }

    public avis(String description, User useravis) {
        Description = description;
        this.useravis = useravis;
    }

    public avis() {
    }
private String name;
	public avis(String description,String name, String aviscanner, User useravis, String type, LocalDate date, String objet) {
		super();
		Description = description;
		this.name=name;
		this.aviscanner = aviscanner;
		this.useravis = useravis;
		this.type = type;
		this.date = date;
		this.objet = objet;
	}

	}
