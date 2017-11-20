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
import javafx.event.Event;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Leejinnyeong
 */
public class My_FavoriteController implements Initializable {

    // My_Favorite.fxml에 들어가면 '자주 가는 매장 목록'(TB_FavoriteList)과 '커피 들고 가는 길 등록 매장 목록'(TB_RegisterList)을 조회할 수 있다.
    /* 자주 가는 매장을 추가하고 싶다면 '커피 들고 가는 길 등록 매장 목록'에서 해당 매장을 선택한 후 추가 버튼을 누르면 된다.
       직접 카페 이름을 검색하여 자주 가는 매장에 등록하는 것은 추후 구현 예정이다. */
    // 자주 가는 매장을 삭제하고 싶다면 '자주 가는 매장 목록'에서 해당 매장을 선택한 후 삭제 버튼을 누르면 된다.
    @FXML
    private TableView<CafeDetails> TB_FavoriteList;
    @FXML
    private TableView<CafeDetails> TB_RegisterList;
    @FXML
    private TableColumn<CafeDetails, String> TB_FavoriteName;
    @FXML
    private TableColumn<CafeDetails, String> TB_RegisterName;
    @FXML
    private Button BTN_Delete;
    @FXML
    private Button BTN_Add;

    private XMLCafeManager cafeManager;
    private ObservableList<CafeDetails> data;
    private HashMap hm;
    private HashMap temphm;

    private XMLFavoriteManager favoriteManager;
    @FXML
    private TextField Text_InputCafe;
    @FXML
    private Button BTN_Add2;
    @FXML
    private Button BTN_BackToMenu_Consumer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTB_FavoriteList();
        initTB_RegisterList();
    }

    @FXML   //메소드에서도 @FXML해줘야 한다. 이것을 안하면 선택을 눌러도 아무런 변화가 없다.
    private void btnAdd(ActionEvent event) {
        favoriteManager = new XMLFavoriteManager();
        hm = new HashMap();
        HashMap SelectList = new HashMap();

        try {
            SelectList = favoriteManager.readXML("./src/xml", "favoritecafe_info.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //현재 선택된 정보를 가지고 오는 부분
        CafeDetails CafeDetails = TB_RegisterList.getSelectionModel().getSelectedItem();
        hm.put("id", CafeDetails.getId());
        hm.put("name", CafeDetails.getName());

        // 수강 신청한 정보가 없거나, 동일한 수강신청 정보가 없을 때 == 추가하는 값이 중복되는 값이 아닐 때
        if (SelectList == null || SelectList.get(CafeDetails.getId()) == null) {
            try {
                favoriteManager.editXML("./src/xml", "favoritecafe_info.xml", hm);

                Alert.AlertType AlterType = null;
                Alert alert = new Alert(AlterType.INFORMATION);
                alert.setTitle("안내");
                alert.setHeaderText("자주 가는 매장 추가");
                alert.setContentText("매장 이름 : " + hm.get("name").toString().trim());
                alert.showAndWait().ifPresent(rs
                        -> {
                    if (rs == ButtonType.OK) {
                        alert.close();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert.AlertType AlterType = null;
            Alert alert = new Alert(AlterType.ERROR);
            alert.setTitle("안내");
            alert.setHeaderText("중복");
            alert.setContentText("이미 추가된 매장입니다.\n");
            alert.showAndWait().ifPresent(rs
                    -> {
                if (rs == ButtonType.OK) {
                    alert.close();
                }
            });
        }

        initTB_FavoriteList();
    }

    /*
    @FXML   
    private void btnAdd2(ActionEvent event){    // 직접 카페 검색해서 추가하는 메소드 (후에 구현)
        favoriteManager = new XMLFavoriteManager();
        hm = new HashMap();
        HashMap SelectList = new HashMap();
        
        try
        {
            SelectList = favoriteManager.readXML("C:\\Users\\Leejinnyeong\\Documents\\NetBeansProjects\\mypage", "favoritecafe_info.xml");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        //현재 선택된 정보를 가지고 오는 부분
        CafeDetails CafeDetails = TB_RegisterList.getSelectionModel().getSelectedItem();    // 이부분만 input값으로 바꾸면 될 듯
        hm.put("id", CafeDetails.getId());
        hm.put("name", CafeDetails.getName());  
        
        // 수강 신청한 정보가 없거나, 동일한 수강신청 정보가 없을 때 == 추가하는 값이 중복되는 값이 아닐 때
        if(SelectList == null || SelectList.get(CafeDetails.getId()) == null)
        {
            try
            {
                favoriteManager.editXML("C:\\Users\\Leejinnyeong\\Documents\\NetBeansProjects\\mypage", "favoritecafe_info.xml", hm);
            
                Alert.AlertType AlterType = null;
                Alert alert = new Alert(AlterType.INFORMATION);
                alert.setTitle("안내");
                alert.setHeaderText("자주 가는 매장 추가");
                alert.setContentText("매장 이름 : " + hm.get("name").toString().trim());
                alert.showAndWait().ifPresent(rs ->
                {
                    if(rs == ButtonType.OK)
                    {
                        alert.close();
                    }
                });
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Alert.AlertType AlterType = null;
            Alert alert = new Alert(AlterType.ERROR);
            alert.setTitle("안내");
            alert.setHeaderText("중복");
            alert.setContentText("이미 추가된 매장입니다.\n");
            alert.showAndWait().ifPresent(rs ->
            {
                if(rs == ButtonType.OK)
                {
                    alert.close();
                }
            });
        }
        
        initTB_FavoriteList();
    }
     */
    private void initTB_RegisterList() {
        cafeManager = new XMLCafeManager();
        data = FXCollections.observableArrayList();
        hm = new HashMap();

        // 파일 오픈 시 오류 출력
        try {
            hm = cafeManager.readXML("./src/xml", "cafe_info.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<String> iterator = hm.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            temphm = (HashMap) hm.get(key); //temporary hashmap
            data.add(new CafeDetails(key, temphm.get("name").toString().trim()));
        }

        TB_RegisterName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TB_RegisterList.setItems(null);
        TB_RegisterList.setItems(data);
    }

    private void tpChanged(Event event) {
        initTB_FavoriteList();
    }

    private void initTB_FavoriteList() {
        favoriteManager = new XMLFavoriteManager();
        data = FXCollections.observableArrayList();
        hm = new HashMap();

        // 파일 오픈 시 오류 출력
        try {
            hm = favoriteManager.readXML("./src/xml", "favoritecafe_info.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<String> iterator = hm.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            temphm = (HashMap) hm.get(key); //temporary hashmap
            data.add(new CafeDetails(key, temphm.get("name").toString().trim()));
        }

        TB_FavoriteName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TB_FavoriteList.setItems(null);
        TB_FavoriteList.setItems(data);
    }

    @FXML
    private void btnDeleteClick(ActionEvent event) {
        cafeManager = new XMLCafeManager();
        CafeDetails cafeDetails = TB_FavoriteList.getSelectionModel().getSelectedItem();

        try {

            // 여기는 confirmation 아래는 information
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("자주 가는 매장 삭제");
            alert.setContentText("매장 이름 : " + cafeDetails.getName() + "\n자주 가는 매장 목록에서 삭제하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    cafeManager.editXML("./src/xml", "favoritecafe_info.xml", cafeDetails.getId());
                    Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                    subAlert.setTitle("안내");
                    subAlert.setHeaderText("자주 가는 매장 삭제 완료");
                    subAlert.setContentText("매장 이름 : " + cafeDetails.getName() + "\n자주 가는 매장 목록에서 삭제되었습니다.");
                    Optional<ButtonType> rs = subAlert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("자주 가는 매장 삭제 철회");
                subAlert.setContentText("자주 가는 매장 삭제가 철회되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initTB_FavoriteList(); // 갱신을 하기 위해
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
