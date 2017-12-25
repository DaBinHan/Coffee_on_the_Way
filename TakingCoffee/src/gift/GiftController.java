package gift;

import ClassObj.Consumer;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class GiftController implements Initializable {

    @FXML
    private TextField SearchID;
    @FXML
    private TextField PhoneNumber;
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
    @FXML
    private Label Label_GiftToNonmember;
    @FXML
    private Label Label_GiftToMember;
    @FXML
    private ImageView ImageView_Lens;
    @FXML
    private Button btn_choose_m;
    @FXML
    private Button btn_choose_nm;

    /**
     * Initializes the controller class.
     */
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private ObservableList<Consumer> data = FXCollections.observableArrayList();
    
    public GiftController() {
        connection = ConnectionUtil.connectdb();
    }
    
    @FXML
    private void btnSelect1(ActionEvent event) throws IOException{
        String id = SearchID.getText().toString(); // text를 입력받아 string으로 전환

        String sql = "SELECT * FROM consumer WHERE consumer_id = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("존재하지 않는 아이디입니다.", null);
            } else {
                String UserName = resultSet.getString("name");
                
                
                //TakingCoffee.Consumer.setId(resultSet.getString("consumer_id"));
                //뜬금없이 사용자 아이디를 왜 바꿈?
                
                CheckBox("선물할 회원이 맞습니까?", null, UserName);
                
                TakingCoffee.receiverId = id;
                
                Parent window1;
                window1 = FXMLLoader.load(getClass().getResource("ChooseCafe.fxml"));

                Stage mainStage;
                mainStage = TakingCoffee.parentWindow;
                mainStage.getScene().setRoot(window1);
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

    public static void CheckBox(String infoMessage, String headerMessage, String UserName) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setHeaderText(headerMessage);
        alert.setContentText("선물을 받으실 분 : " + UserName + "님!\n" + infoMessage);
        alert.showAndWait();
    }
    
    @FXML
    private void btnSelect2(ActionEvent event) throws IOException{
        TakingCoffee.receiverId = PhoneNumber.getText();
        
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("ChooseCafe.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void imageViewClicked(MouseEvent event) {
    }
    
}
