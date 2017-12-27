package order;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Order_Step2_MenuChoiceController implements Initializable {

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
    private Label Label_step2;
    @FXML
    private TableView<ViewMenu> TableView_Menu;
    @FXML
    private TableColumn<ViewMenu, String> TableColumn_MenuName;
    @FXML
    private TableColumn<ViewMenu, String> TableColumn_Price;
    @FXML
    private TableColumn<ViewMenu, String> TableColumn_Option;
    @FXML
    private Button BTN_Menu_Choice;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<ViewMenu> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_TableView_Menu();
    }

    public Order_Step2_MenuChoiceController() {
        connection = takingcoffee.util.ConnectionUtil.connectdb();
    }

    private void init_TableView_Menu() {

        String cafe_name = TakingCoffee.SelectedCafe.getCafename();

        String sql = "SELECT * FROM menu WHERE cafe_name = ?";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cafe_name);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String menu_name = resultSet.getString("menu_name");
                String Price = resultSet.getString("price");
                String Option = resultSet.getString("op");

                // System.out.println("메뉴 이름 : "+menu_name+" 가격 : "+Price); 테스트용
                data.add(new ViewMenu(menu_name, Price, Option));

            }

            TableColumn_MenuName.setCellValueFactory(new PropertyValueFactory<>("MenuName"));
            TableColumn_Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
            TableColumn_Option.setCellValueFactory(new PropertyValueFactory<>("Option"));

            TableView_Menu.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Push_BTN_Menu_Choice(ActionEvent event) {

        ViewMenu ViewMenu = TableView_Menu.getSelectionModel().getSelectedItem();
        confirmBox(ViewMenu.getMenuName(), ViewMenu.getPrice(), ViewMenu.getOption());
    }

    public class ViewMenu {

        private SimpleStringProperty MenuName;
        private SimpleStringProperty Price;
        private SimpleStringProperty Option;

        public ViewMenu(String MenuName, String Price, String Option) {
            this.MenuName = new SimpleStringProperty(MenuName);
            this.Price = new SimpleStringProperty(Price);
            this.Option = new SimpleStringProperty(Option);
        }

        public String getMenuName() {
            return MenuName.get();
        }

        public void setMenuName(SimpleStringProperty MenuName) {
            this.MenuName = MenuName;
        }

        public String getPrice() {
            return Price.get();
        }

        public void setPrice(SimpleStringProperty Price) {
            this.Price = Price;
        }

        public String getOption() {
            return Option.get();
        }

        public void setOption(SimpleStringProperty Option) {
            this.Option = Option;
        }
    }

    public void confirmBox(String Menu_Name, String Price, String Option) { // 알림창

        if (Option == null) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("안내");
                alert.setHeaderText("");
                alert.setContentText("메뉴 이름 : " + Menu_Name + "\n" + "가격 : " + Price + "을 선택하시겠습니까?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        //확인을 누르면 메뉴와 가격을 메인에 저장한다.
                        TakingCoffee.SelectedCafe.Menu.setMenuName(Menu_Name);
                        TakingCoffee.SelectedCafe.Menu.setPrice(Price);

                        // 그리고 step3로 넘어간다.
                        Parent window1;
                        window1 = FXMLLoader.load(getClass().getResource("Order_Step3_ConfirmPrice.fxml"));

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
                    subAlert.setContentText("메뉴 선택이 취소되었습니다.");
                    Optional<ButtonType> rs = subAlert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("안내");
                alert.setHeaderText("");
                alert.setContentText("메뉴 이름 : " + Menu_Name + "\n" + "가격 : " + Price + "을 선택하시겠습니까?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        //확인을 누르면 메뉴와 가격을 메인에 저장한다.
                        TakingCoffee.SelectedCafe.Menu.setMenuName(Menu_Name);
                        TakingCoffee.SelectedCafe.Menu.setPrice(Price);
                        TakingCoffee.SelectedCafe.Menu.setOp(Option);

                        // 그리고 옵션을 적는 새 창을 띄운다.
                        Parent root;
                        root = FXMLLoader.load(getClass().getResource("Order_SubStep2_1_OptionChoice.fxml"));

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 400, 205));
                        stage.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (result.get() == ButtonType.CANCEL) {
                    Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                    subAlert.setTitle("안내");
                    subAlert.setHeaderText("취소 메시지");
                    subAlert.setContentText("메뉴 선택이 취소되었습니다.");
                    Optional<ButtonType> rs = subAlert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
