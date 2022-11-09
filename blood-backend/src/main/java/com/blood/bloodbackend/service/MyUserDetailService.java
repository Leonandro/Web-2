package com.blood.bloodbackend.service;

import com.blood.bloodbackend.model.PapelUsuario;
import com.blood.bloodbackend.model.Usuario;
import com.blood.bloodbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario user = repository.findByUsername(userName);
        if (user == null){
            throw new UsernameNotFoundException(userName);
        }

        return new User(user.getUsername(), user.getPassword(), user.getPapeis());
        //return  new User("foo", "foo", new ArrayList<>());
    }

}
