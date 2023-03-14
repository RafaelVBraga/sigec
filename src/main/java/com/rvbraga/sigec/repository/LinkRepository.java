package com.rvbraga.sigec.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvbraga.sigec.model.Link;

public interface LinkRepository extends JpaRepository<Link, UUID>{

}
