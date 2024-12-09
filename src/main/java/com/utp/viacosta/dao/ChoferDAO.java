package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.ChoferModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoferDAO extends JpaRepository<ChoferModelo, Integer> {
    ChoferModelo findByDni(String dni);
}
