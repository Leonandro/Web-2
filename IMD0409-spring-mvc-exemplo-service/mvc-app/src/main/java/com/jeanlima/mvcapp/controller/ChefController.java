package com.jeanlima.mvcapp.controller;

import com.jeanlima.mvcapp.model.Chef;
import com.jeanlima.mvcapp.model.Usuario;
import com.jeanlima.mvcapp.model.util.chefFormDto;
import com.jeanlima.mvcapp.service.ChefService;
import com.jeanlima.mvcapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


@Controller
@RequestMapping("/chef")
public class ChefController { //extends AbstractController<Usuario, Integer> {

    @Autowired
    ChefService service;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public String findAll(Model model) {

        List<Chef> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "chef/listChef";

    }

    @GetMapping("/home")
    public String finById(@RequestParam("id") String id, Model model) {

        Chef chef = this.service.getById(Integer.parseInt(id));
        model.addAttribute("chef", chef);
        return "chef/chefHome";
    }

    @GetMapping("/form")
    public String showForm(@ModelAttribute chefFormDto chef, Model model) {
        model.addAttribute("endpointFinal", "cadastrar");
        model.addAttribute("chefAntigo", new chefFormDto());
        model.addAttribute("chef", new chefFormDto());
        return "chef/formChef";
    }

    @RequestMapping("/cadastrar")
    public String saveUser(@ModelAttribute("chef") chefFormDto chef,  Model model){


        Chef chefASerSalvo = new Chef();
        chefASerSalvo.setAlcunha(chef.getAlcunha());
        chefASerSalvo.setRestaurante(chef.getRestaurante());

        Optional.ofNullable(this.usuarioService.getById(chef.getUsuario())).ifPresentOrElse(
                (c) -> {chefASerSalvo.setUsuario(c);},
                () -> {}
        );
        this.service.save(chefASerSalvo);
        List<Chef> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "chef/listChef";
    }

    @RequestMapping("/atualizar")
    public String updateUser(@ModelAttribute("chefAntigo") Chef chef,  Model model){
//        if(usuario.getNome() != null && usuario.getEmail() != null){
//            Usuario usuarioASerModificado = usuarioService.getById(usuario.getId());
//            usuarioASerModificado.setNome( usuario.getNome() );
//            usuarioASerModificado.setEmail( usuario.getEmail() );
//            usuarioASerModificado.setIdade( usuario.getIdade() );
//            usuarioASerModificado.setChef( usuario.getChef() );
//            usuarioASerModificado.setAvaliacoes( usuario.getAvaliacoes() );
//            usuarioService.save(usuarioASerModificado);
//        }
        List<Chef> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "chef/listChef";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") String id,Model model) {

        this.service.delete(Integer.parseInt(id));
        List<Chef> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "chef/listChef";
    }

    @GetMapping("/formUpdate")
    public String updateById(@RequestParam("id") String id,Model model) {

        Chef chef = this.service.getById(Integer.parseInt(id));
        model.addAttribute("endpointFinal", "atualizar");
        model.addAttribute("chefAntigo", chef);
        Chef chefNovo = new Chef();
        chefNovo.setId(Integer.parseInt(id));
        model.addAttribute("chef", chefNovo);
        return "chef/formChef";
    }

}
