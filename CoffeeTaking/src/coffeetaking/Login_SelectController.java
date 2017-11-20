package coffeetaking;

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
 * @author DaBin
 */
public class Login_SelectController implements Initializable {

    @FXML
    private ImageView ImageView_Main_Icon;
    @FXML
    private Label Label_CafeBoss;
    @FXML
    private Label Label_Consumer;
    @FXML
    private Button BTN_CafeBoss;
    @FXML
    private Button BTN_Consumer;
    @FXML
    private Label Label_Main_Title;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void Push_BTN_CafeBoss(ActionEvent event) throws IOException {

        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Login_Manager.fxml"));
        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

    }

    @FXML
    private void Push_BTN_Consumer(ActionEvent event) throws IOException {

        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Login_Consumer.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

    }

}
