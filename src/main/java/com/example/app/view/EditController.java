package com.example.app.view;


import java.net.URL;
import java.util.ResourceBundle;

import com.example.app.entity.Index;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class EditController {

    public AnchorPane root;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="indexJudge"
    public ChoiceBox<String> indexJudge; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    public Button cancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="indexName"
    public TextField indexName; // Value injected by FXMLLoader

    @FXML // fx:id="indexStatus"
    public ChoiceBox<String> indexStatus; // Value injected by FXMLLoader

    @FXML // fx:id="indexCode"
    public TextField indexCode; // Value injected by FXMLLoader

    @FXML // fx:id="indexData"
    public TextField indexData; // Value injected by FXMLLoader

    @FXML // fx:id="saveBtn"
    public Button saveBtn; // Value injected by FXMLLoader

    public String indexNum;




    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert indexJudge != null : "fx:id=\"indexJudge\" was not injected: check your FXML file 'edit.fxml'.";
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'edit.fxml'.";
        assert indexName != null : "fx:id=\"indexName\" was not injected: check your FXML file 'edit.fxml'.";
        assert indexStatus != null : "fx:id=\"indexStatus\" was not injected: check your FXML file 'edit.fxml'.";
        assert indexCode != null : "fx:id=\"indexCode\" was not injected: check your FXML file 'edit.fxml'.";
        assert indexData != null : "fx:id=\"indexData\" was not injected: check your FXML file 'edit.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'edit.fxml'.";

        indexStatus.getItems().addAll("正常", "停用");
        indexStatus.setValue("正常");
        indexJudge.getItems().addAll("大于", "大于等于", "等于", "小于等于", "小于");
        indexJudge.setValue("大于");

    }

    public Index save() {

        String indexName = this.indexName.getText();
        String indexCode = this.indexCode.getText();
        String indexData = this.indexData.getText();
        String indexJudge = this.indexJudge.getValue();
        String indexStatus = this.indexStatus.getValue();
        Index index = new Index(false,indexCode,indexData,indexJudge,indexName,indexNum,indexStatus);
        return index;

    }
    
    public void cancel(MouseEvent mouseEvent) {
        indexName.setText("");
        indexCode.setText("");
        indexData.setText("");
        indexJudge.setValue("");
        indexStatus.setValue("");
    }
}

