package com.AppRH.AppRH.repository;

import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Funcionario;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
    
    Funcionario findById(long id);

    //busca
    Funcionario findByNome(String nome);

    @Query(value= "select u from Funcionario u  where u.nome like %?1%")
    List<Funcionario> findByNomes(String nome);
}
