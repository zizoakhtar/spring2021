package com.bezkoder.springjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import com.bezkoder.springjwt.security.services.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PropositionController {
    @Autowired
    private PropositionRepository propositionRepositiory ;
    @Autowired
    RoleRepository roleRepository;
@Autowired
private UserController userController;
    @Autowired
    UserRepository userRepository;

    
    
    
    
    
        @GetMapping(value = "/listproposition" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<proposition> propoList(){
        return  propositionRepositiory.findAll();

    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping(value = "/listpropo/{id}" )
    public proposition proposition(@PathVariable(name = "id") Long id ) {
        return propositionRepositiory.findById(id).get();
    }


    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @RequestMapping(value = "/ajoutpropo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public proposition addpropo(@RequestBody proposition u) {
        System.out.println("(Service Side) Creating equipe : ");
        proposition equipe = propositionRepositiory.save(u);
        return equipe;
    }
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping(value = "/deletepropo/{id}" )
    public void Delete(@PathVariable(name = "id") Long id ) {
        propositionRepositiory.deleteById(id);

    }



    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")

    @RequestMapping(value = "/statPropo", method = RequestMethod.GET)
    public StatPropo stat()
    {
    int n=propositionRepositiory.countpropositions();
    
    int annee2019=propositionRepositiory.countpropositionvalide(2019);
    int annee2020=propositionRepositiory.countpropositionvalide(2020);
    int annee2021=propositionRepositiory.countpropositionvalide(2021);
    int annee2022=propositionRepositiory.countpropositionvalide(2022);

    
    annee2019=annee2019*100/n;
    annee2020=annee2020*100/n;
    annee2021=annee2021*100/n;
    annee2022=annee2022*100/n;
   StatPropo e=new StatPropo();
   e.setLab1("Année 2019");
   e.setN1(annee2019);
  
   e.setLab2("Année 2020");
   e.setN2(annee2020);
   
   e.setLab3("Année 2021");
   e.setN3(annee2021);
   
   e.setLab4("Année 2022");
   e.setN4(annee2022);
   
   
   return e;
   
    }
    
    
}