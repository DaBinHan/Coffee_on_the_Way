package mypage;

import Classobj.*;
import takingcoffee.util.ConnectionUtil;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author Leejinnyeong
 */
public class My_FavoriteController implements Initializable {

    // My_Favorite.fxml에 들어가면 '자주 가는 매장 목록'(TB_FavoriteList)과 '커피 들고 가는 길 등록 매장 목록'(TB_RegisterList)을 조회할 수 있다.
    /* 자주 가는 매장을 추가하고 싶다면 '커피 들고 가는 길 등록 매장 목록'에서 해당 매장을 선택한 후 추가 버튼을 누르면 된다.
       직접 카페 이름을 검색하여 자주 가는 매장에 등록하는 것은 추후 구현 예정이다. */
    // 자주 가는 매장을 삭제하고 싶다면 '자주 가는 매장 목록'에서 해당 매장을 선택한 후 삭제 버튼을 누르면 된다.
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
    private Label Label_FavoriteList;
    @FXML
    private TextField TextField_InputCafe;
    @FXML
    private TableView<FavoriteCafe> TB_FavoriteList;
    @FXML
    private TableColumn<FavoriteCafe, String> TB_FavoriteName;
    @FXML
    private Button BTN_Delete;
    @FXML
    private Button BTN_Add;

    // 아래 3줄은 모든 controller에 선언되어 있어야 한다. 언제 DB를 사용할 지 모르기 때문에.
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<FavoriteCafe> data = FXCollections.observableArrayList();

    // ** observable list를 새로 추가해서 btnAdd, btnDelete를 눌렀을 때 리스트가 리뉴얼되도록!
    private ObservableList<FavoriteCafe> data2 = FXCollections.observableArrayList();

    //controller 생상자에는 db와 연결하려는 문장을 적어줘야 한다.
    // 언제 db가 필요할 지 모르니깐 항상 써주자.
    public My_FavoriteController() {
        connection = ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTB_FavoriteList();
    }

    // ** observable list를 새로 추가해서 btnAdd, btnDelete를 눌렀을 때 리스트가 리뉴얼되도록!
    private void initTB_FavoriteList() {
        
        String sql = "SELECT * FROM favorite_cafe WHERE consumer_id = ?"; // sql문 하드코딩
        // table 이름과 column 이름이 맞는지 확인할 것
        // table 이름 : consumer , column 이름 : consumer_id와 password
        // ? 는 우리가 preparedstatement라는 객체를 사용하기 때문에 사용 가능함
        
        try {
            preparedStatement = connection.prepareStatement(sql);
            // connection. 는 connection으로 db에 걸어준다는 의미다.
            preparedStatement.setString(1, TakingCoffee.Consumer.getId());
            // 1번 물음표에는 회원의 id를 넣어라
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                // 우리가 저장했던 viewcafename 객체를 data(옵져버블리스트) 에 저장한다.
                String CafeName = resultSet.getString("cafe_name");
                String Id=resultSet.getString("f_id");
                data.add(new FavoriteCafe(Id, CafeName));
                
                TB_FavoriteName.setCellValueFactory(new PropertyValueFactory<>("CafeName"));
                // CafeName을 cafename으로 수정하면 테이블뷰에 뿌려지지 않고 있다...

                TB_FavoriteList.setItems(data);
                // tableview에 data를 보여준다

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public static class FavoriteCafeName{
        
        private SimpleStringProperty cafename;

        public FavoriteCafeName(String cafename) {
            this.cafename = new SimpleStringProperty(cafename);
        }

        public String getCafeName() {
            return cafename.get();
        }
    }
    */
    
    @FXML
    private void ImageViewClicked(MouseEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("My_Page.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1);
    }
    
    // ** btnAdd, btnDelete를 눌렀을 때 리스트가 리뉴얼되도록!
    @FXML
    private void btnInputAdd(ActionEvent event) {

        String favoritecafename = TextField_InputCafe.getText().toString(); // text를 입력받아 string으로 전환

        String sql1 = "SELECT * FROM cafe WHERE cafe_name = ?"; // sql문 하드코딩

        try {
            
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, favoritecafename);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                infoBox("제휴되지 않은 매장입니다!", null, null);
            }
            else{
                String sql2 = "SELECT * FROM favorite_cafe WHERE consumer_id =? and cafe_name = ?"; // sql문 하드코딩
                preparedStatement=null;
                resultSet=null;
                preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setString(1, TakingCoffee.Consumer.getId());
                preparedStatement.setString(2, favoritecafename);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    infoBox("이미 자주 가는 매장 목록에 등록된 카페입니다.", null, null);
                }
                else{
                    String sql3 = "INSERT INTO favorite_cafe (consumer_id, cafe_name) values(?, ?)";
                    preparedStatement=null;
                    resultSet=null;
                    preparedStatement = connection.prepareStatement(sql3);
                    preparedStatement.setString(1, TakingCoffee.Consumer.getId());
                    preparedStatement.setString(2, favoritecafename);
                    preparedStatement.executeUpdate();
                    infoBox("자주 가는 매장 목록에 등록되었습니다.", null, null);
                }
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

    public static void WelcomeBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
    
    /*
    @FXML
    private void tpChanged(Event event)     {
        initTB_FavoriteList();
    }
     */
    
    // ** btnAdd, btnDelete를 눌렀을 때 리스트가 리뉴얼되도록!
    @FXML
    public void btnDeleteClick(ActionEvent event) {
        
        FavoriteCafe FavoriteCafe = TB_FavoriteList.getSelectionModel().getSelectedItem();
        String favoritecafeid=FavoriteCafe.getId();
        int a=Integer.parseInt(favoritecafeid);
        confirmBox(a);
    }
    
    public void confirmBox(int a) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("자주 가는 매장 목록에서 삭제하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { // 확인을 누를 경우
                try {
                    String sql4 = "DELETE FROM favorite_cafe where f_id = ?";
                    // sql문 하드코딩, atatement에선 안되고 prepared statement에서만 가능
                    preparedStatement = connection.prepareStatement(sql4);//db와 연결하고 sql을 실행
                    preparedStatement.setInt(1, a);//첫번째 물음표                    
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //initTB_FavoriteList(); //테이블 갱신
                infoBox("자주 가는 매장 목록에서 삭제되었습니다.", null, null);
            }
            else if(result.get() == ButtonType.CANCEL)
                {
                    Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                    subAlert.setTitle("안내");
                    subAlert.setHeaderText("삭제 철회");
                    subAlert.setContentText("자주 가는 매장 삭제가 철회되었습니다.");
                    Optional<ButtonType> rs = subAlert.showAndWait();
                }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}