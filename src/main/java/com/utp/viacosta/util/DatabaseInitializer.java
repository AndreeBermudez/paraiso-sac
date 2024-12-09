package com.utp.viacosta.util;

import com.utp.viacosta.modelo.*;
import com.utp.viacosta.dao.*;
import com.utp.viacosta.modelo.enums.TipoAsiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class DatabaseInitializer {
    @Autowired
    private EmpresaDAO empresaDAO;
    @Autowired
    private SedeDAO sedeDAO;
    @Autowired
    private RolDAO rolDAO;
    @Autowired
    private ChoferDAO choferDAO;
    @Autowired
    private EmpleadoDAO empleadoDAO;
    @Autowired
    private BusDAO busDAO;
    @Autowired
    private TipoAsientoDAO tipoAsientoDAO;
    @Autowired
    private RutaDAO rutaDAO;
    @Autowired
    private AsignacionBusRutaDAO asignacionBusRutaDAO;

    @Bean
    public String initDatabase() {
        if (empresaDAO.count() == 0) {
            // 1. Empresa (unchanged)
            EmpresaModelo empresa = new EmpresaModelo(null, "JA PARAISO SAC", "20608378759",
                    "Centro Comercial Plaza Norte, Nro. 6911, Dpto. LB Int. 92A", "LIMA", "LIMA", "PERU", "940326347", null);
            empresaDAO.save(empresa);

            // 2. Sedes (unchanged)
            SedeModelo sede1 = new SedeModelo(null, "Sede Chimbote", "Terminal Terrestre",
                    "Chimbote", "Ancash", "Peru", "987654321", null, empresa);
            SedeModelo sede2 = new SedeModelo(null, "Sede Central", "Centro Comercial Plaza Norte",
                    "Lima", "Lima", "Peru", "987654321", null, empresa);
            sedeDAO.saveAll(Arrays.asList(sede1, sede2));

            // 3. Roles (unchanged)
            RolModelo rolAdmin = new RolModelo(null, "ADMINISTRADOR", null);
            RolModelo rolVentas = new RolModelo(null, "VENTAS", null);
            rolDAO.saveAll(Arrays.asList(rolAdmin, rolVentas));

            // 4. Empleados (added one more)
            EmpleadoModelo empleado1 = new EmpleadoModelo();
            empleado1.setNombre("Juan");
            empleado1.setApellido("Perez");
            empleado1.setDni("12345678");
            empleado1.setCorreo("admin");
            empleado1.setPassword("123");
            empleado1.setTelefono("987654321");
            empleado1.setEstado(true);
            empleado1.setIdSede(sede1.getId());
            empleado1.setRoles(new HashSet<>(Arrays.asList(rolAdmin)));

            EmpleadoModelo empleado2 = new EmpleadoModelo();
            empleado2.setNombre("Maria");
            empleado2.setApellido("Lopez");
            empleado2.setDni("87654321");
            empleado2.setCorreo("maria.lopez@example.com");
            empleado2.setPassword("password456");
            empleado2.setTelefono("123456789");
            empleado2.setEstado(true);
            empleado2.setIdSede(sede1.getId());
            empleado2.setRoles(new HashSet<>(Arrays.asList(rolVentas)));

            EmpleadoModelo empleado3 = new EmpleadoModelo();
            empleado3.setNombre("Andree");
            empleado3.setApellido("Bermudez");
            empleado3.setDni("45678912");
            empleado3.setCorreo("andreebermudez@example.com");
            empleado3.setPassword("123456");
            empleado3.setTelefono("987654321");
            empleado3.setEstado(true);
            empleado3.setIdSede(sede1.getId());
            empleado3.setRoles(new HashSet<>(Arrays.asList(rolVentas)));

            empleadoDAO.saveAll(Arrays.asList(empleado1, empleado2, empleado3));


// 6. Buses creation
            BusModelo bus1 = new BusModelo();
            bus1.setMarca("Volvo");
            bus1.setPlaca("ABC-123");
            bus1.setModelo("Modelo X");
            bus1.setPrimerPiso(12);
            bus1.setSegundoPiso(30);
            bus1.setCapacidadAsientos(42);
            bus1.setCapacidadCarga(1000.0);

            BusModelo bus2 = new BusModelo();
            bus2.setMarca("Mercedes");
            bus2.setPlaca("XYZ-789");
            bus2.setModelo("Modelo Y");
            bus2.setPrimerPiso(12);
            bus2.setSegundoPiso(30);
            bus2.setCapacidadAsientos(42);
            bus2.setCapacidadCarga(1200.0);

            BusModelo bus3 = new BusModelo();
            bus3.setMarca("Scania");
            bus3.setPlaca("DEF-456");
            bus3.setModelo("Modelo Z");
            bus3.setPrimerPiso(12);
            bus3.setSegundoPiso(30);
            bus3.setCapacidadAsientos(42);
            bus3.setCapacidadCarga(1500.0);

            BusModelo bus4 = new BusModelo();
            bus4.setMarca("Volvo");
            bus4.setPlaca("GHI-789");
            bus4.setModelo("Modelo W");
            bus4.setPrimerPiso(12);
            bus4.setSegundoPiso(30);
            bus4.setCapacidadAsientos(42);
            bus4.setCapacidadCarga(1000.0);

            BusModelo bus5 = new BusModelo();
            bus5.setMarca("Mercedes");
            bus5.setPlaca("JKL-012");
            bus5.setModelo("Modelo V");
            bus5.setPrimerPiso(12);
            bus5.setSegundoPiso(30);
            bus5.setCapacidadAsientos(42);
            bus5.setCapacidadCarga(1200.0);

            BusModelo bus6 = new BusModelo();
            bus6.setMarca("Scania");
            bus6.setPlaca("MNO-345");
            bus6.setModelo("Modelo U");
            bus6.setPrimerPiso(12);
            bus6.setSegundoPiso(30);
            bus6.setCapacidadAsientos(42);
            bus6.setCapacidadCarga(1500.0);

// Save all buses
            busDAO.saveAll(Arrays.asList(bus1, bus2, bus3, bus4, bus5, bus6));

            // 7. Choferes (new)
            ChoferModelo chofer1 = new ChoferModelo(null, "Carlos", "Rodriguez", "23456789", "A-III", "943235689", true, null);
            ChoferModelo chofer2 = new ChoferModelo(null, "Pedro", "Martinez", "34567890", "A-III", "943235689", true, null);
            ChoferModelo chofer3 = new ChoferModelo(null, "Luis", "Garcia", "45678901", "A-III", "943235689", true, null);
            choferDAO.saveAll(Arrays.asList(chofer1, chofer2, chofer3));

            // 8. TipoAsiento (unchanged)
            TipoAsientoModelo tipoVIP = new TipoAsientoModelo(null, TipoAsiento.ECONOMICO, "Asiento VIP", 25.0, null);
            TipoAsientoModelo tipoECO = new TipoAsientoModelo(null, TipoAsiento.VIP, "Asiento Económico", 0.0, null);
            tipoAsientoDAO.saveAll(Arrays.asList(tipoVIP, tipoECO));

            // 9. Rutas (modified durations)
            RutaModelo ruta1 = new RutaModelo(null, "Chimbote", "Lima", "10h", null);
            RutaModelo ruta2 = new RutaModelo(null, "Chimbote", "Trujillo", "12h", null);
            RutaModelo ruta3 = new RutaModelo(null, "Chimbote", "Huaraz", "8h", null);
            rutaDAO.saveAll(Arrays.asList(ruta1, ruta2, ruta3));

            // 10. AsignacionBusRuta (multiple assignments per bus)
            LocalDate fecha1 = LocalDate.of(2024, 12, 9);
            LocalDate fecha2 = LocalDate.of(2024, 12, 9);
            LocalDate fecha3 = LocalDate.of(2024, 12, 10);

            createAsignaciones(bus1, ruta1, fecha1, chofer1, Arrays.asList("08:00", "15:00", "20:00", "22:00"));
            createAsignaciones(bus2, ruta2, fecha2, chofer2, Arrays.asList("09:00"));
            createAsignaciones(bus3, ruta3, fecha3, chofer3, Arrays.asList("10:00"));
            return "Database initialized successfully";
        } else {
            return "Database already initialized";
        }
    }


    private void createAsignaciones(BusModelo bus, RutaModelo ruta, LocalDate fecha,
                                    ChoferModelo chofer, List<String> horarios) {
        for (String horario : horarios) {
            String[] parts = horario.split(":");
            LocalTime tiempo = LocalTime.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

            AsignacionBusRutaModelo asig = new AsignacionBusRutaModelo(
                    null,               // idAsignacion
                    bus.getIdBus(),    // idBus (int)
                    ruta.getIdRuta(),  // idRuta (int)
                    fecha,             // fechaSalida
                    tiempo,            // horaSalida
                    30.0,             // precio
                    bus,              // busAsignado
                    ruta,             // rutaAsignada
                    chofer,           // choferAsignado
                    null,             // detalleBoletaModelo
                    null              // estadosFecha
            );
            asignacionBusRutaDAO.save(asig);
        }
    }
}