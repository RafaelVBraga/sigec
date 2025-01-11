package com.rvbraga.sigec.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.dto.ClienteDto;
import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Documento;
import com.rvbraga.sigec.repository.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;	

	

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findByNome(String nome, Pageable paging) {
		return clienteRepository.findByNomeContainingIgnoreCase(nome, paging);
	}
	public List<Cliente> findByNome(String nome) {
		return clienteRepository.findByNomeContainingIgnoreCase(nome);
	}

	public Page<Cliente> findAll(Pageable paging) {

		return clienteRepository.findAll(paging);
	}

	public Cliente saveCliente(Cliente cliente) {

		cliente.setDataCadastro(LocalDate.now());
		return clienteRepository.save(cliente);
	}
	public Cliente editCliente(Cliente cliente) {
		
		return clienteRepository.saveAndFlush(cliente);
	}

	public Boolean deleteCliente(Cliente cliente) {
		try {
			clienteRepository.delete(cliente);
			return true;
		} catch (Exception e) {
			return false;
		}
	} 
	public Boolean checkExisteCpfCadastrado(Cliente cliente) {
		return clienteRepository.findByCpf(cliente.getCpf())!=null;
	
	}
	public Boolean checkExisteRgCadastrado(Cliente cliente) {
		 return clienteRepository.findByRg(cliente.getRg())!=null; 			
	}

	public List<Cliente> findByCpf(String Cpf) {
		return clienteRepository.findByCpfContaining(Cpf);
	}

	public Cliente findById(UUID id) {
		return clienteRepository.findById(id).get();
	}

	public Cliente clienteDto2Cliente(ClienteDto cDto) {
		Optional<Cliente> cliente = clienteRepository.findById(cDto.getId());
		cliente.get().setCpf(cDto.getCpf());
		
		cliente.get().setNome(cDto.getNome());
		cliente.get().setRg(cDto.getRg());
		
		cliente.get().setProfissao(cDto.getProfissao());
		cliente.get().setDataNascimento(cDto.getDataNascimento());
		cliente.get().setTelefones(cDto.getTelefones());
		cliente.get().setEndereco(cDto.getEndereco());
		
		return cliente.get();

	}
	
		
	public List<Documento> getDocumentos(UUID id){
		return this.findById(id).getDocumentos();
	}

	public void deleteCliente(UUID id) {
		clienteRepository.deleteById(id);
	}
	
/*
	public JasperPrint gerarRelatorio(String tipoRelatorio, UUID idCliente) throws IOException, JRException {
		Map<String, Object> params = new HashMap<>();
		InputStream jasperStream = null;
		
		switch (tipoRelatorio) {
		case "Dec_Hipo":
			jasperStream = resourceLoader.getResource("classpath:reports/Declaracao_hipo_A4.jasper").getInputStream();
			break;
		case "Proc":
			jasperStream = resourceLoader.getResource("classpath:reports/Procuracao_A4.jasper").getInputStream();
			params.put("nomeAdvogado", "Nome_Advogado"); 
			params.put("OAB_Advogado", "OAB_Advogado"); 
			params.put("cpf_Advogado", "cpf_Advogado"); 
			params.put("rg_Advogado", "rg_Advogado"); 			
			break;
		}

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		Cliente cliente = clienteRepository.findById(idCliente).get();

		
		params.put("cpf", cliente.getCpf());
		params.put("logradouro", cliente.getEndereco().getLogradouro());
		params.put("numero", cliente.getEndereco().getNumero());
		params.put("complemento", cliente.getEndereco().getComplemento());
		params.put("bairro", cliente.getEndereco().getBairro());
		params.put("cep", cliente.getEndereco().getCep());

		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		clientes.add(cliente);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,
				new JRBeanCollectionDataSource(clientes, false));
		return jasperPrint;
	}
*/
}
