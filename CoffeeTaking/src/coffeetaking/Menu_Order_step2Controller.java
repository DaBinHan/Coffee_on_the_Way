package coffeetaking;

import coffeetaking.util.ConnectionUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaBin
 */
public class Menu_Order_step2Controller implements Initializable {

    @FXML
    private Label Label_Main_Title;
    @FXML
    private ImageView ImageView_Main_Icon;
    @FXML
    private Label Label_step2;
    @FXML
    private TableView<Menu> TableView_Menu;
    @FXML
    private TableColumn<Menu, String> TableColumn_Menu_Name;
    @FXML
    private TableColumn<Menu, String> TableColumn_Price;
    @FXML
    private Button BTN_Select;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<Menu> data = FXCollections.observableArrayList();

    public Menu_Order_step2Controller() {
        connection = ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_TableView_Menu();
    }

    @FXML
    private void Push_BTN_Select(ActionEvent event) {
        Menu Menu = TableView_Menu.getSelectionModel().getSelectedItem();
        
        confirmBox(Menu.getName(), Menu.getPrice());

    }

    private void init_TableView_Menu() {

        String cafe_name = CoffeeTaking.SelectedCafe;

        String sql = "SELECT * FROM menu WHERE cafe_name = ?";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cafe_name);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String menu_name = resultSet.getString("menu_name");
                String Price = resultSet.getString("price");

                // System.out.println("메뉴 이름 : "+menu_name+" 가격 : "+Price); 테스트용
                data.add(new Menu(menu_name, Price));

            }

            TableColumn_Menu_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn_Price.setCellValueFactory(new PropertyValueFactory<>("price"));

            TableView_Menu.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class Menu {

        private final SimpleStringProperty name; // **오류 조심 menu_name같이 언더바 붙인 이름은 인식을 못함!
        private final SimpleStringProperty price;

        private Menu(String n, String p) {
            this.name = new SimpleStringProperty(n);
            this.price = new SimpleStringProperty(p);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String n) {
            name.set(n);
        }

        public String getPrice() {
            return price.get();
        }

        public void setPrice(String p) {
            price.set(p);
        }
    }

    public void confirmBox(String Menu_Name, String Price) { // 알림창
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("");
            alert.setContentText("카페 이름 : " + Menu_Name + "\n" + "가격 : " + Price + "을 선택하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    //확인을 누르면 메뉴와 가격을 메인에 저장한다.
                    CoffeeTaking.SelectedMenu = Menu_Name;
                    CoffeeTaking.TotalPrice = Price;

                    // 그리고 step3로 넘어간다.
                    Parent window1;
                    window1 = FXMLLoader.load(getClass().getResource("Menu_Order_step3.fxml"));

                    Stage mainStage; //Here is the magic. We get the reference to main Stage.
                    mainStage = CoffeeTaking.parentWindow;
                    mainStage.getScene().setRoot(window1); //we dont need to change whole sceene, only set new root.

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("취소 메시지");
                subAlert.setContentText("메뉴 선택이 취소되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
