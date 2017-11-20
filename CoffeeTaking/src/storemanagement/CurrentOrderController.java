/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagement;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import static java.util.Spliterators.iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author YEONCHAN
 */
public class CurrentOrderController implements Initializable {

    @FXML
    private Label Title_CurrentOrder;
    @FXML
    private TableView<OrderDetails> TB_CurrentOrder;

    /**
     * Initializes the controller class.
     */
    private XMLOrderManager orderManager;
    private ObservableList<OrderDetails> data;
    private HashMap hm;
    private HashMap temphm;
    @FXML
    private TableColumn<OrderDetails, String> OrderNumber_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderDetails, String> ClientID_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderDetails, String> OrderBeverage_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderDetails, String> OrderAmount_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderDetails, ?> CompleteMaking_TB_CurrentOrder;//제작완료 버튼 삽입 column
    @FXML
    private TableColumn<OrderDetails, String> ArrivalTime_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderDetails, ?> CheckReceipt_TB_CurrentOrder;//수령여부 확인 버튼 삽입 column

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTB_CurrentOrder();
    }
    
  // Button button1 = new Button("제작완료");

    private void initTB_CurrentOrder() {
        orderManager = new XMLOrderManager();
        data = FXCollections.observableArrayList();
        hm = new HashMap();

        // 파일 오픈 시 오류 출력
        try {
            // 상대경로 지정
            hm = orderManager.readXML("./src/xml", "CurrentOrder_info.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator<String> iterator = hm.keySet().iterator();
        while (iterator.hasNext()) {
            int k = 1;
            String key = iterator.next();
            temphm = (HashMap) hm.get(key);

            data.add(new OrderDetails(temphm.get("clientID").toString().trim(), temphm.get("beverage").toString().trim(), temphm.get("amount").toString().trim(), temphm.get("arrTime").toString().trim()));
            
        }

        ClientID_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        OrderBeverage_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>("beverage"));
        OrderAmount_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ArrivalTime_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>("arrTime"));
        //OrderNumber_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>(num));
          

        TB_CurrentOrder.setItems(null);
        TB_CurrentOrder.setItems(data);
    }

}
