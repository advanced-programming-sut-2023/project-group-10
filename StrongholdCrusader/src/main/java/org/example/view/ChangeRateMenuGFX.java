package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class ChangeRateMenuGFX extends Application {
    public Label foodRateLabel;
    public Slider foodRateSlider;
    public Label taxRateLabel;
    public Slider taxRateSlider;
    public Label fearRateLabel;
    public Slider fearRateSlider;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL changeRateMenuFXML = ChangeRateMenuGFX.class.getResource("/view/changeRateMenu.fxml");
        assert changeRateMenuFXML != null;
        Pane rootPane = FXMLLoader.load(changeRateMenuFXML);
        Scene gameScene = new Scene(rootPane);
        primaryStage.setScene(gameScene);
    }

    public void initialize() {
        foodRateSlider.valueProperty().addListener(observable -> updateLabelText("food", foodRateLabel, foodRateSlider));
        taxRateSlider.valueProperty().addListener(observable -> updateLabelText("tax", taxRateLabel, taxRateSlider));
        fearRateSlider.valueProperty().addListener(observable -> updateLabelText("fear", fearRateLabel, fearRateSlider));
        updateLabelText("food", foodRateLabel, foodRateSlider);
        updateLabelText("tax", taxRateLabel, taxRateSlider);
        updateLabelText("fear", fearRateLabel, fearRateSlider);
    }

    private void updateLabelText(String name, Label label, Slider slider) {
        label.setText(name + " = " + (int) slider.getValue());
    }

    public void updateRates(MouseEvent mouseEvent) {
        //TODO: save rate changes to government
    }

    public void cancelChanges(MouseEvent mouseEvent) throws Exception {
        new PopularityFactorsMenuGFX().start(SignupMenu2.stage);
    }
}
