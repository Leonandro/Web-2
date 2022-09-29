package com.jeanlima.mvcapp.controller;

import com.jeanlima.mvcapp.model.Avaliacao;
import com.jeanlima.mvcapp.model.Usuario;
import com.jeanlima.mvcapp.model.util.avaliacaoFormDto;
import com.jeanlima.mvcapp.service.AvaliacaoService;
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
@RequestMapping("/avaliacao")
public class AvaliacaoController { //extends AbstractController<Usuario, Integer> {

    @Autowired
    AvaliacaoService service;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ReceitaService receitaService;

//    @Override
//    protected AbstractService<Usuario, Integer> service() {
//        return this.usuarioService;
//    }

    @GetMapping
    public String findAll(Model model) {

        List<Avaliacao> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "avaliacao/listAvaliacao";

    }

    @GetMapping("/home")
    public String finById(@RequestParam("id") String id, Model model) {

        Avaliacao avaliacao = this.service.getById(Integer.parseInt(id));
        model.addAttribute("avaliacao", avaliacao);
        return "avaliacao/avaliacaoHome";
    }

    @GetMapping("/form")
    public String showForm(@ModelAttribute Usuario usuario, Model model) {
        model.addAttribute("endpointFinal", "cadastrar");
        model.addAttribute("avaliacaoAntiga", new Avaliacao());
        model.addAttribute("dto", new avaliacaoFormDto());
        return "avaliacao/formAvaliacao";
    }

    @RequestMapping("/cadastrar")
    public String saveUser(@ModelAttribute("avaliacao") avaliacaoFormDto avaliacao,  Model model){
        //if(usuario.getNome() != null && usuario.getEmail() != null) usuarioService.save(usuario);
        Avaliacao avaliacao1 = new Avaliacao();

        avaliacao1.setReceita(this.receitaService.getById(Integer.parseInt(avaliacao.getReceitaId())));
        avaliacao1.setUsuario(this.usuarioService.getById(Integer.parseInt(avaliacao.getUserId())));
        avaliacao1.setRank(Integer.parseInt(avaliacao.getRank()));
        service.save(avaliacao1);
        List<Avaliacao> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "avaliacao/listAvaliacao";
    }

    @RequestMapping("/atualizar")
    public String updateUser(@ModelAttribute("avaliacaoAntiga") Avaliacao avaliacao,  Model model){
        if(true){
            Avaliacao avaliacaoASerModificada = this.service.getById(avaliacao.getId());
            avaliacaoASerModificada.setRank( avaliacao.getRank() );
            avaliacaoASerModificada.setUsuario( avaliacao.getUsuario() );
            avaliacaoASerModificada.setReceita( avaliacao.getReceita() );

            service.save(avaliacaoASerModificada);
        }
        List<Avaliacao> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "avaliacao/listAvaliacao";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") String id,Model model) {

        this.service.delete(Integer.parseInt(id));
        List<Avaliacao> entidades = this.service.findAll();
        model.addAttribute("entidades", entidades);
        return "avaliacao/listAvaliacao";
    }

    @GetMapping("/formUpdate")
    public String updateById(@RequestParam("id") String id,Model model) {

        Avaliacao avaliacao = this.service.getById(Integer.parseInt(id));
        model.addAttribute("endpointFinal", "atualizar");
        model.addAttribute("avaliacaoAntiga", avaliacao);
        avaliacaoFormDto avaliacaoNova = new avaliacaoFormDto();
        avaliacaoNova.setId(Integer.parseInt(id));
        model.addAttribute("dto", avaliacaoNova);
        return "avaliacao/formAvaliacao";
    }

}

