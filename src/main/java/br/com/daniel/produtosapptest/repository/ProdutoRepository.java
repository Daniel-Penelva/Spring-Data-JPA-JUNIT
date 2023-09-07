package br.com.daniel.produtosapptest.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.daniel.produtosapptest.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
    
    public Produto findByNome(String nome);
}
