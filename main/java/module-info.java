module com.example.portal {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
   requires java.sql;
   requires jdk.net;

   opens com.example.portal to javafx.fxml;
    exports com.example.portal;
}