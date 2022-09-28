package com.jeanlima.mvcapp.repository;

import com.jeanlima.mvcapp.model.AbstractModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractRepository<T extends AbstractModel<PK>, PK extends Serializable> extends JpaRepository<T, PK> {

    @Override
    @Query(value = "select * from #{#entityName} where ativo = true", nativeQuery = true)
    List<T> findAll();

    @Override
    @Query(value = "select * from #{#entityName} where id = ?1 and ativo = true", nativeQuery = true)
    Optional<T> findById(PK arg0);

    @Override
    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} SET ativo=false where id = ?1", nativeQuery = true)
    void deleteById(PK arg0);
}
