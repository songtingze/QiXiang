/**
 * Sample Skeleton for 'setting.fxml' Controller Class
 */
package com.example.app.view;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

@Component
public class SettingController {

    @FXML
    private AnchorPane test;
    @FXML
    private AnchorPane index;
    @FXML
    private AnchorPane phone;
    @FXML
    private AnchorPane indicators;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="search"
    private TextField search; // Value injected by FXMLLoader
    @FXML // fx:id="navigation"
    private Label navigation; // Value injected by FXMLLoader
    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader
    @FXML // fx:id="toolTree"
    private TreeView<String> toolTree; // Value injected by FXMLLoader
    @FXML // fx:id="saveBtn"
    private Button saveBtn; // Value injected by FXMLLoader
    @FXML
    private IndexController indexController;
//    @FXML
//    private MultiTableController multiTableController;
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'setting.fxml'.";
        assert navigation != null : "fx:id=\"navigation\" was not injected: check your FXML file 'setting.fxml'.";
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'setting.fxml'.";
        assert toolTree != null : "fx:id=\"toolTree\" was not injected: check your FXML file 'setting.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'setting.fxml'.";
        assert test != null : "fx:id=\"test\" was not injected: check your FXML file 'setting.fxml'.";
        assert index != null : "fx:id=\"index\" was not injected: check your FXML file 'setting.fxml'.";

        test.setVisible(false);
        index.setVisible(false);
        phone.setVisible(false);
        indicators.setVisible(true);



        //菜单栏
        TreeItem<String> rootItem = new TreeItem<String> ("气象预警");
        rootItem.setExpanded(true);
        TreeItem<String> item1 = new TreeItem<String> ("气象信息监控");
        item1.setExpanded(false);
        rootItem.getChildren().add(item1);
        TreeItem<String> item2 = new TreeItem<String> ("预警指标管理");
        item2.setExpanded(false);
        rootItem.getChildren().add(item2);
        TreeItem<String> item3 = new TreeItem<String> ("手机推送管理");
        item3.setExpanded(false);
        rootItem.getChildren().add(item3);
        toolTree.setRoot(rootItem);
        toolTree.setShowRoot(false);
        //点击菜单item事件
        toolTree.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<TreeItem <String>>() {
                    @Override
                    public void changed(ObservableValue<? extends TreeItem<String>> observableValue,
                                        TreeItem<String> oldItem, TreeItem<String> newItem) {
//                        System.out.println(newItem.getValue());
//                        System.out.println(getTreeRoute(newItem));
                        //导航栏
                        navigation.setText(getTreeRoute(newItem));
                        //界面切换
                        if(newItem.getValue().equals("气象信息监控")){
                            try {
                                indexController.initialize();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            index.setVisible(true);
                            test.setVisible(false);
                            indicators.setVisible(false);
                            phone.setVisible(false);
                        }else if(newItem.getValue().equals("预警指标管理")){
                            index.setVisible(false);
                            test.setVisible(false);
                            phone.setVisible(false);
                            indicators.setVisible(true);
                        }else if(newItem.getValue().equals("手机推送管理")){
                            index.setVisible(false);
                            test.setVisible(false);
                            indicators.setVisible(false);
                            phone.setVisible(true);
                        }
                    }
                });
    }


    private String getTreeRoute(TreeItem<String> item){
        String route = "";
        if(item.getParent() != null){
            route = getTreeRoute(item.getParent()) + " > " +item.getValue();
        }else{
            route = item.getValue();
        }
        return route;
    }

    
}
