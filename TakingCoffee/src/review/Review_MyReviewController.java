//나의 리뷰 조회, 수장, 삭제
package review;

import ClassObj.ReviewInfo;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Review_MyReviewController implements Initializable {

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
    private TableView<SubReview> MyReviewList;
    @FXML
    private TableColumn<SubReview, String> cafename;
    @FXML
    private TableColumn<SubReview, String> menuname;
    @FXML
    private TableColumn<SubReview, String> star;
    @FXML
    private TableColumn<SubReview, String> title;
    @FXML
    private Button BTN_inquir;
    @FXML
    private Button BTN_delete;
    @FXML
    private Button BTN_modify;

    //디비를 사용하기 위한 3세트
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<SubReview> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initMyReviewTable();
    }

    public Review_MyReviewController() {//생성자, db아 연결
        connection = ConnectionUtil.connectdb();
    }

    public void initMyReviewTable() {
        //나의 리뷰 테이블 초기화
        String sql = "SELECT * FROM Review WHERE consumer_id = ? "; // sql문 하드코딩, atatement에선 안되고 prepared statement에서만 가능

        try {
            String id = TakingCoffee.Consumer.getId();
            preparedStatement = null;
            resultSet = null;
            preparedStatement = connection.prepareStatement(sql);//db와 연결하고 sql을 실행
            preparedStatement.setString(1, id);//첫번째 물음표
            resultSet = preparedStatement.executeQuery();
            data.clear();

            while (resultSet.next()) {
                int Reviewid = resultSet.getInt("review_id");
                String CafeName = resultSet.getString("cafe_name");
                String MenuName = resultSet.getString("menu_name");
                String Star = resultSet.getString("star");
                String ReviewTitle = resultSet.getString("review_title");
                String Text = resultSet.getString("review_text");
                String ReviewDate = resultSet.getString("review_date");

                data.add(new SubReview(Reviewid, CafeName, MenuName, Star, ReviewTitle, Text, ReviewDate));

                cafename.setCellValueFactory(new PropertyValueFactory<>("cafename"));
                menuname.setCellValueFactory(new PropertyValueFactory<>("menuname"));
                star.setCellValueFactory(new PropertyValueFactory<>("star"));//db랑 상관이 없음, consumer field에서 이름을 가져오는 것이다.
                title.setCellValueFactory(new PropertyValueFactory<>("title"));

                MyReviewList.setItems(data);//update
            }
            /*table view에 들어갈 객체를 만들고, =consumer
             Simplestirngporoperty를 변수로 가지고 getter&setter
            ovservable list 사용하여 뿌린다.*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void btnInqure(ActionEvent event) throws IOException {
        //리뷰조회버튼
        SubReview SubReview = MyReviewList.getSelectionModel().getSelectedItem();
        InfoBox(SubReview.getCafename().toString(), SubReview.getMenuname().toString(), SubReview.getStar().toString(), SubReview.getTitle().toString(), SubReview.getText());
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        //삭제버튼
        SubReview SubReview = MyReviewList.getSelectionModel().getSelectedItem();
        String reviewid = SubReview.getReviewid();
        int a = Integer.parseInt(reviewid);
        confirmBox(a);

    }

    @FXML
    private void btnmodify(ActionEvent event) throws IOException {
        //리뷰수정버튼
        SubReview SubReview = MyReviewList.getSelectionModel().getSelectedItem();

        String reviewid = SubReview.getReviewid();
        int a = Integer.parseInt(reviewid);
        String ConId = TakingCoffee.Consumer.getId();;
        String CafeName = SubReview.getCafename();
        String MenuName = SubReview.getMenuname();
        String Star = SubReview.getStar();
        String ReviewTitle = SubReview.getTitle();
        String text = SubReview.getText();
        String date = SubReview.getDate();
        TakingCoffee.ReviewInfo = new ReviewInfo(a, ConId, CafeName, MenuName, Star, ReviewTitle, text, date);
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Review_StarMod.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1);
    }
    
    public void InfoBox(String cafename, String menuname, String star, String title, String text) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("리뷰 정보\n" + "카페 : " + cafename + "\n메뉴 : " + menuname + "\n별점 : " + star + " 점\n제목 : " + title + "\n내용 : " + text);
            alert.showAndWait().ifPresent(rs
                    -> {
                if (rs == ButtonType.OK) {
                    alert.close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmBox(int reviewid) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("리뷰를 삭제하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { // 확인을 누를 경우
                try {
                    String sql = "DELETE FROM Review where review_id = ?";
                    // sql문 하드코딩, atatement에선 안되고 prepared statement에서만 가능
                    preparedStatement = connection.prepareStatement(sql);//db와 연결하고 sql을 실행
                    preparedStatement.setInt(1, reviewid);//첫번째 물음표
                    preparedStatement.executeUpdate();

                    // 확인을 누를 경우 동작할 것 선택. 
                    // 메뉴 정보를 저장한다든지 등
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //테이블 갱신*/
                data = FXCollections.observableArrayList();
                initMyReviewTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class SubReview {

        private SimpleStringProperty reviewid;
        private SimpleStringProperty cafename;
        private SimpleStringProperty menuname;
        private SimpleStringProperty star;
        private SimpleStringProperty title;
        private SimpleStringProperty text;
        private SimpleStringProperty date;

        public SubReview(int reviewid, String cafename, String menuname, String star, String title, String text, String date) {
            this.reviewid = new SimpleStringProperty(String.valueOf(reviewid));
            this.cafename = new SimpleStringProperty(cafename);
            this.menuname = new SimpleStringProperty(menuname);
            this.star = new SimpleStringProperty(star);
            this.title = new SimpleStringProperty(title);
            this.text = new SimpleStringProperty(text);
            this.date = new SimpleStringProperty(date);
        }

        public String getCafename() {
            return cafename.get();
        }

        public void setCafename(SimpleStringProperty cafename) {
            this.cafename = cafename;
        }

        public String getMenuname() {
            return menuname.get();
        }

        public void setMenuname(SimpleStringProperty menuname) {
            this.menuname = menuname;
        }

        public String getStar() {
            return star.get();
        }

        public void setStar(SimpleStringProperty star) {
            this.star = star;
        }

        public String getTitle() {
            return title.get();
        }

        public void setTitle(SimpleStringProperty title) {
            this.title = title;
        }

        public String getReviewid() {
            return reviewid.get();
        }

        public String getText() {
            return text.get();
        }

        public void setText(SimpleStringProperty text) {
            this.text = text;
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(SimpleStringProperty date) {
            this.date = date;
        }
    }

}
