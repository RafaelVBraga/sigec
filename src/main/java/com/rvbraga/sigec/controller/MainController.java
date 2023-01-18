package com.rvbraga.sigec.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rvbraga.sigec.dto.PesquisaDto;
import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.service.ClienteService;

@Controller
@RequestMapping("/sigec")
public class MainController {
	@Autowired
	private ClienteService clienteService; 
	
	@GetMapping("/home")
	public String home() {
		return "home.html";
	}
	@GetMapping("/clientes")
	public String cliente(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue ="3")int size) {
		
		PesquisaDto pesquisa = new PesquisaDto();
		model.addAttribute("pesquisa",pesquisa );
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
			
		}catch(Exception e) {
			 model.addAttribute("message", e.getMessage());
		}
		return "cliente.html";
	}
	 
	@PostMapping("/clientes/pesquisa")
	public String pesquisaCliente(Model model,@ModelAttribute("pesquisa") PesquisaDto pesquisa, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue ="3")int size) {
		System.out.println("Entrou no controller");
		System.out.println(pesquisa.getPesquisaCpf());
		System.out.println(pesquisa.getPesquisaNome());
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
			
		}catch(Exception e) {
			 model.addAttribute("message", e.getMessage());
		}
		return "cliente.html";
		
		
	}
}
