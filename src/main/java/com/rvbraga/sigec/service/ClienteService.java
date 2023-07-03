package com.rvbraga.sigec.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rvbraga.sigec.dto.ClienteDto;
import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.repository.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;

	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "sigec" + File.separator
			+ "digitalizacoes" + File.separator;

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findByNome(String nome, Pageable paging) {
		return clienteRepository.findByNomeContainingIgnoreCase(nome, paging);
	}

	public Page<Cliente> findAll(Pageable paging) {

		return clienteRepository.findAll(paging);
	}

	public Cliente saveCliente(Cliente cliente)  {
		
		cliente.setDataCadastro(LocalDate.now());
		return clienteRepository.save(cliente);
	}

	public Cliente saveDocs(Cliente cliente, MultipartFile[] files) throws IOException{ 
		File path;

		path = new File(UPLOAD_DIRECTORY, cliente.getCpf().toString());
		path.mkdirs();
		Path fileCpfDig = Paths.get(UPLOAD_DIRECTORY, cliente.getCpf().toString(), File.separator + "CPF.png");
		Path fileRgDig = Paths.get(UPLOAD_DIRECTORY, cliente.getCpf().toString(), File.separator + "RG.png");
		Path fileEndDig = Paths.get(UPLOAD_DIRECTORY, cliente.getCpf().toString(), File.separator + "ENDERECO.png");
		
		cliente.setCpfDigital(fileCpfDig.toString());
		cliente.setRgDigital(fileRgDig.toString());
		cliente.setEnderecoDigital(fileEndDig.toString());
		
		Files.write(fileCpfDig, files[0].getBytes());
		Files.write(fileEndDig, files[1].getBytes());
		Files.write(fileRgDig, files[2].getBytes());
		
		return cliente;
	}

	public MultipartFile[] loadDocs(Cliente cliente) {
		MultipartFile[] files = {};
		File path = new File(UPLOAD_DIRECTORY, cliente.getCpf().toString());
		return files;
	}

	public Boolean deleteCliente(Cliente cliente) {
		try {
			clienteRepository.delete(cliente);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Cliente findByCpf(String Cpf) {
		return clienteRepository.findByCpf(Cpf);
	}

	public Cliente findById(UUID id) {
		return clienteRepository.findById(id).get();
	}

	public Cliente clienteDto2Cliente(ClienteDto cDto) {
		Optional<Cliente> cliente = clienteRepository.findById(cDto.getId());
		cliente.get().setCpf(cDto.getCpf());
		cliente.get().setNome(cDto.getNome());
		cliente.get().setRg(cDto.getRg());

		cliente.get().setDataNascimento(cDto.getDataNascimento());
		cliente.get().setTelefone(cDto.getTelefone());

		return cliente.get();

	}

	public void deleteCliente(UUID id) {
		clienteRepository.deleteById(id);
	}

}
