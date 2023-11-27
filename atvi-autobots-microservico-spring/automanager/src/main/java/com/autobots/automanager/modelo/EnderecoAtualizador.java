package com.autobots.automanager.modelo;

import java.util.List;

import com.autobots.automanager.entidades.Endereco;

public class EnderecoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Endereco endereco, Endereco atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getEstado())) {
				endereco.setEstado(atualizacao.getEstado());
			}
			if (!verificador.verificar(atualizacao.getCidade())) {
				endereco.setCidade(atualizacao.getCidade());
			}
			if (!verificador.verificar(atualizacao.getBairro())) {
				endereco.setBairro(atualizacao.getBairro());
			}
			if (!verificador.verificar(atualizacao.getRua())) {
				endereco.setRua(atualizacao.getRua());
			}
			if (!verificador.verificar(atualizacao.getNumero())) {
				endereco.setNumero(atualizacao.getNumero());
			}
			if (!verificador.verificar(atualizacao.getInformacoesAdicionais())) {
				endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());
			}
		}
	}
	
	public void atualizar(List<Endereco> enderecos, List<Endereco> atualizacoes) {
		for (Endereco atualizacao : atualizacoes) {
			for (Endereco endereco : enderecos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == endereco.getId()) {
						atualizar(endereco, atualizacao);
					}
				}
			}
		}
	}
}