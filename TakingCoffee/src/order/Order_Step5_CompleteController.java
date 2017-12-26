package order;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import static takingcoffee.Login_ConsumerController.infoBox;
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Order_Step5_CompleteController implements Initializable {

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
    private Label Label_Bill;
    @FXML
    private Label Label_step5;
    @FXML
    private Label Label_Bill_Title;
    @FXML
    private Button BTN_Finish;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int OrderID;

    public Order_Step5_CompleteController() {
        connection = takingcoffee.util.ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (TakingCoffee.Consumer_OrderInfo.getOp() == null) {

            Label_Bill.setText("카페: " + TakingCoffee.Consumer_OrderInfo.getCafename() + "\n" + "메뉴: " + TakingCoffee.Consumer_OrderInfo.getMenuname() + "\n"
                    + "도착 예정 시간: " + TakingCoffee.Consumer_OrderInfo.getArrTime() + "\n"
                    + "결제 방식: " + TakingCoffee.Consumer_OrderInfo.getPaymentType());
        } else {
            Label_Bill.setText("카페: " + TakingCoffee.Consumer_OrderInfo.getCafename() + "\n" + "메뉴: " + TakingCoffee.Consumer_OrderInfo.getMenuname() + "\n"
                    + "도착 예정 시간: " + TakingCoffee.Consumer_OrderInfo.getArrTime() + "\n"
                    + "결제 방식: " + TakingCoffee.Consumer_OrderInfo.getPaymentType() + "\n"
                    + "선택 사항: " + TakingCoffee.Consumer_OrderInfo.getOp());
        }

    }

    @FXML
    private void Push_BTN_Finish(ActionEvent event) throws Exception {
        confirmBox();
    }

    private void Send_OrderInfo_To_DB() {
        String id = TakingCoffee.Consumer_OrderInfo.getConsumerid();
        String CafeName = TakingCoffee.Consumer_OrderInfo.getCafename();
        String MenuName = TakingCoffee.Consumer_OrderInfo.getMenuname();
        String amount = TakingCoffee.Consumer_OrderInfo.getAmount();
        String ArrTime = TakingCoffee.Consumer_OrderInfo.getArrTime();
        String PaymentType = TakingCoffee.Consumer_OrderInfo.getPaymentType();
        int menu_complement = TakingCoffee.Consumer_OrderInfo.getMenucomplete();
        int menu_receipt = TakingCoffee.Consumer_OrderInfo.getMenureceipt();
        String Option = TakingCoffee.Consumer_OrderInfo.getOp();

        String sql = "INSERT INTO orderinfo(consumer_id, cafe_name, menu_name, amount, arrTime, paymentType, menu_complete, menu_receipt, op) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //9개

        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, CafeName);
            preparedStatement.setString(3, MenuName);
            preparedStatement.setString(4, amount);
            preparedStatement.setString(5, ArrTime);
            preparedStatement.setString(6, PaymentType);
            preparedStatement.setInt(7, menu_complement);
            preparedStatement.setInt(8, menu_receipt);
            preparedStatement.setString(9, Option);
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                OrderID = rs.getInt(1); //전역변수로 선언된 OrderID에 auto_increment로 생성된 order_id값을 넣어준다.
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Send_ConsumerAndOrder_To_DB() {
        String id = TakingCoffee.Consumer_OrderInfo.getConsumerid();

        String sql = "INSERT INTO ConsumerAndOrder (consumer_id, order_id) VALUES(?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, OrderID);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Make_Gifticon_ToBe_Used() {
        int gift_id = TakingCoffee.Gifticon.getGiftId();
        
        //System.out.print(gift_id);
        
        String sql = "UPDATE gifticon SET used = 1 WHERE gift_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, gift_id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmBox() { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");

            alert.setContentText("상기 내역대로 주문하시겠어요?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    infoBox("메인 화면으로 되돌아갑니다.", "안내", null);

                    Send_OrderInfo_To_DB();
                    Send_ConsumerAndOrder_To_DB();

                    if (TakingCoffee.Consumer_OrderInfo.getPaymentType().compareTo("기프티콘") == 0) {
                        Make_Gifticon_ToBe_Used();
                    }

                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("/takingcoffee/AfterLogin_Consumer.fxml"));

                    Stage mainStage; //Here is the magic. We get the reference to main Stage.
                    mainStage = TakingCoffee.parentWindow;
                    mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.ㄹ

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("취소 메시지");
                subAlert.setContentText("최종 주문이 취소되었습니다.");
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
