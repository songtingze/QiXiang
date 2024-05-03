package com.example.app.view;

import com.alibaba.fastjson.JSONObject;
import com.example.app.service.PhoneService;
import com.example.app.service.UserService;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    private UserService userService;

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

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stateAddress",stateAddressText);
        jsonObject.put("stateName",stateNameText);
        jsonObject.put("userName",userNameText);
        jsonObject.put("userPsw",userPswText);
        userService.modifyUserInfo(jsonObject);



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("个人信息修改");
        alert.setHeaderText(null);
        alert.setContentText("新的个人信息已保存");

        alert.showAndWait();

        initData();

    }


    private void initData() throws IOException {

        JSONObject jsonObject = userService.getUserInfo();
        stateAddress.setText(jsonObject.getString("stateAddress"));
        stateName.setText(jsonObject.getString("stateName"));
        userName.setText(jsonObject.getString("userName"));
        userPsw.setText(jsonObject.getString("userPsw"));

    }

}
