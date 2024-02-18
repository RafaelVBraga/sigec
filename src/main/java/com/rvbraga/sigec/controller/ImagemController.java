package com.rvbraga.sigec.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sigec/imagem")
public class ImagemController {
	@Autowired
	private ResourceLoader resourceLoader;

	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "sigec" + File.separator
			+ "digitalizacoes" + File.separator;
	
	
	@CrossOrigin
	@PostMapping
	public DeferredResult<String> upload(@RequestParam("files[]")MultipartFile[] files) {
		DeferredResult<String> resultado = new DeferredResult<>();	
		Thread processamento = new Thread(()->{
			resultado.setResult(UPLOAD_DIRECTORY);
		});
		processamento.start();
		return resultado;
	}
	
	public Boolean deleteTemp() {
		return true;
	}

}
