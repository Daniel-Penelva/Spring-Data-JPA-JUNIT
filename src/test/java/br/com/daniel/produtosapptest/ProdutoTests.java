package br.com.daniel.produtosapptest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    //@Test
    @Rollback(false)
    public void testCriarProduto(){
        Produto produto = new Produto("X-Box One", 4000);
        Produto criarProduto = produtoRepository.save(produto);

        // Verificar se o objeto `criarProduto` não é nulo.
        assertNotNull(criarProduto);
    }

    //@Test
    public void testBuscarProdutoPorNome(){
        String nome = "TV Samsung HD";

        Produto produto = produtoRepository.findByNome(nome);

        assertNotNull(produto);
        assertEquals(nome, produto.getNome());
        System.out.println("É o mesmo valor buscado"); 
    }

    //@Test
    public void testBuscarProdutoPorNomeNoExists(){
        String nome = "IPhone 11";

        Produto produto = produtoRepository.findByNome(nome);

        assertNull(produto);
        System.out.println("Não é o mesmo valor buscado"); 
    }

    @Test
    @Rollback(false)
    public void testAtualizarProduto(){
        // Cria um novo nome de produto
        String nomeProduto = "TV Samsung HD 72 polegadas";

        // Atualiza esse novo produto passando o id para ser encontrado
        Produto produto = new Produto(nomeProduto, 6000);
        produto.setId(1);

        // Salva o produto 
        produtoRepository.save(produto);

        // busca o nome do produto atualizado 
        Produto atualizarProduto = produtoRepository.findByNome(nomeProduto);

        // Verifica se o nome é o mesmo que foi atualizado
         assertNotNull(atualizarProduto);  
         assertEquals(atualizarProduto.getNome(), nomeProduto);
    }
}
