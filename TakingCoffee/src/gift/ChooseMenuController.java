package gift;

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
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import takingcoffee.*;

/**
 * FXML Controller class
 *
 * @author yousungwoo
 */
public class ChooseMenuController implements Initializable {

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
    private TableView<Menu> TableView_Menu;
    @FXML
    private TableColumn<Menu, String> TableColumn_Menu_Name;
    @FXML
    private TableColumn<Menu, String> TableColumn_Price;
    @FXML
    private Label Label_SearchMenu;
    @FXML
    private Button btn_next_CM;

    /**
     * Initializes the controller class.
     */
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<Menu> data = FXCollections.observableArrayList();

    public ChooseMenuController() {
        connection = takingcoffee.util.ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_TableView_Menu();
    }

    @FXML
    private void btnSelect3(ActionEvent event) {
        Menu Menu = TableView_Menu.getSelectionModel().getSelectedItem();

        confirmBox(Menu.getName(), Menu.getPrice());
    }

    private void init_TableView_Menu() {

        String sql = "SELECT * FROM menu WHERE cafe_name = ?";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, TakingCoffee.GiftCafe.getCafename());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("menu_name");
                String price = resultSet.getString("price");

                // System.out.println("메뉴 이름 : "+menu_name+" 가격 : "+Price); 테스트용
                data.add(new Menu(name, price));

                TableColumn_Menu_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                TableColumn_Price.setCellValueFactory(new PropertyValueFactory<>("price"));

                TableView_Menu.setItems(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Menu {

        private String mname; // **오류 조심 menu_name같이 언더바 붙인 이름은 인식을 못함!
        private String price;

        private Menu(String n, String p) {
            this.mname = n;
            this.price = p;
        }

        public String getName() {
            return mname;
        }

        public void setName(String n) {
            this.mname = n;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String p) {
            this.price = p;
        }
    }

    public void confirmBox(String MenuName, String Price) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("메뉴 이름 : " + MenuName + "\n" + "가격 : " + Price + "을 선택하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    //확인을 누르면 메뉴와 가격을 메인에 저장한다.
                    TakingCoffee.GiftCafe.Menu.setMenuName(MenuName);
                    TakingCoffee.GiftCafe.Menu.setPrice(Price);

                    // 그리고 step3로 넘어간다.
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("PayForCharge.fxml"));

                    Stage mainStage; //Here is the magic. We get the reference to main Stage.
                    mainStage = TakingCoffee.parentWindow;
                    mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Click_ImageView_Order_heading(MouseEvent event) throws Exception {

        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/order/Order_Main.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Click_ImageView_Mypage_heading(MouseEvent event) throws Exception {

        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/mypage/My_Page.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Click_ImageView_Gift_heading(MouseEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/gift/Gift.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Click_ImageView_Review_heading(MouseEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/review/Review_FirstPage.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Click_ImageView_Store_heading(MouseEvent event) throws Exception {
        infoBox("카페 사장님 전용 메뉴 입니다.", "안내", null);
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
