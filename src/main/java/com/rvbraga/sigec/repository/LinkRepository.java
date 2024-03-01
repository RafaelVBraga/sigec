package com.rvbraga.sigec.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Link;
@Repository
public interface LinkRepository extends JpaRepository<Link, UUID>{

}
