package com.autobots.automanager.controles;

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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.modelos.DocumentoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

import java.util.List;

@RestController
public class DocumentoControle {
	
	@Autowired
	private DocumentoRepositorio repositorio;
	
	@Autowired
	private DocumentoSelecionador selecionadorDocumento;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;


	@GetMapping("/documento/{id}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable Long id) {
		List<Documento> documentos = repositorio.findAll();
		Documento documento = selecionadorDocumento.selecionar(documentos, id);
		if(documento == null) {
			ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}
		else {
			adicionadorLink.adicionarLink(documentos);
			adicionadorLink.adicionarLinkDelete(documento);
			adicionadorLink.adicionarLinkUpdate(documento);
			ResponseEntity<Documento> resposta = new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos() {
		List<Documento> documentos = repositorio.findAll();
		if (documentos.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(documentos);
			for(Documento documento: documentos) {
				adicionadorLink.adicionarLinkUpdate(documento);
				adicionadorLink.adicionarLinkDelete(documento);				
			};
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarDocumento(@RequestBody Documento atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Documento documento = repositorio.getById(atualizacao.getId());
		if (documento != null) {
			DocumentoAtualizador atualizador = new DocumentoAtualizador();
			atualizador.atualizar(documento, atualizacao);
			repositorio.save(documento);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	
	@DeleteMapping("/deletar-documento/{numero}")
	public ResponseEntity<?> excluirDocumento(@PathVariable String numero) {
		HttpStatus status = HttpStatus.CONFLICT;
		List<Cliente> clientes = clienteRepositorio.findAll();
		if(clientes != null) {
			for(Cliente cliente : clientes) {
				for(Documento documento : cliente.getDocumentos()) {
					if(documento.getNumero() == numero.intern()) {
						cliente.getDocumentos().remove(documento);
						repositorio.deleteById(documento.getId());
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
	
	@DeleteMapping("/deletar-documentos")
	public ResponseEntity<?> excluirDocumentos(@RequestBody Cliente cliente) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Cliente clienteSelecionado = clienteRepositorio.getById(cliente.getId());
		if(clienteSelecionado != null) {
			clienteSelecionado.getDocumentos().clear();
			clienteRepositorio.save(clienteSelecionado);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
	
}
