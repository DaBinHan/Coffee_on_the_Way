package order;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Order_SubStep2_1_OptionChoiceController implements Initializable {

    @FXML
    private Label Label_OptionChoice;
    @FXML
    private TextField TextField_OptionChoice;
    @FXML
    private Button BTN_OptionWrite;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String OptionLimit = takingcoffee.TakingCoffee.SelectedCafe.Menu.getOp();
        TextField_OptionChoice.setPromptText(OptionLimit);
    }

    @FXML
    private void Push_BTN_OptionWrite(ActionEvent Event) {
        String OptionWrite = TextField_OptionChoice.getText().toString();

        if (OptionWrite == null) {
            takingcoffee.TakingCoffee.SelectedCafe.Menu.setOp(null);
            confirmBox();
        } else {
            takingcoffee.TakingCoffee.SelectedCafe.Menu.setOp(OptionWrite);
            confirmBox();
        }
    }

    public void confirmBox() { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("옵션 작성을 완료하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("Order_Step3_ConfirmPrice.fxml"));

                    Stage mainStage; //Here is the magic. We get the reference to main Stage.
                    mainStage = TakingCoffee.parentWindow;
                    mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

                    Stage stage = (Stage) BTN_OptionWrite.getScene().getWindow();
                    stage.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
              
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
