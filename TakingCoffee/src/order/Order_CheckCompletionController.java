package order;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Order_CheckCompletionController implements Initializable {

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
    private TableView<ViewOrderInfo> TableView_ChkComp;
    @FXML
    private TableColumn<ViewOrderInfo, String> TableColumn_ChkComp_Cafe;
    @FXML
    private TableColumn<ViewOrderInfo, String> TableColumn_ChkComp_Menu;
    @FXML
    private TableColumn<ViewOrderInfo, String> TableColumn_ChkComp_ArrT;
    @FXML
    private Button BTN_ChkComp;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<ViewOrderInfo> data = FXCollections.observableArrayList();

    public Order_CheckCompletionController() {
        connection = takingcoffee.util.ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_TableView_ChkComp();
    }

    private void init_TableView_ChkComp() {
        String ConsumerID = TakingCoffee.Consumer.getId();
        String sql = "Select order_id, cafe_name, menu_name, arrTime from orderinfo "
                + "where order_id = (Select order_id from ConsumerAndOrder where consumer_id = ? and orderinfo.order_id = ConsumerAndOrder.order_id)" // and orderinfo.order_id = ConsumerAndOrder.order_id 안 붙이면 둘 이상의 중복 쿼리를 가져오는 문제가 생긴다.
                + "and menu_receipt = 0"; // menu_receipt가 1이면 수령 2이면 미수령을 의미하므로 불러올 필요가 없다.

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ConsumerID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int orderid = resultSet.getInt("order_id");
                String Cafe_Name = resultSet.getString("cafe_name");
                String Menu_Name = resultSet.getString("menu_name");
                String ArrTime = resultSet.getString("arrTime");

                data.add(new ViewOrderInfo(orderid, Cafe_Name, Menu_Name, ArrTime));
                TableColumn_ChkComp_Cafe.setCellValueFactory(new PropertyValueFactory<>("CafeName"));
                TableColumn_ChkComp_Menu.setCellValueFactory(new PropertyValueFactory<>("MenuName"));
                TableColumn_ChkComp_ArrT.setCellValueFactory(new PropertyValueFactory<>("ArrTime"));
                TableView_ChkComp.setItems(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Push_BTN_ChkComp(ActionEvent event) {
        ViewOrderInfo ViewOrderInfo = TableView_ChkComp.getSelectionModel().getSelectedItem();
        int OrderID = ViewOrderInfo.getOrderId();
        
        String sql = "Select menu_complete from orderinfo where order_id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, OrderID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int menu_comp = resultSet.getInt("menu_complete");
                
                if (menu_comp == 0){
                    infoBox("음료가 아직 완성되지 않았습니다", "안내", null);
                }
                else{
                    infoBox("음료가 완성되었습니다!", "안내", null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewOrderInfo {

        private int OrderId;
        private SimpleStringProperty CafeName;
        private SimpleStringProperty MenuName;
        private SimpleStringProperty ArrTime;

        public ViewOrderInfo(int OrderId, String CafeName, String MenuName, String ArrTime) {
            this.OrderId = OrderId;
            this.CafeName = new SimpleStringProperty(CafeName);
            this.MenuName = new SimpleStringProperty(MenuName);
            this.ArrTime = new SimpleStringProperty(ArrTime);
        }

        public int getOrderId() {
            return OrderId;
        }

        public void setOrderId(int OrderId) {
            this.OrderId = OrderId;
        }

        public String getCafeName() {
            return CafeName.get();
        }

        public void setCafeName(SimpleStringProperty CafeName) {
            this.CafeName = CafeName;
        }

        public String getMenuName() {
            return MenuName.get();
        }

        public void setMenuName(SimpleStringProperty MenuName) {
            this.MenuName = MenuName;
        }

        public String getArrTime() {
            return ArrTime.get();
        }

        public void setArrTime(SimpleStringProperty ArrTime) {
            this.ArrTime = ArrTime;
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
