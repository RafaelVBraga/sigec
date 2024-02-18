package com.rvbraga.sigec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.repository.AuthoritiesRepository;
import com.rvbraga.sigec.repository.UsersRepository;
import com.rvbraga.sigec.security.Authorities;
import com.rvbraga.sigec.security.Users;

@Service
public class AuthoritiesService { 
	
	@Autowired
	private AuthoritiesRepository usersRepository;
	
	public Authorities save(Authorities auth) {
		return usersRepository.save(auth);
	}
	
	public List<Authorities> list(){
		return usersRepository.findAll();
	}
	
	public Authorities load(String authority) {
		Authorities auth= usersRepository.findByAuthority(authority);
		return auth;
	}
	

}
