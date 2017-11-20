package coffeetaking;

import coffeetaking.util.ConnectionUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private Label Label_ID;
    @FXML
    private Label Label_Password;
    @FXML
    private TextField TextField_ID;
    @FXML
    private PasswordField TextField_Password;
    @FXML
    private Label Label_Main_Title;
    @FXML
    private ImageView ImageView_Main_Icon;
    @FXML
    private Button BTN_Login;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public Login_ConsumerController() {
        connection = ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void Push_BTN_Login(ActionEvent event) {
        String id = TextField_ID.getText().toString(); // text를 입력받아 string으로 전환
        String password = TextField_Password.getText().toString();

        String sql = "SELECT * FROM consumer WHERE id = ? and password = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("존재하지 않는 아이디와 비밀번호 입니다.", "로그인 실패", null);
            } else {
                String UserName = resultSet.getString("name");
                
                //로그인 하는 순간 매씬은 ID와 name을 공유한다.
                CoffeeTaking.ConsumerID = id;
                CoffeeTaking.ConsumerName = UserName;
                
                WelcomeBox("안녕하세요!.", "환영 합니다.", null, UserName);
                
                Parent window1;
                window1 = FXMLLoader.load(getClass().getResource("Menu_Consumer.fxml"));

                Stage mainStage; //Here is the magic. We get the reference to main Stage.
                mainStage = CoffeeTaking.parentWindow;
                mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        alert.setContentText(infoMessage+" "+UserName+"님!");
        alert.showAndWait();
    }

}
