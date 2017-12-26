/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managing;

import ClassObj.OrderInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import takingcoffee.util.ConnectionUtil;

/**
 *
 * @author YEONCHAN
 */
public class ButtonController {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    PreparedStatement preparedStatement2 = null;

    public ButtonController() {
        connection = ConnectionUtil.connectdb();
    }

    void addCompleteButton(TableView tblView, TableColumn<OrderInfo, Void> TC) {//ì œìž‘ ì™„ë£Œ ë²„íŠ¼

        Callback<TableColumn<OrderInfo, Void>, TableCell<OrderInfo, Void>> cellFactory = new Callback<TableColumn<OrderInfo, Void>, TableCell<OrderInfo, Void>>() {
            @Override
            public TableCell<OrderInfo, Void> call(final TableColumn<OrderInfo, Void> param) {
                final TableCell<OrderInfo, Void> cell = new TableCell<OrderInfo, Void>() {

                    private final Button btn = new Button("제작완료");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                int selectedIndex = getTableRow().getIndex();//0, 1, 2, 3 ...ì에 대해서

                                OrderInfo selectedInfo = (OrderInfo) tblView.getItems().get(selectedIndex);

                                String sql = "UPDATE orderinfo SET menu_complete = '1' WHERE order_id=?";
                                preparedStatement = connection.prepareStatement(sql);
                                preparedStatement.setInt(1, selectedInfo.getOrderid());
                                preparedStatement.executeUpdate();

                                btn.setVisible(false);//버튼 사라지게
                                /*
                             *
                            OrderInfo data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                             *
                                 */

                            } catch (SQLException e) {
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        TC.setCellFactory(cellFactory);

    }

    void ReceivedButton(TableView tblView, TableColumn<OrderInfo, Void> TC) {
        Callback<TableColumn<OrderInfo, Void>, TableCell<OrderInfo, Void>> cellFactory = new Callback<TableColumn<OrderInfo, Void>, TableCell<OrderInfo, Void>>() {
            @Override
            public TableCell<OrderInfo, Void> call(final TableColumn<OrderInfo, Void> param) {
                final TableCell<OrderInfo, Void> cell = new TableCell<OrderInfo, Void>() {

                    private final Button btn1 = new Button("수령");
                    private final Button btn2 = new Button("미수령");

                    {
                        btn1.setOnAction((ActionEvent event) -> {//수령버튼클릭
                            try {
                                int selectedIndex = getTableRow().getIndex();//0, 1, 2, 3 ...ì에 대해서
                                OrderInfo selectedInfo = (OrderInfo) tblView.getItems().get(selectedIndex);

                                String sql = "UPDATE orderinfo SET menu_receipt = '1' WHERE order_id=?";
                                preparedStatement = connection.prepareStatement(sql);
                                preparedStatement.setInt(1, selectedInfo.getOrderid());
                                preparedStatement.executeUpdate();

                                String sql2 = "DELETE FROM ConsumerAndOrder WHERE order_id = ? ";//ConsumerAndOrder에서 삭제
                                preparedStatement2 = connection.prepareStatement(sql2);
                                preparedStatement2.setInt(1, selectedInfo.getOrderid());
                                preparedStatement2.execute();

                                btn1.setVisible(false);//버튼 사라지게
                                btn2.setVisible(false);
                                /*
                             *
                            OrderInfo data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                             *
                                 */

                            } catch (SQLException e) {
                            }
                        });

                        btn2.setOnAction((ActionEvent event) -> {//미수령버튼 클릭
                            try {
                                int selectedIndex = getTableRow().getIndex();//0, 1, 2, 3 ...ì에 대해서

                                OrderInfo selectedInfo = (OrderInfo) tblView.getItems().get(selectedIndex);

                                String sql = "UPDATE orderinfo SET menu_receipt = '2' WHERE order_id=?";
                                preparedStatement = connection.prepareStatement(sql);
                                preparedStatement.setInt(1, selectedInfo.getOrderid());
                                preparedStatement.executeUpdate();

                                String sql2 = "DELETE FROM ConsumerAndOrder WHERE order_id = ?";
                                preparedStatement2 = connection.prepareStatement(sql2);
                                preparedStatement2.setInt(1, selectedInfo.getOrderid());
                                preparedStatement2.execute();

                                btn1.setVisible(false);
                                btn2.setVisible(false);//버튼 사라지게
                                /*
                             *
                            OrderInfo data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                             *
                                 */

                            } catch (SQLException e) {
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hb = new HBox(btn1, btn2);
                            setGraphic(hb);
                        }
                    }
                };
                return cell;
            }
        };

        TC.setCellFactory(cellFactory);

    }

    void addOptionButton(TableView tblView, TableColumn<OrderInfo, Void> TC) {

        Callback<TableColumn<OrderInfo, Void>, TableCell<OrderInfo, Void>> cellFactory = new Callback<TableColumn<OrderInfo, Void>, TableCell<OrderInfo, Void>>() {
            @Override
            public TableCell<OrderInfo, Void> call(final TableColumn<OrderInfo, Void> param) {
                final TableCell<OrderInfo, Void> cell = new TableCell<OrderInfo, Void>() {

                    private final Button btn = new Button("옵션보기");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                int selectedIndex = getTableRow().getIndex();//0, 1, 2, 3 ...ì에 대해서

                                OrderInfo selectedInfo = (OrderInfo) tblView.getItems().get(selectedIndex);

                                String sql = "select * from orderinfo WHERE order_id=?";
                                preparedStatement = connection.prepareStatement(sql);
                                preparedStatement.setInt(1, selectedInfo.getOrderid());
                                resultSet = preparedStatement.executeQuery();

                                while (resultSet.next()) {
                                    if (resultSet.getString("op") == null) {
                                        btn.setVisible(false);// 옵션이 없으면 버튼 사라지게!!!
                                    } else {
                                        infoBox(resultSet.getString("op"), null, null);
                                    }
                                    /*
                             *
                            OrderInfo data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                             *
                                     */
                                }
                            } catch (SQLException e) {
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        TC.setCellFactory(cellFactory);

    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) { // 알림창
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // option은 information이나 confirmation
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
