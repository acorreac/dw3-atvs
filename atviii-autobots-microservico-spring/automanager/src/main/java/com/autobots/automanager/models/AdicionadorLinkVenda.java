package com.autobots.automanager.models;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.entitades.Venda;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<Venda>{
	
	@Override
	public void adicionarLink(List<Venda> lista) {
		for(Venda venda:lista) {
			long id = venda.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VendaControle.class)
							.encontrarVenda(id))
					.withRel("Visualizar venda de id " + id);
			venda.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Venda objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.encontrarVendas())
				.withRel("Lista de vendas");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkUpdate(Venda objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.atualizarVenda(objeto.getId(), objeto))
				.withRel("Atualizar venda de id " + objeto.getId());
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkDelete(Venda objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.deletarVenda(objeto.getId()))
				.withRel("Excluir venda de id " + objeto.getId());
		objeto.add(linkProprio);
	}
}
