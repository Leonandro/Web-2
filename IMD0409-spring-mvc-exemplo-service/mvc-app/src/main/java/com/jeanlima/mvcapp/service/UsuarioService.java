package com.jeanlima.mvcapp.service;

import com.jeanlima.mvcapp.model.Usuario;
import com.jeanlima.mvcapp.repository.AbstractRepository;
import com.jeanlima.mvcapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService extends AbstractService<Usuario, Integer> {
    @Autowired
    private UsuarioRepository repository;


    @Override
    protected AbstractRepository<Usuario, Integer> repository() {
        return this.repository;
    }

}