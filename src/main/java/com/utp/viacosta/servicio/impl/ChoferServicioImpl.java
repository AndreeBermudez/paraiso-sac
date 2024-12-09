package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.ChoferDAO;
import com.utp.viacosta.modelo.ChoferModelo;
import com.utp.viacosta.modelo.EmpleadoModelo;
import com.utp.viacosta.servicio.ChoferServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChoferServicioImpl implements ChoferServicio {
    @Autowired
    private ChoferDAO choferDAO;

    @Override
    public List<ChoferModelo> findAll() {
        return choferDAO.findAll();
    }

    @Override
    public ChoferModelo save(ChoferModelo chofer) {
        return choferDAO.save(chofer);
    }

    @Override
    public ChoferModelo findByDni(String dni) {
        return choferDAO.findByDni(dni);
    }
}
