/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package takingcoffee;

import ClassObj.Manager;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import static takingcoffee.Login_ConsumerController.WelcomeBox;
import static takingcoffee.Login_ConsumerController.infoBox;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Login_ManagerController implements Initializable {

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
    ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = takingcoffee.util.ConnectionUtil.connectdb();
    }

    @FXML
    private void Push_BTN_Login(ActionEvent event) {
        String id = TextField_ID.getText().toString(); // text를 입력받아 string으로 전환
        String pw = PasswordField_PW.getText().toString();

        String sql = "SELECT * FROM manager WHERE manager_id = ? and password = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pw);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("존재하지 않는 아이디와 비밀번호 입니다.", "로그인 실패", null);
            } else {

                String UserName = resultSet.getString("name");
                String CafeName = resultSet.getString("cafe_name");
                String phonenumber = resultSet.getString("phonenumber");
                String Email = resultSet.getString("Email");

                //Consumer 객체에 정보 저장
                TakingCoffee.Manager = new Manager(id, pw, UserName, CafeName, phonenumber, Email);

                WelcomeBox("안녕하세요!.", "환영 합니다.", null, UserName);

                Parent window1;
                window1 = FXMLLoader.load(getClass().getResource("AfterLogin_Manager.fxml"));

                Stage mainStage; //Here is the magic. We get the reference to main Stage.
                mainStage = TakingCoffee.parentWindow;
                mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
