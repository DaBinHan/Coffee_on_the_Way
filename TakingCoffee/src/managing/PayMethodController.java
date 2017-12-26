/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managing;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author YEONCHAN
 */
public class PayMethodController implements Initializable {

    @FXML
    private ImageView ImageView_MainTitle;
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
    private RadioButton RB_Avail;
    @FXML
    private ToggleGroup PostPayAvail;
    @FXML
    private RadioButton RB_NotAvail;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Button BTN_Confirm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public PayMethodController() {
        connection = ConnectionUtil.connectdb();
    }

    @FXML
    private void ManagingClicked(MouseEvent event) throws IOException {
        Parent window1;//Parent 객체 만드는 이유 ,, 가장 조상 클래스,,, 얘를 계속 override하면 다른 class와 호환이 쉬워서??
        window1 = FXMLLoader.load(getClass().getResource("SelectFunction.fxml"));//getResource뒤에 다음에 들어가고 싶은 화면을 넣음. 
        Stage mainStage;//
        mainStage = TakingCoffee.parentWindow;//여기에 접근해서 내가 원하는 창을 계속 띄어줘야함. 그래서 public으로 선언.
        mainStage.getScene().setRoot(window1);//we dont need to change whole scene,

    }

    @FXML
    private void ConfirmBTNClicked(ActionEvent event) throws IOException {
        Parent window1;
        String CafeName = TakingCoffee.Manager.getCafename();

        if (RB_Avail.isSelected()) {//후불 가능 선택
            try {
                String sql = "UPDATE cafe SET PostPayAvail = '1' where cafe_name = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, CafeName);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            infoBox("매장에서 고객이 커피를 수령 후, 현장에서 결제를 진행합니다", null, "후불 결제 가능");
            window1 = FXMLLoader.load(getClass().getResource("PayMethod.fxml"));

        } else if (RB_NotAvail.isSelected()) {//후불 불가능 선택
            try {
                String sql = "UPDATE cafe SET PostPayAvail = '0' where cafe_name = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, CafeName);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ErrorBox("선불 결제로만 커피를 주문 받습니다.", null, "후불 결제 불가능");
            window1 = FXMLLoader.load(getClass().getResource("PayMethod.fxml"));

        } else {//후불 결제여부 선택하지 않음(radio button에서 아무것도 선택X)
            window1 = FXMLLoader.load(getClass().getResource("PayMethod.fxml"));
            WarnBox("기존 화면으로 돌아갑니다", null, "변경 없음");
        }
        Stage mainStage;//
        mainStage = TakingCoffee.parentWindow;//여기에 접근해서 내가 원하는 창을 계속 띄어줘야함. 그래서 public으로 선언.
        mainStage.getScene().setRoot(window1);
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void WarnBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.WARNING); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void ErrorBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.ERROR); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
    
}


