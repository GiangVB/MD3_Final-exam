package config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private static Connection connection;
    private SingletonConnection() {
    }
    public static final String URL = "jdbc:mysql://localhost:3306/product_management";
    public static final String USER = "root";
    public static final String PASSWORD = "Ho!Ecbl4y3nl0?";

    public static Connection getConnect(){
//        if (connection==null){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );
            System.out.println("ket noi thanh cong");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ket noi khong thanh cong");
            e.printStackTrace();
        }
//        }
        return  connection;
    }
}
