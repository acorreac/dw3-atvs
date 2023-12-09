package com.autobots.automanager.entitades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Mercadoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true)
	private Date validade;
	
	@Column(nullable = true)
	private Date fabricao;
	
	@Column(nullable = true)
	private Date cadastro;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private long quantidade;
	
	@Column(nullable = false)
	private double valor;
	
	@Column()
	private String descricao;
	
	@Column()
	private Boolean original;
}