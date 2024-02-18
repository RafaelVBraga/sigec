package com.rvbraga.sigec.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Pagamento;

import lombok.Data;

@Data
public class PagamentoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private Cliente cliente;
	private String descricao;
	private Integer parcelas;
	private Integer parcelas_vencidas; // Não pagas com data de vencimento atrasado
	private Integer parcelas_pagas;	   // Valor da parcela paga
	private Integer parcelas_a_vencer; // Parcelas não pagas e não vencidas
	private Integer parcelas_restantes;// Parcelas não pagas vencidas ou não
	private Float valor_total;
	private Float valor_pago;
	private Float valor_restante;
	
	public static PagamentoDto generateDto(Pagamento pagamento) {
		PagamentoDto pagDto = new PagamentoDto();
		pagDto.descricao = pagamento.getDescricao();
		pagDto.cliente = pagamento.getCliente();
		pagDto.id = pagamento.getId();
		pagDto.parcelas = pagamento.getParcelas().size();
		pagDto.parcelas_vencidas = (int) pagamento.getParcelas()
		 	.stream()
		 	.filter(e -> e.getDataPagamento()==null)
		 	.filter(e -> e.getDataVencimento().isBefore(LocalDate.now()))
		 	.count();
		pagDto.parcelas_pagas = (int) pagamento.getParcelas()
			 	.stream()
			 	.filter(e -> e.getDataPagamento()!=null)			 	
			 	.count();
		
		pagDto.parcelas_a_vencer = (int) pagamento.getParcelas()
			 	.stream()
			 	.filter(e -> e.getDataPagamento()==null)	
			 	.filter(e -> e.getDataVencimento().isAfter(LocalDate.now()))
			 	.count();	 
		pagDto.valor_total = pagamento.getValor();
		pagDto.parcelas_restantes = pagDto.parcelas - pagDto.parcelas_pagas ;
		
		
		pagDto.valor_pago = pagamento.getParcelas()
			 	.stream()
			 	.filter(e -> e.getDataPagamento()!=null)			
			 	.reduce(Float.valueOf(0),(parcialResult,parcela) -> parcialResult + parcela.getValor(),Float::sum);
		
		pagDto.valor_restante = pagDto.valor_total - pagDto.valor_pago;
		
		return pagDto ;
	}

}
