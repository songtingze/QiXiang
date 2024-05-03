package com.example.app.view;

import com.alibaba.fastjson.JSONObject;
import com.example.app.entity.Message;
import com.example.app.entity.Phone;
import com.example.app.entity.SearchInfo;
import com.example.app.service.FileService;
import com.example.app.view.components.MyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class DataTableController {

    public TableColumn opreation;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button flashBtn;

    @FXML // fx:id="seq"
    private TableColumn<Message,String> seq; // Value injected by FXMLLoader

    @FXML // fx:id="time"
    private TableColumn<Message,String> time; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private TableColumn<Message,String> status; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private TableColumn<Message,String> library; // Value injected by FXMLLoader

//    @FXML
//    private TableColumn<Message,String> info; // Value injected by FXMLLoader

    @FXML // fx:id="phoneTable"
    private MyTableView dataTable; // Value injected by FXMLLoader

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    private ObservableList<SearchInfo> data =
            FXCollections.observableArrayList();

    @Autowired
    private FileService fileService;

//    private int selectNum = 0;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert seq != null : "fx:id=\"seq\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert time != null : "fx:id=\"time\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert library != null : "fx:id=\"status\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert dataTable != null : "fx:id=\"dataTable\" was not injected: check your FXML file 'multiTable.fxml'.";

        //表格宽度
        seq.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.05));
        time.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.25));
        status.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.25));
        library.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.25));
        opreation.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.1));
//        info.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.35));

        initData();

        //初始化表格
        seq.setCellValueFactory(new PropertyValueFactory<>("seq"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        library.setCellValueFactory(new PropertyValueFactory<>("library"));
        opreation.setCellValueFactory(new PropertyValueFactory<>("opreation"));
//        info.setCellValueFactory(new PropertyValueFactory<>("info"));

        opreation.setCellFactory((col)->{

                    TableCell<Message, String> cell = new TableCell<Message, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button = new Button("查看详情");
                            button.getStyleClass().add("opreation");
//                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");

                            button.setOnMouseClicked((col) -> {

                                //获取list列表中的位置，进而获取列表对应的信息数据
                                SearchInfo selectedItem = data.get(getIndex());
                                System.out.println(selectedItem);

                                Stage stage = new Stage();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/text.fxml"));
                                Parent parent = null;
                                try {
                                    parent = loader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //初始化编辑窗口
                                TextController textController = loader.getController();
                                textController.info.setText(selectedItem.getInfo());
//                                int num = selectedItem.getPhone().size();
//                                ObservableList<Phone> phonelist =
//                                        FXCollections.observableArrayList();
//                                for(int i=0 ; i<num ;i++){
//                                    System.out.println(selectedItem.getPhone().get(i));
//                                    phonelist.add(selectedItem.getPhone().get(i));
//                                }
//
//                                listController.list.setItems(phonelist);
//                                listController.list.setEditable(true);

                                //编辑窗口确认按钮点击事件
                                textController.saveBtn.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        //关闭子窗口
                                        Scene scene = textController.root.getScene();
                                        Stage window = (Stage) scene.getWindow();
                                        window.close();
                                    }
                                });
                                //打开子窗口
                                Scene scene = new Scene(parent);
                                stage.setScene(scene);
                                stage.setTitle("数据获取详情");
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.show();

                            });

                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button);
                            }
                        }
                    };
                    return cell;
                }
        );

        dataTable.setItems(data);
        dataTable.setEditable(true);


    }

    @FXML//清除
    public boolean delete(MouseEvent event) throws IOException {
        data.clear();
        fileService.deleteSearchInfoTxt();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("清空信息");
        alert.setHeaderText(null);
        alert.setContentText("已清空");

        alert.showAndWait();

        return true;
    }
    @FXML//刷新
    public void flash(MouseEvent mouseEvent) throws IOException {
        initData();
        dataTable.refresh();
//        selectNum=0;
    }

    private void initData() throws IOException {
        data.clear();
        List<String> list = fileService.readSearchInfoTxt();
        for (int i = 0; i < list.size();i ++){
            String[] line = list.get(i).split("-");
            SearchInfo searchInfo = new SearchInfo(i+"",line[0],line[1],line[2],line[3]);
            data.add(searchInfo);
        }
    }
}
