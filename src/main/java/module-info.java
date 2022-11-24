module dam.proyectoempleados {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;


    opens dam.proyectoempleados to javafx.fxml;
    exports dam.proyectoempleados;
}