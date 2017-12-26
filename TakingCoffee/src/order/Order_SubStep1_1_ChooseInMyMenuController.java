package order;

import ClassObj.Cafe;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static order.Order_Step1_CafeChoiceController.infoBox;
import takingcoffee.*;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Order_SubStep1_1_ChooseInMyMenuController implements Initializable {

    @FXML
    private TableView<MyMenuInfo> TB_MyMenuList;
    @FXML
    private TableColumn<MyMenuInfo, String> TB_CafeName;
    @FXML
    private TableColumn<MyMenuInfo, String> TB_MenuName;
    @FXML
    private TableColumn<MyMenuInfo, String> TB_MaterialQuantity;
    @FXML
    private Button BTN_Choose;

    private ObservableList<MyMenuInfo> data = FXCollections.observableArrayList();

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTB_MyMenuList();
    }

    public Order_SubStep1_1_ChooseInMyMenuController() {
        connection = takingcoffee.util.ConnectionUtil.connectdb();
    }

    private void initTB_MyMenuList() {

        String sql = "SELECT * FROM mymenu WHERE consumer_id = ?"; // sql문 하드코딩
        // table 이름과 column 이름이 맞는지 확인할 것
        // table 이름 : consumer , column 이름 : consumer_id와 password
        // ? 는 우리가 preparedstatement라는 객체를 사용하기 때문에 사용 가능함

        try {
            preparedStatement = connection.prepareStatement(sql);
            // connection. 는 connection으로 db에 걸어준다는 의미다.
            preparedStatement.setString(1, TakingCoffee.Consumer.getId());
            // 1번 물음표에는 회원의 id를 넣어라
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // 우리가 저장했던 viewcafename 객체를 data(옵져버블리스트) 에 저장한다.
                String CafeName = resultSet.getString("cafe_name");
                String MenuName = resultSet.getString("menu_name");
                String Option = resultSet.getString("op");
                data.add(new MyMenuInfo(CafeName, MenuName, Option));

                TB_CafeName.setCellValueFactory(new PropertyValueFactory<>("cafename"));
                TB_MenuName.setCellValueFactory(new PropertyValueFactory<>("menuname"));
                TB_MaterialQuantity.setCellValueFactory(new PropertyValueFactory<>("option"));

                TB_MyMenuList.setItems(data);
                // tableview에 data를 보여준다

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Push_BTN_Choose(ActionEvent event) throws Exception {
        MyMenuInfo MyMenuInfo = TB_MyMenuList.getSelectionModel().getSelectedItem();
        String CafeName = MyMenuInfo.getCafename();
        String MenuName = MyMenuInfo.getMenuname();
        String Option = MyMenuInfo.getOption();

        String sql = "SELECT * FROM menu WHERE cafe_name = ? and menu_name = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, CafeName);
            preparedStatement.setString(2, MenuName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                String Price = resultSet.getString("price");

                confirmBox(CafeName, MenuName, Price, Option);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void confirmBox(String CafeName, String MenuName, String Price, String Option) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("선택하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    //확인을 누르면 카페와 메뉴에 대한 모든 정보를 저장한다.
                    TakingCoffee.SelectedCafe = new Cafe(CafeName);
                    TakingCoffee.SelectedCafe.Menu.setMenuName(MenuName);
                    TakingCoffee.SelectedCafe.Menu.setPrice(Price);
                    TakingCoffee.SelectedCafe.Menu.setOp(Option);

                    // 그리고 step3로 넘어간다.
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("Order_Step3_ConfirmPrice.fxml"));

                    Stage mainStage; //Here is the magic. We get the reference to main Stage.
                    mainStage = TakingCoffee.parentWindow;
                    mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

                    Stage stage = (Stage) BTN_Choose.getScene().getWindow();
                    stage.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("취소 메시지");
                subAlert.setContentText("선택이 취소되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
