package com.rvbraga.sigec.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import com.rvbraga.sigec.dto.ClienteDto;
import com.rvbraga.sigec.dto.MensagemDto;
import com.rvbraga.sigec.dto.PesquisaDto;
import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Processo;
import com.rvbraga.sigec.service.ClienteService;
import com.rvbraga.sigec.service.ProcessoService;
import com.rvbraga.sigec.utils.Utilidades;

@Controller
@RequestMapping("/sigec")
public class MainController {
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProcessoService processoService;

	private Utilidades utilidades = new Utilidades();

	@GetMapping("/home")
	public String home() {
		return "home.html";  
	}
 
	@GetMapping("/clientes")
	public String cliente(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) { 
		
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
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem(e.getMessage());
			mensagem.setStatus("Falha");
			model.addAttribute("feedback",mensagem);
			
			
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
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Corrija os campos assinalados!");
			mensagem.setStatus("AVISO");
			model.addAttribute("feedback",mensagem);
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
				MensagemDto mensagem = new MensagemDto();
				mensagem.setMensagem(e.getMessage());
				mensagem.setStatus("FALHA");
				model.addAttribute("feedback",mensagem);
			}
		
		if (!pesquisa.getPesquisaCpf().isBlank()) {
			try {
				clientes.add(clienteService.findByCpf(pesquisa.getPesquisaCpf()));
				model.addAttribute("lista_clientes", clientes);
			}catch(Exception e) {
				
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
		model.addAttribute("titulo_pagina", "Cadastro de clientes");		
		model.addAttribute("racas", utilidades.getRacas());
		model.addAttribute("generos", utilidades.getGeneros());
		model.addAttribute("cliente", cliente);
		return "cliente_add_edit.html";  
	}  

	@PostMapping("/cliente/salvar") 
	public String salvarCliente(Model model, @Validated Cliente cliente, Errors errors, RedirectAttributes attributes) {
		
		cliente.setDataCadastro(LocalDate.now());
		if (errors.hasErrors()) { 
			model.addAttribute("titulo_pagina", "Cadastro de clientes");			
			model.addAttribute("racas", utilidades.getRacas());
			model.addAttribute("generos", utilidades.getGeneros());
			model.addAttribute("cliente", cliente); 
			model.addAttribute("erros", errors);
			
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Corrija os campos assinalados");
			mensagem.setStatus("AVISO");
			model.addAttribute("feedback",mensagem);
			
	        return "cliente_add_edit.html"; 
	    }
		try {
			Cliente cliente_salvo = clienteService.saveCliente(cliente);
			model.addAttribute("titulo_pagina", "Cadastro de clientes");
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Cliente " + cliente_salvo.getId() + " salvo!");
			mensagem.setStatus("SUCESSO");
			model.addAttribute("feedback",mensagem);
			//model.addAttribute("mensagem_sucesso", "Cliente " + cliente_salvo.getId() + " salvo!");
		} catch (Exception e) {
			model.addAttribute("titulo_pagina", "Cadastro de clientes");
			model.addAttribute("cliente", cliente);
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem(e.getMessage());
			mensagem.setStatus("FALHA");
			model.addAttribute("feedback",mensagem);
			return "cliente_add_edit.html";
		}
		return cliente(model,1,5);
	}
	 
	@GetMapping("/cliente/editar")  
	public String editarCliente(Model model, @ModelAttribute("id") UUID id) {
		
		ClienteDto cliente = new ClienteDto(clienteService.findById(id));
		
		model.addAttribute("titulo_pagina", "Edição de clientes");
		model.addAttribute("cliente",cliente);		
		model.addAttribute("generos", utilidades.getGeneros());
		model.addAttribute("racas", utilidades.getRacas());
		return "cliente_add_edit.html";
		
	}
	@PostMapping("/cliente/editar")
	public String salvarEdicao(Model model, @Validated ClienteDto cliente, Errors errors, RedirectAttributes attributes) {
		
		
		if (errors.hasErrors()) { 
			model.addAttribute("titulo_pagina", "Cadastro de clientes");
			model.addAttribute("campo_raca", "Raça:");
			model.addAttribute("racas", utilidades.getRacas());
			model.addAttribute("cliente", cliente); 
			model.addAttribute("erros", errors);
	        return "cliente_add_edit.html"; 
	    }
		Cliente cliente_editado = clienteService.saveCliente(clienteService.clienteDto2Cliente(cliente));
		MensagemDto mensagem = new MensagemDto();
		mensagem.setMensagem("Cliente " + cliente_editado.getId() + " editado!");
		mensagem.setStatus("SUCESSO");
		model.addAttribute("feedback",mensagem);
		
		return cliente(model,1,5);
	}
	
	@GetMapping("/cliente/deletar")
	public String deletarCliente(Model model, @ModelAttribute("id") UUID id) {
		
		clienteService.deleteCliente(id);
		MensagemDto mensagem = new MensagemDto();
		mensagem.setMensagem("Cliente deletado!");
		mensagem.setStatus("FALHA");
		model.addAttribute("feedback",mensagem);
		return cliente(model,1,5);
		
	}
	@GetMapping("/cliente/visualizar") 
	public String visualizarCliente(Model model, @ModelAttribute("id") UUID id) {
		
		Cliente cliente = clienteService.findById(id);		
		List<Processo> processos = processoService.findProcessosByCliente(cliente);
		model.addAttribute("cliente",cliente);
		model.addAttribute("processos",processos);
		return "visualizar_cliente.html";
		
	} 
	
	@GetMapping("/resumo")
	public String resumo() {
		return "resumo.html";  
	}
	
	@GetMapping("/pagamentos")
	public String pagamentos() {
		return "pagamentos.html";   
	}
	
	@GetMapping("/processos")
	public String processos() {
		return "processos.html";   
	}
	
	@GetMapping("/agenda")
	public String agenda(Model model,@RequestParam(defaultValue="2023")int ano_atual,@RequestParam(defaultValue="1")int mes_atual ) {
		//ZoneId brazilZoneId = ZoneId.of("America/Sao_Paulo");		
		LocalDate dataAtual = LocalDate.of(ano_atual, mes_atual, 1);
		LocalDate hoje = LocalDate.now();
		LocalDate primeiroDia = LocalDate.of(dataAtual.getYear(), dataAtual.getMonth(), 1);
		ArrayList<Integer> mes = utilidades.fillMonth(primeiroDia.getMonth().length(primeiroDia.isLeapYear()));				
		model.addAttribute("nome_mes", utilidades.translateMonth(dataAtual.getMonth()));
		model.addAttribute("mes", mes);
		model.addAttribute("dias_semana", utilidades.getDiasSemana());
		model.addAttribute("primeiro_dia_semana_do_mes",utilidades.translateDayOfWeek(primeiroDia.getDayOfWeek()));
		model.addAttribute("mes_atual",dataAtual.getMonth().getValue());
		model.addAttribute("ano_atual", dataAtual.getYear()); 
		model.addAttribute("hoje",hoje.getDayOfMonth());
		model.addAttribute("texto_reunioes","Reuniões");
		
		return "agenda.html";  
	}
	

}
