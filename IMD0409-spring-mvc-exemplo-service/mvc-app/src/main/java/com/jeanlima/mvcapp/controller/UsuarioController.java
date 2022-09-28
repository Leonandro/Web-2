package com.jeanlima.mvcapp.controller;

import com.jeanlima.mvcapp.model.Estudante;
import com.jeanlima.mvcapp.model.Usuario;
import com.jeanlima.mvcapp.service.AbstractService;
import com.jeanlima.mvcapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/usuario")
public class UsuarioController { //extends AbstractController<Usuario, Integer> {

    @Autowired
    UsuarioService usuarioService;

//    @Override
//    protected AbstractService<Usuario, Integer> service() {
//        return this.usuarioService;
//    }

    @GetMapping
    public String findAll(Model model) {

        List<Usuario> entidades = this.usuarioService.findAll();
        model.addAttribute("entidades", entidades);
        return "usuario/listUsuario";

    }

    @GetMapping("/home")
    public String finById(@RequestParam("id") String id,Model model) {

        Usuario usuario = this.usuarioService.getById(Integer.parseInt(id));
        model.addAttribute("usuario", usuario);
        return "usuario/usuarioHome";
    }

    @GetMapping("/form")
    public String showForm(@ModelAttribute Usuario usuario, Model model) {
        model.addAttribute("endpointFinal", "cadastrar");
        model.addAttribute("usuarioAntigo", new Usuario());
        model.addAttribute("usuario", new Usuario());
        return "usuario/formUsuario";
    }

    @RequestMapping("/cadastrar")
    public String saveUser(@ModelAttribute("usuario") Usuario usuario,  Model model){
        if(usuario.getNome() != null && usuario.getEmail() != null) usuarioService.save(usuario);
        List<Usuario> entidades = this.usuarioService.findAll();
        model.addAttribute("entidades", entidades);
        return "usuario/listUsuario";
    }

    @RequestMapping("/atualizar")
    public String updateUser(@ModelAttribute("usuarioAntigo") Usuario usuario,  Model model){
        if(usuario.getNome() != null && usuario.getEmail() != null){
            Usuario usuarioASerModificado = usuarioService.getById(usuario.getId());
            usuarioASerModificado.setNome( usuario.getNome() );
            usuarioASerModificado.setEmail( usuario.getEmail() );
            usuarioASerModificado.setIdade( usuario.getIdade() );
            usuarioASerModificado.setChef( usuario.getChef() );
            usuarioASerModificado.setAvaliacoes( usuario.getAvaliacoes() );
            usuarioService.save(usuarioASerModificado);
        }
        List<Usuario> entidades = this.usuarioService.findAll();
        model.addAttribute("entidades", entidades);
        return "usuario/listUsuario";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") String id,Model model) {

        this.usuarioService.delete(Integer.parseInt(id));
        List<Usuario> entidades = this.usuarioService.findAll();
        model.addAttribute("entidades", entidades);
        return "usuario/listUsuario";
    }

    @GetMapping("/formUpdate")
    public String updateById(@RequestParam("id") String id,Model model) {

        Usuario usuario = this.usuarioService.getById(Integer.parseInt(id));
        model.addAttribute("endpointFinal", "atualizar");
        model.addAttribute("usuarioAntigo", usuario);
        Usuario usuarioNovo = new Usuario();
        usuarioNovo.setId(Integer.parseInt(id));
        model.addAttribute("usuario", usuarioNovo);
        return "usuario/formUsuario";
    }

}
