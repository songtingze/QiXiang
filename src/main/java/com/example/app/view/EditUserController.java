package com.example.app.view;

import com.example.app.entity.State;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EditUserController {

    public AnchorPane root;
    public Label notice;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cancelBtn"
    public Button cancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="saveBtn"
    public Button saveBtn; // Value injected by FXMLLoader

    @FXML
    public TextField stateAddress; // Value injected by FXMLLoader

    @FXML
    public TextField stateName; // Value injected by FXMLLoader

    @FXML
    public TextField userName; // Value injected by FXMLLoader

    @FXML
    public TextField userPsw; // Value injected by FXMLLoader
    public String seq;



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'edit.fxml'.";
        assert stateAddress != null : "fx:id=\"indexName\" was not injected: check your FXML file 'edit.fxml'.";
        assert stateName != null : "fx:id=\"indexStatus\" was not injected: check your FXML file 'edit.fxml'.";
        assert userName != null : "fx:id=\"indexCode\" was not injected: check your FXML file 'edit.fxml'.";
        assert userPsw != null : "fx:id=\"indexData\" was not injected: check your FXML file 'edit.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'edit.fxml'.";

    }

    public State save(){
        String stateAddress = this.stateAddress.getText();
        String stateName = this.stateName.getText();
        String userName = this.userName.getText();
        String userPsw = this.userPsw.getText();
        State state = new State(false,seq,stateAddress,stateName,userName,userPsw);
        return state;
    }

    public void cancel(MouseEvent mouseEvent) {
        stateAddress.setText("");
        stateName.setText("");
        userName.setText("");
        userPsw.setText("");
    }
}

