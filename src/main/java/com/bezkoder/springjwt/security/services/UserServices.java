package com.bezkoder.springjwt.security.services;

import java.util.List;
 
import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bezkoder.springjwt.models.Stage;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.StageRepository;
import com.bezkoder.springjwt.repository.UserRepository;
 
@Service
@Transactional
public class UserServices {
     
    @Autowired
    private StageRepository repo;

}