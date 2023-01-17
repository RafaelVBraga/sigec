package com.rvbraga.sigec.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PesquisaDto {
	
	private String pesquisaNome;
	@Size(min=11, max=11)
	private String pesquisaCpf;

}
