package com.example.app.view;

import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import com.example.app.entity.Phone;
import com.example.app.entity.State;
import com.example.app.service.IndexService;
import com.example.app.service.PhoneService;
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
import javafx.scene.control.Alert;
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
public class UserTableController {
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
    private TableColumn<State,String> seq; // Value injected by FXMLLoader

    @FXML // fx:id="stateAddress"
    private TableColumn<State,String> stateAddress; // Value injected by FXMLLoader

    @FXML // fx:id="stateName"
    private TableColumn<State,String> stateName; // Value injected by FXMLLoader

    @FXML // fx:id="userName"
    private TableColumn<State,String> userName; // Value injected by FXMLLoader

    @FXML // fx:id="userPsw"
    private TableColumn<State,String> userPsw; // Value injected by FXMLLoader

    @FXML // fx:id="phoneTable"
    private MyTableView userTable; // Value injected by FXMLLoader

    @FXML // fx:id="mColumnSelect"
    private TableColumn<State, Boolean> mColumnSelect; // Value injected by FXMLLoader

    private ObservableList<State> data =
            FXCollections.observableArrayList();

    @Autowired
    private PhoneService phoneService;

    private int selectNum = 0;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        editBtn.setDisable(true);

        //表格宽度
        mColumnSelect.prefWidthProperty().bind(userTable.widthProperty().multiply(0.05));
        seq.prefWidthProperty().bind(userTable.widthProperty().multiply(0.1));
        stateAddress.prefWidthProperty().bind(userTable.widthProperty().multiply(0.3));
        stateName.prefWidthProperty().bind(userTable.widthProperty().multiply(0.15));
        userName.prefWidthProperty().bind(userTable.widthProperty().multiply(0.2));
        userPsw.prefWidthProperty().bind(userTable.widthProperty().multiply(0.2));

        initData();

        //初始化表格
        mColumnSelect.setCellValueFactory(new PropertyValueFactory<State, Boolean>("selected"));
        seq.setCellValueFactory(new PropertyValueFactory<>("seq"));
        stateAddress.setCellValueFactory(new PropertyValueFactory<>("stateAddress"));
        stateName.setCellValueFactory(new PropertyValueFactory<>("stateName"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userPsw.setCellValueFactory(new PropertyValueFactory<>("userPsw"));
        userTable.setItems(data);
        userTable.setEditable(true);

        //表格多选
        mColumnSelect.setCellFactory(
                CellFactory.tableCheckBoxColumn(new Callback<Integer, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(Integer index) {
                        final Phone g = (Phone) userTable.getItems().get(index);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editUser.fxml"));
        Parent parent = loader.load();
        EditUserController editUserController = loader.getController();
        editUserController.saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("btn1的单击事件");
                State newph = editUserController.save();
                data.add(newph);
                userTable.refresh();
                //添加到文件
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("stateAddress",newph.getAddress());
                jsonObject.put("stateName",newph.getState());
                jsonObject.put("userName",newph.getUserName());
                jsonObject.put("userPsw",newph.getPassword());
                try {
                    Result<String> result = phoneService.addPhone(jsonObject);
                    if(result.getCode().equalsIgnoreCase("0")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("添加个人信息");
                        alert.setHeaderText(null);
                        alert.setContentText(result.getData());

                        alert.showAndWait();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("添加个人信息");
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
                Scene scene = editUserController.root.getScene();
                Stage window = (Stage) scene.getWindow();
                window.close();
            }
        });
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("添加个人信息");
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
            State ph = data.get(i);
            if (ph.getSelected()) {
                data.remove(ph);
                //删除到文件
                Result<String> result = phoneService.deletePhone(Integer.parseInt(ph.getSeq()));
                if(result.getCode().equalsIgnoreCase("0")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("删除个人信息");
                    alert.setHeaderText(null);
                    alert.setContentText(result.getData());

                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("删除个人信息");
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
        State selectedItem = null;
        for (int i = size - 1; i >= 0; i--) {
            State ph = data.get(i);
            if (ph.getSelected()) {
                selectedItem = ph;
            }
        }


        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editUser.fxml"));
        Parent parent = loader.load();
        //初始化编辑窗口
        EditUserController editUserController = loader.getController();
        editUserController.stateAddress.setText(selectedItem.getAddress());
        editUserController.stateName.setText(selectedItem.getState());
        editUserController.userName.setText(selectedItem.getUserName());
        editUserController.userPsw.setText(selectedItem.getPassword());

        editUserController.seq=selectedItem.getSeq();
        //编辑窗口确认按钮点击事件
        editUserController.saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("btn1的单击事件");
                State newph = editUserController.save();
                for (int i = size - 1; i >= 0; i--) {
                    State p = data.get(i);
                    if (p.getSelected()) {
//                        添加到文件
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("seq",Integer.parseInt(p.getSeq()));
                        jsonObject.put("stateAddress",p.getAddress());
                        jsonObject.put("stateName",p.getState());
                        jsonObject.put("userName",p.getUserName());
                        jsonObject.put("userPsw",p.getPassword());
                        try {
                            Result<String> result = phoneService.modifyPhone(jsonObject);
                            if(result.getCode().equalsIgnoreCase("0")){
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("修改个人信息");
                                alert.setHeaderText(null);
                                alert.setContentText(result.getData());

                                alert.showAndWait();
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("修改个人信息");
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
                            data.get(i).setSelected(newph.getSelected());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //刷新表格
                userTable.refresh();
                selectNum=0;
//                indexTable.setItems(data);
                //关闭子窗口
                Scene scene = editUserController.root.getScene();
                Stage window = (Stage) scene.getWindow();
                window.close();
            }
        });
//        editPhoneController.phone.setDisable(true);
        //打开子窗口
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("编辑个人信息");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
    @FXML//刷新
    public void flash(MouseEvent mouseEvent) throws IOException {
        initData();
        userTable.refresh();
        selectNum=0;
    }

    private void initData() throws IOException {
        data.clear();
//        Result<List<Phone>> result = phoneService.queryAllPhone();
//        if(result.getCode().equalsIgnoreCase("0")){
//            List<Phone> phoneList = result.getData();
//            for(Phone phone:phoneList){
//                if(phone.getStatus().equalsIgnoreCase("yes")){
//                    phone.setStatus("正常");
//                }else if(phone.getStatus().equalsIgnoreCase("no")){
//                    phone.setStatus("停用");
//                }
//                data.add(phone);
//            }
//        }

    }


}
