package com.example.app.view;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import com.example.app.common.SendMsg;
import com.example.app.common.StaElemSearchAPI_CLIB_callAPI_to_array2D;
import com.example.app.repository.DataRepository;
import com.example.app.service.DataService;
import com.example.app.service.FileService;
import com.example.app.service.IndexService;
import com.example.app.service.PhoneService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@Component
public class IndexController {

    public GridPane gridpane;
    public ScrollPane scrollpane;
    public GridPane titlepane;
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
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private FileService fileService;

    @Autowired
    private DataRepository dataRepository;

    private Integer times = 0;

    public IndexController() {
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert info != null : "fx:id=\"info\" was not injected: check your FXML file 'index.fxml'.";
        gridpane.getChildren().clear();
        titlepane.getChildren().clear();
        //事件label
        Label indexname1 = new Label("气象指标");
        Label datas1 = new Label("气象数据");
        Label dataStatus1 = new Label("数据状态");
        HBox hBox1 = new HBox(5);
        hBox1.getChildren().addAll(indexname1);
        hBox1.getStyleClass().add("title");
        titlepane.add(hBox1, 0, 0, 1, 1);
        HBox hBox2 = new HBox(5);
        hBox2.getChildren().addAll(datas1);
        hBox2.getStyleClass().add("title");
        titlepane.add(hBox2,1,0, 1, 1);
        HBox hBox3 = new HBox(5);
        hBox3.getChildren().addAll(dataStatus1);
        hBox3.getStyleClass().add("title");
        titlepane.add(hBox3, 2, 0, 1, 1);
        titlepane.setHgap(5);
        titlepane.setVgap(10);

        JSONObject jsonObject = dataService.getDataJSONObject().getData();
        time.setText("当前时间:"+jsonObject.getJSONObject("dataTime").getString("time"));
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for(int i = 0; i < jsonArray.size();i ++){
            JSONObject data = jsonArray.getJSONObject(i);
            JSONObject index = indexService.queryByIndexCode(data.getString("indexCode"));
            Label indexname = new Label(index.getString("indexName"));
            Label datas;
            if(data.getString("data").equalsIgnoreCase("") &&
                    data.getString("dataStatus").equalsIgnoreCase("UnKnown")){
                datas = new Label("暂无");
            }else{
                datas = new Label(data.getString("data"));
            }
            Label dataStatus = null;
            switch (data.getString("dataStatus")){
                case "UnKnown":dataStatus = new Label("还未获取数据");break;
                case "lack":dataStatus = new Label("数据缺失");break;
                case "abnormal":dataStatus = new Label("数据异常");break;
                case "normal":dataStatus = new Label("数据正常");break;
            }
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
            HBox hindexname = new HBox(5);
            hindexname.getChildren().addAll(indexname);
            HBox hdatas = new HBox(5);
            hdatas.getChildren().addAll(datas);
            HBox hstatus = new HBox(5);
            hstatus.getChildren().addAll(status,dataStatus);
            gridpane.add(hindexname, 0, i, 1, 1);
            gridpane.add(hdatas,1,i, 1, 1);
            gridpane.add(hstatus, 2, i, 1, 1);
            hindexname.getStyleClass().add("dataText");
            hdatas.getStyleClass().add("dataText");
            hstatus.getStyleClass().add("dataText");
            gridpane.setHgap(2);
            gridpane.setVgap(20);
            gridpane.setMaxHeight(20);
        }

    }

//    @Scheduled(cron = "*/10 * * * * ?")   //定时器定义，设置执行时间
//    private void process() throws IOException {
//        System.out.println("定时器1执行"+times++);
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//        System.out.println(format.format(new Date()));
//        StaElemSearchAPI_CLIB_callAPI_to_array2D staElemSearchAPI_clib_callAPI_to_array2D = new StaElemSearchAPI_CLIB_callAPI_to_array2D();
//        try {
////            System.out.println(indexService.queryAllIndexCode());
//            if(!indexService.queryAllIndexCode().equalsIgnoreCase("")){
//                JSONObject jsonObject = staElemSearchAPI_clib_callAPI_to_array2D.test(indexService.queryAllIndexCode());
//                dataRepository.getData(jsonObject);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                //更新JavaFX的主线程的代码放在此处
//                try {
//                    initialize();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }
//    @Scheduled(cron = "*/10 * * * * ?")   //定时器定义，设置执行时间
//    private void process() throws IOException {
//        Result<String> phoneResult = phoneService.getPhones();
//        Result<String> warningResult = dataRepository.getWarningInfo();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//        String times = dateFormat.format(new Date());
//        if(phoneResult.getCode().equalsIgnoreCase("0")){
//            String phones = phoneResult.getData();
//            System.out.println(phones);
//            if(warningResult.getCode().equalsIgnoreCase("0")){
//                String waringInfo = warningResult.getData();
//                System.out.println(waringInfo);
//                SendMsg sendMsg = new SendMsg();
//                Result<String> sendResult = sendMsg.sendSMS(phones,waringInfo,"");
//                if(sendResult.getCode().equalsIgnoreCase("0")){
//                    fileService.writeWarningTxt(times+"-"+phones+"-成功-"+sendResult.getData()+"。\n");
//                }else {
//                    fileService.writeWarningTxt(times+"-"+phones+"-失败-"+sendResult.getMsg()+"。\n");
//                }
//            }
//        }
//    }
}

