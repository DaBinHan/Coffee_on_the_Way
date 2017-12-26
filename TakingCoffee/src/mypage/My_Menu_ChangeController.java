package mypage;

import ClassObj.MyMenuInfo;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static mypage.My_FavoriteController.infoBox;
import takingcoffee.TakingCoffee;
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author Leejinnyeong
 */
public class My_Menu_ChangeController implements Initializable {

    @FXML
    private TextField TextField_MyMenuOptionChange;

    @FXML
    private Button BTN_Change;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private Label Label_OptionChoice;

    public My_Menu_ChangeController() {
        connection = ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // ** btnchange click 하면 새 창 닫아지고 원래 창만 남도록
    // ** btnchange 버튼을 누르면 원래 창의 list가 리뉴얼되도록!
    @FXML
    public void Click_btnModify(ActionEvent event) throws Exception {

        String newop = TextField_MyMenuOptionChange.getText().toString(); // text를 입력받아 string으로 전환        

        String mycafename = takingcoffee.TakingCoffee.MyCafe.getCafename();
        String mymenuname = takingcoffee.TakingCoffee.MyCafe.Menu.getMenuName();
        String beforeop = takingcoffee.TakingCoffee.MyCafe.Menu.getOp();

        String sql = "UPDATE mymenu SET op = ? WHERE consumer_id = ? and cafe_name = ? and menu_name = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            // connection. 는 connection으로 db에 걸어준다는 의미다.
            preparedStatement.setString(1, newop);
            preparedStatement.setString(2, TakingCoffee.Consumer.getId());
            preparedStatement.setString(3, mycafename);
            preparedStatement.setString(4, mymenuname);
            // 1번 물음표에는 회원의 id를 넣어라
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        confirmBox();
    }

    public void confirmBox() { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("수정을 완료하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {

                    String newop = TextField_MyMenuOptionChange.getText().toString(); // text를 입력받아 string으로 전환        

                    String mycafename = takingcoffee.TakingCoffee.MyCafe.getCafename();
                    String mymenuname = takingcoffee.TakingCoffee.MyCafe.Menu.getMenuName();
                    String beforeop = takingcoffee.TakingCoffee.MyCafe.Menu.getOp();

                    String sql = "UPDATE mymenu SET op = ? WHERE consumer_id = ? and cafe_name = ? and menu_name = ?"; // sql문 하드코딩

                    try {
                        preparedStatement = connection.prepareStatement(sql);
                        // connection. 는 connection으로 db에 걸어준다는 의미다.
                        preparedStatement.setString(1, newop);
                        preparedStatement.setString(2, TakingCoffee.Consumer.getId());
                        preparedStatement.setString(3, mycafename);
                        preparedStatement.setString(4, mymenuname);
                        // 1번 물음표에는 회원의 id를 넣어라
                        preparedStatement.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    infoBox("나만의 메뉴가 수정되었습니다.", null, null);

                    Parent window1;
                    // 원래 창을 재호출해서 수정 사항을 반영한다.
                    window1 = FXMLLoader.load(getClass().getResource("My_Menu.fxml"));

                    Stage mainStage; //Here is the magic. We get the reference to main Stage.
                    mainStage = TakingCoffee.parentWindow;
                    mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

                    Stage stage = (Stage) BTN_Change.getScene().getWindow(); // 현재 버튼의 창 정보를 받아온다.
                    stage.close(); // 그 창을 닫는다.

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("취소 메시지");
                subAlert.setContentText("수정이 취소되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
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

}
