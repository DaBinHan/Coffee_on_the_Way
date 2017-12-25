/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gift;

import gift.PayForChargeController.CoffeeBean;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author yousungwoo
 */
public class PayForChargeController implements Initializable {

    @FXML
    private ImageView ImageView_MainTitle;
    @FXML
    private ImageView ImageView_StoreSearch;
    @FXML
    private TextField TextField_StoreSearch;
    @FXML
    private Label Label_CustomerService;
    @FXML
    private Label Label_AboutUs;
    @FXML
    private Separator Separator_first;
    @FXML
    private Separator Separator_second;
    @FXML
    private ImageView ImageView_Gift_heading1;
    @FXML
    private ImageView ImageView_Review_heading;
    @FXML
    private ImageView ImageView_Store_heading;
    @FXML
    private ImageView ImageView_Mypage_heading;
    @FXML
    private ImageView ImageView_Order_heading;
    @FXML
    private Button BTN_StoreSearch;
    @FXML
    private ImageView ImageView_Lens;
    @FXML
    private Label Label_Logout;
    @FXML
    private Button BTN_next_PFC;
    @FXML
    private Button BTN_ChargeCoffee;
    @FXML
    private TableView<CoffeeBean> TableView_CoffeeBean;
    @FXML
    private TableColumn<CoffeeBean, String> TableColumn_RetainedCB;
    @FXML
    private TableColumn<CoffeeBean, String> TableColumn_PriceofCB;

    /**
     * Initializes the controller class.
     */
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    private ObservableList<CoffeeBean> data = FXCollections.observableArrayList();
    
    public PayForChargeController() {
        connection = ConnectionUtil.connectdb();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_TableView_CoffeeBean();
    }    

    @FXML
    private void imageViewClicked(MouseEvent event) {
    }

    @FXML
    private void btnSelect2(ActionEvent event) {
        int retainedCB = Integer.parseInt(TakingCoffee.BeanAmount);
        int priceofCB = Integer.parseInt(TakingCoffee.price);
        
        if ((retainedCB - priceofCB) < 0){
            infoBox("보유 원두가 부족합니다.", null);
        }
        else {
            confirmBox(retainedCB, priceofCB);
        }
    }
    
    private void init_TableView_CoffeeBean(){
        String retainedCB = TakingCoffee.BeanAmount;
        String priceofCB = TakingCoffee.price;

        data.add(new CoffeeBean(retainedCB, priceofCB));
                
        TableColumn_RetainedCB.setCellValueFactory(new PropertyValueFactory<>("retainedCB"));
        TableColumn_PriceofCB.setCellValueFactory(new PropertyValueFactory<>("priceofCB"));

        TableView_CoffeeBean.setItems(data);
        
    }
    
    public static class CoffeeBean {
        private String retainedCB;
        private String priceofCB;

        public void setRetainedCB(String retainedCB) {
            this.retainedCB = retainedCB;
        }

        public void setPriceofCB(String priceofCB) {
            this.priceofCB = priceofCB;
        }

        public String getRetainedCB() {
            return retainedCB;
        }

        public String getPriceofCB() {
            return priceofCB;
        }

        public CoffeeBean(String retainedCB, String priceofCB) {
            this.retainedCB = retainedCB;
            this.priceofCB = priceofCB;
        }
    }
    
    public void confirmBox(int retainedCB, int priceofCB) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("보유 원두 : " + retainedCB + "\n가격 : " + priceofCB + "\n남은 원두 보유량 : " + (retainedCB-priceofCB) + "\n결제하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    String sql1 = "UPDATE consumer SET BeanAmount = ? WHERE consumer_id = ?";
                    preparedStatement = connection.prepareStatement(sql1);
                    
                    String RemainedAmount = String.valueOf(retainedCB-priceofCB);
                    preparedStatement.setString(1, RemainedAmount);
                    preparedStatement.setString(2, TakingCoffee.id);
                    preparedStatement.executeUpdate();
                    
                    long time = System.currentTimeMillis();
                    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String str = dayTime.format(new Date(time));
                    
                    String sql2 = "INSERT INTO gifticon (gift_date, sender_id, receiver_id, cafe_name, menu_name) VALUES (?, ?, ?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql2);
                    preparedStatement.setString(1, str);
                    preparedStatement.setString(2, TakingCoffee.id);
                    preparedStatement.setString(3, TakingCoffee.receiverId);
                    preparedStatement.setString(4, TakingCoffee.cafename);
                    preparedStatement.setString(5, TakingCoffee.menuname);
                    preparedStatement.executeUpdate();
                    
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("GiftComplete.fxml"));

                    Stage mainStage; //Here is the magic. We get the reference to main Stage.
                    mainStage = TakingCoffee.parentWindow;
                    mainStage.getScene().setRoot(window1);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void infoBox(String infoMessage, String headerMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
