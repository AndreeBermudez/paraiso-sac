package com.utp.viacosta;

import com.utp.viacosta.util.DatabaseInitializer;
import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DatabaseInitializer.class)
public class RunViaCosta extends Application {

	private ConfigurableApplicationContext springContext;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		springContext = SpringApplication.run(RunViaCosta.class);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Pasar el contexto de Spring a la clase utilitaria
		FxmlCargarUtil.setApplicationContext(springContext);

		// Cargar la pantalla de login utilizando el metodo utilitario
		var fxml = new FXMLLoader(getClass().getResource("/vista/LoginVista.fxml"));
		fxml.setControllerFactory(springContext::getBean);

		// Cargar archivo css
		Scene scene = new Scene(fxml.load());
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

		stage.setTitle("Sistema Pasajes Via Costa");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() {
		springContext.close();
	}
}