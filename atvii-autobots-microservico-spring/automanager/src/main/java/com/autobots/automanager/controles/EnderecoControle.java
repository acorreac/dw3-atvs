package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class EnderecoControle {
	
	@Autowired
	public EnderecoRepositorio repositorio;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
		
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;
	
	@GetMapping("/endereco/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
		Endereco endereco = repositorio.findById(id).get();
		if (endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(endereco);
			adicionadorLink.adicionarLink(endereco);
			adicionadorLink.adicionarLinkUpdate(endereco);
			adicionadorLink.adicionarLinkDelete(endereco);
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/enderecos")
	public ResponseEntity<List<Endereco>> obterEnderecos() {
		List<Endereco> enderecos = repositorio.findAll();
		if (enderecos.isEmpty()) {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}
		else {
			adicionadorLink.adicionarLink(enderecos);
			for(Endereco endereco: enderecos) {
				adicionadorLink.adicionarLink(endereco);
				adicionadorLink.adicionarLinkUpdate(endereco);
				adicionadorLink.adicionarLinkDelete(endereco);
			};
			
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PutMapping("/endereco/atualizar")
	public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Endereco endereco = repositorio.getById(atualizacao.getId());
		if(endereco != null) {
			EnderecoAtualizador atualizador = new EnderecoAtualizador();
			atualizador.atualizar(endereco, atualizacao);
			repositorio.save(endereco);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarEndereco(@PathVariable Long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<Cliente> clientes = clienteRepositorio.findAll();
		Endereco endereco = repositorio.findById(id).orElse(null);
		if(endereco == null) {
			return new ResponseEntity<>(status);
		}else {
			for(Cliente cliente: clientes) {
				if(cliente.getEndereco() != null && cliente.getEndereco().getId() == id) {
					cliente.setEndereco(null);
					clienteRepositorio.save(cliente);
					status = HttpStatus.OK;
					break;
				}
			}
		}
		return new ResponseEntity<>(status);
	}
	
}
