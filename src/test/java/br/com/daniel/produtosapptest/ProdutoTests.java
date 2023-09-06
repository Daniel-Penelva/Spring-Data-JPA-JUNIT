package br.com.daniel.produtosapptest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import br.com.daniel.produtosapptest.model.Produto;
import br.com.daniel.produtosapptest.repository.ProdutoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProdutoTests {

    /*OBS. Vamos usar o H2 Database para testes unitários para isso vamos adicionar a dependência H2Database no pom.xml, vamos crair um ambiente 
    de teste separado que não afeta o banco de dados de produção. Isso é fundamental para garantir que os testes sejam repetíveis, previsíveis 
    e independentes de outros sistemas. */

    @Autowired
    ProdutoRepository produtoRepository;

    @Test
    @Rollback(false)
    public void testCriarProduto(){
        Produto produto = new Produto("X-Box One", 4000);
        Produto criarProduto = produtoRepository.save(produto);

        // Verificar se o objeto `criarProduto` não é nulo.
        assertNotNull(criarProduto);
    }
}
