package com.rvbraga.sigec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.security.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
	Optional<Users> findByUsername(String username); 
	List<Users> findAll();	
	
}
