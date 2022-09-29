package com.jeanlima.mvcapp.service;

import com.jeanlima.mvcapp.model.Avaliacao;
import com.jeanlima.mvcapp.model.Chef;
import com.jeanlima.mvcapp.repository.AbstractRepository;
import com.jeanlima.mvcapp.repository.AvaliacaoRepository;
import com.jeanlima.mvcapp.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChefService extends AbstractService<Chef, Integer> {
    @Autowired
    private ChefRepository repository;


    @Override
    protected AbstractRepository<Chef, Integer> repository() {
        return this.repository;
    }

}
