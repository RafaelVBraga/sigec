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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
			model.addAttribute("mensagem_tabela", clientes.isEmpty()?"Dados indisponíveis":"Tabela carregada!");
			
		}catch(Exception e) {
			 model.addAttribute("mensagem_tabela", e.getMessage());
			 model.addAttribute("mensagem_erro", e.getMessage());
			 
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
			model.addAttribute("mensagem_tabela", clientes.isEmpty()?"Dados indisponíveis":"Tabela carregada!");
			
		}catch(Exception e) {
			 model.addAttribute("mensagem_tabela", e.getMessage());
			 model.addAttribute("mensagem_erro", e);
		}
		return "cliente.html";	
	} 
	
	@GetMapping("/cliente/adicionar") 
	public String adicionarCliente(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("campo_raca", "Raça:");
		model.addAttribute("racas",utilidades.getRacas());
		model.addAttribute("cliente", cliente);
		return "cliente_add_edit.html";
	}
	@PostMapping("/")
	public String salvarCliente(Model model, @ModelAttribute("cliente") Cliente cliente) {
		try {
		Cliente cliente_salvo =  clienteService.saveCliente(cliente);
		model.addAttribute("mensagem_sucesso","Cliente "+cliente_salvo.getId()+" salvo!");
		}catch(Exception e) {
			model.addAttribute("mensagem_erro","Erro ao salvar cliente: "+e);
		}
		return "redirect:/clientes";
	}
}
