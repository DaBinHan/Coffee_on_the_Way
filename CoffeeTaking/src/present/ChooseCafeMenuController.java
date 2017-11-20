/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yousungwoo
 */
public class ChooseCafeMenuController implements Initializable {

    @FXML
    private Button btn_next_CCM;
    @FXML
    private Tab TP_Cafe;
    @FXML
    private TableView<?> TB_Cafe;
    @FXML
    private TableColumn<?, ?> Name_TB_Cafe;
    @FXML
    private Tab TP_MenuNumber;
    @FXML
    private TableView<?> TB_MenuNumber;
    @FXML
    private TableColumn<?, ?> MenuName_TB_MenuNumber;
    @FXML
    private TextField SelectNumber;

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void btnSelect2(ActionEvent event) throws IOException{
        Parent window2;
        window2 = FXMLLoader.load(getClass().getResource("PayForCharge.fxml"));

        Stage mainStage;
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window2);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
