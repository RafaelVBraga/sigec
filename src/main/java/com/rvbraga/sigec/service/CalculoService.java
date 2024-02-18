package com.rvbraga.sigec.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.model.Calculo;

@Service
public class CalculoService {
	@Autowired
	private TabelaSeguroDesempregoService tabelaService;
	public Calculo calcular(Calculo calc) {
		try {
			calc.setValorHoraTrabalhada(calc.getSalarioBase() / calc.getHorasMensais());
			calc.setValorHoraExtra(calc.getValorHoraTrabalhada() + calc.getValorHoraTrabalhada() * 50 / 100);
			calc.setValorHoraNoturna(calc.getValorHoraTrabalhada()+calc.getValorHoraTrabalhada()*20 / 100);
			calc.setValorHoraExtraNoturna(calc.getValorHoraNoturna()+calc.getValorHoraExtra()*50/100 + calc.getValorHoraTrabalhada()*20/100);
			calc.setValorFerias(calc.getSalarioBase()+calc.getSalarioBase()*1/3);
			calc.setValorDecimoTerceiro(calc.getSalarioBase()+calc.getSalarioBase()*calc.getPericulosidade()/100+calc.getSalarioBase()*calc.getInsalubridade()/100);
			if (calc.getDataDemissao() != null)
				calc.setTempoEmpresa(calc.getDataAdmissao().until(calc.getDataDemissao()));
			else
				calc.setTempoEmpresa(calc.getDataAdmissao().until(LocalDate.now()));
			calc.setTempoEmpresaTexto(String.format("%d anos, %d meses, %d dias", calc.getTempoEmpresa().getYears(),
					calc.getTempoEmpresa().getMonths(), calc.getTempoEmpresa().getDays()));
			calc.setValorParcelaSeguro(tabelaService.getParcela(calc.getSalarioBase()));
			calc.setParcelasSeguro(tabelaService.getNumeroParcelas(calc.getTempoEmpresa()));
			calc.setSaldoSalario(null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
				return new Calculo();
		}
		return calc;
	}

}
