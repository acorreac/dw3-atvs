package com.autobots.automanager.modelo;

import java.util.List;

import com.autobots.automanager.entidades.Endereco;


public class EnderecoSelecionador {
	public static Endereco selecionar(List<Endereco> enderecos, Long id) {
		Endereco selecionado = null;
		for (Endereco endereco : enderecos) {
			if (endereco.getId() == id) {
				selecionado = endereco;
			}
		}
		return selecionado;
	}
}
