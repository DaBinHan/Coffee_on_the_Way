/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypage;

import Classobj.*;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;
import takingcoffee.util.ConnectionUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.TextArea;
import static mypage.My_FavoriteController.infoBox;

/**
 * FXML Controller class
 *
 * @author Leejinnyeong
 */
public class ChargeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
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
    private Label Label_HaveBean;
    @FXML
    private Label Label_BeanAmount;
    
    @FXML
    private Label Label_ChargeList;
    @FXML
    private TableView<ChargeInfo> TB_ChargeList;
    @FXML
    private TableColumn<ChargeInfo, String> TB_ChargeDate;
    @FXML
    private TableColumn<ChargeInfo, String> TB_ChargeAmount;
    @FXML
    private TableColumn<ChargeInfo, String> TB_DepositCheck;
    @FXML
    private TextField TextField_ChargeInput;
    @FXML
    private Button BTN_Charge;
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    private ObservableList<ChargeInfo> data = FXCollections.observableArrayList();
    private ObservableList<Consumer> data2 = FXCollections.observableArrayList();
    
    
    public ChargeController() {
        connection = ConnectionUtil.connectdb();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        init_BeanAmount();
        initTB_ChargeList();
    }
    
    private void init_BeanAmount(){
       
        String BeanAmount=TakingCoffee.Consumer.getBeanAmount();
        String sql = "SELECT * FROM consumer WHERE consumer_id = ?";
                       
        try {
            preparedStatement = connection.prepareStatement(sql);
            // connection. 는 connection으로 db에 걸어준다는 의미다.
            preparedStatement.setString(1, TakingCoffee.Consumer.getId());
            // 1번 물음표에는 회원의 id를 넣어라
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                BeanAmount = resultSet.getString("BeanAmount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
                
        Label_BeanAmount.setText(BeanAmount);
    }
    
    private void initTB_ChargeList(){
                
        String sql = "SELECT * FROM charge WHERE consumer_id = ?"; // sql문 하드코딩
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
                String ChargeDate = resultSet.getString("charge_date");
                String ChargeAmount = resultSet.getString("charge_amount");
                String BeanBefore = resultSet.getString("bean_before");
                String BeanAfter = resultSet.getString("bean_after");
                String DepositeCheck = resultSet.getString("deposit_check");
                data.add(new ChargeInfo(ChargeDate, ChargeAmount, BeanBefore, BeanAfter, DepositeCheck));
                
                TB_ChargeDate.setCellValueFactory(new PropertyValueFactory<>("chargedate"));
                TB_ChargeAmount.setCellValueFactory(new PropertyValueFactory<>("chargeamount"));
                TB_DepositCheck.setCellValueFactory(new PropertyValueFactory<>("depositcheck"));
                
                TB_ChargeList.setItems(data);
                // tableview에 data를 보여준다
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    public static class ChargeInfo{
        
        private SimpleStringProperty chargedate;
        private SimpleStringProperty chargeamount;
        private SimpleStringProperty beanbefore;
        private SimpleStringProperty beanafter;
        private SimpleStringProperty depositcheck;

        public ChargeInfo(String chargedate, String chargeamount, String beanbefore, String beanafter, String depositcheck) {
            this.chargedate =  new SimpleStringProperty(chargedate);
            this.chargeamount =  new SimpleStringProperty(chargeamount);
            this.beanbefore =  new SimpleStringProperty(beanbefore);
            this.beanafter =  new SimpleStringProperty(chargeamount+beanbefore);  //chargeamount+beanbefore;
            this.depositcheck =  new SimpleStringProperty(depositcheck);
        }

        public String getChargedate() {
            return chargedate.get();
        }

        public String getChargeamount() {
            return chargeamount.get();
        }

        public String getBeanbefore() {
            return beanbefore.get();
        }

        public String getBeanafter() {
            return beanafter.get();
        }

        public String getDepositcheck() {
            return depositcheck.get();
        }
    }
    */
    
    @FXML
    private void ImageViewClicked(MouseEvent event) throws Exception{
        Parent window1;
        window1=FXMLLoader.load(getClass().getResource("My_Page.fxml"));

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1);
    }
    
    @FXML
    private void btnCharge(ActionEvent event){
        
        String chargeinput = TextField_ChargeInput.getText().toString(); // text를 입력받아 string으로 전환
        String BeanAmount=TakingCoffee.Consumer.getBeanAmount();
        Integer amountafter=Integer.parseInt(chargeinput)+Integer.parseInt(BeanAmount);
        
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = dayTime.format(new Date(time));
        
        // btnCharge를 누르면 먼저 테이블 charge를 갱신한다.
        String sql1="INSERT INTO charge (consumer_id, charge_date, charge_amount, bean_before, bean_after, deposit_check) values (?, ?, ?, ?, ?, ?)";
        
        try {
            preparedStatement=null;
            resultSet=null;
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, TakingCoffee.Consumer.getId());
            preparedStatement.setString(2, str);
            preparedStatement.setString(3, chargeinput);
            preparedStatement.setString(4, TakingCoffee.Consumer.getBeanAmount());
            preparedStatement.setString(5, String.valueOf(amountafter));
            preparedStatement.setString(6, String.valueOf(0));
            preparedStatement.executeUpdate();
            infoBox("원두 " + chargeinput + "개가 충전되었습니다.", null, null);
            } catch (Exception e) {
            e.printStackTrace();
        }

        // btnCharge를 누르면 테이블 Consumer도 갱신해야 한다. (attribute BeanAmount가 있기 때문)
        String sql2 = "UPDATE consumer SET BeanAmount = ? where consumer_id = ?";
         try {
            preparedStatement=null;
            resultSet=null;
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, String.valueOf(amountafter));
            preparedStatement.setString(2, TakingCoffee.Consumer.getId());
            preparedStatement.executeUpdate();
           } catch (Exception e) {
            e.printStackTrace();
        }
         
        // run 되는 동안 Consumer 클래스 안의 BeanAmount 갱신
        // Consumer 클래스의 하드코딩을 없애고 로그인한 정보를 넣는다면 없어도 되는 코드다.
        TakingCoffee.Consumer.setBeanAmount(String.valueOf(amountafter));
    }
}
