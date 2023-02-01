package com.example.app.view;

import com.example.app.entity.Message;
import com.example.app.entity.Phone;
import com.example.app.view.components.MyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

    @FXML
    private TableColumn<Message,String> info; // Value injected by FXMLLoader

    @FXML // fx:id="phoneTable"
    private MyTableView messageTable; // Value injected by FXMLLoader

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    private ObservableList<Message> data =
            FXCollections.observableArrayList();

//    @Autowired
//    private PhoneService phoneService;

//    private int selectNum = 0;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert seq != null : "fx:id=\"seq\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert time != null : "fx:id=\"time\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'multiTable.fxml'.";
        assert messageTable != null : "fx:id=\"phoneTable\" was not injected: check your FXML file 'multiTable.fxml'.";

        //表格宽度
        seq.prefWidthProperty().bind(messageTable.widthProperty().multiply(0.05));
        time.prefWidthProperty().bind(messageTable.widthProperty().multiply(0.15));
        status.prefWidthProperty().bind(messageTable.widthProperty().multiply(0.2));
        opreation.prefWidthProperty().bind(messageTable.widthProperty().multiply(0.2));
        info.prefWidthProperty().bind(messageTable.widthProperty().multiply(0.4));

        initData();

        //初始化表格
        seq.setCellValueFactory(new PropertyValueFactory<>("seq"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        opreation.setCellValueFactory(new PropertyValueFactory<>("opreation"));
        info.setCellValueFactory(new PropertyValueFactory<>("info"));

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
                                Message selectedItem = data.get(getIndex());
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
                                textController.info.setText("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试");
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
                                stage.setTitle("短信发送手机号");
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

        messageTable.setItems(data);
        messageTable.setEditable(true);


    }

    @FXML//清除
    public boolean delete(MouseEvent event) throws IOException {
        data.clear();
        return true;
    }
    @FXML//刷新
    public void flash(MouseEvent mouseEvent) throws IOException {
        initData();
        messageTable.refresh();
//        selectNum=0;
    }

    private void initData() throws IOException {
        data.clear();
        Phone phone1 = new Phone(true,"1","123456","789","65");
        Phone phone2 = new Phone(true,"2","12355556","789","65");
        Phone phone3 = new Phone(true,"3","1234777556","789","65");
        Phone phone4 = new Phone(true,"4","123466656","789","65");
        data.add(new Message("1","2022年08月03日 01:36:10","一次提交信息不能超过600个手机号码",
                new ArrayList<Phone>(Arrays.asList(phone1,phone2))));
        data.add(new Message("2","2","3",new ArrayList<Phone>(Arrays.asList(phone3))));
        data.add(new Message("3","2","3",new ArrayList<Phone>(Arrays.asList(phone4))));
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
