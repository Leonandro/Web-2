package com.jeanlima.mvcapp.service;

import com.jeanlima.mvcapp.model.Receita;
import com.jeanlima.mvcapp.model.Usuario;
import com.jeanlima.mvcapp.repository.AbstractRepository;
import com.jeanlima.mvcapp.repository.ReceitaRepository;
import com.jeanlima.mvcapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ReceitaService extends AbstractService<Receita, Integer> {
    @Autowired
    private ReceitaRepository repository;


    @Override
    protected AbstractRepository<Receita, Integer> repository() {
        return this.repository;
    }

}