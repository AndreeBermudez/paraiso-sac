package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.respuesta.ReniecRespuesta;
import com.utp.viacosta.agregates.retrofit.ReniecService;
import com.utp.viacosta.agregates.retrofit.api.ReniecCliente;
import com.utp.viacosta.modelo.ChoferModelo;
import com.utp.viacosta.servicio.ChoferServicio;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ChoferControlador implements Initializable {

    @Autowired
    private ChoferServicio choferServicio;
    @Value("${token.api}")
    private String tokenApi;
    @FXML
    private Button btn_actualizar, btnLimpiar, btn_guardar;
    @FXML
    private TableView<ChoferModelo> tabla_choferes;
    @FXML
    private TableColumn<ChoferModelo, String> columnNombre;
    @FXML
    private TableColumn<ChoferModelo, String> columnApellido;
    @FXML
    private TableColumn<ChoferModelo, String> columnDni;
    @FXML
    private TableColumn<ChoferModelo, String> columnTelefono;
    @FXML
    private TableColumn<ChoferModelo, Void> columnAcciones;
    @FXML
    private TableColumn<ChoferModelo, String> columnLicencia;
    @FXML
    private TextField txt_apellido, txt_telefono, txt_licencia, txt_nombre, txt_dni;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarChoferes();

        // Listener para detectar la selección en la tabla y cargar los datos en los campos
        tabla_choferes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarActualizar();
                btn_actualizar.setVisible(true);
                btnLimpiar.setVisible(true);
                btn_guardar.setVisible(false);
            }
        });
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);
    }


    @FXML
    private void guardarChoferes(ActionEvent event) {
        if (!validarEntradas()) {
            return;
        }
        ChoferModelo choferModelo = new ChoferModelo();
        choferModelo.setDni(txt_dni.getText());
        choferModelo.setNombre(txt_nombre.getText());
        choferModelo.setApellido(txt_apellido.getText());
        choferModelo.setTelefono(txt_telefono.getText());
        choferModelo.setLicenciaConducir(txt_licencia.getText());
        choferServicio.save(choferModelo);
        listarChoferes();
        clear();
    }

    @FXML
    private void listarChoferes() {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnLicencia.setCellValueFactory(new PropertyValueFactory<>("licenciaConducir"));

        columnAcciones.setCellFactory(col -> new TableCell<>() {
            private final Button iconoEstado = new Button();

            {
                iconoEstado.setOnAction(event -> {
                    ChoferModelo choferModelo = getTableView().getItems().get(getIndex());
                    choferModelo.setEstado(!choferModelo.isEstado());
                    choferServicio.save(choferModelo);
                    listarChoferes();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ChoferModelo choferModelo = getTableView().getItems().get(getIndex());
                    iconoEstado.setText(choferModelo.isEstado() ? "Deshabilitar" : "   Habilitar  ");
                    iconoEstado.setStyle(choferModelo.isEstado() ? "-fx-background-color: #1a44ff; -fx-text-fill: white; " : "-fx-background-color: #ff0000;-fx-text-fill: white;");
                    setGraphic(iconoEstado);
                }
            }
        });
        tabla_choferes.getItems().setAll(choferServicio.findAll());
    }

    @FXML
    void act_actualizar(ActionEvent event) {
        ChoferModelo choferSelected = tabla_choferes.getSelectionModel().getSelectedItem();

        // Actualizar los campos del empleado seleccionado
        choferSelected.setDni(txt_dni.getText());
        choferSelected.setNombre(txt_nombre.getText());
        choferSelected.setApellido(txt_apellido.getText());
        choferSelected.setTelefono(txt_telefono.getText());
        choferSelected.setLicenciaConducir(txt_licencia.getText());
        choferServicio.save(choferSelected);
        listarChoferes();
        clear();
        btnLimpiar();// Ocultar los botones de limpiar y actualizar
    }

    //Metodos de apoyo
    @FXML
    void actLimpiar(ActionEvent event) {
        btnLimpiar();
    }

    @FXML
    public void clear() {
        txt_dni.setText("");
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_telefono.setText("");
        txt_licencia.setText("");
    }

    public void btnLimpiar() {
        clear();
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);
        btn_guardar.setVisible(true);
    }

    @FXML
    private void seleccionarActualizar() {
        ChoferModelo choferSelected = tabla_choferes.getSelectionModel().getSelectedItem();
        if (choferSelected != null) {
            txt_dni.setText(choferSelected.getDni());
            txt_nombre.setText(choferSelected.getNombre());
            txt_apellido.setText(choferSelected.getApellido());
            txt_telefono.setText(choferSelected.getTelefono());
            txt_licencia.setText(choferSelected.getLicenciaConducir());
        }
    }


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.show();
    }


    private boolean validarEntradas() {
        if (txt_dni.getText().isEmpty() || txt_nombre.getText().isEmpty() || txt_apellido.getText().isEmpty() || txt_telefono.getText().isEmpty() || txt_licencia.getText().isEmpty()) {
            mostrarAlerta("Por favor, completa todos los campos.");
            return false;
        }
        if (txt_dni.getText().length() != 8) {
            mostrarAlerta("El DNI debe tener 8 dígitos.");
            return false;
        }
        if (txt_telefono.getText().length() != 9) {
            mostrarAlerta("El teléfono debe tener 9 dígitos.");
            return false;
        }
        return true;
    }

    @FXML
    public void handleKeyChofer(Event event) {
        if (txt_dni.getText().length() < 8) {
            txt_nombre.setText("");
            txt_apellido.setText("");
            return;
        }
        if (txt_dni.getText().length() == 8) {
            Retrofit retrofit = ReniecCliente.getClient();
            ReniecService reniecService = retrofit.create(ReniecService.class);
            String token = "Bearer " + tokenApi;
            Call<ReniecRespuesta> call = reniecService.getDatosPersona(token, txt_dni.getText());
            call.enqueue(new Callback<ReniecRespuesta>() {
                @Override
                public void onResponse(Call<ReniecRespuesta> call, Response<ReniecRespuesta> response) {
                    if (response.isSuccessful()) {
                        ReniecRespuesta datosPersona = response.body();
                        if (datosPersona != null) {
                            txt_nombre.setText(datosPersona.getNombres());
                            txt_apellido.setText(datosPersona.getApellidoPaterno() + " " + datosPersona.getApellidoMaterno());
                        }
                    } else {
                        mostrarAlerta("Error en la respuesta: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<ReniecRespuesta> call, Throwable t) {
                    mostrarAlerta("Error en la llamada: " + t.getMessage());
                }
            });
        }
    }

    @Deprecated
    public void guardarEmpleados(ActionEvent actionEvent) {
    }
}
