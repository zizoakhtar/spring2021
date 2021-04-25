package com.bezkoder.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.FileInfo;
import com.bezkoder.springjwt.models.Stage;

@Repository
public interface FileRep extends JpaRepository<FileInfo, Long>{
	 @Query("SELECT u FROM FileInfo u where u.stagiaire.id = :id")
		public FileInfo findFicheByNote(@Param("id") Long id);

}
