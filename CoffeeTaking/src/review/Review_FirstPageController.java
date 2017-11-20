/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package review;

import coffeetaking.CoffeeTaking;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Review_FirstPageController implements Initializable {

    @FXML
    private Button NewReview;
    @FXML
    private Button ExistingReview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     @FXML
    private void btn_newReview(ActionEvent event) throws Exception{
//신규리뷰작성으로, 아직 미구현
    Parent window1;
    window1=FXMLLoader.load(getClass().getResource("Review_content.fxml"));
    
    Stage mainStage;
    mainStage = CoffeeTaking.parentWindow;
    mainStage.getScene().setRoot(window1);
    //다음창으로
    }
     @FXML
    private void btn_existingReview(ActionEvent event) throws Exception{
//기존리뷰조회로
    Parent window1;
    window1=FXMLLoader.load(getClass().getResource("Review_MyReview.fxml"));
    
    Stage mainStage;
    mainStage = CoffeeTaking.parentWindow; // 메인 패키지의 parentWindow를 참조해야함.
    mainStage.getScene().setRoot(window1);
    //다음창으로
    }
    
}
