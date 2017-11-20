/*
 * 매장관리 중 주문관리 - 현재 주문관리만 구현하였습니다.
 * XMLfile인 CurrentOrder_info에서 현재 주문을 따왔는데, 넘버링이랑 제작완료 버튼, 수령여부확인버튼 구현을 못했습니다.
 * 또한, 시간 순서대로 id를 부여하였는데 update될 때 아이디순으로 정렬되지 않는 것 같습니다.
 * 버튼안에 imageView는 절대경로로 설정하였는데 import시키면 아마 Scenebuilder에서 다시 지정해야될 것 같습니다. 
 * 그럴 경우를 대비해 압축파일안에 images 폴더에 jpg파일을 넣어놨습니다.
 */
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
public class OrderMgmtController implements Initializable {

    @FXML
    private Label Title_OrderMgmt;
    @FXML
    private ImageView Img_CurrentOrder;
    @FXML
    private ImageView Img_PastOrder;
    @FXML
    private Button BTN_CurrentOrder;
    @FXML
    private Button BTN_PastOrder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void CurrentOrderClicked(ActionEvent event) throws IOException{
        Parent window1;//Parent 객체 만드는 이유 ,, 가장 조상 클래스,,, 얘를 계속 override하면 다른 class와 호환이 쉬워서??
        window1 = FXMLLoader.load(getClass().getResource("CurrentOrder.fxml"));//getResource뒤에 다음에 들어가고 싶은 화면을 넣음. 
        
        Stage mainStage ;//
        mainStage = CoffeeTaking.parentWindow;//여기에 접근해서 내가 원하는 창을 계속 띄어줘야함. 그래서 public으로 선언.
        mainStage.getScene().setRoot(window1);//we dont need to change whole scene,
    }
    
}
