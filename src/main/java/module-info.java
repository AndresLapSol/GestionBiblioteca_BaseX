module org.example.gestionbiblioteca_basex {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Añade esto para trabajar con ventanas y escenas
    requires java.sql;
    requires java.desktop;
    requires basex;

    // Abre el paquete principal a javafx.fxml para permitir el acceso a los recursos (FXML)
    opens org.example.gestionbiblioteca_basex to javafx.fxml;

    // Exporta el paquete principal y el paquete de controladores
    exports org.example.gestionbiblioteca_basex;
    exports org.example.gestionbiblioteca_basex.Controllers;

    // Abre el paquete de controladores a javafx.fxml para permitir el acceso reflexivo
    opens org.example.gestionbiblioteca_basex.Controllers to javafx.fxml;

}