/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypage;

import coffeetaking.CoffeeTaking;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Leejinnyeong
 */
public class My_MenuController implements Initializable {

    @FXML
    private TableView<MyMenuDetails> TB_MyMenuList;
    @FXML
    private TableColumn<MyMenuDetails, String> TB_CafeName;
    @FXML
    private TableColumn<MyMenuDetails, String> TB_MenuName;
    @FXML
    private TableColumn<MyMenuDetails, String> TB_MaterialQuantity;

    @FXML
    private Button BTN_Delete;
    @FXML
    private Button BTN_Add;
    @FXML
    private Button BTN_Change;

    private XMLMyMenuManager mymenuManager;
    private ObservableList<MyMenuDetails> data2;
    private HashMap hm;
    private HashMap temphm;
    @FXML
    private Button BTN_BackToMenu_Consumer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initTB_MyMenuList();
    }

    private void initTB_MyMenuList() {
        mymenuManager = new XMLMyMenuManager();
        data2 = FXCollections.observableArrayList();
        hm = new HashMap();

        // 파일 오픈 시 오류 출력
        try {
            hm = mymenuManager.readXML("./src/xml", "mymenu_info.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<String> iterator = hm.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            temphm = (HashMap) hm.get(key); //temporary hashmap
            data2.add(new MyMenuDetails(key, temphm.get("cafename").toString().trim(), temphm.get("menuname").toString().trim(), temphm.get("materialquantity").toString().trim()));
        }

        TB_CafeName.setCellValueFactory(new PropertyValueFactory<>("cafename"));
        TB_MenuName.setCellValueFactory(new PropertyValueFactory<>("menuname"));
        TB_MaterialQuantity.setCellValueFactory(new PropertyValueFactory<>("materialquantity"));

        TB_MyMenuList.setItems(null);
        TB_MyMenuList.setItems(data2);
    }

    @FXML
    private void btnDeleteClick(ActionEvent event) {
        mymenuManager = new XMLMyMenuManager();
        MyMenuDetails mymenuDetails = TB_MyMenuList.getSelectionModel().getSelectedItem();

        try {

            // 여기는 confirmation 아래는 information
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("나만의 메뉴 삭제");
            alert.setContentText("나만의 메뉴 : " + mymenuDetails.getCafename() + "의 " + mymenuDetails.getMenuname() + " " + mymenuDetails.getMaterialquantity() + "\n나만의 메뉴 목록에서 삭제하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    mymenuManager.editXML("./src/xml", "mymenu_info.xml", mymenuDetails.getId());
                    Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                    subAlert.setTitle("안내");
                    subAlert.setHeaderText("나만의 메뉴 삭제 완료");
                    subAlert.setContentText("나만의 메뉴 : " + mymenuDetails.getCafename() + "의 " + mymenuDetails.getMenuname() + " " + mymenuDetails.getMaterialquantity() + "\n나만의 메뉴 목록에서 삭제되었습니다.");
                    Optional<ButtonType> rs = subAlert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("나만의 메뉴 삭제 철회");
                subAlert.setContentText("나만의 메뉴 삭제가 철회되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initTB_MyMenuList(); // 갱신을 하기 위해
    }

    @FXML
    private void Push_BTN_BackToMenu_Consumer(ActionEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("/coffeetaking/Menu_Consumer.fxml"));

        Stage mainStage; //Here is the magic. We get the reference to main Stage.
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.
    }
}
