package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static  final String URL = "jdbc:mysql://localhost:3306/human_management";

    private static final String USER_DB = "root";

    private static  final String PASS_DB = "dinhtheanh998";

   public static Connection getMyConnection() throws SQLException{
       return DriverManager.getConnection(URL, USER_DB, PASS_DB);
   }
}
