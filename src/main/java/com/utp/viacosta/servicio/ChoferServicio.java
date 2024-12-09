package com.utp.viacosta.servicio;

import com.utp.viacosta.modelo.ChoferModelo;

import java.util.List;

public interface ChoferServicio {
    List<ChoferModelo> findAll();
    ChoferModelo save(ChoferModelo chofer);
    ChoferModelo findByDni(String dni);
}
