package com.example.planilhav3.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Imovel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate vencimento;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reajuste;
	private String imobiliaria;
	private String cpfl;
	private String saae;

	@Deprecated
	protected Imovel() {
	}
	
	

	public Imovel(Long id, String nome, LocalDate vencimento, LocalDate reajuste, String imobiliaria, String cpfl, String saae) {
		super();
		this.id = id;
		this.nome = nome;
		this.vencimento = vencimento;
		this.reajuste = reajuste;
		this.imobiliaria = imobiliaria;
		this.cpfl = cpfl;
		this.saae = saae;
	}



	public Imovel(String nome) {
		super();
		this.nome = nome;
	}

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

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public LocalDate getReajuste() {
		return reajuste;
	}

	public void setReajuste(LocalDate reajuste) {
		this.reajuste = reajuste;
	}

	public String getImobiliaria() {
		return imobiliaria;
	}

	public void setImobiliaria(String imobiliaria) {
		this.imobiliaria = imobiliaria;
	}
	
	public String getCpfl() {
		return cpfl;
	}
	
	public void setCpfl(String cpfl) {
		this.cpfl = cpfl;
	}
	
	public String getSaae() {
		return saae;
	}
	
	public void setSaae(String saae) {
		this.saae = saae;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Imovel other = (Imovel) obj;
		return Objects.equals(id, other.id);
	}



	@Override
	public String toString() {
		return "Imovel [id=" + id + ", nome=" + nome + ", vencimento=" + vencimento + ", reajuste=" + reajuste
				+ ", imobiliaria=" + imobiliaria + "]";
	}


	
	

}
