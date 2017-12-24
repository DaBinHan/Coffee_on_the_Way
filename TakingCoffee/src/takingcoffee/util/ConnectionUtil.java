package takingcoffee.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * mysql에 접속하기 위한 DBConnection class
 *
 *
 * @author DaBin
 */
public class ConnectionUtil {

    Connection conn = null;

    public static Connection connectdb() { // 애가 없으면 컨트롤러에서 DB에 접근 자체가 불가능
        try {
        
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://my5008.gabiadb.com/coffeeway", "sogang", "cafedream123");
            
            //root 아이디로도 접속 가능
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/swingapp", "root", "sogang20120732");
            return conn;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}