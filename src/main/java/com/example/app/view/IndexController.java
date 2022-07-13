package com.example.app.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.service.DataService;
import com.example.app.service.IndexService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DataService dataService;

    @Autowired
    private IndexService indexService;



    public IndexController() {
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert info != null : "fx:id=\"info\" was not injected: check your FXML file 'index.fxml'.";
        //事件label
        JSONObject jsonObject = dataService.getDataJSONObject().getData();
        time.setText(jsonObject.getJSONObject("dataTime").getString("time"));
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for(int i = 0; i < jsonArray.size();i ++){
            JSONObject data = jsonArray.getJSONObject(i);
            JSONObject index = indexService.queryByIndexCode(data.getString("indexCode"));
            Label indexname = new Label(index.getString("indexName"));
            Label datas = new Label(data.getString("data"));
            Label dataStatus = new Label(data.getString("dataStatus"));
            ImageView status;
            if(data.getString("dataStatus").equalsIgnoreCase("normal")){
                status = new ImageView("static/gou.png");
                status.setFitWidth(20);
                status.setFitHeight(20);
            }else{
                status = new ImageView("static/warning.png");
                status.setFitWidth(20);
                status.setFitHeight(20);
            }
            gridpane.add(indexname, 0, i, 1, 1);
            gridpane.add(datas,1,i, 1, 1);
            gridpane.add(dataStatus, 2, i, 1, 1);
            gridpane.add(status, 3, i, 1, 1);
            gridpane.setHgap(15);
            gridpane.setVgap(15);
        }

    }
}

