module com.example.eduquiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires java.sql; // Para MariaDB (JDBC)
    requires org.jetbrains.annotations; // JetBrains @NotNull, etc.

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.eduquiz to javafx.fxml;
    exports com.example.eduquiz;
}