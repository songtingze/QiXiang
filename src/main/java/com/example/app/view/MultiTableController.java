package com.example.app.view;

import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import com.example.app.entity.Index;
import com.example.app.repository.IndexRepository;
import com.example.app.service.IndexService;
import com.example.app.view.components.CellFactory;
import com.example.app.view.components.MyTableView;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class MultiTableController {
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button flashBtn;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="indexNum"
    private TableColumn<Index,String> indexNum; // Value injected by FXMLLoader

    @FXML // fx:id="indexJudge"
    private TableColumn<Index,String> indexJudge; // Value injected by FXMLLoader

    @FXML // fx:id="indexName"
    private TableColumn<Index,String> indexName; // Value injected by FXMLLoader

    @FXML // fx:id="indexTable"
    private MyTableView indexTable; // Value injected by FXMLLoader

    @FXML // fx:id="indexStatus"
    private TableColumn<Index,String> indexStatus; // Value injected by FXMLLoader

    @FXML // fx:id="indexCode"
    private TableColumn<Index,String> indexCode; // Value injected by FXMLLoader

    @FXML // fx:id="mColumnSelect"
    private TableColumn<Index, Boolean> mColumnSelect; // Value injected by FXMLLoader

    @FXML // fx:id="indexData"
    private TableColumn<Index,String> indexData; // Value injected by FXMLLoader

    private ObservableList<Index> data =
            FXCollections.observableArrayList();

    @Autowired
    private IndexService indexService;

    @Autowired
    private IndexRepository indexRepository;

    private int selectNum = 0;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert indexNum != null : "fx:id=\"indexNum\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert indexJudge != null : "fx:id=\"indexJudge\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert indexName != null : "fx:id=\"indexName\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert indexTable != null : "fx:id=\"indexTable\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert indexStatus != null : "fx:id=\"indexStatus\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert indexCode != null : "fx:id=\"indexCode\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert mColumnSelect != null : "fx:id=\"mColumnSelect\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert indexData != null : "fx:id=\"indexData\" was not injected: check your FXML file 'multiTable.fxml'.";
        editBtn.setDisable(true);

        //表格宽度
        mColumnSelect.prefWidthProperty().bind(indexTable.widthProperty().multiply(0.05));
        indexNum.prefWidthProperty().bind(indexTable.widthProperty().multiply(0.1));
        indexJudge.prefWidthProperty().bind(indexTable.widthProperty().multiply(0.15));
        indexName.prefWidthProperty().bind(indexTable.widthProperty().multiply(0.2));
        indexStatus.prefWidthProperty().bind(indexTable.widthProperty().multiply(0.15));
        indexCode.prefWidthProperty().bind(indexTable.widthProperty().multiply(0.25));
        indexData.prefWidthProperty().bind(indexTable.widthProperty().multiply(0.1));

        initData();


        //初始化表格
        mColumnSelect.setCellValueFactory(new PropertyValueFactory<Index, Boolean>("selected"));
        indexNum.setCellValueFactory(new PropertyValueFactory<>("indexNum"));
        indexJudge.setCellValueFactory(new PropertyValueFactory<>("indexJudge"));
        indexStatus.setCellValueFactory(new PropertyValueFactory<>("indexStatus"));
        indexName.setCellValueFactory(new PropertyValueFactory<>("indexName"));
        indexCode.setCellValueFactory(new PropertyValueFactory<>("indexCode"));
        indexData.setCellValueFactory(new PropertyValueFactory<>("indexData"));
        indexTable.setItems(data);
        indexTable.setEditable(true);

        //表格多选
        mColumnSelect.setCellFactory(
                CellFactory.tableCheckBoxColumn(new Callback<Integer, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(Integer index) {
                        final Index g = (Index) indexTable.getItems().get(index);
                        ObservableValue<Boolean> ret =
                                new SimpleBooleanProperty(g, "selected", g.getSelected());
                        ret.addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(
                                    ObservableValue<? extends Boolean> observable,
                                    Boolean oldValue, Boolean newValue) {
                                g.setSelected(newValue);
                                //编辑按钮控制
                                if(newValue){
                                    selectNum++;
//                                    System.out.println(selectNum);
                                }else{
                                    selectNum--;
                                }
                                if(selectNum != 1){
                                    editBtn.setDisable(true);
                                }else{
                                    editBtn.setDisable(false);
                                }
//                                System.out.println(g.getIndexCode()+':'+g.getSelected());
                            }
                        });
                        return ret;
                    }
                }));


    }
    @FXML//添加
    public void add(MouseEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit.fxml"));
        Parent parent = loader.load();
        EditController editController = loader.getController();
        editController.saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("btn1的单击事件");
                Index newIndex = editController.save();
                data.add(newIndex);
                indexTable.refresh();
                //添加到文件
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("indexName",newIndex.getIndexName());
                jsonObject.put("indexCode",newIndex.getIndexCode());
                jsonObject.put("indexData",newIndex.getIndexData());
                jsonObject.put("indexJudge",newIndex.getIndexJudge());
                jsonObject.put("indexStatus",newIndex.getIndexStatus());
                try {
                    Result<String> result = indexRepository.addIndex(jsonObject);
                    if(result.getCode().equalsIgnoreCase("0")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("添加气象指标");
                        alert.setHeaderText(null);
                        alert.setContentText(result.getData());

                        alert.showAndWait();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("添加气象指标");
                        alert.setHeaderText(null);
                        alert.setContentText(result.getMsg());

                        alert.showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    initData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectNum=0;
//                indexTable.setItems(data);
                Scene scene = editController.root.getScene();
                Stage window = (Stage) scene.getWindow();
                window.close();
            }
        });
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("添加指标");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    @FXML//删除
    public boolean delete(MouseEvent event) throws IOException {
        int size = data.size();
        if (size <= 0) {
            return false;
        }
        for (int i = size - 1; i >= 0; i--) {
            Index s = data.get(i);
            if (s.getSelected()) {
                data.remove(s);
                //删除到文件
                Result<String> result = indexRepository.deleteIndex(Integer.parseInt(s.getIndexNum()));
                if(result.getCode().equalsIgnoreCase("0")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("删除气象指标");
                    alert.setHeaderText(null);
                    alert.setContentText(result.getData());

                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("删除气象指标");
                    alert.setHeaderText(null);
                    alert.setContentText(result.getMsg());

                    alert.showAndWait();
                }
            }
        }
        initData();
        selectNum=0;
        return true;
    }
    @FXML//编辑
    public void edit(MouseEvent mouseEvent) throws IOException {
        //获取选中的值
        int size = data.size();
        if (size <= 0) {
            return;
        }
        Index selectedItem = null;
        for (int i = size - 1; i >= 0; i--) {
            Index s = data.get(i);
            if (s.getSelected()) {
                selectedItem = s;
            }
        }


        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit.fxml"));
        Parent parent = loader.load();
        //初始化编辑窗口
        EditController editController = loader.getController();
        editController.indexName.setText(selectedItem.getIndexName());
        editController.indexCode.setText(selectedItem.getIndexCode());
        editController.indexData.setText(selectedItem.getIndexData());
//        editController.indexJudge.getItems().addAll("正常", "停用");
//        switch (selectedItem.getIndexJudge()){
//            case ">":editController.indexJudge.setValue("大于");break;
//            case ">=":editController.indexJudge.setValue("大于等于");break;
//            case "==":editController.indexJudge.setValue("等于");break;
//            case "<=":editController.indexJudge.setValue("小于等于");break;
//            case "<":editController.indexJudge.setValue("小于");break;
//            case "!=":editController.indexJudge.setValue("不等于");break;
//            case "[]":editController.indexJudge.setValue("范围");break;
//            case "lack":editController.indexJudge.setValue("缺值");break;
//        }
//        editController.indexStatus.getItems().addAll("大于", "大于等于", "等于", "小于等于", "小于","不等于","范围","缺值");
//        if(selectedItem.getIndexStatus().equalsIgnoreCase("yes")){
//            editController.indexStatus.setValue("正常");
//        }else if(selectedItem.getIndexStatus().equalsIgnoreCase("no")){
//            editController.indexStatus.setValue("停用");
//        }
        editController.indexNum=selectedItem.getIndexNum();
        //编辑窗口确认按钮点击事件
        editController.saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("btn1的单击事件");
                Index newIndex = editController.save();
                for (int i = size - 1; i >= 0; i--) {
                    Index s = data.get(i);
                    if (s.getSelected()) {
                        //添加到文件
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("indexNum",Integer.parseInt(s.getIndexNum()));
                        jsonObject.put("indexName",newIndex.getIndexName());
                        jsonObject.put("indexCode",newIndex.getIndexCode());
                        jsonObject.put("indexData",newIndex.getIndexData());
                        jsonObject.put("indexJudge",newIndex.getIndexJudge());
                        jsonObject.put("indexStatus",newIndex.getIndexStatus());
                        try {
                            Result<String> result = indexRepository.modifyIndex(jsonObject);
                            if(result.getCode().equalsIgnoreCase("0")){
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("修改气象指标");
                                alert.setHeaderText(null);
                                alert.setContentText(result.getData());

                                alert.showAndWait();
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("修改气象指标");
                                alert.setHeaderText(null);
                                alert.setContentText(result.getMsg());

                                alert.showAndWait();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            initData();
                            //修改表格中的值
                            data.get(i).setSelected(newIndex.getSelected());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //刷新表格
                indexTable.refresh();
                selectNum=0;
//                indexTable.setItems(data);
                //关闭子窗口
                Scene scene = editController.root.getScene();
                Stage window = (Stage) scene.getWindow();
                window.close();
            }
        });
        //打开子窗口
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("编辑指标");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
    @FXML//刷新
    public void flash(MouseEvent mouseEvent) throws IOException {
        initData();
        indexTable.refresh();
        selectNum=0;
    }

    private void initData() throws IOException {
        data.clear();
        Result<List<Index>> result = indexService.queryAllIndex();
        if(result.getCode().equalsIgnoreCase("0")){
            List<Index> indexList = result.getData();
            for(Index index:indexList){
                if(index.getIndexStatus().equalsIgnoreCase("yes")){
                    index.setIndexStatus("正常");
                }else if(index.getIndexStatus().equalsIgnoreCase("no")){
                    index.setIndexStatus("停用");
                }
                switch (index.getIndexJudge()){
                    case ">":index.setIndexJudge("大于");break;
                    case ">=":index.setIndexJudge("大于等于");break;
                    case "==":index.setIndexJudge("等于");break;
                    case "<=":index.setIndexJudge("小于等于");break;
                    case "<":index.setIndexJudge("小于");break;
                    case "!=":index.setIndexJudge("不等于");break;
                    case "[]":index.setIndexJudge("范围");break;
                    case "lack":index.setIndexJudge("缺值");break;
                }
                data.add(index);
            }
        }

    }


}
