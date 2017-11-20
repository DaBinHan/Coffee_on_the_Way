/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeetaking;

import coffeetaking.util.ConnectionUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Menu_Order_step1Controller implements Initializable {

    @FXML
    private Label Label_Main_Title;
    @FXML
    private ImageView ImageView_Main_Icon;
    @FXML
    private Label Label_step1;
    @FXML
    private TextField TextField_SearchBar;
    @FXML
    private Label Label_Search;
    @FXML
    private Button BTN_Search;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public Menu_Order_step1Controller() {
        connection = ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void Push_BTN_Search(ActionEvent event) {
        String Cafe_Name = TextField_SearchBar.getText().toString(); // text를 입력받아 string으로 전환

        String sql = "SELECT * FROM cafe WHERE cafe_name = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Cafe_Name);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("제휴되지 않은 매장입니다.", "검색 실패", null);
            } else {

                String Uni_Name = resultSet.getString("Uni_name");
                String Addr = resultSet.getString("Addr");

                confirmBox(Cafe_Name, Uni_Name, Addr);

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

    public void confirmBox(String Cafe_Name, String Uni_Name, String Addr) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("카페 이름 : " + Cafe_Name + "\n" + "대학교 : " + Uni_Name + "\n" + "위치 : " + Addr + "을 선택하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    //확인을 누르면 Cafe를 메인에 저장한다.
                    CoffeeTaking.SelectedCafe = Cafe_Name;
                       
                    // 그리고 step2로 넘어간다.
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("Menu_Order_step2.fxml"));

                    Stage mainStage; //Here is the magic. We get the reference to main Stage.
                    mainStage = CoffeeTaking.parentWindow;
                    mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("취소 메시지");
                subAlert.setContentText("카페 선택이 취소되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
