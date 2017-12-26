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
public class CurrentOrderController implements Initializable {

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
    private TableView<OrderInfo> TB_CurrentOrder;
    @FXML
    private TableColumn<OrderInfo, ?> OrderNumber_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderInfo, ?> ClientID_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderInfo, ?> OrderBeverage_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderInfo, ?> OrderAmount_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderInfo, ?> PaymentType_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderInfo, Void> CompleteMaking_TB_CurrentOrder;//컴플맅
    @FXML
    private TableColumn<OrderInfo, Void> CheckReceipt_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderInfo, ?> ArrivalTime_TB_CurrentOrder;
    @FXML
    private TableColumn<OrderInfo, Void> Option_TB_CurrentOrder;
    @FXML
    private ImageView ImageView_Lens;

    // private final Button btn = new Button("제작완료");
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<OrderInfo> data = FXCollections.observableArrayList();//tableView는 항상 observableList로 뿌려진다.

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_TableView_CurrentOrder();// TODO
    }

    public CurrentOrderController() {//컨트롤러 생성자는 DB와 연결!!!!!!!!!항상 습관적으로 ,,,,,,,,해두길,,
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

    private void init_TableView_CurrentOrder() {

        ButtonController BCR = new ButtonController();

        try {//obeservableList에 gabiaDB에 잇는 Consumer의 data를 저장한다.

            String sql = "select * from orderinfo where cafe_name = ? and  menu_receipt=0";
            //카페 이름은 manager에서 받아오고, 제작 미완료 && 미수령 음료를 "현재 처리해야 할 음료"로 구분, 읽어들여옴
            String CafeName = TakingCoffee.Manager.getCafename();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, CafeName);
            resultSet = preparedStatement.executeQuery();
            /*
             * 잠깐 쓸 data를 사용하려면 data.add(new Consumer(x,x,x, ....));로 잠깐 쓰고 사라지게하면 됨.
             */
            while (resultSet.next()) {
                int orderid = resultSet.getInt("order_id");
                String consumerid = resultSet.getString("consumer_id");
                String Cafename = resultSet.getString("cafe_name");
                String menuname = resultSet.getString("menu_name");
                String amount = resultSet.getString("amount");
                String arrTime = resultSet.getString("arrTime");
                String paymentType = resultSet.getString("paymentType");
                int menucomplete = resultSet.getInt("menu_complete");
                int menureceipt = resultSet.getInt("menu_receipt");
                String option = resultSet.getString("op");

                data.add(new OrderInfo(orderid, consumerid, Cafename, menuname, amount, arrTime, paymentType, menucomplete, menureceipt, option));
            }

            /* 
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone_number");
                String Beans = resultSet.getString("BeanAmount");

              //  data.add(new Consumer(name, phone, Beans, null, null, null, null));

            }
             */
            //order id && order num
            OrderNumber_TB_CurrentOrder.setCellFactory(new LineNumbersCellFactory());
            ClientID_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>("consumerid"));//" " 안에는 consumer()에 있는 이름과 같아야함
            OrderBeverage_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>("menuname"));
            OrderAmount_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>("amount"));
            ArrivalTime_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>("arrTime"));
            PaymentType_TB_CurrentOrder.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

            BCR.addCompleteButton(TB_CurrentOrder, CompleteMaking_TB_CurrentOrder);
            BCR.ReceivedButton(TB_CurrentOrder, CheckReceipt_TB_CurrentOrder);
            BCR.addOptionButton(TB_CurrentOrder, Option_TB_CurrentOrder);
            ArrivalTime_TB_CurrentOrder.setSortType(TableColumn.SortType.ASCENDING);//도착예정시간 오름차순으로 정렬

            TB_CurrentOrder.setItems(data);
            TB_CurrentOrder.getSortOrder().add(ArrivalTime_TB_CurrentOrder);//정렬에 필요한 메소드
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
}
