package com.rvbraga.sigec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvbraga.sigec.security.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities,Long>{
		Authorities findByAuthority(String auth);
}
