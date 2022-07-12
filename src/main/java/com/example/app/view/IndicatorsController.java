package com.example.app.view;


/**
 * Sample Skeleton for 'indicators.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import com.example.app.entity.Index;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

@Component
public class IndicatorsController {

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
