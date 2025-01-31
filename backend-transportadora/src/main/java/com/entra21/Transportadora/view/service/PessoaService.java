package com.entra21.Transportadora.view.service;

import com.entra21.Transportadora.model.dto.PessoaDTO;
import com.entra21.Transportadora.model.entity.PessoaEntity;
import com.entra21.Transportadora.view.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService implements UserDetailsService{

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PessoaEntity user = pessoaRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public PessoaDTO buscarUsuarioLogado() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        PessoaEntity user = new PessoaEntity();
        try {
            user = (PessoaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        pessoaDTO.setCpf(user.getCpf());
        pessoaDTO.setNome(user.getNome());
        pessoaDTO.setSobrenome(user.getSobrenome());
        pessoaDTO.setTelefone(user.getTelefone());
        pessoaDTO.setLogin(user.getLogin());
        pessoaDTO.setSenha(user.getSenha());
        pessoaDTO.setDesabilitado(user.getDesabilitado());
        pessoaDTO.setIdPessoa(user.getIdPessoa());

        return pessoaDTO;
    }

    public PessoaDTO findByCpf(String cpf){
        PessoaEntity pessoaEntity = pessoaRepository.findByCpf(cpf).orElseThrow(
            () -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada");});
        PessoaDTO dto = new PessoaDTO();
        dto.setNome(pessoaEntity.getNome());
        dto.setSobrenome(pessoaEntity.getSobrenome());
        dto.setCpf(pessoaEntity.getCpf());
        dto.setTelefone(pessoaEntity.getTelefone());
        return dto;
    }

    public List<PessoaDTO> getAll() {
        return pessoaRepository.findAll().stream().map(pr -> {
            PessoaDTO dto = new PessoaDTO();
            dto.setNome(pr.getNome());
            dto.setSobrenome(pr.getSobrenome());
            dto.setCpf(pr.getCpf());
            dto.setTelefone(pr.getTelefone());
            return dto;
        }).collect(Collectors.toList());
    }

    public void save(PessoaDTO input) {
        PessoaEntity pessoaEntity = new PessoaEntity();
        pessoaEntity.setNome(input.getNome());
        pessoaEntity.setSobrenome(input.getSobrenome());
        pessoaEntity.setTelefone(input.getTelefone());
        pessoaEntity.setCpf(input.getCpf());
        pessoaEntity.setLogin(input.getLogin());
        pessoaEntity.setSenha(input.getSenha());
        pessoaEntity.setDesabilitado(false);
        pessoaRepository.save(pessoaEntity);
    }

    public void updatePessoa(String cpf, PessoaDTO pessoaPayLoadDTO) {
        PessoaEntity pessoa = pessoaRepository.findByCpf(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!"));
        if (pessoaPayLoadDTO.getNome() != null && pessoaPayLoadDTO.getNome() != "") pessoa.setNome(pessoaPayLoadDTO.getNome());
        if (pessoaPayLoadDTO.getSobrenome() != null && pessoaPayLoadDTO.getSobrenome() != "") pessoa.setSobrenome(pessoaPayLoadDTO.getSobrenome());
        if (pessoaPayLoadDTO.getTelefone() != null && pessoaPayLoadDTO.getTelefone() != "") pessoa.setTelefone(pessoaPayLoadDTO.getTelefone());
        if (pessoaPayLoadDTO.getCpf() != null && pessoaPayLoadDTO.getCpf() != "") pessoa.setCpf(pessoaPayLoadDTO.getCpf());
        if (pessoaPayLoadDTO.getLogin() != null && pessoaPayLoadDTO.getLogin() != "") pessoa.setLogin(pessoaPayLoadDTO.getLogin());
        if (pessoaPayLoadDTO.getSenha() != null && pessoaPayLoadDTO.getSenha() != "") pessoa.setSenha(pessoaPayLoadDTO.getSenha());
        if (pessoaPayLoadDTO.getDesabilitado() != null && pessoaPayLoadDTO.getNome() != "") pessoa.setDesabilitado(pessoaPayLoadDTO.getDesabilitado());
        pessoaRepository.save(pessoa);
    }

    public PessoaEntity buscarLogin(PessoaDTO login) {
        PessoaEntity pessoaEntity = pessoaRepository.findByLogin(login.getUsername());
        if (pessoaEntity != null && pessoaEntity.getSenha().equals(login.getPassword())) {
            return pessoaEntity;
        }
        return null;
    }
}
