
package review;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Review_contentController implements Initializable {

    @FXML
    private Button Content_next;
    @FXML
    private ScrollPane Content;
    @FXML
    private TextField Content_text;
    @FXML
    private ImageView Content_Img;
    @FXML
    private TextField Content_CafeName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
//입력받은 리뷰내용을 저장하고 다음페이지로
    @FXML
    private void btnClick(ActionEvent event) {
   /*     reviewManager = new XMLReview_contentManager();
        hm = new HashMap();
        HashMap SelectList = new HashMap();

        try {
            SelectList = reviewManager.readXML("C:\\Users\\User\\Documents\\NetBeansProjects\\Review", "review_content.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //현재 선택된 정보를 가지고 오는 부분

        //hm.put("id", R.getId());
        //hm.put("cafe", R.getCafe());
        //hm.put("text", R.getText());
        //리뷰저장
        try {
            //reviewManager.editXML("C:\\Users\\User\\Documents\\NetBeansProjects\\Review", "review.content.xml", hm);

            Alert.AlertType AlterType = null;
            Alert alert = new Alert(AlterType.INFORMATION);
            alert.setTitle("안내");
            alert.setHeaderText("리뷰 정보");
            alert.setContentText("선택사항\n" + "카페이름 : " + hm.get("cafe").toString().trim()
                    + "\n내용 : " + hm.get("text").toString().trim());
            alert.showAndWait().ifPresent(rs
                    -> {
                if (rs == ButtonType.OK) {
                    alert.close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
