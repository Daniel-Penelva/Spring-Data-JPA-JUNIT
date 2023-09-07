package br.com.daniel.produtosapptest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import br.com.daniel.produtosapptest.model.Produto;
import br.com.daniel.produtosapptest.repository.ProdutoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class ProdutoTests{

    /*OBS. Vamos usar o H2 Database para testes unitários para isso vamos adicionar a dependência H2Database no pom.xml, vamos crair um ambiente 
    de teste separado que não afeta o banco de dados de produção. Isso é fundamental para garantir que os testes sejam repetíveis, previsíveis 
    e independentes de outros sistemas. */

    @Autowired
    ProdutoRepository produtoRepository;

    @Test
    @Rollback(false)
    public void testCriarProduto(){
        Produto produto = new Produto("teste", 4000);
        Produto criarProduto = produtoRepository.save(produto);

        // Verificar se o objeto `criarProduto` não é nulo.
        assertNotNull(criarProduto);
    }

    @Test
    public void testBuscarProdutoPorNome(){
        String nome = "X-Box One";

        Produto produto = produtoRepository.findByNome(nome);

        assertNotNull(produto);
        assertEquals(nome, produto.getNome());
        System.out.println("É o mesmo valor buscado"); 
    }

    @Test
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
        String nomeProduto = "TV Samsung HD 72 polegadas + caixa de som";

        // Atualiza esse novo produto passando o id para ser encontrado
        Produto produto = new Produto(nomeProduto, 8000);
        produto.setId(1);

        // Salva o produto 
        produtoRepository.save(produto);

        // busca o nome do produto atualizado 
        Produto atualizarProduto = produtoRepository.findByNome(nomeProduto);

        // Verifica se o nome é o mesmo que foi atualizado
         assertNotNull(atualizarProduto);  
         assertEquals(atualizarProduto.getNome(), nomeProduto);
    }

    @Test
    public void testListarProduto(){
        Iterable<Produto> produtos = produtoRepository.findAll();

        int count = 0;
         for(Produto produto: produtos){
            count++;
         }

         assertTrue(count > 0);

          for(Produto produto: produtos){
            System.out.println(produto);
            System.out.println("-----------------------------");
         }

       /* OBS. o método assertTrue() para verificar se o valor de count é maior que zero, indicando que pelo menos um produto foi retornado. Se 
       nenhum produto for retornado, o teste falhará. Caso contrário, o teste passará.*/
    }

    @Test
    public void testDeletarProduto(){
        Integer id = 3;

        // Verifica se um objeto de produto com o identificador especificado existe no banco de dados.
        boolean existeProdutoId = produtoRepository.findById(id).isPresent(); // true

        produtoRepository.deleteById(id);

        boolean naoExisteProdutoId = produtoRepository.findById(id).isPresent(); //false

        assertTrue(existeProdutoId);
        assertFalse(naoExisteProdutoId);
    }

}
