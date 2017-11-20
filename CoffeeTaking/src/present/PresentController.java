
package present;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yousungwoo
 */
public class PresentController implements Initializable {

    @FXML
    private TextField SearchID;
    @FXML
    private TextField PhoneNumber;
    @FXML
    private Button btn_choose_review;

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void btnSelect1(ActionEvent event) throws IOException{
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("ChooseCafeMenu.fxml"));

        Stage mainStage;
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
