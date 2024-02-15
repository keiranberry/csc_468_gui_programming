module berry_keiran.catcafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens berry_keiran.catcafe to javafx.fxml;
    exports berry_keiran.catcafe;
}