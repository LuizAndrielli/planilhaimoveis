package com.example.planilhav3.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.planilhav3.entity.Imovel;
import com.example.planilhav3.repositories.ImovelRepository;
import com.example.planilhav3.service.ImovelService;

@Controller
public class ImovelController {

	private ImovelService service;

	@Autowired
	private ImovelRepository repo;

	private DateTimeFormatter dF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter dF2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	@GetMapping("/")
	public String index(Model model) {
		List<Imovel> listAll = repo.findAll();
		Iterator<Imovel> iterator = listAll.iterator();

		while (iterator.hasNext()) {
			Imovel imovel = iterator.next();
			if (imovel.getVencimento().isBefore(imovel.getReajuste())) {
				iterator.remove();
			}
		}

		model.addAttribute("listaImoveisVencidos", listAll);
		return "/imovel/main";
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
			if (imovel.getVencimento().isBefore(imovel.getReajuste())) {
				listAll.remove(imovel);
			}
			model.addAttribute("listaimoveis", listAll);
			return "imovel/notify";
		}
		return "index";
	}

//	@PostMapping("/imoveis/novo")
//	public ResponseEntity<Imovel> salvarImovel(@RequestBody Imovel imovel)  {
//		Imovel resultado = repo.save(imovel);	
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(resultado.getId()).toUri();
//		 return ResponseEntity.created(uri).body(resultado);
//	}

	@GetMapping("/imoveis/novo")
	public String novaPessoa(@ModelAttribute("imovel") Imovel imovel) {
		return "imovel/form";
	}

	@PostMapping("/register")
	public String registrarImovelHtml(@ModelAttribute Imovel imovel, Model model,
			@RequestParam("reajuste") String reajusteString, @RequestParam("vencimento") String vencimentoString) {
		model.addAttribute("firstname", imovel.getNome());

		if (imovel.getVencimento() != null) {
			model.addAttribute("vencimento", imovel.getVencimento().format(dF));
		}

		if (imovel.getReajuste() != null) {
			model.addAttribute("reajuste", imovel.getReajuste().format(dF));
		}

		if (vencimentoString != null && !vencimentoString.isEmpty()) {
			LocalDate data = LocalDate.parse(vencimentoString, dF);
			model.addAttribute("vencimento", data);
		}

		if (reajusteString != null && !reajusteString.isEmpty()) {
			LocalDate data = LocalDate.parse(reajusteString, dF);
			model.addAttribute("reajuste", data);
		}

		model.addAttribute("imobiliaria", imovel.getImobiliaria());
		repo.save(imovel);
		return "redirect:/imoveis/novo";
	}

	@PostMapping("/imoveis/salvar")
	public String salvarImovel2(@ModelAttribute("imovel") Imovel imovel) {
		repo.save(imovel);
		return "redirect:imovel/list";
	}

	@GetMapping("/imoveis/deletar/{id}")
	public String deletarImovel(@PathVariable("id") Long id) {
		Optional<Imovel> imovel = repo.findById(id);
		if (imovel.isEmpty()) {
			throw new IllegalArgumentException("Pessoa invalida");
		}

		repo.delete(imovel.get());

		return "redirect:/imoveis/lista";
	}

	@GetMapping("/imoveis/alterar/{id}")
	public String alterarImovel(@PathVariable("id") Long id, Model model) {
		Optional<Imovel> imovelOpt = repo.findById(id);
		if (imovelOpt.isEmpty()) {
			throw new IllegalArgumentException("Imovel invalido");
		}
		model.addAttribute("imovel", imovelOpt);
		return "imovel/form";
	}

}
