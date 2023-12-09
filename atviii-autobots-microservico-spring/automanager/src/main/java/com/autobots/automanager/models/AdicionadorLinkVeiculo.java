package com.autobots.automanager.models;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.entitades.Veiculo;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<Veiculo>{
	@Override
	public void adicionarLink(List<Veiculo> lista) {
		for(Veiculo veiculo:lista) {
			long id = veiculo.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControle.class)
							.encontrarVeiculo(id))
					.withRel("Visualizar veiculo de id " + id);
			veiculo.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Veiculo objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VeiculoControle.class)
						.encontrarVeiculos())
				.withRel("Lista de veiculos");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkUpdate(Veiculo objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VeiculoControle.class)
						.atualizarVeiculo(objeto.getId(), objeto))
				.withRel("Atualizar veiculo de id " + objeto.getId());
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkDelete(Veiculo objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VeiculoControle.class)
						.deletarVeiculo(objeto.getId()))
				.withRel("Excluir veiculo de id " + objeto.getId());
		objeto.add(linkProprio);
	}
}
