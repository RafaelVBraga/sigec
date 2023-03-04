package com.rvbraga.sigec.utils;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Utilidades {
	private String racas[] = {"Não informado","Preto","Indígena","Pardo","Amarelo","Branco"};
	private String dias_semana[] = {"Domingo","Segunda", "Terca","Quarta","Quinta", "Sexta", "Sabado"};
	private String generos[] = {"Masculino","Feminino","Transgênero"};
	private String tiposProcessos[] = {"Civil", "Penal","Previdenciário","Trabalhista","Tributário"};
	private String statusProcessos[] = {"Inicial","Recurso Ordinário", "Agrave de Instrumento", "Embargos de declaração", "Contestação","Manifestação sobre Contestação", "Recurso Inominado",};
	
	
	public Utilidades() {
		
	}
	
	public List<String>getRacas() {
		return Arrays.asList(racas);
	}
	
	public List<String>getGeneros(){
		return Arrays.asList(generos);
	}
	
	public List<String> getDiasSemana() {
		return Arrays.asList(dias_semana);
	}
	
	public List<String> getTiposProcessos(){
		return Arrays.asList(tiposProcessos).stream().sorted().toList();
	}
	public List<String> getStatusProcessos() {
		return Arrays.asList(statusProcessos);
	}
	
	public String translateMonth(Month month) {
		switch (month) {
		case JANUARY: 	return "Janeiro";
		case FEBRUARY:	return "Fevereiro";
		case MARCH:	return "Março";
		case APRIL: return "Abril";
		case MAY: return "Maio";
		case JUNE: return "Junho";
		case JULY: return "Julho";
		case AUGUST: return "Agosto";
		case SEPTEMBER: return "Setembro";
		case OCTOBER: return "Outubro";
		case NOVEMBER: return "Novembro";
		case DECEMBER: return "Dezembro";
		default:
			throw new IllegalArgumentException("Unexpected value: " + month);
		}
	}
	public ArrayList<Integer> fillMonth(int lenght){
		ArrayList<Integer> mes = new ArrayList<Integer>();
		for(int aux = 2; aux<=lenght;aux++) {
			mes.add(aux);
		}
		return mes;
	}
	public String translateDayOfWeek(DayOfWeek dayOfWeek)  {
		switch (dayOfWeek) {
		case SUNDAY: return dias_semana[0];
		case MONDAY: return dias_semana[1];
		case TUESDAY: return dias_semana[2];
		case WEDNESDAY: return dias_semana[3];
		case THURSDAY: return dias_semana[4];
		case FRIDAY: return dias_semana[5];
		case SATURDAY: return dias_semana[6];
		default:
			throw new IllegalArgumentException("Valor 'Dia da Semana' inválido");
		}
	}
	
}
