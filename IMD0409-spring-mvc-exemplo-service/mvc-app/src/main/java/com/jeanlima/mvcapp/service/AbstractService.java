package com.jeanlima.mvcapp.service;

import com.jeanlima.mvcapp.model.AbstractModel;
import com.jeanlima.mvcapp.repository.AbstractRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Service
public abstract class AbstractService<T extends AbstractModel<PK>, PK extends Serializable> {
    protected abstract AbstractRepository<T, PK> repository();

    public List<T> findAll() {
        return repository().findAll();
    }



    public T getById(PK id) {
        Optional<T> entity = repository().findById(id);
        if (!entity.isPresent()) {
            return null;
        }
        return entity.get();
    }

    public ResponseEntity<T> save(T entity) {
        boolean newItem = false;
        if (entity.getId() == null) {
            newItem = true;
        } else {
        }
        entity = repository().save(entity);
        return ResponseEntity.ok(entity);
    }

    public ResponseEntity<T> delete(PK id) {
        Optional<T> entity = repository().findById(id);
        if (entity.isPresent()) {
            repository().deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}