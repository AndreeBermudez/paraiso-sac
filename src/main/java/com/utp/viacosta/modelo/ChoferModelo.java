package com.utp.viacosta.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "choferes")
public class ChoferModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chofer")
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String licenciaConducir;
    private String telefono;
    private boolean estado;
    @OneToMany(mappedBy = "choferAsignado")
    private Set<AsignacionBusRutaModelo> asignaciones = new HashSet<>();

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }

}
