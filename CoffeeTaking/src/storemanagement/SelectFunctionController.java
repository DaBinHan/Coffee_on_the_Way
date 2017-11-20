package storemanagement;

import coffeetaking.CoffeeTaking;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author YEONCHAN
 */
public class SelectFunctionController implements Initializable {

    @FXML
    private ImageView Img_FavMenu;
    @FXML
    private ImageView Img_DiscountMenu;
    @FXML
    private ImageView Img_PayMethod;
    @FXML
    private ImageView Img_OrderMgmt;
    @FXML
    private Label Title_StoreMgmt;
    @FXML
    private Button BTN_FavMenu;
    @FXML
    private Button BTN_DiscountMenu;
    @FXML
    private Button BTN_PayMethod;
    @FXML
    private Button BTN_OrderMgmt;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    private void OrderMgmtClicked(ActionEvent event) throws IOException{
        Parent window1;//Parent 객체 만드는 이유 ,, 가장 조상 클래스,,, 얘를 계속 override하면 다른 class와 호환이 쉬워서??
        window1 = FXMLLoader.load(getClass().getResource("OrderMgmt.fxml"));//getResource뒤에 다음에 들어가고 싶은 화면을 넣음. 
        
        Stage mainStage ;
        mainStage = CoffeeTaking.parentWindow;//기존 패키지가 아니라 메인이 있는 패키지로 접근해서 띄워준다.
        mainStage.getScene().setRoot(window1);//we dont need to change whole scene,
        
    }

 

   
    
}
