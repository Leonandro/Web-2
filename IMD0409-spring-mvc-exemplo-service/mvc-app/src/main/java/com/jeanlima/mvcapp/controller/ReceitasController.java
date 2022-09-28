package com.jeanlima.mvcapp.controller;

import com.jeanlima.mvcapp.model.Receita;
import com.jeanlima.mvcapp.model.Usuario;
import com.jeanlima.mvcapp.service.ReceitaService;
import com.jeanlima.mvcapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;




@Controller
@RequestMapping("/receita")
public class ReceitasController { //extends AbstractController<Usuario, Integer> {

    @Autowired
    ReceitaService service;


    @GetMapping
    public String findAll(Model model) {

        List<Receita> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "receita/listReceita";

    }

    @GetMapping("/home")
    public String finById(@RequestParam("id") String id,Model model) {

        Receita receita = this.service.getById(Integer.parseInt(id));
        model.addAttribute("receita", receita);
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

