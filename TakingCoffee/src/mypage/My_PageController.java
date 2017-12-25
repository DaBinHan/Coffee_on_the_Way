package mypage;

import takingcoffee.util.ConnectionUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author Leejinnyeong
 */
public class My_PageController implements Initializable {

    /* My_Page.fxml은 '자주 가는 매장 목록'(BTN_FavoriteList), '나만의 메뉴 목록'(BTN_MyMenuList),
        '원두 충전'(BTN_Charge), '주문 내역'(BTN_OrderList) 버튼을 갖는다. */
    
    @FXML
    private ImageView ImageView_MainTitle;
    @FXML
    private ImageView ImageView_StoreSearch;
    @FXML
    private TextField TextField_StoreSearch;
    @FXML
    private Button BTN_StoreSearch;
    @FXML
    private ImageView ImageView_Lens;
    @FXML
    private Label Label_Logout;
    @FXML
    private Label Label_CustomerService;
    @FXML
    private Label Label_AboutUs;
    @FXML
    private Separator Separator_first;
    @FXML
    private Separator Separator_second;
    @FXML
    private ImageView ImageView_Gift_heading;
    @FXML
    private ImageView ImageView_Review_heading;
    @FXML
    private ImageView ImageView_Store_heading;
    @FXML
    private ImageView ImageView_Mypage_heading;
    @FXML
    private ImageView ImageView_Order_heading;
    
    @FXML
    private Button BTN_FavoriteList;
    @FXML
    private Button BTN_MyMenuList;
    @FXML
    private Button BTN_Charge;
    @FXML
    private Button BTN_OrderList;
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    public My_PageController() {
        connection = ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void imageViewClicked(MouseEvent event) throws Exception{
        
    }
    
    @FXML
    private void btnFavoriteList(ActionEvent event) throws Exception{
        Parent window1;
        window1=FXMLLoader.load(getClass().getResource("My_Favorite.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;      // 원래는 mainStage = Mypage.parentWindow; 였음
        mainStage.getScene().setRoot(window1);
    }

    @FXML
    private void btnMyMenuList(ActionEvent event) throws Exception{
        Parent window1;
        window1=FXMLLoader.load(getClass().getResource("My_Menu.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1);
    }
    
    @FXML
    private void btnCharge(ActionEvent event) throws Exception{
        Parent window1;
        window1=FXMLLoader.load(getClass().getResource("Charge.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1);
    }
    
    @FXML
    private void btnOrderList(ActionEvent event) throws Exception{
        Parent window1;
        window1=FXMLLoader.load(getClass().getResource("My_Order.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1);
    }
        
    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
    
}
