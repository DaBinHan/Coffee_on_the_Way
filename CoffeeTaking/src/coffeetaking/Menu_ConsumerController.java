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
public class Menu_ConsumerController implements Initializable {

    @FXML
    private Label Label_Main_Title;
    @FXML
    private ImageView ImageView_Main_Icon;
    @FXML
    private Button BTN_Order;
    @FXML
    private Button BTN_Review;
    @FXML
    private Button BTN_MyPage;
    @FXML
    private Button BTN_Present;
    @FXML
    private Label Label_ConsumerName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Label_ConsumerName.setText("무엇을 도와드릴까요? \n" + CoffeeTaking.ConsumerName +"님");
    }

    @FXML
    private void Push_BTN_Order(ActionEvent event) throws IOException {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Menu_Order_step1.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Push_BTN_Review(ActionEvent event) throws IOException {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/review/Review_FirstPage.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Push_BTN_MyPage(ActionEvent event) throws IOException {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/mypage/My_Page.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

    @FXML
    private void Push_BTN_Present(ActionEvent event) throws IOException {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/present/Present.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

}
