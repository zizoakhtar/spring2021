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
 
 
public class UserPDFExporter {
    private static final String HelloWorld = null;
	private Stage s;

     
    public UserPDFExporter(Stage listUsers) {
        this.s = listUsers;
    }
 
   
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
    	//Font blueFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new CMYKColor(255, 0, 0, 0));
    	//Font redFont = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, new CMYKColor(0, 255, 0, 0));
    	//Font yellowFont = FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 0, 255, 0));
    	Document document = new Document();
    	File root = new File("C:/Users/dell/Desktop/pfe/spring-boot-spring-security-jwt-authentication-master/uploads/");
		
    	  try
          {
    	 PdfWriter writer=  PdfWriter.getInstance(document, response.getOutputStream());
    	                   document.open();
    	                   String f="a.jpg";
				Paragraph a=new Paragraph(new Chunk(" Convention du stage \n",    
						FontFactory.getFont(FontFactory.HELVETICA, 40)));

				a.setAlignment(Element.ALIGN_CENTER);
				document.add(a);
    	                   Image image = Image.getInstance(new File(root,f).getAbsolutePath());
    	                
						image.setAlignment(Element.ALIGN_LEFT);


					//	image.setWidthPercentage(-13);

						document.add(image);

						Paragraph by = new Paragraph(new Chunk("\n \n Etudiant : " + s.getStagiaire().getUsername()+ 
               		   "\n  Numéro : " + s.getStagiaire().getTelephone()+"\n  Theme du stage : " + s.getTheme()+
               		   "\n  Date_debut du stage : " + s.getDateDeb()+"\n  Date_fin du stage : " + s.getDatefin()+
               		   "\n  Etablissement : " + s.getEtablissement()+"\n  Adresse : " + s.getAdresse()+
               		   "\n  Encadrant_entreprise : " + s.getEnc_entreprise()+"\n  Mail_encadrant_entreprise  : " + s.getMail_enc_entreprise(),
            		 FontFactory.getFont(FontFactory.COURIER_OBLIQUE, 12)));
document.add(by);
    	 
    	    document.close();
    	    writer.close();
    	} catch (Exception e)
    	{
    	    e.printStackTrace();
    	}
}}