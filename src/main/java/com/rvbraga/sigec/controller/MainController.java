package com.rvbraga.sigec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
		return "main.html";
	}
	@GetMapping("/cliente")
	public String cliente(Model model, PesquisaDto pesquisa) {
		List<Cliente> listaClientes = clienteService.findAll();		
		model.addAttribute("pesquisa", pesquisa);
		model.addAttribute("lista_clientes",listaClientes);
		return "cliente.html";
	}
	
	@PostMapping("/cliente")
	public String pesquisaCliente(Model model,@RequestBody PesquisaDto pesquisa) {
		List<Cliente> listaClientes;
		if(!pesquisa.getPesquisaNome().isEmpty())
			 listaClientes= clienteService.findByNome(pesquisa.getPesquisaNome());		
		model.addAttribute("pesquisa", pesquisa);
		model.addAttribute("lista_clientes",listaClientes);
		return "cliente.html";
	}
}
