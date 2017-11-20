package coffeetaking;

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
 * @author DaBin
 */
public class Menu_Order_step5Controller implements Initializable {

    @FXML
    private ImageView ImageView_Main_Icon;
    @FXML
    private Label Label_Main_Title;
    @FXML
    private Label Label_step5;
    @FXML
    private Label Label_Bill;
    @FXML
    private Label Label_Bill_Title;
    @FXML
    private Button BTN_IsCompleted;
    @FXML
    private Button BTN_BackToMenu_Consumer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Label_Bill.setText("카페: "+CoffeeTaking.SelectedCafe+"\n"+"메뉴: "+CoffeeTaking.SelectedMenu+"\n"+
                "도착 예정 시간: "+CoffeeTaking.ArrivalHour+"시 "+CoffeeTaking.ArrivalMin+"분"+"\n"+
                        "결제 상태: "+CoffeeTaking.HowToPay);
        
    }    
    
    @FXML
    private void Push_BTN_BackToMenu_Consumer(ActionEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Menu_Consumer.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }
    
}
