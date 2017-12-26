package order;

import java.net.URL;
import java.util.Calendar;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Order_Step4_ArrTimeController implements Initializable {

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
    private Label Label_step4;
    @FXML
    private Label Label_ArrTime_Choice;
    @FXML
    private ComboBox<String> ComboBox_AMPM;
    @FXML
    private ComboBox<String> ComboBox_Hour;
    @FXML
    private ComboBox<String> ComboBox_Min;
    @FXML
    private Button BTN_Choice;

    ObservableList<String> AMPM = FXCollections.observableArrayList("AM", "PM");
    ObservableList<String> Hour = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    ObservableList<String> Min = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "31", "32", "33", "34", "35", "36", "37", "38", "39",
            "41", "42", "43", "44", "45", "46", "47", "48", "49",
            "51", "52", "53", "54", "55", "56", "57", "58", "59");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboBox_AMPM.setItems(AMPM);
        ComboBox_Hour.setItems(Hour);
        ComboBox_Min.setItems(Min);

    }

    //ConfirmBox 만들어야함
    @FXML
    private void Push_BTN_Choice(ActionEvent event) {
        Calendar cal = Calendar.getInstance();

        //현재 년도, 월, 일
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int date = cal.get(cal.DATE);

        String Year = Integer.toString(year);
        String Month = Integer.toString(month);
        String Date = Integer.toString(date);

        String AMPM = ComboBox_AMPM.getSelectionModel().getSelectedItem();

        String Hour = ComboBox_Hour.getSelectionModel().getSelectedItem();

        if (AMPM.compareTo("AM") == 0) {
            int IntHour = Integer.parseInt(Hour);

            if (IntHour < 10) {
                Hour = "0" + Hour;
            }
        } else if (AMPM.compareTo("PM") == 0) {
            int IntHour = Integer.parseInt(Hour) + 12;
            Hour = Integer.toString(IntHour);
        }

        String Min = ComboBox_Min.getSelectionModel().getSelectedItem();
        int IntMin = Integer.parseInt(Min);
        if (IntMin < 10) {
            Min = "0" + Min;
        }

        // "yyyy-MM-dd hh:mm:ss"
        String ArrTime = Year + "-" + Month + "-" + Date + " " + Hour + ":" + Min + ":" + "00";

        
        //** 시간을 비교하는 부분 **//
        //현재 시각과 분
        int cur_hour = cal.get(Calendar.HOUR);
        int cur_min = cal.get(Calendar.MINUTE);
        if (cur_hour > Integer.parseInt(Hour)) {

            infoBox("현재 시각 이후의 시간을 입력해주세요.", "안내", null);
        } else if (cur_hour == Integer.parseInt(Hour) && cur_min >= Integer.parseInt(Min)) {
            infoBox("현재 시각 이후의 시간을 입력해주세요.", "안내", null);
        } else {
            confirmBox(ArrTime);
        }
    }

    public void confirmBox(String ArrTime) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");

            alert.setContentText("도착 예정 시간을 정하시겠어요?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    //Date 저장 형식인 "yyyy-MM-dd hh:mm:ss" 형식대로 OrderInfo에 저장.
                    TakingCoffee.Consumer_OrderInfo.setArrTime(ArrTime);

                    // 그리고 step5로 넘어간다.
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("Order_Step5_Complete.fxml"));

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
                subAlert.setContentText("도착 예정 시간 선택이 취소되었습니다.");
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
