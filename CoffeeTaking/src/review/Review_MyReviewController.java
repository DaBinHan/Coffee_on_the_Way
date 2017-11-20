package review;

import coffeetaking.CoffeeTaking;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Review_MyReviewController implements Initializable {

    @FXML
    private TableView<Review_Detail> MyReviewList;
    @FXML
    private TableColumn<Review_Detail, String> CafeName_Col;
    @FXML
    private TableColumn<Review_Detail, String> Title_Col;
    @FXML
    private Button BTN_inquir;
    @FXML
    private Button BTN_delete;
    @FXML
    private Button BTN_BackToMenu_Consumer;

    private XMLReviewListManager rlManager;
    //private XMLReview_contentManager rcManager;
    private ObservableList<Review_Detail> data;
    private HashMap hm;
    private HashMap temphm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initMyReview();
    }

    public void initMyReview() {
        //나의 리뷰 테이블 초기화
        rlManager = new XMLReviewListManager();
        data = FXCollections.observableArrayList();
        hm = new HashMap();

        // 파일 오픈 시 오류 출력
        try {
            hm = rlManager.readXML("./src/xml", "review_list.xml"); // 상대경로 이용
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<String> iterator = hm.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            temphm = (HashMap) hm.get(key);

            data.add(new Review_Detail(key, temphm.get("cafe").toString().trim(), temphm.get("title").toString().trim(), temphm.get("text").toString().trim()));
        }

        CafeName_Col.setCellValueFactory(new PropertyValueFactory<>("cafe"));
        Title_Col.setCellValueFactory(new PropertyValueFactory<>("title"));

        MyReviewList.setItems(null);
        MyReviewList.setItems(data);

    }

    @FXML
    private void btnInqure(ActionEvent event) throws IOException {
        //조회버튼
        /*  Stage stage = new Stage();
        
        Parent root = FXMLLoader.load(getClass().getResource("Review_inq.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();*/

        rlManager = new XMLReviewListManager();
        hm = new HashMap();
        HashMap SelectList = new HashMap();

        try {
            SelectList = rlManager.readXML("./src/xml", "review_list.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //현재 선택된 정보를 가지고 오는 부분
        Review_Detail rDetails = MyReviewList.getSelectionModel().getSelectedItem();
        hm.put("id", rDetails.getId());
        hm.put("cafe", rDetails.getCafe());
        hm.put("title", rDetails.getTitle());
        hm.put("text", rDetails.getText());

        // 리뷰정보를 팝업창에 띄운다
        try {
            //selectManager.editXML("C:\\Users\\User\\Documents\\NetBeansProjects\\Review", "review_list.xml", hm);

            Alert.AlertType AlterType = null;
            Alert alert = new Alert(AlterType.INFORMATION);
            alert.setTitle("안내");
            alert.setHeaderText("리뷰 정보");
            alert.setContentText("선택사항\n" + "카페 : " + hm.get("cafe").toString().trim()
                    + "\n제목 : " + hm.get("title").toString().trim() + "\n내용 : " + hm.get("text").toString().trim());
            alert.showAndWait().ifPresent(rs
                    -> {
                if (rs == ButtonType.OK) {
                    alert.close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        //삭제버튼
        rlManager = new XMLReviewListManager();
        Review_Detail r = MyReviewList.getSelectionModel().getSelectedItem();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("리뷰삭제 메시지");
            alert.setContentText("리뷰 정보\n" + "카페 : " + r.getCafe() + "\n제목 : " + r.getTitle() + "\n내용 : " + r.getText() + "\n를 삭제하십니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    rlManager.editXML("./src/xml", "review_list.xml", r.getId());
                    Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                    subAlert.setTitle("안내");
                    subAlert.setHeaderText("리뷰삭제 메시지");
                    subAlert.setContentText("삭제 완료되었습니다.");
                    Optional<ButtonType> rs = subAlert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("리뷰 삭제 철회");
                subAlert.setContentText("리뷰 삭제가 철회되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initMyReview();//테이블 갱신
    }

    @FXML
    private void Push_BTN_BackToMenu_Consumer(ActionEvent event) throws Exception{
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/coffeetaking/Menu_Consumer.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }

}
