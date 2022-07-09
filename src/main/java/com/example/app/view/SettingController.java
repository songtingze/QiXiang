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

    public AnchorPane test;
    public AnchorPane index;
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


        //菜单栏
        TreeItem<String> rootItem = new TreeItem<String> ("Inbox");
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("Message" + i);
            item.setExpanded(false);
            rootItem.getChildren().add(item);
        }
        toolTree.setRoot(rootItem);
        //点击菜单item事件
        toolTree.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<TreeItem <String>>() {
                    @Override
                    public void changed(ObservableValue<? extends TreeItem<String>> observableValue,
                                        TreeItem<String> oldItem, TreeItem<String> newItem) {
                        System.out.println(newItem.getValue());
//                        System.out.println(getTreeRoute(newItem));
                        //导航栏
                        navigation.setText(getTreeRoute(newItem));
                        //界面切换
                        if(newItem.getValue().equals("Message1")){
                           index.setVisible(true);
                           test.setVisible(false);
                        }else{
                            index.setVisible(false);
                            test.setVisible(true);
                        }

                    }
                });
    }

    //OK按钮
    public void save(MouseEvent mouseEvent) {
        String pressure = indexController.getPressure();

        System.out.println("save"+pressure);
    }

    //Cancel按钮
    public void cancel(MouseEvent mouseEvent) {
        System.out.println("cancel");
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
