package com.bezkoder.springjwt.controllers;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.avis;
import com.bezkoder.springjwt.models.proposition;
import com.bezkoder.springjwt.repository.AvisRepository;
import com.bezkoder.springjwt.repository.PropositionRepository;
import com.bezkoder.springjwt.security.services.UserPDFExporter;
import com.bezkoder.springjwt.security.services.UserServices;
import com.bezkoder.springjwt.up.FilesStorageService;
import com.bezkoder.springjwt.up.ResponseMessage;
import com.lowagie.text.DocumentException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AvisController {

    @Autowired
    private AvisRepository avisRepository ;
    @Autowired
    FilesStorageService storageService;
  @Autowired
  UserController userController;

  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/rec/{id}")
    public ResponseEntity<ResponseMessage> uploadFile(String obj,String desc,@PathVariable(value = "id") Long id_user,
  		  @RequestParam("file") MultipartFile file) {
  	System.out.println("id"+id_user);

  	System.out.println("file"+file);

  	
  	String message = "";
       try {
       
        storageService.store(file);
  
        avis e = new avis(desc,file.getOriginalFilename(),
        		file.getName(),
        		userController.user(id_user),
        	file.getContentType(),LocalDate.now(),obj);
       List<avis> x=new ArrayList<>();
       x.add(e);
        userController.user(id_user).setAvisList(x);  
  avisRepository.save(e);
       
  	message = "Réclamation telechargé avec succées avec la capture: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
     }
  catch (Exception e) {
        message = "Désolée: " + file.getOriginalFilename() + "!";
    
  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }
  
  
  
    
    
    
    
    
    
    
    
    @PostMapping("/addavis/{user_id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> ajouteravis(@PathVariable(value = "user_id") Long id_user , String description ) {
        String message = "";
    avis a = new avis(description,userController.user(id_user));
    avisRepository.save(a);
         message = "You successfully add ";

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @GetMapping(value = "/listavis" )
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<avis> propoList(){
        return  avisRepository.findAll();

    }

	@DeleteMapping(value = "/deleteavis/{id}" )
    public void Delete(@PathVariable(name = "id") Long id ) {
        avisRepository.deleteById(id);

    }
    /* ////////////////////////////find////////////////////// */
    @GetMapping(value = "/getavis/{id}" )
    public avis offre(@PathVariable(name = "id") Long id ) {
        return avisRepository.findById(id).get();
    }
}
