package com.example.app.view;

import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import com.example.app.entity.Phone;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
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
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

@Component
public class PhoneTableController {
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

    @FXML // fx:id="seq"
    private TableColumn<Phone,String> seq; // Value injected by FXMLLoader

    @FXML // fx:id="phone"
    private TableColumn<Phone,String> phone; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private TableColumn<Phone,String> status; // Value injected by FXMLLoader

    @FXML // fx:id="phoneTable"
    private MyTableView phoneTable; // Value injected by FXMLLoader

    @FXML // fx:id="mColumnSelect"
    private TableColumn<Phone, Boolean> mColumnSelect; // Value injected by FXMLLoader

    private ObservableList<Phone> data =
            FXCollections.observableArrayList();

    @Autowired
    private IndexService indexService;

    private int selectNum = 0;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert seq != null : "fx:id=\"seq\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert phoneTable != null : "fx:id=\"phoneTable\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert mColumnSelect != null : "fx:id=\"mColumnSelect\" was not injected: check your FXML file 'multiTable.fxml'.";
        editBtn.setDisable(true);

        initData();

        //初始化表格
        mColumnSelect.setCellValueFactory(new PropertyValueFactory<Phone, Boolean>("selected"));
        seq.setCellValueFactory(new PropertyValueFactory<>("seq"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        phoneTable.setItems(data);
        phoneTable.setEditable(true);

        //表格多选
        mColumnSelect.setCellFactory(
                CellFactory.tableCheckBoxColumn(new Callback<Integer, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(Integer index) {
                        final Phone g = (Phone) phoneTable.getItems().get(index);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editPhone.fxml"));
        Parent parent = loader.load();
        EditPhoneController editPhoneController = loader.getController();
        editPhoneController.saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("btn1的单击事件");
                Phone newph = editPhoneController.save();
                data.add(newph);
                phoneTable.refresh();
                //添加到文件
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("phone",newph.getPhone());
//                jsonObject.put("status",newph.getStatus());
//                try {
//                    indexService.addIndex(jsonObject);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                try {
                    initData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectNum=0;
//                indexTable.setItems(data);
                Scene scene = editPhoneController.root.getScene();
                Stage window = (Stage) scene.getWindow();
                window.close();
            }
        });
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("添加手机");
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
            Phone ph = data.get(i);
            if (ph.getSelected()) {
                data.remove(ph);
                //删除到文件
            }
        }
        return true;
    }
    @FXML//编辑
    public void edit(MouseEvent mouseEvent) throws IOException {
        //获取选中的值
        int size = data.size();
        if (size <= 0) {
            return;
        }
        Phone selectedItem = null;
        for (int i = size - 1; i >= 0; i--) {
            Phone ph = data.get(i);
            if (ph.getSelected()) {
                selectedItem = ph;
            }
        }


        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editPhone.fxml"));
        Parent parent = loader.load();
        //初始化编辑窗口
        EditPhoneController editPhoneController = loader.getController();
        editPhoneController.phone.setText(selectedItem.getPhone());
        editPhoneController.status.setValue(selectedItem.getStatus());
        editPhoneController.seq=selectedItem.getSeq();
        //编辑窗口确认按钮点击事件
        editPhoneController.saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("btn1的单击事件");
                Phone newph = editPhoneController.save();
                for (int i = size - 1; i >= 0; i--) {
                    Phone p = data.get(i);
//                    if (p.getSelected()) {
                        //添加到文件
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("indexNum",Integer.parseInt(p.getIndexNum()));
//                        jsonObject.put("indexName",newIndex.getIndexName());
//                        jsonObject.put("indexCode",newIndex.getIndexCode());
//                        jsonObject.put("indexData",newIndex.getIndexData());
//                        jsonObject.put("indexJudge",newIndex.getIndexJudge());
//                        jsonObject.put("indexStatus",newIndex.getIndexStatus());
//                        try {
//                            System.out.println(indexService.modifyIndex(jsonObject).getMsg());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        try {
                            initData();
                            //修改表格中的值
                            data.get(i).setSelected(newph.getSelected());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                    }
                }
                //刷新表格
                phoneTable.refresh();
                selectNum=0;
//                indexTable.setItems(data);
                //关闭子窗口
                Scene scene = editPhoneController.root.getScene();
                Stage window = (Stage) scene.getWindow();
                window.close();
            }
        });
        editPhoneController.phone.setDisable(true);
        //打开子窗口
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("编辑手机");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
    @FXML//刷新
    public void flash(MouseEvent mouseEvent) throws IOException {
        initData();
        phoneTable.refresh();
        selectNum=0;
    }

    private void initData() throws IOException {
        data.clear();
//        Result<List<Index>> result = indexService.queryAllIndex();
//        if(result.getCode().equalsIgnoreCase("0")){
//            List<Index> indexList = result.getData();
//            for(Index index:indexList){
//                data.add(index);
//            }
//        }

        data.add(new Phone(false,"1","1","1"));
        data.add(new Phone(false,"2","1","1"));
        data.add(new Phone(false,"3","1","1"));
    }


}
