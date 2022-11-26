module dam.proyectoempleados {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;


    opens dam.proyectoempleados to javafx.fxml;
    exports dam.proyectoempleados;
    exports dam.proyectoempleados.controller to javafx.fxml;
    exports dam.proyectoempleados.util;
    exports dam.proyectoempleados.model;
    opens dam.proyectoempleados.model to javafx.fxml;
    opens dam.proyectoempleados.controller to javafx.fxml;
}