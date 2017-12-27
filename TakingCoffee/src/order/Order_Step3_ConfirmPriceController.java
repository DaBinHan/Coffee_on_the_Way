package order;

import ClassObj.OrderInfo;
import java.net.URL;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Order_Step3_ConfirmPriceController implements Initializable {

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
    private Label Label_step3;
    @FXML
    private Label Label_Position_Price;
    @FXML
    private Label Label_Position_Menu;
    @FXML
    private Button BTN_Prepayment;
    @FXML
    private Button BTN_Postpayment;
    @FXML
    private Label Label_Price;
    @FXML
    private Label Label_Menu;
    @FXML
    private Label Label_Position_Option;
    @FXML
    private Label Label_Option;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Set_Price_And_Menu();
    }

    @FXML // 후불은 카페 사장에 따라서 허용되거나 말거나
    private void Push_BTN_Postpayment(ActionEvent event) {
        confirmBox("후불");
    }

    @FXML
    private void Push_BTN_Prepayment(ActionEvent event) {

        int BeanAmount = Integer.parseInt(TakingCoffee.Consumer.getBeanAmount());
        int Price = Integer.parseInt(TakingCoffee.SelectedCafe.Menu.getPrice());

        if (BeanAmount < Price) {
            infoBox("원두가 부족합니다.\n마이페이지에서 충전해주세요.", "안내", null);
        } else {
            confirmBox("선불");
        }

    }

    private void Set_Price_And_Menu() {
        Label_Price.setText(TakingCoffee.SelectedCafe.Menu.getPrice() + "원");
        Label_Menu.setText(TakingCoffee.SelectedCafe.Menu.getMenuName());

        if (TakingCoffee.SelectedCafe.Menu.getOp() == null) {
            Label_Position_Option.setText(null);
            Label_Option.setText(null);
        } else {
            Label_Option.setText(TakingCoffee.SelectedCafe.Menu.getOp());
        }

        if (TakingCoffee.SelectedCafe.getPostpayavail() == 0) {
            BTN_Postpayment.setVisible(false);
        }
    }

    public void confirmBox(String PaymentType) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");

            alert.setContentText(PaymentType + "결제 하시겠어요?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                try {

                    //public OrderInfo(int orderid, String consumerid, String Cafename, String menuname, String amount, String arrTime, String paymentType, int menucomplete, int menureceipt, String option)
                    int orderid = 0; //DBMS에서 AutoIncrement되므로 아무 값이나 넣는다. 
                    String consumerid = TakingCoffee.Consumer.getId();
                    String Cafename = TakingCoffee.SelectedCafe.getCafename();
                    String menuname = TakingCoffee.SelectedCafe.Menu.getMenuName();
                    String amount = "1";
                    String arrTime = null; // 나중에 setarrTime으로 Step4에서 바꿔준다.
                    int menucomplete = 0; // 미완성이므로
                    int menureceipt = 0; // 미수령이므로
                    String option = TakingCoffee.SelectedCafe.Menu.getOp();

                    TakingCoffee.Consumer_OrderInfo = new OrderInfo(orderid, consumerid, Cafename, menuname, amount, arrTime, PaymentType, menucomplete, menureceipt, option);

                    // 그리고 step4로 넘어간다.
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("Order_Step4_ArrTime.fxml"));

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
                subAlert.setContentText("결제 선택이 취소되었습니다.");
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
