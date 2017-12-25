/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypage;

import Classobj.Consumer;
import Classobj.FavoriteCafe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Leejinnyeong
 */

public class Mypage extends Application {
    
    public static Stage parentWindow;
    // Stage 는 창 하나, scene은 창의 내용물
    
    // 그래서 회원정보는 (public) static으로 관리해야 함
    // new Consumer로 Consumer에 담을 객체를 만들어줘야 함
    static String id = "player";
    static String pw = "player123";
    static String name = "이진녕";
    static String phone = "01056636405";
    static String uniname = "서강대학교";
    static String Email = "jin3216@naver.com";
    static String BeanAmount = "0";
    
    public static Consumer Consumer = new Consumer(id, pw, name, phone, uniname, Email, BeanAmount);
    
    @Override
    public void start(Stage stage) throws Exception {
        
        parentWindow=stage;
        
        // '마이페이지'는 My_Page.fxml로 시작한다.
        Parent root = FXMLLoader.load(getClass().getResource("My_Page.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
