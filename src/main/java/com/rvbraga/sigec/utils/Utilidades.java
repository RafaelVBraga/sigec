package com.rvbraga.sigec.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;


@Bean
public class Utilidades {
	private String racas[] = {"Preto","Indígena","Pardo","Amarelo","Branco"};
	
	public Utilidades() {
		
	}
	
	public List<String>getRacas() {
		return Arrays.asList(racas);
	}
	
}
