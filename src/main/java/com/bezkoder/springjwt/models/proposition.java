package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "propositions")
public class proposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proposition;
    private  String propositionscanner ;
    @JsonIgnore
@ManyToOne
@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="User_id")
    private  User user ;
    @Column(name = "type")
    private String type;
    private int date;
    private boolean validation;
public proposition() {}

public proposition(String propositionscanner, String name) {
		super();
		this.propositionscanner = propositionscanner;
		this.name = name;
	}


	public int getDate() {
	return date;
}

public void setDate(int date) {
	this.date = date;
}

public boolean isValidation() {
	return validation;
}

public void setValidation(boolean validation) {
	this.validation = validation;
}

public proposition(String propositionscanner, User user, String type, String name,boolean val,int d) {
    this.propositionscanner = propositionscanner;
    this.user = user;
    this.type = type;
    this.name = name;
    this.validation=val;
    this.date=d;
}
public proposition(String propositionscanner, User user, String type, String name) {
    this.propositionscanner = propositionscanner;
    this.user = user;
    this.type = type;
    this.name = name;
}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="name")
    private String name ;
        public Long getId_proposition() {
        return id_proposition;
    }

    public void setId_proposition(Long id_proposition) {
        this.id_proposition = id_proposition;
    }

    public String getPropositionscanner() {
        return propositionscanner;
    }

    public void setPropositionscanner(String propositionscanner) {
        this.propositionscanner = propositionscanner;
    }

 

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}