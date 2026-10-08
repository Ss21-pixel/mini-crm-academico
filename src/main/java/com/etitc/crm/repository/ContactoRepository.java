package com.etitc.crm.repository;

import com.etitc.crm.modelo.Contacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {

    Optional<Contacto> findByEmail(String email);

    Page<Contacto> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String nombre,
            String apellido,
            String email,
            Pageable pageable
    );
}
