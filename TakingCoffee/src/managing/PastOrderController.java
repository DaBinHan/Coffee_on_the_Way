/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managing;

import ClassObj.OrderInfo;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import takingcoffee.TakingCoffee;
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author YEONCHAN
 */
public class PastOrderController implements Initializable {

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
    private TableView<viewPastOrder> TB_PastOrder;
    @FXML
    private TableColumn<?, ?> OrderNumber_PastOrder;
    @FXML
    private TableColumn<?, ?> CustomerID_PastOrder;
    @FXML
    private TableColumn<?, ?> OrderBeverage_PastOrder;
    @FXML
    private TableColumn<?, ?> OrderAmount_PastOrder;
    @FXML
    private TableColumn< viewPastOrder, ?> ArrivalTime_PastOrder;
    @FXML
    private TableColumn<OrderInfo, String> CheckReceipt_PastOrder;
    @FXML
    private ImageView ImageView_Lens;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<viewPastOrder> data = FXCollections.observableArrayList();//tableView는 항상 observableList로 뿌려진다.

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_TableView_PastOrder(); // TODO
    }

    public PastOrderController() {//컨트롤러 생성자는 DB와 연결!!!!!!!!!항상 습관적으로 ,,,,,,,,해두길,,
        connection = ConnectionUtil.connectdb();
    }

    //넘버링 하는 거
    public class LineNumbersCellFactory<T, E> implements Callback<TableColumn<T, E>, TableCell<T, E>> {

        public LineNumbersCellFactory() {
        }

        public TableCell<T, E> call(TableColumn<T, E> param) {
            return new TableCell<T, E>() {
                @Override
                protected void updateItem(E item, boolean empty) {
                    super.updateItem(item, empty);

                    if (!empty) {
                        setText(this.getTableRow().getIndex() + 1 + "");
                    } else {
                        setText("");
                    }
                }
            };
        }
    }

    private void init_TableView_PastOrder() {
        try {//obeservableList에 gabiaDB에 잇는 Consumer의 data를 저장한다.

            String sql = "select * from orderinfo where cafe_name = ? ";
            //과거주문정보는 제작완료/수령여부 관계없이 select
            String CafeName = TakingCoffee.Manager.getCafename();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, CafeName);
            resultSet = preparedStatement.executeQuery();
            /*
             * 잠깐 쓸 data를 사용하려면 data.add(new Consumer(x,x,x, ....));로 잠깐 쓰고 사라지게하면 됨.
             */
            while (resultSet.next()) {
                //int orderid = resultSet.getInt("order_id");

                String ConsumerId = resultSet.getString("consumer_id");
                //String Cafename = resultSet.getString("cafe_name");
                String MenuName = resultSet.getString("menu_name");
                String Amount = resultSet.getString("amount");
                String ArrTime = resultSet.getString("arrTime");
                //String paymentType = resultSet.getString("paymentType");
                String MenuReceipt = new String();

                if (resultSet.getInt("menu_receipt") == 0) {//주문이 들어와서 제작중이거나 제작완료후 수령대기중인 음료
                    MenuReceipt = "대기중";
                } else if (resultSet.getInt("menu_receipt") == 1) {
                    MenuReceipt = "수령";
                } else {//menu_receipt==2 인 경우
                    MenuReceipt = "미수령";
                }

                // String option = resultSet.getString("op");
                data.add(new viewPastOrder(ConsumerId, MenuName, Amount, ArrTime, MenuReceipt));

            }

            OrderNumber_PastOrder.setCellFactory(new PastOrderController.LineNumbersCellFactory());
            CustomerID_PastOrder.setCellValueFactory(new PropertyValueFactory<>("ConsumerId"));//" " 안에는 consumer()에 있는 이름과 같아야함
            OrderBeverage_PastOrder.setCellValueFactory(new PropertyValueFactory<>("MenuName"));
            OrderAmount_PastOrder.setCellValueFactory(new PropertyValueFactory<>("Amount"));
            ArrivalTime_PastOrder.setCellValueFactory(new PropertyValueFactory<>("ArrTime"));
            CheckReceipt_PastOrder.setCellValueFactory(new PropertyValueFactory<>("MenuReceipt"));

            ArrivalTime_PastOrder.setSortType(TableColumn.SortType.DESCENDING);//도착예정시간 오름차순으로 정렬
            TB_PastOrder.setItems(data);
            TB_PastOrder.getSortOrder().add(ArrivalTime_PastOrder);//정렬에 필요한 메소드
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void ManagingClicked(MouseEvent event) throws IOException {
        Parent window1;//Parent 객체 만드는 이유 ,, 가장 조상 클래스,,, 얘를 계속 override하면 다른 class와 호환이 쉬워서??
        window1 = FXMLLoader.load(getClass().getResource("SelectFunction.fxml"));//getResource뒤에 다음에 들어가고 싶은 화면을 넣음. 

        Stage mainStage;//
        mainStage = TakingCoffee.parentWindow;//여기에 접근해서 내가 원하는 창을 계속 띄어줘야함. 그래서 public으로 선언.
        mainStage.getScene().setRoot(window1);//we dont need to change whole scene,

    }

    public class viewPastOrder {

        private SimpleStringProperty ConsumerId;
        private SimpleStringProperty MenuName;
        private SimpleStringProperty Amount;
        private SimpleStringProperty ArrTime;
        private SimpleStringProperty MenuReceipt;

        public viewPastOrder(String ConsumerId, String MenuName, String Amount, String ArrTime, String MenuReceipt) {
            this.ConsumerId = new SimpleStringProperty(ConsumerId);
            this.MenuName = new SimpleStringProperty(MenuName);
            this.Amount = new SimpleStringProperty(Amount);
            this.ArrTime = new SimpleStringProperty(ArrTime);
            this.MenuReceipt = new SimpleStringProperty(MenuReceipt);
        }

        public String getConsumerId() {
            return ConsumerId.get();
        }

        public void setConsumerId(SimpleStringProperty ConsumerId) {
            this.ConsumerId = ConsumerId;
        }

        public String getMenuName() {
            return MenuName.get();
        }

        public void setMenuName(SimpleStringProperty MenuName) {
            this.MenuName = MenuName;
        }

        public String getAmount() {
            return Amount.get();
        }

        public void setAmount(SimpleStringProperty Amount) {
            this.Amount = Amount;
        }

        public String getArrTime() {
            return ArrTime.get();
        }

        public void setArrTime(SimpleStringProperty ArrTime) {
            this.ArrTime = ArrTime;
        }

        public String getMenuReceipt() {
            return MenuReceipt.get();
        }

        public void setMenuReceipt(SimpleStringProperty MenuReceipt) {
            this.MenuReceipt = MenuReceipt;
        }

    }

}
