//package com.jeanlima.mvcapp.controller;
//
//import com.jeanlima.mvcapp.model.AbstractModel;
//import com.jeanlima.mvcapp.model.Estudante;
//import com.jeanlima.mvcapp.service.AbstractService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.Serializable;
//import java.util.List;
//
//@CrossOrigin
//public abstract class AbstractController<T extends AbstractModel<PK>, PK extends Serializable> {
//    protected abstract AbstractService<T, PK> service();
//
//    @GetMapping
//    public String findAll(Model model) {
//
//        List<T> entidades = service().findAll();
//        model.addAttribute("entidades", entidades);
//
//        return "usuario/listUsuario";
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<T> getById(@PathVariable PK id) {
//        return service().getById(id);
//    }
//
//    @PostMapping
//    public ResponseEntity<T> save(@RequestBody T entity) {
//        return service().save(entity);
//    }
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<T> delete(@PathVariable(value = "id") PK id) {
//        return service().delete(id);
//    }
//}