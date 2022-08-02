package com.example.app.view;

import com.example.app.entity.Phone;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EditPhoneController {

    public AnchorPane root;
    public TextField phone;
    public ChoiceBox<String> status;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cancelBtn"
    public Button cancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="saveBtn"
    public Button saveBtn; // Value injected by FXMLLoader

    public String seq;





    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'edit.fxml'.";
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'edit.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'edit.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'edit.fxml'.";

        status.getItems().addAll("正常", "停用");
        status.setValue("正常");

    }

    public Phone save() {
        String phone = this.phone.getText();
        String status = this.status.getValue();
        if(status.equalsIgnoreCase("正常")){
            status = "yes";
        }else if(status.equalsIgnoreCase("停用")){
            status = "no";
        }
        Phone ph = new Phone(false,seq,phone,status);
        return ph;
    }

    public void cancel(MouseEvent mouseEvent) {
        if(!phone.isDisable()){
            phone.setText("");
        }
        status.setValue("");
    }
}

