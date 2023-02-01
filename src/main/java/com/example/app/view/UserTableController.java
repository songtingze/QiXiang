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
import javafx.scene.control.*;
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

@Component
public class UserTableController {
    public TextField stateAddress;
    public TextField stateName;
    public TextField userName;
    public TextField userPsw;
    public Button saveBtn;
//
//    private ObservableList<State> data =
//            FXCollections.observableArrayList();

    @Autowired
    private PhoneService phoneService;

    private int selectNum = 0;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        initData();
    }
    @FXML//保存
    public void save(MouseEvent event) throws IOException{
        String stateAddressText =  stateAddress.getText();
        String stateNameText =  stateName.getText();
        String userNameText =  userName.getText();
        String userPswText =  userPsw.getText();



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("个人信息修改");
        alert.setHeaderText(null);
        alert.setContentText("新的个人信息已保存");

        alert.showAndWait();
//        Stage stage = new Stage();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editUser.fxml"));
//        Parent parent = loader.load();
//        EditUserController editUserController = loader.getController();
//        editUserController.saveBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
////                System.out.println("btn1的单击事件");
//                State newph = editUserController.save();
//                data.add(newph);
//                userTable.refresh();
//                //添加到文件
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("stateAddress",newph.getAddress());
//                jsonObject.put("stateName",newph.getState());
//                jsonObject.put("userName",newph.getUserName());
//                jsonObject.put("userPsw",newph.getPassword());
//                try {
//                    Result<String> result = phoneService.addPhone(jsonObject);
//                    if(result.getCode().equalsIgnoreCase("0")){
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("添加个人信息");
//                        alert.setHeaderText(null);
//                        alert.setContentText(result.getData());
//
//                        alert.showAndWait();
//                    }else{
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("添加个人信息");
//                        alert.setHeaderText(null);
//                        alert.setContentText(result.getMsg());
//
//                        alert.showAndWait();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    initData();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                selectNum=0;
////                indexTable.setItems(data);
//                Scene scene = editUserController.root.getScene();
//                Stage window = (Stage) scene.getWindow();
//                window.close();
//            }
//        });
//        Scene scene = new Scene(parent);
//        stage.setScene(scene);
//        stage.setTitle("添加个人信息");
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.show();

    }


    private void initData() throws IOException {
        stateAddress.setText("aaaaaaaaa");
        stateName.setText("aaaaa");
        userName.setText("12343566");
        userPsw.setText("12345678");

    }

}
