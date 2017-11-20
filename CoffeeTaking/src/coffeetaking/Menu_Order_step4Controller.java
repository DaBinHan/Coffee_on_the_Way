/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Menu_Order_step4Controller implements Initializable {

    @FXML
    private Label Label_Main_Title;
    @FXML
    private ImageView ImageView_Main_Icon;
    @FXML
    private Label Label_step4;
    @FXML
    private Label Label_info;
    @FXML
    private TextField TextField_Hour;
    @FXML
    private Button BTN_Select;
    @FXML
    private Label Label_Hour;
    @FXML
    private TextField TextField_Min;
    @FXML
    private Label Label_Min;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void Push_BTN_Select(ActionEvent event) throws Exception {
        CoffeeTaking.ArrivalHour = TextField_Hour.getText().toString();
        CoffeeTaking.ArrivalMin = TextField_Min.getText().toString();
        
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Menu_Order_step5.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }
}
