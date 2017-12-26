/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managing;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import takingcoffee.TakingCoffee;
import takingcoffee.util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author YEONCHAN
 */
public class FavMenuController implements Initializable {

    @FXML
    private ImageView ImageView_subtitle;
    @FXML
    private ImageView ImageView_MainTitle;
    @FXML
    private ImageView ImageView_Gift_heading;
    @FXML
    private ImageView ImageView_Review_heading;
    @FXML
    private ImageView ImageView_Store_heading;
    @FXML
    private ImageView ImageView_Mypage_heading;
    @FXML
    private ImageView ImageView_Order_heading;
    @FXML
    private ImageView ImageView_StoreSearch;
    @FXML
    private TextField TextField_StoreSearch;
    @FXML
    private Button BTN_StoreSearch;
    @FXML
    private ImageView ImageView_Lens;
    @FXML
    private Label Label_Logout;
    @FXML
    private Label Label_CustomerService;
    @FXML
    private Label Label_AboutUs;
    @FXML
    private Separator Separator_first;
    @FXML
    private Separator Separator_second;
    @FXML
    private TableView< viewFavMenu> TB_OrderNum;
    @FXML
    private TableColumn<?, ?> OrderNum_TC_number;
    @FXML
    private TableColumn<?, ?> OrderNum_TC_menu;
    @FXML
    private TableColumn<viewFavMenu, ?> OrderNum_TC_count;
    @FXML
    private TableView<viewFavMenu> TB_ReviewScore;
    @FXML
    private TableColumn<?, ?> ReviewScore_TC_number;
    @FXML
    private TableColumn<?, ?> ReviewScore_TC_menu;
    @FXML
    private TableColumn<viewFavMenu, ?> ReviewScore_TC_score;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement2 = null;
    ResultSet resultSet2 = null;

    private ObservableList<viewFavMenu> data = FXCollections.observableArrayList();
    private ObservableList<viewFavMenu> data2 = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_TB_OrderNum();// TODO
        init_TB_ReviewScore();
    }

    public FavMenuController() {
        connection = ConnectionUtil.connectdb();
    }
//넘버링 하는 거

    public class LineNumbersCellFactory<T, E> implements Callback<TableColumn<T, E>, TableCell<T, E>> {

        public LineNumbersCellFactory() {
        }

        public TableCell<T, E> call(TableColumn<T, E> param) {
            return new TableCell<T, E>() {
                @Override
                protected void updateItem(E item, boolean empty) {
                    super.updateItem(item, empty);

                    if (!empty) {
                        setText(this.getTableRow().getIndex() + 1 + "");
                    } else {
                        setText("");
                    }
                }
            };
        }
    }

    private void init_TB_OrderNum() {

        try {
            String sql = "select * from menu where cafe_name=?";
            String CafeName = TakingCoffee.Manager.getCafename();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, CafeName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int cnt = 0;
                try {
                    String sql2 = "select * from orderinfo where cafe_name=?";
                    preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setString(1, CafeName);
                    resultSet2 = preparedStatement2.executeQuery();

                    while (resultSet2.next()) {

                        if (resultSet.getString("menu_name").equals(resultSet2.getString("menu_name"))) {
                            cnt++;
                        } else {
                            continue;
                        }
                    }
                    String menuName = resultSet.getString("menu_name");
                    String count = String.valueOf(cnt);

                    data.add(new viewFavMenu(menuName, count));

                    OrderNum_TC_number.setCellFactory(new FavMenuController.LineNumbersCellFactory());
                    OrderNum_TC_menu.setCellValueFactory(new PropertyValueFactory<>("menu"));
                    OrderNum_TC_count.setCellValueFactory(new PropertyValueFactory<>("numb"));
                    OrderNum_TC_count.setSortType(TableColumn.SortType.DESCENDING);

                    TB_OrderNum.setItems(data);
                    TB_OrderNum.getSortOrder().add(OrderNum_TC_count);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init_TB_ReviewScore() {
        try {
            String sql = "select * from menu where cafe_name=?";
            String CafeName = TakingCoffee.Manager.getCafename();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, CafeName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int cnt = 0;
                int score = 0;
                try {
                    String sql2 = "select * from Review where cafe_name=? and menu_name =?";
                    preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setString(1, CafeName);
                    preparedStatement2.setString(2, resultSet.getString("menu_name"));
                    resultSet2 = preparedStatement2.executeQuery();

                    while (resultSet2.next()) {
                        cnt++;
                        score += Integer.parseInt(resultSet2.getString("star"));
                    }

                    String menuName = resultSet.getString("menu_name");
                    if (cnt == 0) {
                        data2.add(new viewFavMenu(menuName, "리뷰 없음"));
                    } else {
                        String RVScore = Float.toString(score / cnt);

                        data2.add(new viewFavMenu(menuName, RVScore));
                    }
                    ReviewScore_TC_number.setCellFactory(new FavMenuController.LineNumbersCellFactory());
                    ReviewScore_TC_menu.setCellValueFactory(new PropertyValueFactory<>("menu"));
                    ReviewScore_TC_score.setCellValueFactory(new PropertyValueFactory<>("numb"));
                    ReviewScore_TC_score.setSortType(TableColumn.SortType.DESCENDING);

                    TB_ReviewScore.setItems(data2);
                    TB_ReviewScore.getSortOrder().add(ReviewScore_TC_score);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ManagingClicked(MouseEvent event) throws IOException {
        Parent window1;//Parent 객체 만드는 이유 ,, 가장 조상 클래스,,, 얘를 계속 override하면 다른 class와 호환이 쉬워서??
        window1 = FXMLLoader.load(getClass().getResource("SelectFunction.fxml"));//getResource뒤에 다음에 들어가고 싶은 화면을 넣음. 

        Stage mainStage;
        mainStage = TakingCoffee.parentWindow;//기존 패키지가 아니라 메인이 있는 패키지로 접근해서 띄워준다.
        mainStage.getScene().setRoot(window1);//we dont need to change whole scene,

    }

    public class viewFavMenu {

        private SimpleStringProperty menu;
        private SimpleStringProperty numb;

        public viewFavMenu(String menu, String numb) {
            this.menu = new SimpleStringProperty(menu);
            this.numb = new SimpleStringProperty(numb);
        }

        public String getMenu() {
            return menu.get();
        }

        public void setMenu(SimpleStringProperty menu) {
            this.menu = menu;
        }

        public String getNumb() {
            return numb.get();
        }

        public void setNumb(SimpleStringProperty numb) {
            this.numb = numb;
        }

    }

}
