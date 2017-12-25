//리뷰하기의 첫번째 페이지
package review;

import ClassObj.ReviewInfo;
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
import takingcoffee.TakingCoffee;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Review_FirstPageController implements Initializable {

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
    private Button BTN_NewReview;
    @FXML
    private ImageView Img_NewReview;
    @FXML
    private Button BTN_ExistingReview;
    @FXML
    private ImageView Img_ExistingReview;
    @FXML
    private ImageView ImageView_Lens;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     @FXML
    private void btn_newReview(ActionEvent event) throws Exception{
//신규리뷰작성페이지로
    Parent window1;
    window1=FXMLLoader.load(getClass().getResource("Review_Star.fxml"));
    
    Stage mainStage;
    mainStage = TakingCoffee.parentWindow;
    mainStage.getScene().setRoot(window1);

    }
    @FXML
    private void btn_existingReview(ActionEvent event) throws Exception{
//기존리뷰조회로
    Parent window1;
    window1=FXMLLoader.load(getClass().getResource("Review_MyReview.fxml"));
    
    Stage mainStage;
    mainStage = TakingCoffee.parentWindow;
    mainStage.getScene().setRoot(window1);

    }

    
}
