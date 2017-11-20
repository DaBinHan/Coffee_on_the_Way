/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypage;

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
