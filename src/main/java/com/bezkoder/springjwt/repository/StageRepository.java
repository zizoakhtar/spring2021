package com.bezkoder.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.FileInfo;
import com.bezkoder.springjwt.models.Stage;
import com.bezkoder.springjwt.models.proposition;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long>{

    @Query("SELECT u FROM Stage u where u.stagiaire.id = :id")
	public Stage findStageByNote(@Param("id") Long id);

    @Query("SELECT count (u) FROM Stage u ")
    public int countstage();
    
	 
    public Stage findByStagiaire(Long id_user);

    
    @Query("SELECT count(u) FROM Stage u where u.status =:ok")
   	public int findStageByStatus(String ok);
    
    @Query("SELECT s FROM FileInfo s  where s.stagiaire.username LIKE %:username%") 
    public FileInfo trouveFicheByUsername(String username);
    @Query("SELECT s FROM Stage s  where s.stagiaire.id =:id")
    public Stage trouveStageById(Long id);
}
