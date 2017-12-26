//step2 리뷰 제목과 내용 작성 
package review;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Review_ContentController implements Initializable {

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
    private Label Label_ReviewTitle;
    @FXML
    private TextField TextField_ReviewTitle;
    @FXML
    private TextArea TextArea_ReviewContent;
    @FXML
    private Button BTN_next;
    @FXML
    private Label Label_cafename;
    @FXML
    private Label Label_menuname;
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
            Label_cafename.setText(TakingCoffee.ReviewInfo.getCafename());
            Label_menuname.setText(TakingCoffee.ReviewInfo.getMenuname());
        }

    }

    public Review_ContentController() {//생성자, db에 연결
        connection = ConnectionUtil.connectdb();
    }

    @FXML
    private void btnClick(ActionEvent event) throws Exception {
        //화면 정보 받아오기
        int id = TakingCoffee.ReviewInfo.getReviewid();
        String title = TextField_ReviewTitle.getText(); // text를 입력받아 string으로 전환
        String text = TextArea_ReviewContent.getText();
        
        //업데이트
        String sql = "UPDATE Review SET review_title= ?, review_text =? where review_id = ?";
        // sql문 하드코딩, atatement에선 안되고 prepared statement에서만 가능

        try {
            preparedStatement = connection.prepareStatement(sql);//db와 연결하고 sql을 실행
            preparedStatement.setString(1, title);//첫번째 물음표
            preparedStatement.setString(2, text);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
        }
        String consumerid = TakingCoffee.Consumer.getId();
        String cafename = TakingCoffee.ReviewInfo.getCafename();
        try {
            boolean a = checkFavCafe(consumerid, cafename);
            if (a == false) {
                confirmBox(cafename);
            }
        } catch (SQLException e) {
        }

        //나의 리뷰 리스트로
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Review_MyReview.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1);
    }

    public static void InfoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public void confirmBox(String cafename) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText(cafename + "을/를 자주가는 매장에 추가하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { // 확인을 누를 경우
                try {
                    String sql3 = "INSERT INTO favorite_cafe (consumer_id, cafe_name) values(?, ?)";
                    preparedStatement = null;
                    resultSet = null;
                    preparedStatement = connection.prepareStatement(sql3);
                    preparedStatement.setString(1, TakingCoffee.Consumer.getId());
                    preparedStatement.setString(2, cafename);
                    preparedStatement.executeUpdate();
                    InfoBox("자주 가는 매장 목록에 등록되었습니다.", null, null);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkFavCafe(String consumerid, String cafename) throws SQLException {
        //자주가는 매장목록 검색, 리뷰를 작성한 카페가 자주가는 매장 목록에 있으면 true, 없으면 false 반환
        String sql = "SELECT * FROM favorite_cafe WHERE consumer_id = ? and cafe_name = ?"; // sql문 하드코딩, atatement에선 안되고 prepared statement에서만 가능
        preparedStatement = null;
        resultSet = null;
        preparedStatement = connection.prepareStatement(sql);//db와 연결하고 sql을 실행
        preparedStatement.setString(1, consumerid);//첫번째 물음표
        preparedStatement.setString(2, cafename);//두번째 물음표
        resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {//자주가는 매장 목록에 이미 등록되어 있으면 
            //InfoBox("이 카페는 이미 자주가는 매장 목록에 등록되어 있습니다", "안내", null);
            return false;
        } else {//자주가는 매장 목록에 없으면
            return true;
        }
    }
}
