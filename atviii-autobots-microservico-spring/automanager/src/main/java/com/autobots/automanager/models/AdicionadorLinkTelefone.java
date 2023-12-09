package com.autobots.automanager.models;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entitades.Telefone;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone>{
	@Override
	public void adicionarLink(List<Telefone> lista) {
		for(Telefone telefone:lista) {
			long id = telefone.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(TelefoneControle.class)
							.encontrarTelefone(id))
					.withRel("Visualizar telefone de id " + id);
			telefone.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Telefone objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.encontrarTelefones())
				.withRel("Lista de telefones");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkUpdate(Telefone objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.atualizarTelefone(objeto.getId(), objeto))
				.withRel("Atualizar telefone de id " + objeto.getId());
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkDelete(Telefone objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.deletarTelefone(objeto.getId()))
				.withRel("Excluir telefone de id " + objeto.getId());
		objeto.add(linkProprio);
	}
}
