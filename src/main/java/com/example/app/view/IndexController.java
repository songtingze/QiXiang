package com.example.app.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;

@Component
public class IndexController {

    public GridPane gridpane;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="info"
    private AnchorPane info; // Value injected by FXMLLoader

    @FXML
    private Label time;



    public IndexController() {
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws FileNotFoundException {
        assert info != null : "fx:id=\"info\" was not injected: check your FXML file 'index.fxml'.";
        //事件label
        time.setText("2022.7.25");
        //指标label
        for(int i=0;i<10;i++){
            Label indexname = new Label("indexname"+i);
            Label date = new Label("date"+i);
            ImageView normal = new ImageView("static/gou.png");
            normal.setFitWidth(20);
            normal.setFitHeight(20);
            ImageView abnormal = new ImageView("static/warning.png");
            abnormal.setFitWidth(20);
            abnormal.setFitHeight(20);
            gridpane.add(indexname, 0, i, 1, 1);
            gridpane.add(date,1,i, 1, 1);
            gridpane.add(abnormal, 2, i, 1, 1);
            gridpane.setHgap(15);
            gridpane.setVgap(15);
        }

    }
}

