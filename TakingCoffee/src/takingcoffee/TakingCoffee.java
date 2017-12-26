package takingcoffee;

import ClassObj.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static takingcoffee.TakingCoffee.Manager;

/**
 *
 * @author DaBin
 */
public class TakingCoffee extends Application {

    public static Stage parentWindow;
    public static boolean IsConsumer = false;
    public static boolean IsManager = false;

    public static Consumer Consumer;
    public static Manager Manager;

    //주문하기
    public static Cafe SelectedCafe; // 주문시 선택된 카페 정보 카페 선택시 객체 생성
    public static OrderInfo Consumer_OrderInfo;
    public static Gifticon Gifticon;
    
    //마이 페이지
    public static Cafe MyCafe = new Cafe(null); // 생성자로 객체 생성을 해줘야 접근 가능
    
    //선물하기
    public static Cafe GiftCafe = new Cafe(null); // 생성자로 객체 생성을 해줘야 접근 가능
    public static String receiverId;

    //리뷰하기
    public static ReviewInfo ReviewInfo;
    
   

    @Override
    public void start(Stage stage) throws Exception {
        parentWindow = stage;

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

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
