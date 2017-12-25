package mypage;

import ClassObj.MyMenuInfo;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private ObservableList<MyMenuInfo> data = FXCollections.observableArrayList();
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
    public void btnChangeClick(ActionEvent event) throws Exception{
        
        String newop = TextField_MyMenuOptionChange.getText().toString(); // text를 입력받아 string으로 전환        
        
        String mycafename=takingcoffee.TakingCoffee.SelectedCafe.getCafename();
        String mymenuname=takingcoffee.TakingCoffee.SelectedCafe.Menu.getMenuName();
        String beforeop=takingcoffee.TakingCoffee.SelectedCafe.Menu.getOp();
        
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
    }
    
    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
