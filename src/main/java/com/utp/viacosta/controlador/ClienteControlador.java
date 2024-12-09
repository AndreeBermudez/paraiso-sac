package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.respuesta.ReniecRespuesta;
import com.utp.viacosta.agregates.retrofit.ReniecService;
import com.utp.viacosta.agregates.retrofit.api.ReniecCliente;
import com.utp.viacosta.modelo.ChoferModelo;
import com.utp.viacosta.modelo.ClienteModelo;
import com.utp.viacosta.servicio.ClienteServicio;
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
public class ClienteControlador implements Initializable {

    @Autowired
    private ClienteServicio clienteServicio;

    //el token se encuentra en properties
    @Value("${token.api}")
    private String tokenApi;

    @FXML
    private Button btn_actualizar,btn_guardar, btnLimpiar;
    @FXML
    private TableColumn<ClienteModelo, String> columnApellido;
    @FXML
    private TableColumn<ClienteModelo, String> columnDni;
    @FXML
    private TableColumn<ClienteModelo, String> columnId;
    @FXML
    private TableColumn<ClienteModelo, String> columnNombre;
    @FXML
    private TableColumn<ClienteModelo, String> columnTelefono;
    @FXML
    private TableView<ClienteModelo> tabla_clientes;
    @FXML
    private TextField txt_apellido;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_dni;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TableColumn<ClienteModelo, String> columnDireccion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarClientes();

        tabla_clientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarCliente();
                btn_guardar.setVisible(false);
                btn_actualizar.setVisible(true);
                btnLimpiar.setVisible(true);
            }
        });
        btn_guardar.setVisible(true);
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);

    }

    @FXML
    void act_save(ActionEvent event) {
        if (!validarEntradas()) {
            return;
        }

        ClienteModelo clienteModelo = new ClienteModelo();
        clienteModelo.setNombre(txt_nombre.getText());
        clienteModelo.setApellido(txt_apellido.getText());
        clienteModelo.setDni(txt_dni.getText());
        clienteModelo.setTelefono(txt_telefono.getText());
        clienteModelo.setDireccion(txt_direccion.getText());

        clienteServicio.save(clienteModelo);
        listarClientes();
        limpiar();
        limpiarStyles();
    }

    private void listarClientes() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        //configurar la columna de acciones


        tabla_clientes.getItems().setAll(clienteServicio.listaClientes());
    }

    @FXML
    void act_actualizar(ActionEvent event) {
        if (!validarEntradas()) {
            return;
        }
        ClienteModelo clienteModelo = tabla_clientes.getSelectionModel().getSelectedItem();
        clienteModelo.setNombre(txt_nombre.getText());
        clienteModelo.setApellido(txt_apellido.getText());
        clienteModelo.setDni(txt_dni.getText());
        clienteModelo.setTelefono(txt_telefono.getText());
        clienteModelo.setDireccion(txt_direccion.getText());

        clienteServicio.actualizarCliente(clienteModelo);
        listarClientes();
        limpiar();
        limpiarStyles();
    }

    @FXML
    public void actLimpiar(ActionEvent event) {
        limpiar();
        btn_guardar.setVisible(true);
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);
    }


    //Metodos de apoyo
    @FXML
    public void limpiar() {
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_dni.setText("");
        txt_telefono.setText("");
        txt_direccion.setText("");
    }

    @FXML
    public void seleccionarCliente() {
        ClienteModelo clienteModelo = tabla_clientes.getSelectionModel().getSelectedItem();
        txt_nombre.setText(clienteModelo.getNombre());
        txt_apellido.setText(clienteModelo.getApellido());
        txt_dni.setText(clienteModelo.getDni());
        txt_direccion.setText(clienteModelo.getDireccion());
        txt_telefono.setText(clienteModelo.getTelefono());
    }



    //Metodos para validar los campos
    private boolean validarNombre() {
        if (txt_nombre.getText().isEmpty()) {
            txt_nombre.setStyle("-fx-border-color: red");
            mostrarAlerta("El nombre no puede estar vacio.");
            return false;
        }
        return true;
    }

    private boolean validarApellido() {
        if (txt_apellido.getText().isEmpty()) {
            txt_apellido.setStyle("-fx-border-color: red");
            mostrarAlerta("El apellido no puede estar vacio.");
            return false;
        }
        return true;
    }

    private boolean validarDni() {
        String dni = txt_dni.getText();
        if (dni.isEmpty() || dni.length() != 8) {
            txt_dni.setStyle("-fx-border-color: red");
            mostrarAlerta("El DNI debe tener 8 digitos.");
            return false;
        }
        return true;
    }

    private boolean validarDireccion() {
        if (txt_direccion.getText().isEmpty()) {
            mostrarAlerta("La direccion no puede estar vacia.");
            return false;
        }
        return true;
    }


    private boolean validarEntradas() {
        return validarDni() && validarNombre() && validarApellido() && validarDireccion();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.show();
    }

    private void limpiarStyles() {
        txt_nombre.setStyle("");
        txt_apellido.setStyle("");
        txt_dni.setStyle("");
        txt_direccion.setStyle("");
        txt_telefono.setStyle("");
    }


    @FXML
    public void actBuscar(Event event) {
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
                        txt_nombre.setText(datosPersona.getNombres());
                        txt_apellido.setText(datosPersona.getApellidoPaterno() + " " + datosPersona.getApellidoMaterno());
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

}
