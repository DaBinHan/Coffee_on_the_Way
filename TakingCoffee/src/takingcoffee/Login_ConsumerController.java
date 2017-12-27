package takingcoffee;

import ClassObj.Consumer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Login_ConsumerController implements Initializable {

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
    private Label Label_Logout1;
    @FXML
    private Separator Separator_first1;
    @FXML
    private Label Label_ID;
    @FXML
    private Label Label_PW;
    @FXML
    private TextField TextField_ID;
    @FXML
    private PasswordField PasswordField_PW;
    @FXML
    private Button BTN_Login;

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    //LAST_INSERT_ID()로 OrderID 받아오기 위함 
    Statement st;
    ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = takingcoffee.util.ConnectionUtil.connectdb();
    }

    @FXML
    private void Push_BTN_Login(ActionEvent event) {
        String id = TextField_ID.getText().toString(); // text를 입력받아 string으로 전환
        String pw = PasswordField_PW.getText().toString();

        String sql = "SELECT * FROM consumer WHERE consumer_id = ? and password = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pw);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("존재하지 않는 아이디와 비밀번호 입니다.", "로그인 실패", null);
            } else {

                String UserName = resultSet.getString("name");
                String phonenumber = resultSet.getString("phonenumber");
                String uni = resultSet.getString("uni_name");
                String Email = resultSet.getString("Email");
                String BeanAmount = resultSet.getString("BeanAmount");

                //Consumer 객체에 정보 저장
                TakingCoffee.Consumer = new Consumer(id, pw, UserName, phonenumber, uni, Email, BeanAmount);

                if (IsBlackList(id)) {
                    TakingCoffee.IsBlackList = true;
                }

                WelcomeBox("안녕하세요!.", "환영 합니다.", null, UserName);

                Parent window1;
                window1 = FXMLLoader.load(getClass().getResource("AfterLogin_Consumer.fxml"));

                Stage mainStage; //Here is the magic. We get the reference to main Stage.
                mainStage = TakingCoffee.parentWindow;
                mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean IsBlackList(String id) {
        String sql = "SELECT * FROM orderinfo WHERE consumer_id = ? and menu_complete = 1 and menu_receipt = 2";
        int cnt = 0;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cnt++;
            }

            if (cnt >= 3) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void WelcomeBox(String infoMessage, String titleBar, String headerMessage, String UserName) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage + " " + UserName + "님!");
        alert.showAndWait();
    }
}
