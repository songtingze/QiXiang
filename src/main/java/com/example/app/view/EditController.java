package com.example.app.view;

import com.example.app.entity.Index;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EditController {

    public AnchorPane root;
    public Label notice;
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
        indexJudge.getItems().addAll("大于", "大于等于", "等于", "小于等于", "小于","不等于","范围","缺值");
        indexJudge.setValue("大于");

        indexJudge.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                if(new_value.equals(6)){
                    notice.setText("格式如 2,3");
                }else{
                    notice.setText("");
                }
            }
        });

    }

    public Index save(){
        String indexName = this.indexName.getText();
        String indexCode = this.indexCode.getText();
        String indexData = this.indexData.getText();
        String indexJudge = "";
        switch (this.indexJudge.getValue()){
            case "大于":indexJudge = ">";break;
            case "大于等于":indexJudge = ">=";break;
            case "等于":indexJudge = "==";break;
            case "小于等于":indexJudge = "<=";break;
            case "小于":indexJudge = "<";break;
            case "不等于":indexJudge = "!=";break;
            case "范围":indexJudge = "[]";break;
            case "缺值":indexJudge = "lack";break;
        }
        String indexStatus = "";
        switch (this.indexStatus.getValue()){
            case "正常":indexStatus = "yes";break;
            case "停用":indexStatus = "no";break;
        }
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

