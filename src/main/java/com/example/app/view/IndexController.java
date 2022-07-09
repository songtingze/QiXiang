package com.example.app.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class IndexController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="precipitation"
    private TextField precipitation; // Value injected by FXMLLoader

    @FXML // fx:id="visibility"
    private TextField visibility; // Value injected by FXMLLoader

    @FXML // fx:id="humidity"
    private TextField humidity; // Value injected by FXMLLoader

    @FXML // fx:id="pressure"
    private TextField pressure; // Value injected by FXMLLoader

    @FXML // fx:id="windDirection"
    private TextField windDirection; // Value injected by FXMLLoader

    @FXML // fx:id="windSpeed"
    private TextField windSpeed; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert precipitation != null : "fx:id=\"precipitation\" was not injected: check your FXML file 'index.fxml'.";
        assert visibility != null : "fx:id=\"visibility\" was not injected: check your FXML file 'index.fxml'.";
        assert humidity != null : "fx:id=\"humidity\" was not injected: check your FXML file 'index.fxml'.";
        assert pressure != null : "fx:id=\"pressure\" was not injected: check your FXML file 'index.fxml'.";
        assert windDirection != null : "fx:id=\"windDirection\" was not injected: check your FXML file 'index.fxml'.";
        assert windSpeed != null : "fx:id=\"windSpeed\" was not injected: check your FXML file 'index.fxml'.";

    }

    public String getPressure(){
        return pressure.getText();
    }
}
