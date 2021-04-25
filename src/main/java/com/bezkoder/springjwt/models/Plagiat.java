package com.bezkoder.springjwt.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Plagiat {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private  String plagiatscanner ;
	    @LazyCollection(LazyCollectionOption.FALSE)
	    @OneToOne(cascade={CascadeType.REMOVE})
	    @JsonBackReference
	    @JoinColumn(name ="User_id")
	    private  User user ;
	    @Column(name = "type")
	    private String type;
	    private int date;
	    private boolean validation;
	    
	public Plagiat(String propositionscanner, String name) {
			super();
			this.plagiatscanner = propositionscanner;
			this.name = name;
		}

public Plagiat() {}
		public int getDate() {
		return date;
	}

	public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getPlagiatscanner() {
			return plagiatscanner;
		}


		public void setPlagiatscanner(String plagiatscanner) {
			this.plagiatscanner = plagiatscanner;
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

	public Plagiat(String propositionscanner, User user, String type, String name,boolean val,int d) {
	    this.plagiatscanner = propositionscanner;
	    this.user = user;
	    this.type = type;
	    this.name = name;
	    this.validation=val;
	    this.date=d;
	}
	public Plagiat(String propositionscanner, User user, String type, String name) {
	    this.plagiatscanner = propositionscanner;
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
		    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	}
