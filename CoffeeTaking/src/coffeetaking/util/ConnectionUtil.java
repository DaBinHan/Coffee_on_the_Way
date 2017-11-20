package coffeetaking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author DaBin
 */
public class ConnectionUtil {

    Connection conn = null;

    public static Connection connectdb() { // 애가 없으면 컨트롤러에서 DB에 접근 자체가 불가능
        try {
        
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/coffee", "admin", "DB1234");
            
            //root 아이디로도 접속 가능
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/swingapp", "root", "sogang20120732");
            return conn;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}

