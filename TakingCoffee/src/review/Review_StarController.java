//step1 별점 신규 입력 페이지
package review;

import ClassObj.ReviewInfo;
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
public class Review_StarController implements Initializable {

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
    private Button BTN_next;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Review_StarController() {//생성자, db에 연결
        connection = ConnectionUtil.connectdb();
    }

    @FXML
    private void btn_next(ActionEvent event) throws Exception {
//정보 저장 후 다음 창으로

        String id = TakingCoffee.Consumer.getId();
        String cafename = TextField_cafename.getText().toString(); // text를 입력받아 string으로 전환
        String menuname = TextField_menuname.getText().toString();
        String star = TextField_star.getText().toString();
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = dayTime.format(new Date(time));

        int reviewid = 0;

        try {
            if (cafename.length() == 0) {
                InfoBox("카페이름을 넣어주십시오", "안내", null);
            } else if (menuname.length() == 0) {
                InfoBox("메뉴이름을 넣어주십시오", "안내", null);
            } else if (star.length() == 0) {
                InfoBox("별점을 넣어주십시오", "안내", null);
            } else if (star.length() != 0) {
                int s = Integer.parseInt(star);
                if (!(0 <= s && s <= 5)) {
                    InfoBox("0에서 5사이의 별점 값을 넣어주십시오", "안내", null);
                } else {
                    try {
                        String sql = "INSERT INTO Review(consumer_id, cafe_name, menu_name, star,review_date) values(?, ?, ?, ?,?)";
                        //카페, 메뉴, 별점 저장
                        // sql문 하드코딩, atatement에선 안되고 prepared statement에서만 가능
                        preparedStatement = connection.prepareStatement(sql);//db와 연결하고 sql을 실행
                        preparedStatement.setString(1, id);//첫번째 물음표
                        preparedStatement.setString(2, cafename);
                        preparedStatement.setString(3, menuname);
                        preparedStatement.setString(4, star);
                        preparedStatement.setString(5, str);
                        preparedStatement.executeUpdate();
                        

                    } catch (SQLException e) {
                        
                    }
                    try {//다음 페이지로 정보를 넘기기 위해 방금 추가한 행의 리뷰아이디를 가져와 저장
                        String sql2 = "SELECT * FROM Review WHERE consumer_id = ? and review_date = ?";//
                        preparedStatement = null;
                        resultSet = null;
                        preparedStatement = connection.prepareStatement(sql2);
                        preparedStatement.setString(1, id);
                        preparedStatement.setString(2, str);
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            reviewid = resultSet.getInt("review_id");
                        }
                    } catch (SQLException e) {
                        InfoBox("SQL오류", "안내", null);
                    }
                    //ReviewInfo에 저장
                    TakingCoffee.ReviewInfo = new ReviewInfo(reviewid, id, cafename, menuname, star, null, null, str);

                    //step2 제목과 내용 입력 페이지로
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("Review_Content.fxml"));

                    Stage mainStage;
                    mainStage = TakingCoffee.parentWindow;
                    mainStage.getScene().setRoot(window1);

                }
            }

        } catch (Exception e) {
            InfoBox("별점에 숫자를 넣어주십시오", "안내", null);
        }

    }

    public static void InfoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
