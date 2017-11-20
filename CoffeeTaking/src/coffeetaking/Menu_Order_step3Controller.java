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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Menu_Order_step3Controller implements Initializable {

    @FXML
    private Label Label_Main_Title;
    @FXML
    private ImageView ImageView_Main_Icon;
    @FXML
    private Label Label_step3;
    @FXML
    private Label Label_Position_Price;
    @FXML
    private Label Label_Price;
    @FXML
    private Button BTN_Postpayment;
    @FXML
    private Button BTN_Prepayment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Label_Price.setText(CoffeeTaking.TotalPrice + "원");
    }

    @FXML
    private void Push_BTN_Postpayment(ActionEvent event) throws Exception {
        CoffeeTaking.HowToPay = "후불";

        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Menu_Order_step4.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

}
