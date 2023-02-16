package com.rvbraga.sigec.utils;

import java.util.Scanner;

public class testes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);

		//System.out.println("Digite um número:");
       // int num = scan.nextInt();
       /*************************************************************/
		/*
        String listaString = String.valueOf(num);
        char[] ch = listaString.toCharArray();
        
        Integer sum = 0;
        Integer mult = 1;
        
        
        
        for(char c : ch) {
        	String s = String.valueOf(c);
        	sum = sum + Integer.valueOf(s);
        	mult = mult* Integer.valueOf(s);
        }
        
        System.out.println("Dif: "+ (mult-sum));*/
        /*************************************************************/
        
        int R = scan.nextInt();
        int V = Integer.MIN_VALUE;
        while(V<=R) {
        	V = scan.nextInt();
        }
        
        int sum = 0;
        int next = R+1;
        int count = 0;
        
        while(sum <= V) {
        	sum += (V<0)? (R - next):(R + next);
        	if(next < 0) next--; else next++;
        	count++;
        }
        
        System.out.println(count);
        
        scan.close();
	}

}
