/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeetaking;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author DaBin
 */
public class CoffeeTaking extends Application {

    public static Stage parentWindow;

    // 매 씬이 공통적으로 공유하는 정보
    //(1) Consumer의 경우
    public static String ConsumerID = null;
    public static String ConsumerName = null;
    public static String SelectedCafe = null;
    public static String SelectedMenu = null;
    public static String TotalPrice = null;

    public static String HowToPay = null;

    public static String ArrivalHour = null;
    public static String ArrivalMin = null;

    //(2)카페 사장의 경우
    public static String CafeBossID = null;
    public static String CafeBossName = null;

    @Override
    public void start(Stage stage) throws Exception {
        parentWindow = stage;

        Parent root = FXMLLoader.load(getClass().getResource("Login_Select.fxml"));
       
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
