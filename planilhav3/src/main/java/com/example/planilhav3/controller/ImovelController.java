package com.example.planilhav3.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.planilhav3.entity.Imovel;
import com.example.planilhav3.repositories.ImovelRepository;
import com.example.planilhav3.service.ImovelService;


@Controller
public class ImovelController {
	

	private ImovelService service;
	
	@Autowired
	private ImovelRepository repo;	
	
	private DateTimeFormatter dF = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	
	
	@GetMapping("/imoveis/lista")
	public String imoveisLista(Model model) {
		model.addAttribute("listaimoveis", repo.findAll());
		return "imovel/list";
	}
	
	@GetMapping("/imoveis/notificar")
	public String notificar(Model model) {
		List<Imovel> listAll = repo.findAll();
		for (Imovel imovel : listAll) {
			if(imovel.getVencimento().isBefore(imovel.getReajuste())) {
				listAll.remove(imovel);
			}
			model.addAttribute("listaimoveis",listAll);
			return "imovel/notify";
		}
		return "index";
	}
	
	@PostMapping("/imoveis/novo")
	public ResponseEntity<Imovel> salvarImovel(@RequestBody Imovel imovel)  {
		Imovel resultado = repo.save(imovel);	
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(resultado.getId()).toUri();
		 return ResponseEntity.created(uri).body(resultado);
	}
	
	@PostMapping("/register")
	public String registrarImovelHtml(@ModelAttribute Imovel imovel, Model model) {
		model.addAttribute("firstname", imovel.getNome());
//		model.addAttribute("vencimento", imovel.getVencimento().format(dF));		
		model.addAttribute("vencimento", LocalDate.now());
//		model.addAttribute("reajuste", imovel.getReajuste().format(dF));
		model.addAttribute("reajuste", LocalDate.now());
		model.addAttribute("imobiliaria", imovel.getImobiliaria());
		System.out.println(imovel.toString());
		repo.save(imovel);
		return "index";
	}
	
	@GetMapping("/imoveis/deletar/{id}")
	public String deletarImovel(@PathVariable("id")Long id) {
		Optional<Imovel> imovel = repo.findById(id);
		if(imovel.isEmpty()) {
			throw new IllegalArgumentException("Pessoa invalida");
		}
        
		repo.delete(imovel.get());
        
        return "redirect:/imoveis/lista";
	}
	

}
