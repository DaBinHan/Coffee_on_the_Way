
package review;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Review_inqController implements Initializable {

    @FXML
    private TextField Inq_cafe;
    @FXML
    private TextField Inq_text;
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inq_cafe.setText("Arete");id를 못 전해줘서 어떤 리뷰를 띄워야 하는지를 파악할 수 없음
    }    
    
}
