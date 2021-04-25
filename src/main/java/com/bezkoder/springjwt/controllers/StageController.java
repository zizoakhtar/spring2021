package com.bezkoder.springjwt.controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bezkoder.springjwt.models.Choice;
import com.bezkoder.springjwt.models.FileInfo;
import com.bezkoder.springjwt.models.Stage;
import com.bezkoder.springjwt.models.Statistiqque;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.avis;
import com.bezkoder.springjwt.models.proposition;
import com.bezkoder.springjwt.repository.AvisRepository;
import com.bezkoder.springjwt.repository.FileRep;
import com.bezkoder.springjwt.repository.StageRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.services.FichePDFExporter;
import com.bezkoder.springjwt.security.services.UserPDFExporter;
import com.bezkoder.springjwt.up.ResponseMessage;
import com.lowagie.text.DocumentException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class StageController {
	@Autowired
	   private UserController userController ;

	@Autowired
	StageRepository sr;
	@Autowired
    private JavaMailSender javaMailSender;

	@Autowired
	UserRepository ur;
	@Autowired
	FileRep fr;
	@DeleteMapping(value = "/delete/{id}" )
    public void Delete(@PathVariable(name = "id") Long id ) {
        sr.deleteById(id);

    }
	@DeleteMapping(value = "/deletefiche/{id}" )
    public void Deleteddd(@PathVariable(name = "id") Long id ) {
        fr.deleteById(id);

    }

@GetMapping("/getFicheById/{id}")
public FileInfo get1(@PathVariable Long id) {
	return fr.findById(id).get();
}

	@GetMapping(value = "/listFiche" )
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('RAPPORTEUR') or hasRole('ENCADRANT')")
	public List<FileInfo> fiches(){
		return  fr.findAll();

	}

	@GetMapping(value = "/stageId/{id}" )
    public Stage Deletedd(@PathVariable(name = "id") Long id) {
      return  sr.trouveStageById(id);

    }    @GetMapping("/stage/export/pdf/{id}")
    public void exportToPDF(HttpServletResponse response,@PathVariable(name = "id") Long id) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
       // String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_.pdf";
        response.setHeader(headerKey, headerValue);
         
 Stage st=sr.findById(id).get();
         
        UserPDFExporter exporter = new UserPDFExporter(st);
        exporter.export(response);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(st.getStagiaire().getEmail());

        msg.setSubject("bonjour votre convention de  "+st.getTheme()+" est valable ");
        msg.setText("votre convention de  "+st.getTheme()+" est accepter et bien soumise avec forme pdf,"
        		+ " veuillez contacter la direction d'esprit pour le trouver");

        javaMailSender.send(msg);
         
    }
    @GetMapping("/fiche/export/pdf/{id}")
    public void exportToPDFfiche(HttpServletResponse response,@PathVariable(name = "id") Long id) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
       // String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_.pdf";
        response.setHeader(headerKey, headerValue);
         FileInfo st=fr.findById(id).get();
         
        FichePDFExporter exporter = new FichePDFExporter(st);
        
        exporter.export(response);

  
         
    }

@Bean
public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

    mailSender.setUsername("zizoakhtar@gmail.com");
    mailSender.setPassword("Babylife");

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
}

    @RequestMapping(value = "/update/{id}/{email}", method = RequestMethod.PUT)
    public Stage UpdateStage(@PathVariable(name = "id") Long id ,@PathVariable(name="email") String email) {
String msg="hello";

    	System.out.println("email"+email);
    	System.out.println("id"+id);
    	Stage p=sr.findById(id).get();
    	//System.out.println("stage"+p);
    	
    
    	User u =ur.findByEmail(email);


    	
    	p.setEnseig_stage(u);
		p.setStatus("en_cours");
		p.setId(id);             

    	

        SimpleMailMessage msg1 = new SimpleMailMessage();
        msg1.setTo(p.getStagiaire().getEmail());

        msg1.setText("bonjour l'encadrant "+u.getUsername()+" est affecté pour toi, veuillez le contacter depuis son email :"
        		+ "  "+email );
        msg1.setSubject("Affectation de rapporteur "+u.getUsername()+"");

        javaMailSender.send(msg1);
      
    	return sr.save(p);   }
    
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('RAPPORTEUR') or hasRole('ENCADRANT')")

    @RequestMapping(value = "/updatenc/{id}/{email}", method = RequestMethod.PUT)
    public Stage UpdateStagec(@PathVariable(name = "id") Long id ,@PathVariable(name="email") String email) {
String msg="hello";

    	System.out.println("email"+email);
    	System.out.println("id"+id);
    	Stage p=sr.findById(id).get();
    	//System.out.println("stage"+p);
    	
    
    	User u =ur.findByEmail(email);

p.setEnseig_option(u);
p.setStatus("en_cours");
		p.setId(id);             
      
    	

        SimpleMailMessage msg1 = new SimpleMailMessage();
        msg1.setTo(p.getStagiaire().getEmail());

        msg1.setText("bonjour le rapporteur "+u.getUsername()+" est affecté pour toi, veuillez le contacter depuis son email :"
        		+ "  "+email );
        msg1.setSubject("Affectation de rapporteur "+u.getUsername()+"");

        javaMailSender.send(msg1);

    	return sr.save(p);   
    
	
	
	
	
	}
	 @RequestMapping(value = "/updateF/{id}/{email}", method = RequestMethod.PUT)
	    public FileInfo UpdateStxxage(@PathVariable(name = "id") Long id ,@PathVariable(name="email") String email) {
	String msg="hello";

	    	System.out.println("email"+email);
	    	System.out.println("id"+id);
	    	FileInfo p=fr.findById(id).get();
	    	//System.out.println("stage"+p);
	    	
	    
	    	User u =ur.findByEmail(email);


	    	
	    	p.setEnseig_stage(u);
			p.setStatus("en_cours");
			p.setId(id);             

	    	

	        SimpleMailMessage msg1 = new SimpleMailMessage();
	        msg1.setTo(p.getStagiaire().getEmail());

	        msg1.setText("bonjour l'encadrant "+u.getUsername()+" est affecté pour toi, veuillez le contacter depuis son email :"
	        		+ "  "+email );
	        msg1.setSubject("Affectation de rapporteur "+u.getUsername()+"");

	        javaMailSender.send(msg1);
	      
	    	return fr.save(p);   }
	    

	 @GetMapping("/getStagiaire/{username}")    
	    public Object stags(@PathVariable(name = "username") String username) {
	    return	sr.trouveFicheByUsername(username);
	    
	    } 
	 @GetMapping("/getS/{id_user}")    
	    public String UpdssateStageNote(@PathVariable(name = "id_user") Long id_user ) {
	    	Stage s= sr.findById(id_user).get();
	    	return s.getStagiaire().getEmail();
	    
	    } 
    @GetMapping("/getFicheByNote/{id_user}")    
    public FileInfo UpdateStageNote(@PathVariable(name = "id_user") Long id_user ) {
    	return fr.findFicheByNote(id_user);
    
    }
    
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('RAPPORTEUR') or hasRole('ENCADRANT')")
    
    @RequestMapping(value ="/motif/{id}/{txt}", method = RequestMethod.PUT)
    public void auth(@PathVariable(name = "id") Long id,@PathVariable(name = "txt") String txt )
    {
    	FileInfo u=fr.findById(id).get();
    	
    	
SimpleMailMessage msg = new SimpleMailMessage();
msg.setTo(u.getStagiaire().getEmail());

msg.setSubject("bonjour votre fiche-pfe est rejectée !  veuillez contacter la direction d'esprit ");
msg.setText(txt);

javaMailSender.send(msg);
    }
	@Autowired
	AvisRepository ar;
	 @RequestMapping(value ="/traitrecl/{id_avis}/{txt}", method = RequestMethod.PUT)
	    public void traitrecl(@PathVariable(name = "id_avis") Long id,@PathVariable(name = "txt") String txt )
	    {
	    	avis u=ar.findById(id).get();
	    	
	    	
	SimpleMailMessage msg = new SimpleMailMessage();
	msg.setTo(u.getUseravis().getEmail());

	msg.setSubject("bonjour votre réclamation est en cours du traitement  ");
	msg.setText(txt);

	javaMailSender.send(msg);
	    }
	 
	 
    @RequestMapping(value = "/validation/{id}", method = RequestMethod.PUT)
    public FileInfo  validation(@PathVariable(name = "id") Long id )
    {
    	FileInfo stage=fr.findById(id).get();

System.out.println(stage.getStagiaire().getEmail());
if(	(stage.getNote_jury()>10)&&(stage.getNote_rap()>10)&&(stage.getNote_univ()>10))
    {
    stage.setStatus("validée");

    }
else
{
stage.setStatus("terminal");
}		
stage.setId(id);      

SimpleMailMessage msg = new SimpleMailMessage();
msg.setTo(stage.getStagiaire().getEmail());

msg.setSubject("bonjour votre stage de  "+stage.getTitre()+" est validéé veuillez contacter la direction d'esprit ");
msg.setText("votre proposition de  "+stage.getTitre()+" est accepter veuillez contacter la direction d'esprit ");

javaMailSender.send(msg);
return fr.save(stage);
    	
    }
    
    @RequestMapping(value = "/annule/{id}", method = RequestMethod.PUT)
    public Stage UpdateStagest(@PathVariable(name = "id") Long id )
    		
    {

    	 Stage p=sr.findById(id).get();
p.setStatus("annulé");

    	p.setId(id);      

SimpleMailMessage msg = new SimpleMailMessage();
msg.setTo(p.getStagiaire().getEmail());

msg.setSubject("bonjour votre convention est annulé avec succès");
msg.setText("bonjour votre convention est annulé avec succès");

javaMailSender.send(msg);

    	return sr.save(p);

    	}
    

    
    
    @RequestMapping(value = "/notEnc/{id}/{esprit}", method = RequestMethod.PUT)
    public FileInfo UpdateStaget(@PathVariable(name = "id") Long id ,@PathVariable(name="esprit")Float aa)
    		
    {

    	 FileInfo p=fr.findById(id).get();
    	  p.setNote_univ(aa);
     p.setStatus("terminal");
    	p.setId(id);      

    	return fr.save(p);
    	}
    

    
    
    
    
    
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public Statistiqque stat()
    {
    int n=sr.countstage();
    
    int encours=sr.findStageByStatus("en_cours");
    int enattente=sr.findStageByStatus("en_attente");
    int terminal=sr.findStageByStatus("terminal");
    int valider=sr.findStageByStatus("validé");
   encours=encours*100/n;
   terminal=terminal*100/n;
   valider=valider*100/n;
   enattente=enattente*100/n;
   Statistiqque e=new Statistiqque();
   e.setLabel1("en_cours");
   e.setNb1(encours);
   e.setLabel2("en_attente");
   e.setNb2(enattente);
   e.setLabel3("terminé");
   e.setNb3(terminal);
   e.setLabel4("validé");
   e.setNb4(valider);
   
   
   return e;
   
    }
    
    
    @RequestMapping(value = "/NotJury/{id}/{note_jury}", method = RequestMethod.PUT)
    public 	 FileInfo UpdateStagettt(@PathVariable(name = "id") Long id ,@PathVariable(name="note_jury")Float bb)
    		
    {

   	 FileInfo p=fr.findById(id).get();
    	  p.setNote_jury(bb);
    	  p.setStatus("terminal");
    	p.setId(id);      

    	return fr.save(p);

    	} 

@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
@PostMapping("/ajoutFiche/{user_id}")
public  ResponseEntity<ResponseMessage> addxb(@PathVariable(value = "user_id") Long id_user,
		String titre,String description, String problm,
		String fonct,String f1,String f2,String f3,String techno,String t1,String t2,String t3	) {
	String message = "";
	try {
	    FileInfo a = new FileInfo(userController.user(id_user),titre, description, problm,
		 fonct,f1,f2,f3,techno,t1,t2,t3,LocalDate.now());
	    a.setStatus("en_cours");
	  //  userController.user(id_user).setStage(a);
	    fr.save(a);

    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(a.getStagiaire().getEmail());

    msg.setSubject("bonjour votre Fiche-pfe de  "+a.getTitre()+" est en cours du traitement ");
    msg.setText("votre Fiche-pfe de  "+a.getTitre()+" est accepter et bien soumise avec succés, veuillez contacter la direction d'esprit ");

    javaMailSender.send(msg);

message = "Telechargement de votre Fiche-pfe à réussit: " ;
return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
}
catch (Exception e) {
message = "Veuillez ressayer,telechargement non réussi !";

return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
}
	
	
	
	
	
    @RequestMapping(value = "/NotRapp/{id}/{note_rapp}", method = RequestMethod.PUT)
    public 	 FileInfo UpdateStagett(@PathVariable(name = "id") Long id ,@PathVariable(name="note_rapp")Float bb)
    		
    {

   	 FileInfo p=fr.findById(id).get();
    	  p.setNote_rap(bb);
    	  p.setStatus("terminal");
    	p.setId(id);      

    	return fr.save(p);

    	}

    
@GetMapping("/getStageById/{id}")
public Stage get(@PathVariable Long id) {
	return sr.findById(id).get();
}

	@GetMapping(value = "/listStage" )
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('RAPPORTEUR') or hasRole('ENCADRANT')")
	public List<Stage> propoList(){
		return  sr.findAll();

	}


	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping("/ajoutConv/{user_id}")
	public  ResponseEntity<ResponseMessage> addb(@PathVariable(value = "user_id") Long id_user,
			String theme,String dateDeb, String datefin,
			String etablissement, String adresse, 
			String enc_entreprise, String mail_enc_entreprise,String telephone
			) {
		  String message = "";
		   try {
			   Stage a = new Stage(userController.user(id_user),theme, dateDeb, datefin,
		   
			 etablissement, adresse, enc_entreprise,  mail_enc_entreprise,telephone,LocalDate.now());
		    userController.user(id_user).setTelephone(telephone);
		    a.setStatus("en_attente");
		    userController.user(id_user).setStage(a);
		    sr.save(a);
		       
SimpleMailMessage msg = new SimpleMailMessage();
msg.setTo(a.getStagiaire().getEmail());

msg.setSubject("bonjour votre convention de  "+a.getTheme()+" est en cours du traitement ");
msg.setText("votre convention de  "+a.getTheme()+" est accepter et bien soumise avec succés, veuillez contacter la direction d'esprit ");

javaMailSender.send(msg);

message = "Telechargement de votre convention à réussit: " ;
return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
}
catch (Exception e) {
message = "Veuillez ressayer,telechargement non réussi !";

return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }

		
		    }
}