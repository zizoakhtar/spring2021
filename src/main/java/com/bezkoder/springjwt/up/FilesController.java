package com.bezkoder.springjwt.up;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.bezkoder.springjwt.controllers.UserController;
import com.bezkoder.springjwt.models.Plagiat;
import com.bezkoder.springjwt.models.proposition;
import com.bezkoder.springjwt.repository.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FilesController {
	  @Autowired
	  PlagiatRepository pr;
  @Autowired
  FilesStorageService storageService;
@Autowired
PropositionRepository sr;
@Autowired
UserController userController;

@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping("/upload/{id}")
  public ResponseEntity<ResponseMessage> uploadFile(@PathVariable(value = "id") Long id_user,
		  @RequestParam("file") MultipartFile file) {
	System.out.println("id"+id_user);

	System.out.println("file"+file);

	
	String message = "";
     try {
     
      storageService.store(file);
/*
  //    FileInfo a=new FileInfo(file.getName(), file.getContentType(),userController.user(id_user));
//fr.save(a);
     */proposition e = new proposition(file.getName(),userController.user(id_user),
      	file.getContentType(),file.getOriginalFilename(),true,LocalDate.now().getYear());
     userController.user(id_user).setPropo(e);  
     userController.user(id_user).getStage().setStatus("terminal");;
sr.save(e);
     
	message = "Telechargement réussi: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
   }
catch (Exception e) {
      message = "Veuillez ressayer,telechargement non réussi: " + file.getOriginalFilename() + "!";
  
return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }


  @PostMapping("/uploadplagiat/{id}")
  public ResponseEntity<ResponseMessage> uploadFilse(@PathVariable(value = "id") Long id_user,
		  @RequestParam("file") MultipartFile file) {
	System.out.println("id"+id_user);

	System.out.println("file"+file);

	
	String message = "";
     try {
     
      storageService.store(file);
/*
  //    FileInfo a=new FileInfo(file.getName(), file.getContentType(),userController.user(id_user));
//fr.save(a);
     */Plagiat e = new Plagiat(file.getName(),userController.user(id_user),
      	file.getContentType(),file.getOriginalFilename(),true,LocalDate.now().getYear());
     userController.user(id_user).setPlagiat(e);  
     userController.user(id_user).getStage().setStatus("terminal");;
pr.save(e);
     
	message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
   }
catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
  
return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }







  List<String> files = new ArrayList<String>();
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @GetMapping("/filesc")

  public List<String> getListFiles( ) {
  List<String> fileNames = files
  .stream().map(fileName -> MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", fileName).build().toString())
  .collect(Collectors.toList());
return files;
}
  
  
  @GetMapping("/files")
  public ResponseEntity<List<proposition>> dd() {
    List<proposition> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

     return new proposition(filename, url);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }
  @GetMapping("/files/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
  @GetMapping("/plagiat/{filename:.+}")
  public ResponseEntity<Resource> getFilep(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
}
