package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.round;

public class Controller implements Initializable {
    @FXML
    private Slider slider;
    @FXML
    private Button btnZoomOut;
    @FXML
    private Button btnZoomIn;

    private double originalMinSlider;
    private double originalMaxSlider;

    private double multiplicator;
    private double actualValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        multiplicator = 1;
        actualValue = 0;
        originalMinSlider = slider.getMin();
        originalMaxSlider = slider.getMax();
        btnZoomOut.setDisable(true);
        System.out.println("Original min slider value: " + originalMinSlider);
        System.out.println("Original max slider value: " + originalMaxSlider);
    }

    public void onActionZoomIn(ActionEvent actionEvent) {
        if (multiplicator < (originalMaxSlider / 2)) {
            actualValue = slider.getValue();
            multiplicator *= 2;
            btnZoomOut.setDisable(false);
            btnZoomIn.setDisable(false);
        } else {
            btnZoomOut.setDisable(false);
            btnZoomIn.setDisable(true);
        }
        zoom();
    }

    public void onActionZoomOut(ActionEvent actionEvent) {
        if (multiplicator > 2) {
            actualValue = slider.getValue();
            multiplicator /= 2;
            btnZoomOut.setDisable(false);
            btnZoomIn.setDisable(false);
        } else {
            multiplicator = 1;
            btnZoomOut.setDisable(true);
            btnZoomIn.setDisable(false);
        }
        zoom();
    }

    public void zoom() {
        double distance = round((originalMaxSlider - originalMinSlider) / multiplicator);
        double actualMinSlider = round(actualValue - distance / 2);
        double actualMaxSlider = round(actualValue + distance / 2);
        if (distance >= 1) {
            if (actualMinSlider < 0) {
                actualMinSlider = 0;
                actualMaxSlider = distance;
            }
            if (actualMaxSlider > originalMaxSlider) {
                actualMinSlider = originalMaxSlider - distance;
                actualMaxSlider = originalMinSlider;
            }
            slider.setMin(actualMinSlider);
            slider.setMax(actualMaxSlider);
            slider.setMajorTickUnit(distance/10);
            slider.snapToTicksProperty();
        }
    }
}
