package db;

import java.sql.*;

public class Connexion {
   public static Connection con;

    public static void startConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/macnss", "root", "");
    }


}


