package dam.proyectoempleados;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

//Clase 'main' que extiende de la clase 'Application'
public class Main extends Application {
    //Este es el Stage principal que contendrá toda la aplicación
    private Stage primaryStage;

    //El borderpane donde montamos el root-layout
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        //1) Declaración del Stage principal que contendrá toda la app
        this.primaryStage = primaryStage;
        //Establecemos título de la aplicación
        this.primaryStage.setTitle("Gestión empleados");
        //2) Método que inicia el root-layout
        initRootLayout();
        //3) Mostramos, sobre el root-layout, la  vista de las operaciones de empleados
        showEmployeeOperationsView();
    }

    //Inicia el root-layout.
    public void initRootLayout() {
        try {
            //Primero, carga el root-layout del fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();
            //Segundo, instancia el Scene y asigna el root-layout a éste
            Scene scene = new Scene(rootLayout);
            //Establece el Scene en el Stage
            primaryStage.setScene(scene);
            //Tercero, muestra el root-layout en pantalla, al mostrar el Stage
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Muestra la vista de operaciones de los empleados, dentro del root-layout
    public void showEmployeeOperationsView() {
        try {
            //Primero, carga la vista desde el fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("EmployeeView.fxml"));
            //Establece el AnchorPane donde montamos toda la view
            AnchorPane employeeOperationsView = loader.load();
            //Establece el view en el centro del root-layout
            rootLayout.setCenter(employeeOperationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}