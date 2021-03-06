package mypage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author Leejinnyeong
 */
public class My_OrderController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    private Label Label_OrderList;
    @FXML
    private TableView<OrderInfo> TB_OrderList;
    @FXML
    private TableColumn<OrderInfo, String> TB_OrderDate;
    @FXML
    private TableColumn<OrderInfo, String> TB_OrderCafe;
    @FXML
    private TableColumn<OrderInfo, String> TB_OrderMenu;
    @FXML
    private TableColumn<OrderInfo, String> TB_TakeCheck;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<OrderInfo> data = FXCollections.observableArrayList();
    int flag = 0; // 미수령 경고 한번 출력하려는 flag

    public My_OrderController() {
        connection = ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initTB_OrderList();
    }

    private void initTB_OrderList() {

        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = dayTime.format(new Date(time));

        String sql = "SELECT * FROM orderinfo WHERE consumer_id = ?"; // sql문 하드코딩
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
                String ArrTime = resultSet.getString("arrTime");
                String CafeName = resultSet.getString("cafe_name");
                String MenuName = resultSet.getString("menu_name");
                String MenuReceipt;

                if (resultSet.getInt("menu_receipt") == 0) {
                    MenuReceipt = "수령 대기";
                } else if (resultSet.getInt("menu_receipt") == 1) {
                    MenuReceipt = "수령 완료";
                } else {
                    MenuReceipt = "미수령";
                }

                if (MenuReceipt.compareTo("미수령") == 0 && flag == 0) {
                    infoBox("미수령 이력이 있습니다.\n미수령 3회 이상 시 이용이 제한됩니다.", "안내", null);
                    flag = 1;
                }

                data.add(new OrderInfo(ArrTime, CafeName, MenuName, MenuReceipt));

                TB_OrderDate.setCellValueFactory(new PropertyValueFactory<>("arrtime"));
                TB_OrderCafe.setCellValueFactory(new PropertyValueFactory<>("cafename"));
                TB_OrderMenu.setCellValueFactory(new PropertyValueFactory<>("menuname"));
                TB_TakeCheck.setCellValueFactory(new PropertyValueFactory<>("menureceipt"));

                TB_OrderList.setItems(data);
                // tableview에 data를 보여준다
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class OrderInfo {

        private SimpleStringProperty arrtime;
        private SimpleStringProperty cafename;
        private SimpleStringProperty menuname;
        private SimpleStringProperty menureceipt;

        public OrderInfo(String arrtime, String cafename, String menuname, String menureceipt) {
            this.arrtime = new SimpleStringProperty(arrtime);
            this.cafename = new SimpleStringProperty(cafename);
            this.menuname = new SimpleStringProperty(menuname);
            this.menureceipt = new SimpleStringProperty(menureceipt);
        }

        public String getArrtime() {
            return arrtime.get();
        }

        public String getCafename() {
            return cafename.get();
        }

        public String getMenuname() {
            return menuname.get();
        }

        public String getMenureceipt() {
            return menureceipt.get();
        }

        public void setArrtime(String arrtime) {
            this.arrtime = new SimpleStringProperty(arrtime);
        }

        public void setCafename(String cafename) {
            this.cafename = new SimpleStringProperty(cafename);
        }

        public void setMenuname(String menuname) {
            this.menuname = new SimpleStringProperty(menuname);
        }

        public void setMenureceipt(String menureceipt) {
            this.menureceipt = new SimpleStringProperty(menureceipt);
        }
    }

    @FXML
    private void ImageViewClicked(MouseEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("My_Page.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1);
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
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

}
