
package com.bezkoder.springjwt.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="stage")
	@OneToOne(cascade={CascadeType.REMOVE})
   @JsonBackReference
	private Stage stage;
    
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

    private String qui;
    
    public String getQui() {
		return qui;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setQui(String qui) {
		this.qui = qui;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="useravis",cascade={CascadeType.REMOVE})
    @JsonBackReference
    private List<avis> avisList=new ArrayList<>() ;
   
    //@LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(cascade={CascadeType.REMOVE})
    //@JsonBackReference
    private proposition propo  ;
    
  //  @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(cascade={CascadeType.REMOVE})
    //@JsonBackReference
    private Plagiat plagiat ;
    @LazyCollection(LazyCollectionOption.FALSE)
    
	@OneToOne(cascade={CascadeType.REMOVE})
   @JsonBackReference
	private FileInfo fiche;
    
    private String valider;
private String telephone;
	public String isValider() {
        return valider;
    }

    public void setValider(String valider) {
        this.valider = valider;
    }


    public proposition getPropo() {
		return propo;
	}

	public void setPropo(proposition propo) {
		this.propo = propo;
	}



	public List<avis> getAvisList() {
		return avisList;
	}

	public void setAvisList(List<avis> avisList) {
		this.avisList = avisList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", stage=" + stage + ", email=" + email + ", password="
				+ password + ", roles=" + roles + ", qui=" + qui + ", avisList=" + avisList + ", propo=" + propo
				+ ", valider=" + valider + ", telephone=" + telephone + "]";
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	}
