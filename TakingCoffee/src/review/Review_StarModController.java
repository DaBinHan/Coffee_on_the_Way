//별점 수정
package review;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Review_StarModController implements Initializable {

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
    private Label Label_step2;
    @FXML
    private Label Label_cafename;
    @FXML
    private Button BTN_modify;
    @FXML
    private Label Label_menuname;
    @FXML
    private Label Label_menuname1;
    @FXML
    private TextField TextField_cafename;
    @FXML
    private TextField TextField_menuname;
    @FXML
    private TextField TextField_star;
    //디비를 사용하기 위한 3세트
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (TakingCoffee.ReviewInfo != null) {
            TextField_cafename.setText(TakingCoffee.ReviewInfo.getCafename());
            TextField_menuname.setText(TakingCoffee.ReviewInfo.getMenuname());
            TextField_star.setText(TakingCoffee.ReviewInfo.getStar());
        }
    }

    public Review_StarModController() {//생성자, db에 연결
        connection = ConnectionUtil.connectdb();
    }

    @FXML
    private void btn_modify(ActionEvent event) throws IOException {
        //정보를 수정하고 저장한다
        int reviewid = TakingCoffee.ReviewInfo.getReviewid();
        String cafename = null;
        cafename = TextField_cafename.getText();
        String menuname = null;
        menuname = TextField_menuname.getText();
        String star = null;
        star = TextField_star.getText().toString();

        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = dayTime.format(new Date(time));
        TakingCoffee.ReviewInfo.setCafename(cafename);
        TakingCoffee.ReviewInfo.setMenuname(menuname);

        try {
            if (cafename.length() == 0) {
                InfoBox("카페이름을 넣어주십시오", "안내", null);
            } else if (menuname.length() == 0) {
                InfoBox("메뉴이름을 넣어주십시오", "안내", null);
            } else if (!IsRightCafe(cafename)) {
                InfoBox("제휴되지 않은 매장입니다.", "안내", null);
            } else if (!IsRightMenu(cafename, menuname)) {
                InfoBox("해당 매장에 없는 메뉴입니다.", "안내", null);
            } else if (star.length() == 0) {
                InfoBox("별점을 넣어주십시오", "안내", null);
            } else if (star.length() != 0) {
                int s = Integer.parseInt(star);
                if (!(0 <= s && s <= 5)) {
                    InfoBox("0에서 5사이의 값을 넣어주십시오", "안내", null);
                } else {
                    try {//정보수정
                        String sql = "UPDATE Review SET cafe_name= ?, menu_name  =?, star =?, review_date =? where review_id = ?";
                        // sql문 하드코딩, atatement에선 안되고 prepared statement에서만 가능

                        preparedStatement = connection.prepareStatement(sql);//db와 연결하고 sql을 실행
                        preparedStatement.setString(1, cafename);//첫번째 물음표
                        preparedStatement.setString(2, menuname);
                        preparedStatement.setString(3, star);
                        preparedStatement.setString(4, str);
                        preparedStatement.setInt(5, reviewid);
                        preparedStatement.executeUpdate();

                    } catch (SQLException e) {
                    }

                    //step2 제목과 내용 입력 페이지로
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("Review_ContentMod.fxml"));

                    Stage mainStage;
                    mainStage = TakingCoffee.parentWindow;

                    mainStage.getScene().setRoot(window1);

                }
            }
        } catch (Exception e) {
            InfoBox("별점에 1~5사이의 정수를 넣어주십시오", "안내", null);
        }

    }

    public boolean IsRightCafe(String CafeName) {

        String sql = "Select * from cafe where cafe_name = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);//db와 연결하고 sql을 실행
            preparedStatement.setString(1, CafeName);//첫번째 물음표

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean IsRightMenu(String CafeName, String MenuName) {

        String sql = "Select * from menu where cafe_name = ? and menu_name = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);//db와 연결하고 sql을 실행
            preparedStatement.setString(1, CafeName);
            preparedStatement.setString(2, MenuName);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void InfoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
