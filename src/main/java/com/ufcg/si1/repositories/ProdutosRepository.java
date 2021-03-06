package com.ufcg.si1.repositories;

import com.ufcg.si1.model.Produto;


import org.springframework.data.repository.CrudRepository;

public interface ProdutosRepository extends CrudRepository<Produto, Long> {

    public Produto getProdutoById(Long id);
    
    public boolean existsProdutoById(Long id);
}
