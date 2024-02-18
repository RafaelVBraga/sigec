package com.rvbraga.sigec.service;

import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.model.TabelaSeguroDesemprego;
import com.rvbraga.sigec.repository.TabelaSeguroDesempregoRepository;

@Service
public class TabelaSeguroDesempregoService {
	@Autowired
	private TabelaSeguroDesempregoRepository tabelaRepository;

	public Float getParcela(Float salario) {
		Float valor = (float) 0.0;
		try {
			if(salario<=2041.39f) { valor=salario*(0.8f); return valor>1412f?valor:1412;};
			if(salario>2041.39&&salario<3042.65f) {
				float diferenca = salario-2041.39f;
				return diferenca*0.5f + 1633.10f;
			}
			if(salario>3042.65f) return 2313.74f;
		
		}catch(Exception e) {
			System.out.println("Erro recuperando tabela Seguro Desemprego!");
		}
		return valor;
	}
	public Float calcula(Float salario) {//falta terminar
		Float valor = (float) 0.0;
		TabelaSeguroDesemprego tabela = tabelaRepository.findAll().get(0);
		try {
			if(salario<=tabela.getPiso()) { valor=salario*(tabela.getFaixas().get(0).getMultiplicador()); return valor>tabela.getSalarioMinimo()?valor:tabela.getSalarioMinimo();};
			if(salario>tabela.getFaixas().get(1).getMinimo()&&salario<tabela.getFaixas().get(1).getMaximo()) {
				float diferenca = salario-tabela.getFaixas().get(1).getMinimo();
				return diferenca*tabela.getFaixas().get(1).getMultiplicador() + tabela.getFaixas().get(1).getDiferenca();
			}
			if(salario>tabela.getFaixas().get(3).getMaximo()) return tabela.getTeto();
		
		}catch(Exception e) {
			System.out.println("Erro recuperando tabela Seguro Desemprego!");
		}
		return valor;
	}
	public Integer getNumeroParcelas(Period tempoEmpresa) {
		if(tempoEmpresa.getYears()<1&&tempoEmpresa.getMonths()>=6) return 3;
		if(tempoEmpresa.getYears()==1) return 4;
		if(tempoEmpresa.getYears()>=2) return 5;
		return 0;
	}
}
