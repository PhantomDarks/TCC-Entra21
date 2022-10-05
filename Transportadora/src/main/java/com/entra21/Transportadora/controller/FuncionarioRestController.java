package com.entra21.Transportadora.controller;

import com.entra21.Transportadora.model.dto.Funcionario.FuncionarioDTO;
import com.entra21.Transportadora.model.dto.Funcionario.FuncionarioAddDTO;
import com.entra21.Transportadora.view.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioRestController {

   @Autowired
   FuncionarioService funcionarioService;


   @GetMapping
   public List<FuncionarioDTO> getAllFuncionario() {
       return funcionarioService.getAllFuncionario();
   }

   @PostMapping
   public void addFuncionario(
           @RequestBody FuncionarioAddDTO funcionarioPayLoadDTO
   ){
      funcionarioService.saveFuncionario(funcionarioPayLoadDTO);
   }
//
////    @Autowired
////    private FuncionarioRepository funcionarioRepository;
//
////    @GetMapping
////    public List<FuncionarioEntity> getAllFuncionarios(){
////        return funcionarioRepository.findAll();
////    }
}
