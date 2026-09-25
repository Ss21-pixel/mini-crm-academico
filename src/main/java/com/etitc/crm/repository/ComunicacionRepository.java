package com.etitc.crm.repository;

import com.etitc.crm.entity.Comunicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComunicacionRepository extends JpaRepository<Comunicacion, Long> {
}