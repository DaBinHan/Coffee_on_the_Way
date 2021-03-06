package gift;

import java.io.IOException;
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
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author yousungwoo
 */
public class ChooseCafeController implements Initializable {

    @FXML
    private ImageView ImageView_MainTitle;
    @FXML
    private ImageView ImageView_StoreSearch;
    @FXML
    private TextField TextField_StoreSearch;
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
    private TextField TextField_SearchBar;
    @FXML
    private Label Label_Search;
    @FXML
    private Button BTN_Search;
    @FXML
    private ImageView ImageView_Lens;

    /**
     * Initializes the controller class.
     */
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private TextField SearchCafe;
    
    public ChooseCafeController() {
        connection = ConnectionUtil.connectdb();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void imageViewClicked(MouseEvent event) {
    }

    @FXML
    private void btnSelect(ActionEvent event)throws IOException{
        String cafename = SearchCafe.getText().toString(); // text를 입력받아 string으로 전환

        String sql = "SELECT * FROM menu WHERE cafe_name = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cafename);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("제휴되지 않은 매장입니다.", null);
            } else {
                TakingCoffee.GiftCafe.setCafename(cafename);
                
                Parent window2;
                window2 = FXMLLoader.load(getClass().getResource("ChooseMenu.fxml"));

                Stage mainStage;
                mainStage = TakingCoffee.parentWindow;
                mainStage.getScene().setRoot(window2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static void infoBox(String infoMessage, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
    
        @FXML
    private void Click_ImageView_Order_heading(MouseEvent event) throws Exception {

        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/order/Order_Main.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Click_ImageView_Mypage_heading(MouseEvent event) throws Exception {

        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/mypage/My_Page.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Click_ImageView_Gift_heading(MouseEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/gift/Gift.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Click_ImageView_Review_heading(MouseEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/review/Review_FirstPage.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Click_ImageView_Store_heading(MouseEvent event) throws Exception {
        infoBox("카페 사장님 전용 메뉴 입니다.", "안내", null);
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
