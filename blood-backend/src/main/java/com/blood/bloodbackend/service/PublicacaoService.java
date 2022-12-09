package com.blood.bloodbackend.service;

import com.blood.bloodbackend.model.DTO.PublicacaoIncomingDTO;
import com.blood.bloodbackend.model.DTO.UnidadeDoacaoIncomingDTO;
import com.blood.bloodbackend.model.Endereco;
import com.blood.bloodbackend.model.Publicacao;
import com.blood.bloodbackend.model.UnidadeDeDoacao;
import com.blood.bloodbackend.model.Usuario;
import com.blood.bloodbackend.repository.EnderecoRepository;
import com.blood.bloodbackend.repository.PublicacaoRepository;
import com.blood.bloodbackend.repository.UnidadeDeDoacaoRepository;
import com.blood.bloodbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PublicacaoService {

    @Autowired
    PublicacaoRepository repository;

    @Autowired
    UnidadeDeDoacaoRepository unidadeDeDoacaoRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Publicacao> getPublicacoes () {
        return repository.findAll();
    }

    public  Publicacao getPublicacao (long id) {
        Optional<Publicacao> publicacao = repository.findById(id);

        if(!publicacao.isPresent()) {
            throw new NoSuchElementException("Publicação não encontrada");
        }

        return publicacao.get();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Publicacao cadastrarPublicacao (PublicacaoIncomingDTO publicacao) {
        Optional<Usuario> usuario = usuarioRepository.findById(publicacao.getUsuario());
        Optional<UnidadeDeDoacao> unidadeDeDoacao = unidadeDeDoacaoRepository.findById(publicacao.getUnidadeDoacao());

        if (!usuario.isPresent() || !unidadeDeDoacao.isPresent()){
            throw new NoSuchElementException("Endereço não cadastrado ou Unidade de Doação não cadastrada");
        }

        Publicacao novaPublicacao = new Publicacao();
        novaPublicacao.setDataDeCriacao(publicacao.getDataCriacao());
        novaPublicacao.setImageCode(publicacao.getImageCode());
        novaPublicacao.setNomeDonatario(publicacao.getNomeDonatario());
        novaPublicacao.setIdadeDonatario(publicacao.getIdadeDonatario());
        novaPublicacao.setPrioridade(publicacao.getPrioridade());
        novaPublicacao.setTipoSanguineo(publicacao.getTipoSanguineo());
        novaPublicacao.setDoador(0);


        if (usuario.get().getPublicacao() != null && (!usuario.get().getPublicacao().isEmpty())){
            deletePublicacao(usuario.get().getPublicacao().get(0).getId());
        }

        List<Publicacao> publicacao_unidade = new ArrayList<Publicacao>();
        if(unidadeDeDoacao.get() != null && (!unidadeDeDoacao.get().getPublicacao().isEmpty())){
            publicacao_unidade = unidadeDeDoacao.get().getPublicacao();
        }


        novaPublicacao.setUsuario(usuario.get());
        novaPublicacao.setUnidadeDeDoacao(unidadeDeDoacao.get());

        Publicacao publicacaoSalva = repository.save(novaPublicacao);
//        List<Publicacao> publicacao_usuario = usuario.get().getPublicacao();
        List<Publicacao> publicacao_usuario = new ArrayList<>();


        publicacao_unidade.add(publicacaoSalva);
        publicacao_usuario.add(publicacaoSalva);
        unidadeDeDoacao.get().setPublicacao(publicacao_unidade);

        System.out.println("LISTA DE PUBLICAÇÕES " + publicacao_usuario.size());
        List<Publicacao> a = usuario.get().getPublicacao();
        a.clear();
        usuario.get().setPublicacao(a);
        usuario.get().setPublicacao(publicacao_usuario);

//        unidadeDeDoacaoRepository.save(unidadeDeDoacao.get());
//        usuarioRepository.save(usuario.get());

        return publicacaoSalva;
    }

    public Publicacao definirDoador (long idPublicacao, long idDoador) {
        Optional<Publicacao> publicacao = repository.findById(idPublicacao);
        Optional<Usuario> doador = usuarioRepository.findById(idDoador);

        if (!publicacao.isPresent() || !doador.isPresent()) {
            throw new NoSuchElementException("Publicação ou Doador não cadastrado");
        }

        publicacao.get().setDoador(idDoador);

        return repository.save(publicacao.get());

    }

    public Publicacao deletePublicacao (long id) {
        Optional <Publicacao> publicacao = repository.findById(id);
        if(!publicacao.isPresent()){
            throw  new NoSuchElementException("Publicacao não encontrada");
        }
        repository.deleteById(id);
        return publicacao.get();
    }

}
