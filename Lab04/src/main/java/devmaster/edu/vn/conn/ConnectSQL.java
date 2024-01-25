package devmaster.edu.vn.conn;
import java.sql.*;
public class ConnectSQL {
    public static Connection getMSSQLConnection() throws SQLException, ClassNotFoundException {
        String hostName = "LAPTOP-SSHUBNQA";
        String dbName = "Lab04JspServletJDBC";
        String userName = "sa";
        String password = "123456";
        String connectionURL = "jdbc:sqlserver://" + hostName + ":1433;databaseName=" + dbName + ";user=" + userName + ";password=" + password + ";encrypt=true;trustServerCertificate=true";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = (Connection) DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }
    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }
    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
        }
    }
}
