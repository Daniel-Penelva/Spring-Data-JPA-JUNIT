# JUnit para Spring Data JPA

## Entidade Produto

A entidade `Produto` é uma representação de um produto em um sistema. Ela é usada para armazenar informações sobre produtos, como nome e preço. Esta documentação descreve a estrutura e os atributos da entidade `Produto`.

```java
package br.com.daniel.produtosapptest.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 64)
    private String nome;
    private float preco;

    public Produto(){

    }

    public Produto(String nome, float preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Produto(Integer id, String nome, float preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto [id=" + id + ", nome=" + nome + ", preco=" + preco + "]";
    }
}
```

### Estrutura da Entidade Produto

A entidade `Produto` possui os seguintes atributos:

- **id**: Um identificador único para o produto.
- **nome**: O nome do produto, com um limite de 64 caracteres.
- **preco**: O preço do produto.

### Construtores

A classe `Produto` possui os seguintes construtores:

1. **Produto()**: Um construtor padrão vazio.
2. **Produto(String nome, float preco)**: Um construtor que aceita o nome e o preço do produto como parâmetros.
3. **Produto(Integer id, String nome, float preco)**: Um construtor que aceita um ID, nome e preço do produto como parâmetros.

### Métodos Getters e Setters

A classe `Produto` possui métodos getters e setters para acessar e modificar seus atributos. Eles são:

- `getId()`: Retorna o ID do produto.
- `setId(Integer id)`: Define o ID do produto.
- `getNome()`: Retorna o nome do produto.
- `setNome(String nome)`: Define o nome do produto.
- `getPreco()`: Retorna o preço do produto.
- `setPreco(float preco)`: Define o preço do produto.

### Método toString()

A classe `Produto` implementa o método `toString()`, que fornece uma representação em forma de string do objeto, incluindo seus atributos.

---

Claro, vou criar uma documentação em formato Markdown (.md) para a interface `ProdutoRepository`:

---

## Documentação da Interface ProdutoRepository

A interface `ProdutoRepository` é parte de um sistema que utiliza o Spring Data JPA para acessar e manipular dados relacionados a produtos. Esta interface define métodos para realizar operações de consulta e persistência em produtos.

```java
package br.com.daniel.produtosapptest.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.daniel.produtosapptest.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
    
    public Produto findByNome(String nome);
}

```

### Métodos da Interface

A interface `ProdutoRepository` estende a interface `CrudRepository`, que fornece métodos CRUD básicos para a entidade `Produto`. Além disso, a interface `ProdutoRepository` inclui um método personalizado `findByNome(String nome)`. Aqui estão os principais métodos:

#### Método `findByNome(String nome)`

Este método permite buscar um produto pelo nome. Ele retorna uma instância de `Produto` que corresponda ao nome fornecido como parâmetro.

Exemplo de uso:

```java
Produto produto = produtoRepository.findByNome("Produto A");
```

#### Métodos CRUD Padrão

Além do método personalizado `findByNome`, a interface `ProdutoRepository` herda todos os métodos CRUD padrão do Spring Data JPA, como `save`, `findById`, `findAll`, `delete`, entre outros. Esses métodos permitem criar, ler, atualizar e excluir registros de produtos no banco de dados de forma simples e eficaz.

---

Claro, vou criar uma documentação em formato Markdown (.md) para a classe de teste `ProdutoTests`:

---

## Classe de Teste ProdutoTests

A classe `ProdutoTests` é parte de um sistema de testes unitários para a entidade `Produto` em uma aplicação Spring Boot que utiliza o Spring Data JPA. Esta classe contém vários métodos de teste que abordam diferentes aspectos da funcionalidade relacionada a produtos.

```java
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

    @Autowired
    ProdutoRepository produtoRepository;

    @Test
    @Rollback(false)
    public void testCriarProduto(){
        Produto produto = new Produto("teste", 4000);
        Produto criarProduto = produtoRepository.save(produto);

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
      
        String nomeProduto = "TV Samsung HD 72 polegadas + caixa de som";

        Produto produto = new Produto(nomeProduto, 8000);
        produto.setId(1);

        produtoRepository.save(produto);
        
        Produto atualizarProduto = produtoRepository.findByNome(nomeProduto);

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

    }

    @Test
    public void testDeletarProduto(){
        Integer id = 3;

        boolean existeProdutoId = produtoRepository.findById(id).isPresent(); 
        produtoRepository.deleteById(id);
        boolean naoExisteProdutoId = produtoRepository.findById(id).isPresent(); 

        assertTrue(existeProdutoId);
        assertFalse(naoExisteProdutoId);
    }

}

```

### Configuração de Teste

Antes de começar a explicar os métodos de teste, é importante destacar algumas configurações específicas desta classe:

- **`@DataJpaTest`**: Esta anotação indica que a classe de teste se concentra em testes relacionados ao acesso a dados com o uso do Spring Data JPA. Ela configura um ambiente de teste específico para o JPA.

- **`@AutoConfigureTestDatabase(replace = Replace.NONE)`**: Esta anotação especifica que o ambiente de teste não deve substituir o banco de dados por um em memória (como o H2) e, em vez disso, deve usar o banco de dados de produção configurado na aplicação. Isso é útil para garantir que os testes sejam executados no contexto de um banco de dados real, não afetando os dados de produção.

- **`@TestMethodOrder(MethodOrderer.Alphanumeric.class)`**: Esta anotação define a ordem de execução dos métodos de teste com base em ordem alfabética. Isso permite controlar a ordem em que os testes são executados.

### Métodos de Teste

A classe `ProdutoTests` contém vários métodos de teste que cobrem diferentes cenários. Aqui estão os principais métodos:

#### Método `testCriarProduto()`

Este método testa a criação de um novo produto no banco de dados. Ele cria um novo objeto `Produto`, o salva usando o repositório `produtoRepository` e verifica se o objeto salvo não é nulo. Isso garante que a criação de produtos funcione corretamente.

#### Método `testBuscarProdutoPorNome()`

Este método testa a busca de um produto pelo nome. Ele utiliza o método `findByNome` do `produtoRepository` para procurar um produto com um nome específico no banco de dados. O teste verifica se o produto não é nulo e se o nome do produto corresponde ao nome fornecido.

#### Método `testBuscarProdutoPorNomeNoExists()`

Este método testa a busca de um produto pelo nome quando o produto não existe no banco de dados. Ele verifica se o produto retornado é nulo, indicando que o produto não foi encontrado.

#### Método `testAtualizarProduto()`

Este método testa a atualização de um produto existente no banco de dados. Ele cria um novo objeto `Produto` com um nome diferente, atribui o ID de um produto existente a ele e o salva no banco de dados. Em seguida, verifica se o produto foi atualizado corretamente.

#### Método `testListarProduto()`

Este método testa a listagem de todos os produtos no banco de dados. Ele utiliza o método `findAll` do `produtoRepository` para obter todos os produtos e verifica se pelo menos um produto foi retornado. Além disso, exibe informações sobre cada produto no console.

#### Método `testDeletarProduto()`

Este método testa a exclusão de um produto do banco de dados. Ele verifica se um produto com um ID específico existe inicialmente, exclui o produto e, em seguida, verifica se o produto não existe mais no banco de dados.

### Como Usar

Para executar esses testes, você deve executar a classe `ProdutoTests` como um teste JUnit em sua IDE ou usando ferramentas de construção, como o Maven ou Gradle. Certifique-se de que sua aplicação Spring Boot esteja configurada corretamente para o ambiente de teste.

### Considerações Finais

A classe `ProdutoTests` demonstra como criar testes unitários eficazes para funcionalidades relacionadas a produtos em uma aplicação Spring Data JPA. Esses testes ajudam a garantir que a lógica de negócios relacionada a produtos funcione corretamente e permaneça estável à medida que a aplicação é desenvolvida e mantida.

---

## Autor
### **Feito por:** `Daniel Penelva de Andrade`