package com.example.planilhav3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.planilhav3.entity.Imovel;
import com.example.planilhav3.repositories.ImovelRepository;

@Component
@Transactional
public class PopulacaoInicial implements CommandLineRunner {

	@Autowired
	private ImovelRepository repo;
	
	DateTimeFormatter dF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public void run(String... args) throws Exception {

		
		Imovel i1 = new Imovel(null, "TesteConst1", LocalDate.parse("24/07/2023", dF), LocalDate.parse("24/07/2024", dF),
				"Imobiliaria Construtor", null, null);
		Imovel i2 = new Imovel(null, "TesteVencido", LocalDate.parse("21/07/2023", dF), LocalDate.parse("21/07/2022", dF), "Const", null, null);
		repo.save(i1);
		repo.save(i2);

	}

}
