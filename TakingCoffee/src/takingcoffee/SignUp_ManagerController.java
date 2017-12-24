package takingcoffee;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class SignUp_ManagerController implements Initializable {

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
    private Label Label_Name;
    @FXML
    private Label Label_PhoneNum;
    @FXML
    private Label Label_CafeName;
    @FXML
    private Label Label_Email;
    @FXML
    private TextField TextField_ID;
    @FXML
    private TextField TextField_PW;
    @FXML
    private TextField TextField_Name;
    @FXML
    private TextField TextField_PhoneNum;
    @FXML
    private TextField TextField_CafeName;
    @FXML
    private TextField TextField_Email;
    @FXML
    private Button BTN_SignUp;
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = takingcoffee.util.ConnectionUtil.connectdb();
    }    
    
      @FXML
    private void Push_BTN_SignUp(ActionEvent event) {
        String ID = TextField_ID.getText().toString();
        String PW = TextField_PW.getText().toString();
        String Name = TextField_Name.getText().toString();
        String CafeName = TextField_CafeName.getText().toString();
        String PhoneNum = TextField_PhoneNum.getText().toString();
        String Email = TextField_Email.getText().toString();

        //(1) 빈 칸이 모두 채워지지 않은 경우
        if (ID.equals("") || PW.equals("") || Name.equals("") || CafeName.equals("") || PhoneNum.equals("") || Email.equals("")) {
            infoBox("빈 칸을 모두 채워주세요!", "회원가입 실패", null);
        } //(2) 빈 칸이 모두 채워진 경우
        else {
            //(2) - 1 중복된 ID가 있는지 확인
            String sql = "SELECT * FROM manager WHERE manager_id = ?";

            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, ID);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) { // 중복된 ID가 존재한다면
                    infoBox("이미 존재하는 아이디 입니다.", "회원가입 실패", null);
                } //(2) - 2 중복된 ID가 없다면 회원가입 진행
                else {
                    confirmBox(ID, PW, Name, PhoneNum, CafeName, Email);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    // 회원 가입 시 최종 확인
    public void confirmBox(String ID, String PW, String Name, String CafeName, String PhoneNum, String Email) {
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText(Name + "님!" + "\n" + "ID : " + ID + "로 회원 가입 하시겠어요?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try { // 확인을 누를 경우 sql문을 보낸다.
                    String sql = "insert into manager (manager_id, password, name, cafe_name, phonenumber, Email) values(?, ?, ?, ?, ?, ?)";
                    
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, ID);
                    preparedStatement.setString(2, PW);
                    preparedStatement.setString(3, Name);
                    preparedStatement.setString(4, CafeName);
                    preparedStatement.setString(5, PhoneNum);
                    preparedStatement.setString(6, Email);

                    preparedStatement.executeUpdate(); // insert구문 사용시에는 executeQuery가 아니라 executeUpdate 사용해야 한다.

                    infoBox("회원이 되신 것을 축하드립니다.", "회원가입 성공", null);

                } catch (Exception e) {
                    infoBox("알 수 없는 이유로 회원 가입에 실패했습니다.", "회원가입 실패", null);
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("취소 메시지");
                subAlert.setContentText("회원 가입이 취소되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
