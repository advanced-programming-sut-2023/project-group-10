module StrongholdCrusader {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires org.apache.commons.lang3;
    requires java.desktop;
    requires com.google.common;


    opens org.example.view to javafx.fxml;
    exports org.example.view.enums.messages;
    exports org.example.model;
    exports org.example.model.game;
    exports org.example.model.game.envirnmont;
    exports org.example.model.game.buildings.buildingconstants;
    exports org.example.model.game.units.unitconstants;
    opens org.example.model to com.google.gson;
    opens org.example.view.transitions to javafx.fxml;
    opens org.example.connection to com.google.gson;
    opens org.example.model.chat to com.google.gson;
    opens org.example.connection.Handlers to com.google.gson;
    opens org.example.model.lobby to com.google.gson;
    //exports org.example.controller;
}