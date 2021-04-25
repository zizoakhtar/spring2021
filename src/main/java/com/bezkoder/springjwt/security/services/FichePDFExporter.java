package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.*;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;

import com.bezkoder.springjwt.models.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
 
 
public class FichePDFExporter {
    private static final String HelloWorld = null;
	private FileInfo s;

     
    public FichePDFExporter(FileInfo x) {
        this.s = x;
    }
 
   
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
    	Document document = new Document();
    	File root = new File("C:/Users/dell/Desktop/pfe/spring-boot-spring-security-jwt-authentication-master/uploads/");
		
    	  try
          {
    	 PdfWriter writer=  PdfWriter.getInstance(document, response.getOutputStream());
    	                   document.open();
    	                   String f="a.jpg";
				Paragraph a=new Paragraph(new Chunk(" Fiche-pfe du stage \n",    
						FontFactory.getFont(FontFactory.HELVETICA, 40)));

				a.setAlignment(Element.ALIGN_CENTER);
				document.add(a);
    	                   Image image = Image.getInstance(new File(root,f).getAbsolutePath());
    	                
						image.setAlignment(Element.ALIGN_LEFT);


					//	image.setWidthPercentage(-13);

						document.add(image);

						Paragraph by = new Paragraph(new Chunk("\n \n Etudiant : " + s.getStagiaire().getUsername()+ 
               		   "\n  Numéro : " + s.getStagiaire().getTelephone()+"\n  Email : " + s.getStagiaire().getEmail()+
               		"\n  Mise le   : " + s.getPut()
               		+     		   "\n  Titre de projet : " + s.getTitre()+"\n  Description projet : " + s.getDescription()+
               		   "\n  Problèmatique : " + s.getProblm()+"\n  Fonctionnalités : " + s.getFonctionnality()+
               		   "\n  Technologies : " + s.getTechno(),
            		 FontFactory.getFont(FontFactory.COURIER_OBLIQUE, 12)));
document.add(by);
    	 
    	    document.close();
    	    writer.close();
    	} catch (Exception e)
    	{
    	    e.printStackTrace();
    	}
}}