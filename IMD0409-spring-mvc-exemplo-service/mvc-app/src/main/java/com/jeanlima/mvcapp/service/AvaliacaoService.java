package com.jeanlima.mvcapp.service;

import com.jeanlima.mvcapp.model.Avaliacao;
import com.jeanlima.mvcapp.model.Usuario;
import com.jeanlima.mvcapp.repository.AbstractRepository;
import com.jeanlima.mvcapp.repository.AvaliacaoRepository;
import com.jeanlima.mvcapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AvaliacaoService extends AbstractService<Avaliacao, Integer> {
    @Autowired
    private AvaliacaoRepository repository;


    @Override
    protected AbstractRepository<Avaliacao, Integer> repository() {
        return this.repository;
    }

}
