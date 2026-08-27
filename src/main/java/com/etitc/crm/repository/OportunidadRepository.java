package com.etitc.crm.repository;

import com.etitc.crm.entity.Oportunidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OportunidadRepository extends JpaRepository<Oportunidad, Long> {
}