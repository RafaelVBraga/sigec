package com.rvbraga.sigec.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rvbraga.sigec.dto.PesquisaDto;
import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.service.ClienteService;
import com.rvbraga.sigec.utils.Utilidades;

@Controller
@RequestMapping("/sigec")
public class MainController {
	@Autowired
	private ClienteService clienteService;

	private Utilidades utilidades = new Utilidades();

	@GetMapping("/home")
	public String home() {
		return "home.html";  
	}
 
	@GetMapping("/clientes")
	public String cliente(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) { 
		 
		System.out.println("page/size:" + page +"/"+size );
		PesquisaDto pesquisa = new PesquisaDto();
		pesquisa.setPaginas(size);
		model.addAttribute("pesquisa", pesquisa); 
		model.addAttribute("texto_pagina", "Página ");
 
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();
			Pageable paging = PageRequest.of(page - 1, size);

			Page<Cliente> pageClientes;
			pageClientes = clienteService.findAll(paging);

			clientes = pageClientes.getContent(); 		

			model.addAttribute("lista_clientes", clientes);
			model.addAttribute("currentPage", pageClientes.getNumber()+1);
			model.addAttribute("totalItems", pageClientes.getTotalElements());
			model.addAttribute("totalPages", pageClientes.getTotalPages());
			model.addAttribute("pageSize", size);
			model.addAttribute("mensagem_tabela", clientes.isEmpty() ? "Dados indisponíveis" : "");

		} catch (Exception e) {
			model.addAttribute("mensagem_tabela", e.getMessage());
			model.addAttribute("mensagem_erro", e.getMessage());
			return "home.html";

		}
		return "cliente.html";
	}
 
	@PostMapping("/clientes/pesquisa")
	public String pesquisaCliente(Model model, @Validated@ModelAttribute("pesquisa") PesquisaDto pesquisa,  Errors errors,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		if (errors.hasErrors()) { 
			model.addAttribute("texto_pagina", "Página ");
			model.addAttribute("lista_clientes", clientes);
	        return "cliente_add_edit.html"; 
	    }
		model.addAttribute("texto_pagina", "Página ");
		if (!pesquisa.getPesquisaNome().isBlank())
			try {

				Pageable paging = PageRequest.of(page - 1, pesquisa.getPaginas());

				Page<Cliente> pageClientes;

				pageClientes = clienteService.findByNome(pesquisa.getPesquisaNome(), paging);
				clientes = pageClientes.getContent();
				model.addAttribute("lista_clientes", clientes);
				model.addAttribute("currentPage", pageClientes.getNumber() + 1);
				model.addAttribute("totalItems", pageClientes.getTotalElements());
				model.addAttribute("totalPages", pageClientes.getTotalPages());
				model.addAttribute("pageSize", size);
				model.addAttribute("mensagem_tabela", clientes.isEmpty() ? "Dados indisponíveis" : "");

			} catch (Exception e) {
				model.addAttribute("mensagem_tabela", e.getMessage());
				model.addAttribute("mensagem_erro", e);
			}
		
		if (!pesquisa.getPesquisaCpf().isBlank()) {
			try {
				clientes.add(clienteService.findByCpf(pesquisa.getPesquisaCpf()));
				model.addAttribute("lista_clientes", clientes);
			}catch(Exception e) {
				model.addAttribute("mensagem_erro", e);
			}
		}
		if(pesquisa.getPesquisaCpf().isBlank()&&pesquisa.getPesquisaNome().isBlank()) {
			Pageable paging = PageRequest.of(page - 1, pesquisa.getPaginas());

			Page<Cliente> pageClientes;
			pageClientes = clienteService.findAll(paging);

			clientes = pageClientes.getContent(); 		

			model.addAttribute("lista_clientes", clientes);
			model.addAttribute("currentPage", pageClientes.getNumber()+1);
			model.addAttribute("totalItems", pageClientes.getTotalElements());
			model.addAttribute("totalPages", pageClientes.getTotalPages());
			model.addAttribute("pageSize", size);
			model.addAttribute("mensagem_tabela", clientes.isEmpty() ? "Dados indisponíveis" : "");
			
		}
		
		return "cliente.html";
	}

	@GetMapping("/cliente/adicionar")
	public String adicionarCliente(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("campo_raca", "Raça:");
		model.addAttribute("racas", utilidades.getRacas());
		model.addAttribute("cliente", cliente);
		return "cliente_add_edit.html";  
	}  

	@PostMapping("/cliente/salvar") 
	public String salvarCliente(Model model, @Validated Cliente cliente, Errors errors, RedirectAttributes attributes) {
		System.out.println(cliente.toString()); 
		cliente.setDataCadastro(LocalDate.now());
		if (errors.hasErrors()) { 
			model.addAttribute("campo_raca", "Raça:");
			model.addAttribute("racas", utilidades.getRacas());
			model.addAttribute("cliente", cliente); 
			model.addAttribute("erros", errors);
	        return "cliente_add_edit.html"; 
	    }
		try {
			Cliente cliente_salvo = clienteService.saveCliente(cliente);
			model.addAttribute("mensagem_sucesso", "Cliente " + cliente_salvo.getId() + " salvo!");
		} catch (Exception e) {
			model.addAttribute("cliente", cliente);
			model.addAttribute("mensagem_erro", "Erro ao salvar cliente: " + e);
			return "cliente_add_edit.html";
		}
		return cliente(model,1,3);
	}
}
