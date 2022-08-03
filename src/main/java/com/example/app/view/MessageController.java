package com.example.app.view;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class MessageController {
    @FXML
    private AnchorPane indexTable;
//
//    private MultiTableController multiTableController;


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert indexTable != null : "fx:id=\"indexTable\" was not injected: check your FXML file 'indicators.fxml'.";
    }
}
