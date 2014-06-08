package br.ufrn.paciente.model;

import java.util.Calendar;

public class Evento {

	private Long id;
	private String descricao;
	private Calendar data;
	private Paciente paciente;
	
	public Evento() {
		
	}
	
	public Evento(String descricao, Calendar data, Paciente paciente) {
		this.descricao = descricao;
		this.data = data;
		this.paciente = paciente;
		
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}
