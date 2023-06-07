module StrongholdCrusader {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires org.apache.commons.lang3;
    requires java.desktop;
    requires com.google.common;


    opens org.example.view to javafx.fxml;
    exports org.example.view;
    exports org.example.model.game.envirnmont;
    //exports org.example.model;
    //exports org.example.controller;
}