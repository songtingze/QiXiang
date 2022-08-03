package com.example.app.view;

import com.example.app.entity.Message;
import com.example.app.entity.Phone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


@Component
public class ListController {
    public AnchorPane root;
    public TableColumn<Phone,String> phone;
    public TableView list;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="saveBtn"
    public Button saveBtn; // Value injected by FXMLLoader




    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert phone != null : "fx:id=\"phoneTabel\" was not injected: check your FXML file 'edit.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'edit.fxml'.";

        //表格宽度
        phone.prefWidthProperty().bind(list.widthProperty().multiply(1));
        //初始化表格
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }


    public void save() {
        return ;
    }
}
