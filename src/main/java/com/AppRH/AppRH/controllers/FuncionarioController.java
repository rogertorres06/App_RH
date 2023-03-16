package com.AppRH.AppRH.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Dependentes;
import com.AppRH.AppRH.models.Funcionario;
import com.AppRH.AppRH.repository.DependentesRepository;
import com.AppRH.AppRH.repository.FuncionarioRepository;



@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository fr;

    @Autowired
    private DependentesRepository dr;


    //CHAMA O FORM DE CADASTRAR FUNCIONARIOS
    @RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.GET)
    public String fomr(){
        return "funcionario/formFuncionario";

    }

    // CADASTRAR FUNCIONARIO
    @RequestMapping(value = "/cadastrarFuncionario", method= RequestMethod.POST)
    public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes  attributes){

        if(result.hasErrors()){
            attributes.addAttribute("mensagem", "Verifique os campos");
            return "redirect:/cadastrarFuncionario";
        }

        fr.save(funcionario);
        attributes.addFlashAttribute("mensagem", "Funcionario cadastrado com sucesso");
        return "redirect:/cadastrarFuncionario";

    }

    // LISTAR FUNCIONARIOS

    @RequestMapping("/funcionarios")
    public ModelAndView listaFuncionario(){
        ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
        Iterable<Funcionario> funcionarios = fr.findAll();
        mv.addObject("funcionarios", funcionarios);
        return mv;
    }

    // LSITAR DEPENDENTES 

    @RequestMapping(value = "/dependentes/{id}", method = RequestMethod.GET)
    public ModelAndView dependentes(@PathVariable("id") long id){
        
        Funcionario funcionario = fr.findById(id);
        ModelAndView mv = new ModelAndView("funcionario/dependentes");
        mv.addObject("funcionarios", funcionario);
        // LISTA DE DEPENDENTES BASEADO NO FUNCIONARIO
        Iterable<Dependentes> dependentes = dr.findByFuncionario(funcionario);
        mv.addObject("dependentes", dependentes);

        return mv;
    }

    // ADCIONAR DEPENDENTES 
    @RequestMapping(value = "/dependentes/{id}", method = RequestMethod.POST)
    public String dependentesPost(@PathVariable("id") long id, Dependentes dependentes, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos");

            return "redirect:/dependentes/{id}";
        }

        if(dr.findByCpf(dependentes.getCpf()) != null){
            attributes.addFlashAttribute("mensagem", "Já existe um dependente com esse CPF");
            return "redirect:/dependentes/{id}";
        }

        Funcionario funcionario= fr.findById(id);
        dependentes.setFuncionario(funcionario);
        dr.save(dependentes);
        attributes.addAttribute("mensagem", "Dependente adicionado com sucesso");
        return "redirect:/dependentes/{id}";
    }

    // DELETA FUNCIONARIO

    @RequestMapping("/deletarFuncionario")
    public String deletarFuncionario(long id){
        Funcionario funcionario = fr.findById(id);
        fr.delete(funcionario);
        return "redirect:/funcionarios";

    }

    //ATUALIZAR FUNCIONARIOS

    @RequestMapping(value = "/editar-funcionario", method = RequestMethod.GET)
    public ModelAndView editarFuncionario(long id){

        Funcionario funcionario = fr.findById(id);
        ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
        mv.addObject("funcionario", funcionario);
        return mv;
    }


    //UPDATE FUNCIONARIO

    @RequestMapping(value = "editar-funcionario", method = RequestMethod.POST)
    public String updateFuncionario(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes){

        fr.save(funcionario);
        attributes.addFlashAttribute("sucess", "Funcionario alterado com sucesso!");
        
        long idLong =  funcionario.getId();
        String id = "" + idLong;
        return "redirect:/dependentes/" + id;
    }

    //DELETAR DEPENDENTES

    @RequestMapping("/deletarDependente")
    public String deletarDependente(String cpf){
        Dependentes dependente = dr.findByCpf(cpf);

        Funcionario  funcionario = dependente.getFuncionario();
        String codigo = "" + funcionario.getId();
        dr.delete(dependente);
		return "redirect:/dependentes/" + codigo;
	}

	
    
    
}
