package mypage;

import ClassObj.MyMenuInfo;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import takingcoffee.util.ConnectionUtil;
import takingcoffee.*;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class My_MenuController implements Initializable {

    @FXML
    private TableView<MyMenuInfo> TB_MyMenuList;
    @FXML
    private TableColumn<MyMenuInfo, String> TB_CafeName;
    @FXML
    private TableColumn<MyMenuInfo, String> TB_MenuName;
    @FXML
    private TableColumn<MyMenuInfo, String> TB_MaterialQuantity;
    @FXML
    private Button BTN_Add;
    @FXML
    private Button BTN_Delete;
    @FXML
    private Button BTN_Modify;
    @FXML
    private SplitPane SplitPane_TableBelow;
    @FXML
    private Label Label_MyMenuList;
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
    private TextField TextField_InputMyCafe;
    @FXML
    private TextField TextField_InputMyMenu;
    @FXML
    private TextField TextField_InputMyQuantity;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<MyMenuInfo> data = FXCollections.observableArrayList();

    public My_MenuController() {
        connection = ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTB_MyMenuList();
    }

    @FXML
    private void btnAdd(ActionEvent event) {

        String mycafe = TextField_InputMyCafe.getText().toString(); // text를 입력받아 string으로 전환
        String mymenu = TextField_InputMyMenu.getText().toString(); // text를 입력받아 string으로 전환
        String myquantity = TextField_InputMyQuantity.getText().toString(); // text를 입력받아 string으로 전환

        String sql1 = "SELECT * FROM cafe WHERE cafe_name = ?"; // sql문 하드코딩

        try {

            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, mycafe);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("제휴되지 않은 매장입니다!", null, null);
            } else {
                String sql2 = "SELECT * FROM menu WHERE cafe_name = ? and menu_name = ?"; // sql문 하드코딩
                preparedStatement = null;
                resultSet = null;
                preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setString(1, mycafe);
                preparedStatement.setString(2, mymenu);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String sql3 = "INSERT INTO mymenu (consumer_id, cafe_name, menu_name, op) values (?, ?, ?, ?)";
                    preparedStatement = null;
                    resultSet = null;
                    preparedStatement = connection.prepareStatement(sql3);
                    preparedStatement.setString(1, TakingCoffee.Consumer.getId());
                    preparedStatement.setString(2, mycafe);
                    preparedStatement.setString(3, mymenu);
                    preparedStatement.setString(4, myquantity);
                    preparedStatement.executeUpdate();
                    infoBox("나만의 메뉴 목록에 등록되었습니다.", null, null);
                } else {
                    infoBox("카페에 없는 메뉴입니다.", null, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        data = FXCollections.observableArrayList();
        initTB_MyMenuList();
    }

    public String mycafename;
    public String mymenuname;
    public String myopt;

    @FXML
    private void btnModify(ActionEvent event) throws Exception {

        MyMenuInfo MyMenuInfo = TB_MyMenuList.getSelectionModel().getSelectedItem();
        mycafename = MyMenuInfo.getCafename();
        mymenuname = MyMenuInfo.getMenuname();
        myopt = MyMenuInfo.getOption();

        TakingCoffee.SelectedCafe.setCafename(mycafename);
        TakingCoffee.SelectedCafe.Menu.setMenuName(mymenuname);
        TakingCoffee.SelectedCafe.Menu.setOp(myopt);
        
        Parent root;
        root = FXMLLoader.load(getClass().getResource("My_Menu_Change.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 400, 205));
        stage.show();
    }

    
    @FXML
    public void btnDeleteClick(ActionEvent event) {
        MyMenuInfo MyMenuInfo = TB_MyMenuList.getSelectionModel().getSelectedItem();
        mycafename = MyMenuInfo.getCafename();
        mymenuname = MyMenuInfo.getMenuname();
        myopt = MyMenuInfo.getOption();
        confirmBox(mycafename, mymenuname, myopt);

        data = FXCollections.observableArrayList();
        initTB_MyMenuList();
    }

    private void initTB_MyMenuList() {

        String sql = "SELECT * FROM mymenu WHERE consumer_id = ?"; // sql문 하드코딩
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
                String MenuName = resultSet.getString("menu_name");
                String Option = resultSet.getString("op");
                data.add(new MyMenuInfo(CafeName, MenuName, Option));

                TB_CafeName.setCellValueFactory(new PropertyValueFactory<>("cafename"));
                TB_MenuName.setCellValueFactory(new PropertyValueFactory<>("menuname"));
                TB_MaterialQuantity.setCellValueFactory(new PropertyValueFactory<>("option"));

                TB_MyMenuList.setItems(data);
                // tableview에 data를 보여준다

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

    public void confirmBox(String mycafename, String mymenuname, String myopt) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("나만의 메뉴 목록에서 삭제하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { // 확인을 누를 경우
                try {
                    String sql4 = "DELETE FROM mymenu where cafe_name = ? and menu_name = ? and op = ?";
                    // sql문 하드코딩, atatement에선 안되고 prepared statement에서만 가능
                    preparedStatement = connection.prepareStatement(sql4);//db와 연결하고 sql을 실행
                    preparedStatement.setString(1, mycafename);//첫번째 물음표                    
                    preparedStatement.setString(2, mymenuname);//첫번째 물음표                    
                    preparedStatement.setString(3, myopt);//첫번째 물음표                    
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //initTB_FavoriteList(); //테이블 갱신
                infoBox("자주 가는 매장 목록에서 삭제되었습니다.", null, null);
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("삭제 철회");
                subAlert.setContentText("자주 가는 매장 삭제가 철회되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
