/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypage;

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
 * @author Leejinnyeong
 */
public class My_PageController implements Initializable {

    /* My_Page.fxml은 '자주 가는 매장 목록'(BTN_FavoriteList), '나만의 메뉴 목록'(BTN_MyMenuList),
        '원두 충전'(BTN_Charge), '주문 내역'(BTN_OrderList) 버튼을 갖는다. */
    @FXML
    private Button BTN_FavoriteList;
    @FXML
    private Button BTN_MyMenuList;
    @FXML
    private Button BTN_Charge;
    @FXML
    private Button BTN_OrderList;
    @FXML
    private Button BTN_BackToMenu_Consumer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // '자주 가는 매장 목록'(BTN_FavoriteList)을 누르면 My_Favorite.fxml로 이동한다.
    // '나만의 메뉴 목록'(BTN_MyMenuList)을 누르면 My_Menu.fxml로 이동한다.
    // '원두 충전'(BTN_Charge)을 누르면 Charge.fxml로 이동한다.
    // '주문 내역'(BTN_OrderList)을 누르면 Order.fxml로 이동한다.
    @FXML
    private void btnFavoriteList(ActionEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("My_Favorite.fxml"));

        Stage mainStage;
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1);
    }

    @FXML
    private void btnMyMenuList(ActionEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("My_Menu.fxml"));

        Stage mainStage;
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1);
    }

    @FXML
    private void btnCharge(ActionEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Charge.fxml")); // 존재 x

        Stage mainStage;
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1);
    }

    @FXML
    private void btnOrderList(ActionEvent event) throws Exception {
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource("Order.fxml")); // 존재 x 

        Stage mainStage;
        mainStage = CoffeeTaking.parentWindow;
        mainStage.getScene().setRoot(window1);
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
