package takingcoffee;

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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class SignUp_SelectionController implements Initializable {

    @FXML
    private ImageView ImageView_MainTitle;
    @FXML
    private ImageView ImageView_StoreSearch;
    @FXML
    private TextField TextField_StoreSearch;
    @FXML
    private Button BTN_StoreSearch;
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
    private Label Label_Logout1;
    @FXML
    private Separator Separator_first1;
    @FXML
    private Label Label_ConsumerSignUp;
    @FXML
    private Button BTN_ConsumerSignUp;
    @FXML
    private Label Label_ManagerSignUp;
    @FXML
    private Button BTN_ManagerSignUp;
    @FXML
    private ImageView ImageView_Lens;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void Push_BTN_ConsumerSignUp(ActionEvent event) throws IOException {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("SignUp_Consumer.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Push_BTN_ManagerSignUp(ActionEvent event) throws IOException {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("SignUp_Manager.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = TakingCoffee.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

}
