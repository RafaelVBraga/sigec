package com.rvbraga.sigec.utils;

import java.util.Arrays;
import java.util.List;

public class Utilidades {
	private String racas[] = {"Não informado","Preto","Indígena","Pardo","Amarelo","Branco"};
	
	public Utilidades() {
		
	}
	
	public List<String>getRacas() {
		return Arrays.asList(racas);
	}
	
}
