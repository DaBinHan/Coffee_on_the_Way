/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managing;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
 * @author YEONCHAN
 */
public class SelectFunctionController implements Initializable {

    @FXML
    private ImageView ImageView_MainTitle;
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
    private Button BTN_FavMenu;
    @FXML
    private ImageView Img_FavMenu;
    @FXML
    private Button BTN_PayMethod;
    @FXML
    private ImageView Img_PayMethod;
    @FXML
    private Button BTN_OrderMgmt;
    @FXML
    private ImageView Img_OrderMgmt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ManagingClicked(MouseEvent event) throws IOException {
        Parent window1;//Parent 객체 만드는 이유 ,, 가장 조상 클래스,,, 얘를 계속 override하면 다른 class와 호환이 쉬워서??
        window1 = FXMLLoader.load(getClass().getResource("SelectFunction.fxml"));//getResource뒤에 다음에 들어가고 싶은 화면을 넣음. 

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;//기존 패키지가 아니라 메인이 있는 패키지로 접근해서 띄워준다.
        mainStage.getScene().setRoot(window1);//we dont need to change whole scene,

    }

    @FXML
    private void FavMenuClicked(ActionEvent event) throws IOException {
        Parent window1;//Parent 객체 만드는 이유 ,, 가장 조상 클래스,,, 얘를 계속 override하면 다른 class와 호환이 쉬워서??
        window1 = FXMLLoader.load(getClass().getResource("FavMenu.fxml"));//getResource뒤에 다음에 들어가고 싶은 화면을 넣음. 

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;//기존 패키지가 아니라 메인이 있는 패키지로 접근해서 띄워준다.
        mainStage.getScene().setRoot(window1);//we dont need to change whole scene,

    }

    @FXML
    private void PayMethodClicked(ActionEvent event) throws IOException {
        Parent window1;//Parent 객체 만드는 이유 ,, 가장 조상 클래스,,, 얘를 계속 override하면 다른 class와 호환이 쉬워서??
        window1 = FXMLLoader.load(getClass().getResource("PayMethod.fxml"));//getResource뒤에 다음에 들어가고 싶은 화면을 넣음. 

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;//기존 패키지가 아니라 메인이 있는 패키지로 접근해서 띄워준다.
        mainStage.getScene().setRoot(window1);//we dont need to change whole scene,

    }

    @FXML
    private void OrderMgmtClicked(ActionEvent event) throws IOException {
        Parent window1;//Parent 객체 만드는 이유 ,, 가장 조상 클래스,,, 얘를 계속 override하면 다른 class와 호환이 쉬워서??
        window1 = FXMLLoader.load(getClass().getResource("OrderManage.fxml"));//getResource뒤에 다음에 들어가고 싶은 화면을 넣음. 

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;//기존 패키지가 아니라 메인이 있는 패키지로 접근해서 띄워준다.
        mainStage.getScene().setRoot(window1);//we dont need to change whole scene,

    }

  
}
