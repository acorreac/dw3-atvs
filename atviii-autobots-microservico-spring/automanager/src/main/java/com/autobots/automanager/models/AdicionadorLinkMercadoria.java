package com.autobots.automanager.models;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.entitades.Mercadoria;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<Mercadoria>{
	@Override
	public void adicionarLink(List<Mercadoria> lista) {
		for(Mercadoria mercadoria:lista) {
			long id = mercadoria.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.encontrarMercadoria(id))
					.withRel("Visualizar mercadoria de id " + id);
			mercadoria.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Mercadoria objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.encontrarMercadorias())
				.withRel("Lista de clientes");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkUpdate(Mercadoria objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.atualizarMercadoria(objeto.getId(), objeto))
				.withRel("Atualizar mercadoria de id " + objeto.getId());
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkDelete(Mercadoria objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.deletarMercadoriaEmpresa(objeto.getId()))
				.withRel("Excluir mercadoria de id " + objeto.getId());
		objeto.add(linkProprio);
	}
}
