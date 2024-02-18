package com.rvbraga.sigec.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.rvbraga.sigec.dto.PagamentoDto;
import com.rvbraga.sigec.dto.PesquisaDto;
import com.rvbraga.sigec.dto.ProcessoDto;
import com.rvbraga.sigec.dto.UsersDto;
import com.rvbraga.sigec.model.Calculo;
import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Documento;
import com.rvbraga.sigec.model.Endereco;
import com.rvbraga.sigec.model.Pagamento;
import com.rvbraga.sigec.model.Parcela;
import com.rvbraga.sigec.model.Processo;
import com.rvbraga.sigec.model.Profissao;
import com.rvbraga.sigec.model.Usuario;
import com.rvbraga.sigec.security.Authorities;
import com.rvbraga.sigec.security.Users;
import com.rvbraga.sigec.service.AuthoritiesService;
import com.rvbraga.sigec.service.CalculoService;
import com.rvbraga.sigec.service.ClienteService;
import com.rvbraga.sigec.service.DocumentoService;
import com.rvbraga.sigec.service.EnderecoService;
import com.rvbraga.sigec.service.PagamentoService;
import com.rvbraga.sigec.service.ParcelaService;
import com.rvbraga.sigec.service.ProcessoService;
import com.rvbraga.sigec.service.ProfissaoService;
import com.rvbraga.sigec.service.UsersService;
import com.rvbraga.sigec.utils.Utilidades;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sigec")
public class MainController {
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProcessoService processoService;
	@Autowired
	private EnderecoService enderecoService;
	@Autowired
	private ProfissaoService profissaoService;
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private PagamentoService pagamentoService;
	@Autowired
	private ParcelaService parcelaService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private Utilidades utilidades;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private CalculoService calculoService;

	@GetMapping("/login")
	public String login(Model model) {
		return "login/login.xhtml";
	}

	@GetMapping("/home")
	public String home() {
		return "home/home.xhtml";
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
			model.addAttribute("currentPage", pageClientes.getNumber() + 1);
			model.addAttribute("totalItems", pageClientes.getTotalElements());
			model.addAttribute("totalPages", pageClientes.getTotalPages());
			model.addAttribute("pageSize", size);
			model.addAttribute("mensagem_tabela", clientes.isEmpty() ? "Sem clientes cadastrados!" : "");

		} catch (Exception e) {
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem(e.getMessage());
			mensagem.setStatus("Falha");
			model.addAttribute("feedback", mensagem);

			return "cliente/home.html";

		}
		return "cliente/cliente.xhtml";
	}

	@PostMapping("/clientes/pesquisa")
	public String pesquisaCliente(Model model, @Validated @ModelAttribute("pesquisa") PesquisaDto pesquisa,
			Errors errors, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

		List<Cliente> clientes = new ArrayList<Cliente>();
		if (errors.hasErrors()) {
			model.addAttribute("texto_pagina", "Página ");
			model.addAttribute("lista_clientes", clientes);
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Corrija os campos assinalados!");
			mensagem.setStatus("AVISO");
			model.addAttribute("feedback", mensagem);
			return "cliente/cliente.xhtml";
		}
		model.addAttribute("texto_pagina", "Página ");
		if (!pesquisa.getPesquisaNome().isBlank()) {
			System.out.println("pesquisando por nome");
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
				return "cliente/cliente.xhtml";

			} catch (Exception e) {
				model.addAttribute("mensagem_tabela", e.getMessage());
				MensagemDto mensagem = new MensagemDto();
				mensagem.setMensagem(e.getMessage());
				mensagem.setStatus("FALHA");
				model.addAttribute("feedback", mensagem);
				return "cliente/cliente.xhtml";
			}
		}
		if (!pesquisa.getPesquisaCpf().isBlank()) {
			System.out.println("pesquisando por cpf");

			try {
				clientes.clear();
				clientes = clienteService.findByCpf(pesquisa.getPesquisaCpf());
				model.addAttribute("lista_clientes", clientes);
				return "cliente/cliente.xhtml";
			} catch (Exception e) {

			}
		}
		if (pesquisa.getPesquisaCpf().isBlank() && pesquisa.getPesquisaNome().isBlank()) {
			System.out.println("pesquisa padrão");
			Pageable paging = PageRequest.of(page - 1, pesquisa.getPaginas());

			Page<Cliente> pageClientes;
			pageClientes = clienteService.findAll(paging);

			clientes = pageClientes.getContent();

			model.addAttribute("lista_clientes", clientes);
			model.addAttribute("currentPage", pageClientes.getNumber() + 1);
			model.addAttribute("totalItems", pageClientes.getTotalElements());
			model.addAttribute("totalPages", pageClientes.getTotalPages());
			model.addAttribute("pageSize", size);
			model.addAttribute("mensagem_tabela", clientes.isEmpty() ? "Dados indisponíveis" : "");
			return "cliente/cliente.xhtml";

		}

		return "cliente/cliente.xhtml";
	}

	@GetMapping("/cliente/adicionar")
	public String adicionarCliente(Model model) {

		Cliente cliente = new Cliente();
		Endereco endereco = new Endereco();
		cliente.setEndereco(endereco);
		List<Profissao> profissoes = profissaoService.findAll();
		model.addAttribute("profissoes", profissoes);
		model.addAttribute("titulo_pagina", "Cadastro de clientes");
		model.addAttribute("racas", utilidades.getRacas());
		model.addAttribute("generos", utilidades.getGeneros());
		model.addAttribute("cliente", cliente);

		return "cliente/cliente_add_edit.xhtml";
	}

	@PostMapping("/cliente/salvar")
	public String salvarCliente(Model model, @Validated @ModelAttribute("cliente") Cliente cliente, Errors errors,
			RedirectAttributes attributes) throws IOException {

		if (errors.hasErrors()) {

			List<Profissao> profissoes = profissaoService.findAll();
			model.addAttribute("profissoes", profissoes);
			model.addAttribute("titulo_pagina", "Cadastro de clientes");
			model.addAttribute("racas", utilidades.getRacas());
			model.addAttribute("generos", utilidades.getGeneros());
			model.addAttribute("cliente", cliente);
			model.addAttribute("erros", errors);

			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Corrija os campos assinalados");
			mensagem.setStatus("AVISO");
			model.addAttribute("feedback", mensagem);
			return "cliente/cliente_add_edit.xhtml";
		}

		Profissao profissao = new Profissao();
		profissao.setNome(cliente.getProfissao());
		profissaoService.saveSafety(profissao);

		try {
			Endereco endereco = cliente.getEndereco();
			endereco.setCliente(cliente);
			cliente.setEndereco(endereco);
			Cliente cliente_salvo = clienteService.saveCliente(cliente);

			model.addAttribute("titulo_pagina", "Cadastro de clientes");
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Cliente " + cliente_salvo.getNome() + " salvo!");
			mensagem.setStatus("SUCESSO");
			model.addAttribute("feedback", mensagem);

		} catch (Exception e) {
			model.addAttribute("titulo_pagina", "Cadastro de clientes");
			model.addAttribute("cliente", cliente);
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem(e.getMessage());
			mensagem.setStatus("FALHA");
			model.addAttribute("feedback", mensagem);

			return "cliente/cliente_add_edit.xhtml";
		}
		return cliente(model, 1, 5);
	}

	@GetMapping("/cliente/editar")
	public String editarCliente(Model model, @ModelAttribute("id") UUID id) {
		Cliente cliente = clienteService.findById(id);
		ClienteDto clienteDto = new ClienteDto(cliente);
		Endereco endereco = enderecoService.findByCliente(cliente);
		clienteDto.setEndereco(endereco);
		List<Profissao> profissoes = profissaoService.findAll();
		model.addAttribute("profissoes", profissoes);
		model.addAttribute("titulo_pagina", "Edição de clientes");
		model.addAttribute("cliente", clienteDto);
		model.addAttribute("generos", utilidades.getGeneros());
		model.addAttribute("racas", utilidades.getRacas());
		model.addAttribute("cpfdig", cliente.getCpfDigital());
		model.addAttribute("rgdig", cliente.getRgDigital());
		model.addAttribute("enddig", cliente.getEnderecoDigital());
		return "cliente/cliente_add_edit.xhtml";

	}

	@PostMapping("/cliente/editar")
	public String salvarEdicao(Model model, @Validated @ModelAttribute("cliente") ClienteDto cliente, Errors errors,
			RedirectAttributes attributes) throws IOException {

		if (errors.hasErrors()) {

			model.addAttribute("titulo_pagina", "Cadastro de clientes");
			model.addAttribute("campo_raca", "Raça:");
			model.addAttribute("racas", utilidades.getRacas());
			model.addAttribute("cliente", cliente);
			model.addAttribute("erros", errors);
			return "cliente/cliente_add_edit.xhtml";
		}

		Cliente temp = clienteService.clienteDto2Cliente(cliente);

		Cliente cliente_editado = clienteService.saveCliente(temp);
		Profissao profissao = new Profissao();
		profissao.setNome(cliente_editado.getProfissao());
		profissaoService.saveSafety(profissao);
		MensagemDto mensagem = new MensagemDto();
		mensagem.setMensagem("Cliente " + cliente_editado.getId() + " editado!");
		mensagem.setStatus("SUCESSO");
		model.addAttribute("feedback", mensagem);

		return cliente(model, 1, 5);
	}

	@GetMapping(value = "/cliente/delete")
	public String paginaDeleteCliente(Model model, @ModelAttribute("id") UUID id, final HttpServletRequest req) {

		Cliente cliente = clienteService.findById(id);
		model.addAttribute("cliente", cliente);
		return "cliente/deletar_cliente.xhtml";
	}

	@PostMapping("/cliente/deletar")
	public String deletarCliente(Model model, @ModelAttribute("cliente") Cliente cliente) {

		try {
			Cliente clienteDeletavel = clienteService.findById(cliente.getId());
			clienteService.deleteCliente(clienteDeletavel);		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cliente(model, 1, 5);

	}

	@GetMapping("/cliente/processo")
	public String processosCliente(Model model, @ModelAttribute("id") UUID id) {
		ProcessoDto processo = new ProcessoDto();
		String nomeCliente = clienteService.findById(id).getNome();
		processo.setIdCliente(id);
		model.addAttribute("titulo_pagina", "Cadastro de Processo");
		model.addAttribute("nome_cliente", nomeCliente);
		model.addAttribute("lista_status", utilidades.getStatusProcessos());
		model.addAttribute("lista_tipos", utilidades.getTiposProcessos());
		model.addAttribute("processo", processo);

		return "processo_add_edit.xhtml";

	}

	@GetMapping("/cliente/visualizar")
	public String visualizarCliente(Model model, @ModelAttribute("id") UUID id) {

		Cliente cliente = clienteService.findById(id);
		List<Processo> processos = processoService.findProcessosByCliente(cliente);
		model.addAttribute("cliente", cliente);
		model.addAttribute("processos", processos);
		return "cliente/visualizar_cliente.xhtml";

	}

	@GetMapping("/cliente/hipo")
	public String declaracaoHipo(Model model, @ModelAttribute("id") UUID id) {
		Cliente cliente = clienteService.findById(id);
		Endereco endereco = enderecoService.findByCliente(cliente);
		model.addAttribute("cliente", cliente);
		model.addAttribute("endereco", endereco);
		model.addAttribute("dia", LocalDate.now().getDayOfMonth());
		model.addAttribute("mes", utilidades.translateMonth(LocalDate.now().getMonth()));
		model.addAttribute("ano", LocalDate.now().getYear());

		return "declaracoes/declaracao_hipo.xhtml";
	}

	@GetMapping("/cliente/proc")
	public String procuracao(Model model, @ModelAttribute("id") UUID id) {
		Cliente cliente = clienteService.findById(id);
		Endereco endereco = enderecoService.findByCliente(cliente);
		model.addAttribute("cliente", cliente);
		model.addAttribute("endereco", endereco);
		model.addAttribute("dia", LocalDate.now().getDayOfMonth());
		model.addAttribute("mes", utilidades.translateMonth(LocalDate.now().getMonth()));
		model.addAttribute("ano", LocalDate.now().getYear());

		return "declaracoes/procuracao.xhtml";
	}

	@GetMapping("/cliente/docs")
	public String pageDocumentos(Model model, @ModelAttribute("id") UUID id) {
		Cliente cliente = clienteService.findById(id);
		if (cliente.getDocumentos().isEmpty() || cliente.getDocumentos() == null) {
			List<Documento> documentos = new ArrayList<Documento>();
			cliente.setDocumentos(documentos);
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("dia", LocalDate.now().getDayOfMonth());
		model.addAttribute("mes", utilidades.translateMonth(LocalDate.now().getMonth()));
		model.addAttribute("ano", LocalDate.now().getYear());

		return "documentos/documentos.xhtml";
	}

	@GetMapping("/cliente/doc/novo")
	public String paginaDocumento(Model model, @ModelAttribute("id") UUID id) {
		Documento doc = new Documento();
		model.addAttribute("cliente_id", id);
		model.addAttribute("documento", doc);
		return "documentos/cadastro_doc.xhtml";
	}

	@PostMapping(value = "/cliente/doc/novo", params = { "cliente_id" })
	public String cadastroDocumento(Model model, @Validated @ModelAttribute("documento") Documento documento,
			Errors errors, final HttpServletRequest req) {
		if (errors.hasErrors()) {
			model.addAttribute("documento", documento);
			return "documentos/cadastro_doc.xhtml";
		}
		final UUID id = UUID.fromString(req.getParameter("cliente_id"));
		Cliente cliente = clienteService.findById(id);
		documento.setCliente(cliente);
		try {
			cliente.getDocumentos().add(documento);
		} catch (Exception e) {
			List<Documento> docs = new ArrayList<Documento>();
			cliente.setDocumentos(docs);
			cliente.getDocumentos().add(documento);
		}
		try {
			clienteService.saveCliente(cliente);
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Sucesso ao salvar documentos!");
			mensagem.setStatus("SUCESSO");
			model.addAttribute("msg", mensagem);
		} catch (Exception e) {
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Ocorreu um erro ao salvar documentos:" + e.getMessage());
			mensagem.setStatus("FALHA");
			model.addAttribute("msg", mensagem);
		}

		return pageDocumentos(model, id);
	}

	@GetMapping("/cliente/doc/editar")
	public String paginaEditarDocumento(Model model, @ModelAttribute("id") UUID id) {
		Documento doc = documentoService.findById(id);
		model.addAttribute("cliente_id", doc.getCliente().getId());
		model.addAttribute("documento", doc);
		return "documentos/cadastro_doc.xhtml";
	}

	@PostMapping(value = "/cliente/doc/editar", params = { "cliente_id" })
	public String editarDocumento(Model model, @Validated @ModelAttribute("documento") Documento documentoEditar,
			Errors errors, final HttpServletRequest req) {
		final UUID id = UUID.fromString(req.getParameter("cliente_id"));
		if (errors.hasErrors()) {
			model.addAttribute("cliente_id", id);
			model.addAttribute("documento", documentoEditar);
			return "documentos/cadastro_doc.xhtml";
		}
		Cliente cliente = clienteService.findById(id);
		documentoEditar.setCliente(cliente);
		try {
			documentoService.save(documentoEditar);
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Sucesso ao salvar documentos!");
			mensagem.setStatus("SUCESSO");
			model.addAttribute("msg", mensagem);
		} catch (Exception e) {
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Ocorreu um erro ao salvar documentos:" + e.getMessage());
			mensagem.setStatus("FALHA");
			model.addAttribute("msg", mensagem);
		}

		return pageDocumentos(model, id);
	}

	@GetMapping(value = "/cliente/doc/delete", params = { "cliente_id" })
	public String paginaDeleteDocumento(Model model, @ModelAttribute("id") UUID id, final HttpServletRequest req) {
		final UUID id_cliente = UUID.fromString(req.getParameter("cliente_id"));
		Documento doc = documentoService.findById(id);
		model.addAttribute("cliente_id", id_cliente);
		model.addAttribute("documento", doc);
		return "documentos/deletar_documento.xhtml";
	}

	@PostMapping(value = "/cliente/doc/delete", params = { "cliente_id" })
	public String deletarDocumento(Model model, @ModelAttribute("id") UUID id, final HttpServletRequest req) {
		final UUID id_cliente = UUID.fromString(req.getParameter("cliente_id"));

		try {
			documentoService.deleteEndereco(id);
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Sucesso ao deletar documento!");
			mensagem.setStatus("SUCESSO");
			model.addAttribute("msg", mensagem);
		} catch (Exception e) {
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Ocorreu um erro ao deletar o documento:" + e.getMessage());
			mensagem.setStatus("FALHA");
			model.addAttribute("msg", mensagem);
		}

		return pageDocumentos(model, id_cliente);
	}

	@GetMapping("/cliente/pagamentos")
	public String pagePagamentos(Model model, @ModelAttribute("id") UUID id) {
		Cliente cliente = clienteService.findById(id);
		if (cliente.getPagamentos().isEmpty() || cliente.getPagamentos() == null) {
			List<Pagamento> pagamentos = new ArrayList<Pagamento>();
			cliente.setPagamentos(pagamentos);
		}

		List<PagamentoDto> pagamentosDto = new ArrayList<PagamentoDto>();
		try {
			pagamentosDto = cliente.getPagamentos().stream().map(e -> PagamentoDto.generateDto(e))
					.collect(Collectors.toList());
		} catch (Exception e) {

		}
		model.addAttribute("pagamentos", pagamentosDto);
		model.addAttribute("cliente", cliente);

		return "pagamentos/pagamentos.xhtml";
	}

	@GetMapping("/cliente/pagamento/novo")
	public String paginaCadastroPagamento(Model model, @ModelAttribute("id") UUID id) {
		Pagamento pagamento = new Pagamento();
		model.addAttribute("cliente_id", id);
		model.addAttribute("pagamento", pagamento);
		return "pagamentos/cadastro_pag.xhtml";
	}

	@PostMapping(value = "/cliente/pagamento/novo", params = { "cliente_id" })
	public String CadastroPagamento(Model model, @Validated @ModelAttribute("pagamento") Pagamento pagamento,
			Errors errors, final HttpServletRequest req) {
		final UUID id = UUID.fromString(req.getParameter("cliente_id"));
		if (errors.hasErrors()) {
			model.addAttribute("pagamento", pagamento);
			model.addAttribute("cliente_id", id);
			return "pagamentos/cadastro_pag.xhtml";
		}

		Cliente cliente = clienteService.findById(id);
		pagamento.setCliente(cliente);
		List<Parcela> parcelas = new ArrayList<Parcela>();
		LocalDate data = pagamento.getDataCadastro();
		for (int numParcela = 1; numParcela <= pagamento.getNumeroParcelas(); numParcela++) {
			Parcela parcela = new Parcela();
			parcela.setDataVencimento(data);
			parcela.setValor(pagamento.getValor() / pagamento.getNumeroParcelas());
			parcela.setPagamento(pagamento);
			parcela.setVencida(false);
			parcela.setPaga(false);
			data = data.plusMonths(1);
			parcelas.add(parcela);

		}
		pagamento.setParcelas(parcelas);
		try {
			cliente.getPagamentos().add(pagamento);
		} catch (Exception e) {
			List<Pagamento> pags = new ArrayList<Pagamento>();
			cliente.setPagamentos(pags);
			cliente.getPagamentos().add(pagamento);
		}
		try {
			clienteService.saveCliente(cliente);

			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Sucesso ao salvar pagamento!");
			mensagem.setStatus("SUCESSO");
			model.addAttribute("msg", mensagem);

		} catch (Exception e) {
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Ocorreu um erro ao salvar pagamento:" + e.getMessage());
			mensagem.setStatus("FALHA");
			model.addAttribute("msg", mensagem);
		}

		return pagePagamentos(model, id);

	}

	@GetMapping(value = "/cliente/pagamento/parcelas", params = { "cliente_id" })
	public String pageParcelas(Model model, @ModelAttribute("id") UUID id, final HttpServletRequest req) {
		Pagamento pagamento = pagamentoService.findById(id);
		final UUID cliente_id = UUID.fromString(req.getParameter("cliente_id"));
		for (Parcela parcela : pagamento.getParcelas()) {
			if (parcela.getDataPagamento() != null) {
				parcela.setPaga(true);
				parcela.setVencida(false);
			} else if (parcela.getDataVencimento().isBefore(LocalDate.now())) {
				parcela.setVencida(true);
			}

		}
		model.addAttribute("pagamento", pagamento);
		model.addAttribute("cliente_id", cliente_id);

		return "pagamentos/parcelas.xhtml";
	}

	@GetMapping(value = "/cliente/pagamento/parcela/pagar", params = { "pagamento_id" })
	public String pagarParcela(Model model, @ModelAttribute("parcela") Parcela parcela, final HttpServletRequest req) {

		final UUID pagamento_id = UUID.fromString(req.getParameter("pagamento_id"));
		Pagamento pagamento = pagamentoService.findById(pagamento_id);
		Parcela parcelaSalvar = parcelaService.findById(parcela.getId());
		parcelaSalvar.setDataPagamento(LocalDate.now());
		parcelaSalvar.setPaga(true);
		parcelaSalvar.setVencida(false);
		parcelaService.saveParcela(parcelaSalvar);
		model.addAttribute("pagamento", pagamento);
		model.addAttribute("cliente_id", pagamento.getCliente().getId());

		return "pagamentos/parcelas.xhtml";
	}

	@GetMapping(value = "/cliente/pagamento/parcela/extornar", params = { "pagamento_id" })
	public String extornarParcela(Model model, @ModelAttribute("parcela") Parcela parcela,
			final HttpServletRequest req) {

		final UUID pagamento_id = UUID.fromString(req.getParameter("pagamento_id"));
		Pagamento pagamento = pagamentoService.findById(pagamento_id);
		Parcela parcelaSalvar = parcelaService.findById(parcela.getId());
		parcelaSalvar.setDataPagamento(null);
		parcelaSalvar.setPaga(false);
		if (parcelaSalvar.getDataVencimento().isBefore(LocalDate.now()))
			parcelaSalvar.setVencida(true);
		parcelaService.saveParcela(parcelaSalvar);
		model.addAttribute("pagamento", pagamento);
		model.addAttribute("cliente_id", pagamento.getCliente().getId());

		return "pagamentos/parcelas.xhtml";
	}

	@GetMapping(value = "/cliente/pagamento/parcela/recibo", params = { "pagamento_id" })
	public String reciboParcela(Model model, @ModelAttribute("parcela") Parcela parcela, final HttpServletRequest req) {

		final UUID pagamento_id = UUID.fromString(req.getParameter("pagamento_id"));
		Pagamento pagamento = pagamentoService.findById(pagamento_id);
		Parcela parcelaRecibo = parcelaService.findById(parcela.getId());
		model.addAttribute("pagamento", pagamento);
		model.addAttribute("parcela", parcelaRecibo);
		model.addAttribute("cliente", pagamento.getCliente());
		model.addAttribute("dia", parcelaRecibo.getDataPagamento().getDayOfMonth());
		model.addAttribute("mes", utilidades.translateMonth(parcelaRecibo.getDataPagamento().getMonth()));
		model.addAttribute("ano", parcelaRecibo.getDataPagamento().getYear());

		return "declaracoes/recibo_pagamento.xhtml";
	}

	@GetMapping(value = "/cliente/pagamento/parcela/edicao", params = { "pagamento_id" })
	public String pageEditarParcela(Model model, @ModelAttribute("id") UUID parcela_id, final HttpServletRequest req) {

		final UUID pagamento_id = UUID.fromString(req.getParameter("pagamento_id"));
		Pagamento pagamento = pagamentoService.findById(pagamento_id);
		Parcela parcelaEditar = parcelaService.findById(parcela_id);
		model.addAttribute("parcela", parcelaEditar);
		model.addAttribute("pagamento", pagamento);
		model.addAttribute("cliente_id", pagamento.getCliente().getId());

		return "pagamentos/editar_parcela.xhtml";
	}

	@GetMapping(value = "/cliente/pagamento/parcela/adicionar", params = { "cliente_id" })
	public String pageAdicionarParcela(Model model, @ModelAttribute("id") UUID pagamento_id,
			final HttpServletRequest req) {

		final UUID cliente_id = UUID.fromString(req.getParameter("cliente_id"));
		Pagamento pagamento = pagamentoService.findById(pagamento_id);
		Parcela parcelaAdicionar = new Parcela();
		model.addAttribute("parcela", parcelaAdicionar);
		model.addAttribute("pagamento", pagamento);
		model.addAttribute("cliente_id", cliente_id);

		return "pagamentos/editar_parcela.xhtml";
	}

	@PostMapping(value = "/cliente/pagamento/parcela/adicionar", params = { "pagamento_id" })
	public String adicionarParcela(Model model, @ModelAttribute("parcela") Parcela parcela, Errors errors,
			final HttpServletRequest req) {
		try {
			final UUID pagamento_id = UUID.fromString(req.getParameter("pagamento_id"));
			Pagamento pagamento = pagamentoService.findById(pagamento_id);
			if (errors.hasErrors()) {
				model.addAttribute("pagamento", pagamento);
				model.addAttribute("parcela", parcela);
				model.addAttribute("cliente_id", pagamento.getCliente().getId());
				return "pagamentos/editar_parcela.xhtml";
			}
			System.out.println(parcela);
			List<Parcela> parcelas = pagamento.getParcelas();
			parcela.setPagamento(pagamento);
			parcelas.add(parcela);
			pagamento.setParcelas(parcelas);

			pagamentoService.savePagamento(pagamento);
			model.addAttribute("pagamento", pagamento);
			model.addAttribute("cliente_id", pagamento.getCliente().getId());

			return "pagamentos/parcelas.xhtml";
		} catch (Exception e) {
			model.addAttribute("erro", e);
			model.addAttribute("operacao", "Cliente/Pagamento/Adicionar");
			model.addAttribute("recurso", "Parcela");
			return "sistema/error.xhtml";
		}
	}

	@PostMapping(value = "/cliente/pagamento/parcela/editar", params = { "pagamento_id" })
	public String editarParcela(Model model, @ModelAttribute("parcela") Parcela parcela, Errors errors,
			final HttpServletRequest req) {

		final UUID pagamento_id = UUID.fromString(req.getParameter("pagamento_id"));
		Pagamento pagamento = pagamentoService.findById(pagamento_id);
		if (errors.hasErrors()) {
			model.addAttribute("pagamento", pagamento);
			model.addAttribute("parcela", parcela);
			model.addAttribute("cliente_id", pagamento.getCliente().getId());
			return "pagamentos/editar_parcela.xhtml";
		}

		parcela.setPagamento(pagamento);
		parcelaService.saveParcela(parcela);
		model.addAttribute("pagamento", pagamento);
		model.addAttribute("cliente_id", pagamento.getCliente().getId());

		return "pagamentos/parcelas.xhtml";
	}

	@GetMapping("/financeiro")
	public String pagamentos(Model model) {
		return "financeiro/financeiro.xhtml";
	}

	@GetMapping("/gerencial")
	public String gerencial(Model model) {
		List<Users> users = usersService.list();
//		users = users.stream().filter(user -> user.get)// tentar tirar os usuarios desabilitados
		model.addAttribute("usuarios", users);
		return "gerencial/gerencial.xhtml";
	}

	@GetMapping("/gerencial/usuario/novo")
	public String pageAdicionarUsuarios(Model model) {
		model.addAttribute("usuario", new UsersDto());
		return "gerencial/adicionar_editar_usuarios.xhtml";
	}

	@PostMapping("/gerencial/usuario/novo")
	public String adicionarUsuarios(Model model, @ModelAttribute("usuario") UsersDto newUser, Errors error) {

		if (error.hasErrors()) {
			model.addAttribute("usuario", newUser);
			return "gerencial/adicionar_editar_usuarios.xhtml";
		}

		Users userToSave = new Users();
		userToSave.setUsername(newUser.getUsername());
		userToSave.setPassword(encoder.encode(newUser.getPassword()));
		userToSave.setAccountNonExpired(true);
		userToSave.setAccountNonLocked(true);
		userToSave.setCredentialsNonExpired(true);
		userToSave.setEnabled(true);
		Set<Authorities> authorities = new HashSet<Authorities>();
		if (newUser.getCargoGerencial())
			authorities.add(authoritiesService.load("ROLE_ADMIN"));
		authorities.add(authoritiesService.load("ROLE_USER"));
		userToSave.setAuthorities(authorities);
		usersService.save(userToSave);
		return gerencial(model);
	}
	@GetMapping("/gerencial/usuario/editar")
	public String pageEditarUsuarios(Model model,@ModelAttribute("id") Long id) {
		Users usuario =  usersService.findById(id);
		UsersDto usuarioDto = new UsersDto(usuario);
		model.addAttribute("usuario",usuarioDto);
		return "gerencial/adicionar_editar_usuarios.xhtml";
	}
	@PostMapping("/gerencial/usuario/novo")
	public String adicionarUsuarios(Model model, @ModelAttribute("usuario") UsersDto newUser, Errors error) {

		if (error.hasErrors()) {
			model.addAttribute("usuario", newUser);
			return "gerencial/adicionar_editar_usuarios.xhtml";
		}

		Users userToSave = new Users();
		userToSave.setUsername(newUser.getUsername());
		userToSave.setPassword(encoder.encode(newUser.getPassword()));
		userToSave.setAccountNonExpired(true);
		userToSave.setAccountNonLocked(true);
		userToSave.setCredentialsNonExpired(true);
		userToSave.setEnabled(true);
		Set<Authorities> authorities = new HashSet<Authorities>();
		if (newUser.getCargoGerencial())
			authorities.add(authoritiesService.load("ROLE_ADMIN"));
		authorities.add(authoritiesService.load("ROLE_USER"));
		userToSave.setAuthorities(authorities);
		usersService.save(userToSave);
		return gerencial(model);
	}

	@GetMapping("/gerencial/usuario/delete")
	public String deletarUsuario(Model model, @ModelAttribute("id") Long id) {
		Users user = usersService.findById(id);
		user.setAuthorities(null);
		usersService.save(user);
		usersService.delete(user.getId());
		return gerencial(model);
	}

	@GetMapping("/usuario/configuracao")
	public String pageConfigUsuario(Model model, Principal logado) {
		String username = logado.getName();
		Users user = usersService.load(username);
		model.addAttribute("usuario", user);
		return "usuario/usuario_configuracao.xhtml";
	}

	@PostMapping("/usuario/configuracao")
	public String configUsuario(Model model, @ModelAttribute("usuario") Users user, Principal logado) {
		String username = logado.getName();
		Users userLogado = usersService.load(username);
		userLogado.setPassword(encoder.encode(user.getPassword()));
		usersService.save(userLogado);
		return home();
	}

	@GetMapping("/agenda")
	public String agenda(Model model, @RequestParam(defaultValue = "0") int ano_atual,
			@RequestParam(defaultValue = "0") int mes_atual) {
		// ZoneId brazilZoneId = ZoneId.of("America/Sao_Paulo");

		LocalDate dataAtual = (ano_atual == 0 || mes_atual == 0) ? LocalDate.now()
				: LocalDate.of(ano_atual, mes_atual, 1);
		LocalDate hoje = LocalDate.now();

		LocalDate primeiroDia = LocalDate.of(dataAtual.getYear(), dataAtual.getMonth(), 1);
		ArrayList<Integer> mes = utilidades.fillMonth(primeiroDia.getMonth().length(primeiroDia.isLeapYear()));
		model.addAttribute("nome_mes", utilidades.translateMonth(dataAtual.getMonth()));
		model.addAttribute("mes", mes);
		model.addAttribute("dias_semana", utilidades.getDiasSemana());
		model.addAttribute("primeiro_dia_semana_do_mes", utilidades.translateDayOfWeek(primeiroDia.getDayOfWeek()));
		model.addAttribute("mes_atual", dataAtual.getMonth().getValue());
		model.addAttribute("ano_atual", dataAtual.getYear());
		model.addAttribute("hoje", hoje.getDayOfMonth());
		model.addAttribute("texto_reunioes", "Reuniões");

		return "agenda/agenda.xhtml";
	}

	@GetMapping("/calculadora")
	public String pageCalculadora(Model model, Calculo calc) {
		model.addAttribute("calculo", calc);
		return "calculadora/calculadora.xhtml";
	}

	@PostMapping("/calculadora")
	public String playCalculadora(Model model, @ModelAttribute("calculo") @Validated Calculo calc, Errors errors) {
		if (errors.hasErrors()) {
			return pageCalculadora(model, calc);
		}		
		calc = calculoService.calcular(calc);
		model.addAttribute("calculo", calc);
		return "calculadora/calculadora.xhtml";
	}
	

}
