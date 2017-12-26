package order;

import ClassObj.Cafe;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.W;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Order_Step1_CafeChoiceController implements Initializable {

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
    private Label Label_step1;
    @FXML
    private TextField TextField_SearchBar;
    @FXML
    private Button BTN_Step1_StoreSearch;
    @FXML
    private ImageView ImageView_Step1_Lens;
    @FXML
    private TableView<ViewCafe> TableView_MyCafe;
    @FXML
    private TableColumn<ViewCafe, String> TableColumn_MyCafe;
    @FXML
    private TableView<ViewCafe> TableView_MyGificon;
    @FXML
    private TableColumn<ViewCafe, String> TableColumn_MyGificon_Cafe;
    @FXML
    private TableColumn<ViewCafe, String> TableColumn_MyGificon_Menu;
    @FXML
    private Label Label_MyGifticon;
    @FXML
    private TableView<ViewCafe> TableView_Cafe_Uni;
    @FXML
    private TableColumn<ViewCafe, String> TableColumn_Cafe_Uni;
    @FXML
    private Label Label_Cafe_Uni;
    @FXML
    private Button BTN_MyCafe;
    @FXML
    private Button BTN_Cafe_Uni;
    @FXML
    private Button BTN_MyGifticon;
    @FXML
    private Button BTN_ChooseInMyMenu;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<ViewCafe> data = FXCollections.observableArrayList(); //TableView_MyCafe
    private ObservableList<ViewCafe> data2 = FXCollections.observableArrayList(); //TableView_MyGificon
    private ObservableList<ViewCafe> data3 = FXCollections.observableArrayList(); //TableView_Cafe_Uni

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_TableView_MyCafe();
        init_TableView_MyGificon();
        init_TableView_Cafe_Uni();
    }

    public Order_Step1_CafeChoiceController() {
        connection = takingcoffee.util.ConnectionUtil.connectdb();
    }

    private void init_TableView_MyCafe() {
        String id = TakingCoffee.Consumer.getId();

        String sql = "SELECT * FROM favorite_cafe WHERE consumer_id = ?";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String Cafe_Name = resultSet.getString("cafe_name");

                data.add(new ViewCafe(Cafe_Name, null));
                TableColumn_MyCafe.setCellValueFactory(new PropertyValueFactory<>("CafeName"));
                TableView_MyCafe.setItems(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init_TableView_MyGificon() {
        String id = TakingCoffee.Consumer.getId();

        String sql = "SELECT * FROM gifticon WHERE receiver_id = ?";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String Cafe_Name = resultSet.getString("cafe_name");
                String Menu_Name = resultSet.getString("menu_name");

                data2.add(new ViewCafe(Cafe_Name, Menu_Name));
                TableColumn_MyGificon_Cafe.setCellValueFactory(new PropertyValueFactory<>("CafeName"));
                TableColumn_MyGificon_Menu.setCellValueFactory(new PropertyValueFactory<>("Menu"));

                TableView_MyGificon.setItems(data2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init_TableView_Cafe_Uni() {

        String uni = TakingCoffee.Consumer.getUni();

        String sql = "SELECT * FROM cafe WHERE uni_name = ?";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uni);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String Cafe_Name = resultSet.getString("cafe_name");
                data3.add(new ViewCafe(Cafe_Name, null));
                TableColumn_Cafe_Uni.setCellValueFactory(new PropertyValueFactory<>("CafeName"));
                TableView_Cafe_Uni.setItems(data3);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Push_BTN_Step1_StoreSearch(ActionEvent event) {
        String Cafe_Name = TextField_SearchBar.getText().toString(); // text를 입력받아 string으로 전환

        String sql = "SELECT * FROM cafe WHERE cafe_name = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Cafe_Name);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("제휴되지 않은 매장입니다.", "검색 실패", null);
            } else {

                String Uni_Name = resultSet.getString("uni_name");
                String Addr = resultSet.getString("Addr");
                int postpayavail = resultSet.getInt("PostPayAvail");

                confirmBox(Cafe_Name, Uni_Name, Addr, postpayavail);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Push_BTN_MyCafe(ActionEvent event) {
        ViewCafe ViewCafe = TableView_MyCafe.getSelectionModel().getSelectedItem();
        String Cafe_Name = ViewCafe.getCafeName();

        String sql = "SELECT * FROM cafe WHERE cafe_name = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Cafe_Name);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("제휴되지 않은 매장입니다.", "검색 실패", null);
            } else {

                String Uni_Name = resultSet.getString("uni_name");
                String Addr = resultSet.getString("Addr");
                int postpayavail = resultSet.getInt("PostPayAvail");

                confirmBox(Cafe_Name, Uni_Name, Addr, postpayavail);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Push_BTN_Cafe_Uni() {
        ViewCafe ViewCafe = TableView_Cafe_Uni.getSelectionModel().getSelectedItem();
        String Cafe_Name = ViewCafe.getCafeName();

        String sql = "SELECT * FROM cafe WHERE cafe_name = ?"; // sql문 하드코딩

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Cafe_Name);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("제휴되지 않은 매장입니다.", "검색 실패", null);
            } else {

                String Uni_Name = resultSet.getString("uni_name");
                String Addr = resultSet.getString("Addr");
                int postpayavail = resultSet.getInt("PostPayAvail");

                confirmBox(Cafe_Name, Uni_Name, Addr, postpayavail);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Push_BTN_ChooseInMyMenu(ActionEvent event) throws Exception{
        Parent root;
        root = FXMLLoader.load(getClass().getResource("Order_SubStep1_1_ChooseInMyMenu.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 497, 384));
        stage.show();
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public void confirmBox(String Cafe_Name, String Uni_Name, String Addr, int postpayavail) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("카페 이름 : " + Cafe_Name + "\n" + "대학교 : " + Uni_Name + "\n" + "위치 : " + Addr + "\n선택하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    //확인을 누르면 Cafe를 메인에 저장한다. String cafename, String uniname, String addr 만 저장하는 생성자로
                    TakingCoffee.SelectedCafe = new Cafe(Cafe_Name, Uni_Name, Addr, postpayavail);

                    // 그리고 step2로 넘어간다.
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("Order_Step2_MenuChoice.fxml"));

                    Stage mainStage; //Here is the magic. We get the reference to main Stage.
                    mainStage = TakingCoffee.parentWindow;
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

    public class ViewCafe {

        private SimpleStringProperty CafeName; // **오류 조심 menu_name같이 언더바 붙인 이름은 인식을 못함!
        private SimpleStringProperty Menu;

        public ViewCafe(String CafeName, String Menu) {
            this.CafeName = new SimpleStringProperty(CafeName);
            this.Menu = new SimpleStringProperty(Menu);
        }

        public String getCafeName() {
            return CafeName.get();
        }

        public void setCafeName(SimpleStringProperty CafeName) {
            this.CafeName = CafeName;
        }

        public String getMenu() {
            return Menu.get();
        }

        public void setMenu(SimpleStringProperty Menu) {
            this.Menu = Menu;
        }
    }

}
