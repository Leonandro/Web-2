package com.jeanlima.mvcapp.controller;

import com.jeanlima.mvcapp.model.Chef;
import com.jeanlima.mvcapp.model.Receita;
import com.jeanlima.mvcapp.model.Usuario;
import com.jeanlima.mvcapp.model.util.receitaChefDto;
import com.jeanlima.mvcapp.model.util.receitaUserDto;
import com.jeanlima.mvcapp.service.ChefService;
import com.jeanlima.mvcapp.service.ReceitaService;
import com.jeanlima.mvcapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/receita")
public class ReceitasController { //extends AbstractController<Usuario, Integer> {

    @Autowired
    ReceitaService service;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ChefService chefService;

    @GetMapping
    public String findAll(Model model) {

        List<Receita> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "receita/listReceita";

    }

    @RequestMapping("/usuario/{id}")
    public String findAll(@PathVariable("id") String id, Model model) {
        Usuario usuario = this.usuarioService.getById(Integer.parseInt(id));
        List<Receita> receitasQueOUsuarioCurtiu =  Optional.ofNullable( usuario.getReceitasFavoritas() ).orElseGet(() -> new ArrayList<>());
        model.addAttribute("entidades", receitasQueOUsuarioCurtiu);
        return "receita/listReceita";

    }

    @RequestMapping("/chef/{id}")
    public String findAllChefs(@PathVariable("id") String id, Model model) {
        Chef chef = this.chefService.getById(Integer.parseInt(id));
        List<Receita> receitasDoChef =  Optional.ofNullable( chef.getReceitas() ).orElseGet(() -> new ArrayList<>());
        model.addAttribute("entidades", receitasDoChef);
        return "receita/listReceita";

    }

    @GetMapping("/home")
    public String finById(@RequestParam("id") String id,Model model) {

        Receita receita = this.service.getById(Integer.parseInt(id));
        model.addAttribute("receita", receita);
        model.addAttribute("dto", new receitaUserDto());
        model.addAttribute("dtoChef", new receitaChefDto());
        return "receita/receitaHome";

    }

    @RequestMapping("/curtir/{id}")
    public String curtirReceita(@PathVariable(value="id") String id, @ModelAttribute("dto") receitaUserDto dto, Model model) {
        System.out.println("DTO " + dto.getIdUser());
        Receita receita = this.service.getById(Integer.parseInt(id));
        Usuario user = this.usuarioService.getById(dto.getIdUser());

        List<Usuario> usuariosQueCurtiramAReceita = Optional.ofNullable( receita.getUsuarioList() ).orElseGet( () -> new ArrayList<>());
        List<Receita> receitasQueOUsuarioCurtiu =  Optional.ofNullable( user.getReceitasFavoritas() ).orElseGet(() -> new ArrayList<>());

        usuariosQueCurtiramAReceita.add(user);
        receitasQueOUsuarioCurtiu.add(receita);

        user.setReceitasFavoritas(receitasQueOUsuarioCurtiu);
        receita.setUsuarioList(usuariosQueCurtiramAReceita);
        this.usuarioService.save(user);
        this.service.save(receita);

        model.addAttribute("receita", receita);
        model.addAttribute("dto", new receitaUserDto());
        model.addAttribute("dtoChef", new receitaChefDto());
        return "receita/receitaHome";

    }

    @RequestMapping("/assinar/{id}")
    public String assinarReceita(@PathVariable(value="id") String id, @ModelAttribute("dto") receitaChefDto dto, Model model) {
        System.out.println("DTO " + dto.getIdChef());
        Receita receita = this.service.getById(Integer.parseInt(id));
        Chef chef = this.chefService.getById(dto.getIdChef());

        receita.setChef(chef);
        this.service.save(receita);

        model.addAttribute("receita", receita);
        model.addAttribute("dto", new receitaUserDto());
        model.addAttribute("dtoChef", new receitaChefDto());
        return "receita/receitaHome";

    }

    @GetMapping("/form")
    public String showForm(@ModelAttribute Receita receita, Model model) {
        model.addAttribute("endpointFinal", "cadastrar");
        model.addAttribute("receitaAntiga", new Receita());
        model.addAttribute("receita", new Receita());
        return "receita/formReceita";
    }

    @RequestMapping("/cadastrar")
    public String saveUser(@ModelAttribute("receita") Receita receita,  Model model){
        /*if(usuario.getNome() != null && usuario.getEmail() != null)*/ service.save(receita);
        List<Receita> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "receita/listReceita";
    }

    @RequestMapping("/atualizar")
    public String updateUser(@ModelAttribute("receitaAntiga") Receita receita,  Model model){
        if(receita.getNome() != null && receita.getPreparo() != null){
            Receita recietaASerModificada = service.getById(receita.getId());
            recietaASerModificada.setNome(receita.getNome());
            recietaASerModificada.setIngredientes(receita.getIngredientes());
            recietaASerModificada.setPreparo(receita.getPreparo());
            recietaASerModificada.setChef(receita.getChef());
            recietaASerModificada.setAvaliacoes(receita.getAvaliacoes());
            recietaASerModificada.setUsuarioList(receita.getUsuarioList());
            service.save(recietaASerModificada);
        }
        List<Receita> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "receita/listReceita";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") String id,Model model) {

        this.service.delete(Integer.parseInt(id));
        List<Receita> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "receita/listReceita";
    }

    @GetMapping("/formUpdate")
    public String updateById(@RequestParam("id") String id,Model model) {

        Receita receita = this.service.getById(Integer.parseInt(id));
        model.addAttribute("endpointFinal", "atualizar");
        model.addAttribute("receitaAntiga", receita);
        Receita receitaNova = new Receita();
        receitaNova.setId(Integer.parseInt(id));
        model.addAttribute("receita", receitaNova);
        return "receita/formReceita";
    }

}

