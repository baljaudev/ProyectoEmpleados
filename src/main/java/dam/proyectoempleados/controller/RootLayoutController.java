package dam.proyectoempleados.controller;

import dam.proyectoempleados.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class RootLayoutController {

    //Instancia de la clase ejecutable para poder unir el controlador con ella
    private Main main;

    //Método llamado desde la clase ejecutable para idenfiticar este controlador
    public void setMain (Main main) {
        this.main = main;
    }

    //Método para salir de la aplicación
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    //Método para mostrar un alert con la información de la aplicación, al hacer clic en 'Help'
    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Información del programa");
        alert.setHeaderText(null);
        alert.setContentText("Programa desarrollado por Diego Rodriguez, Carlos Ramos y Rubén Balboa");
        alert.show();
    }
}