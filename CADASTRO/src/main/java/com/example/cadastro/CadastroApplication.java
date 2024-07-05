package com.example.cadastro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration; // Adicione esta linha
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
@SpringBootApplication
@Configuration(proxyBeanMethods = false)
public class CadastroApplication {

	private List<Produto> produtos = new ArrayList<>();

	@GetMapping
	public ResponseEntity<List<Produto>> listarProdutos() {
		return ResponseEntity.ok(produtos);
	}

	@PostMapping
	public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto) {
		produto.setId(System.currentTimeMillis()); // Gera um ID único
		produtos.add(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> editarProduto(@PathVariable Long id, @RequestBody Produto produto) {
		Optional<Produto> produtoExistente = produtos.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();

		if (produtoExistente.isPresent()) {
			produtoExistente.get().setNome(produto.getNome());
			produtoExistente.get().setValor(produto.getValor());
			return ResponseEntity.ok(produtoExistente.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> obterProduto(@PathVariable Long id) {
		Optional<Produto> produto = produtos.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();

		if (produto.isPresent()) {
			return ResponseEntity.ok(produto.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(CadastroApplication.class, args);
	}
}

class Produto {
	private Long id;
	private String nome;
	private Double valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
