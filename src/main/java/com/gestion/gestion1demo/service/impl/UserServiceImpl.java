package com.gestion.gestion1demo.service.impl;


import com.gestion.gestion1demo.model.utilisateur;
import com.gestion.gestion1demo.repository.Iutilisateur;
import com.gestion.gestion1demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Iutilisateur userRepository;

    @Override
    public utilisateur findByUsername(String username ) throws UsernameNotFoundException {
        utilisateur u = userRepository.findByUsername( username );
        return u;
    }

    public utilisateur findById( Long id ) throws AccessDeniedException {
        utilisateur u = userRepository.getOne( id );
        return u;
    }

    public List<utilisateur> findAll() throws AccessDeniedException {
        List<utilisateur> result = userRepository.findAll();
        return result;
    }
}
