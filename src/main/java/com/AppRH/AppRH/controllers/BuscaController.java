package com.AppRH.AppRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



import com.AppRH.AppRH.repository.FuncionarioRepository;
import com.AppRH.AppRH.repository.VagaRepository;
import com.AppRH.AppRH.repository.DependentesRepository;
import com.AppRH.AppRH.repository.CandidatoRepository;



@Controller
public class BuscaController {
    
    @Autowired
    private FuncionarioRepository fr;

    @Autowired
    private VagaRepository vr;

    @Autowired
    private DependentesRepository dr;

    @Autowired
    private CandidatoRepository cr;


    //GET
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView abrirIndex(){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    //POST
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView buscarIndex(@RequestParam("buscar") String buscar, @RequestParam("nome") String nome){

        ModelAndView mv = new ModelAndView("index");
        String mensagem = "Resultados da buscar por " + buscar;

        if(nome.equals("nomefuncionario")){
            mv.addObject("funcionarios", fr.findByNomes(buscar));
        }else if(nome.equals("nomedependentes")){
            mv.addObject("dependentes", dr.findByNomesDependentes(buscar));

        }else if(nome.equals("nomecandidato")){
            mv.addObject("candidatos", cr.findByNomesCandidatos(buscar));

        }else if(nome.equals("titulovaga")){
            mv.addObject("vagas",vr.findByNomesVagas(buscar));

        }else{
            mv.addObject("funcionarios",fr.findByNomes(buscar));

            mv.addObject("dependentes", dr.findByNomesDependentes(buscar));

            mv.addObject("candidatos", cr.findByNomesCandidatos(buscar));

            mv.addObject("vagas",vr.findByNomesVagas(buscar));

        }

        mv.addObject("mensagem", mensagem);

        return mv;



    }

}
