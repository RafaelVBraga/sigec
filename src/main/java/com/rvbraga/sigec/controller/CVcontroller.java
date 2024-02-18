package com.rvbraga.sigec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/sigec")
public class CVcontroller { 
	 
 
	@GetMapping("/euro")
	public String login(Model model) { 
		return "euro_cv.xhtml";
	}

}
