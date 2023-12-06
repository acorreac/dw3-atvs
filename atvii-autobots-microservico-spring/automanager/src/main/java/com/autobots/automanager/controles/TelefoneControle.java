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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.modelos.TelefoneSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	
	@Autowired
	private TelefoneRepositorio repositorio;
	
	@Autowired
	private TelefoneSelecionador selecionarTelefone;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Autowired
	private AdicionadorLinkTelefone adicionadorLink;

	@GetMapping("/telefone/{id}")
	public ResponseEntity<Telefone> obterTelefone(@PathVariable Long id) {
		List<Telefone> telefones = repositorio.findAll();
		Telefone telefoneSelecionado = selecionarTelefone.selecionar(telefones, id);
		if (telefoneSelecionado == null) {
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(telefoneSelecionado);
			adicionadorLink.adicionarLink(telefoneSelecionado);
			adicionadorLink.adicionarLinkUpdate(telefoneSelecionado);
			adicionadorLink.adicionarLinkDelete(telefoneSelecionado);
			ResponseEntity<Telefone> resposta = new ResponseEntity<Telefone>(telefoneSelecionado, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/telefones")
	public ResponseEntity<List<Telefone>> obterTelefones() {
		List<Telefone> telefones = repositorio.findAll();
		if (telefones.isEmpty()) {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(telefones);
			for(Telefone telefone: telefones) {
				adicionadorLink.adicionarLink(telefone);
				adicionadorLink.adicionarLinkUpdate(telefone);
				adicionadorLink.adicionarLinkDelete(telefone);
			};
			
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Telefone telefone = repositorio.getById(atualizacao.getId());
		if (telefone != null) {
			TelefoneAtualizador atualizador = new TelefoneAtualizador();
			atualizador.atualizar(telefone, atualizacao);
			repositorio.save(telefone);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/deletar-telefone/{numero}")
	public ResponseEntity<?> excluirTelefone(@PathVariable String numero) {
		HttpStatus status = HttpStatus.CONFLICT;
		List<Cliente> clientes = clienteRepositorio.findAll();
		if(clientes != null) {
			for(Cliente cliente : clientes) {
				for(Telefone telefone : cliente.getTelefones()) {
					if(telefone.getNumero() == numero.intern()) {
						cliente.getTelefones().remove(telefone);
						repositorio.deleteById(telefone.getId());
						status = HttpStatus.OK;
						break;
					}
				}
			}
		}else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/deletar-telefones")
	public ResponseEntity<?> excluirTelefones(@RequestBody Cliente cliente) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Cliente clienteSelecionado = clienteRepositorio.getById(cliente.getId());
		if(clienteSelecionado != null) {
			clienteSelecionado.getTelefones().clear();
			clienteRepositorio.save(clienteSelecionado);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
	
}
