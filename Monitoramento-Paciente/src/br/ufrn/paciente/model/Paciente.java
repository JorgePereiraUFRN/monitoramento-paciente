package br.ufrn.paciente.model;

public class Paciente {

	private Long id;
	private String nome;
	private String diagnostico;
	private String idTopico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getIdTopico() {
		return idTopico;
	}

	public void setIdTopico(String idTopico) {
		this.idTopico = idTopico;
	}

}
