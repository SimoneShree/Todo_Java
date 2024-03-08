package org.example;

import java.sql.*;
import java.util.Scanner;

public class LoginSignup {
    public static final Scanner sc=new Scanner(System.in);
    public static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:todo.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void login(String Username,String Password){
        String select_query="SELECT * FROM Users WHERE Username = ? AND Password = ? ";
    try(PreparedStatement preparedStatement= connection.prepareStatement(select_query)){
        preparedStatement.setString(1,Username);
        preparedStatement.setString(2,Password);
        ResultSet rs=preparedStatement.executeQuery();
        while (rs.next()){
            System.out.println("Logged In");
            System.out.println("Press E to enter task or Press C to see task");
            String xyz=sc.next();
            if (xyz.equalsIgnoreCase("E")){
                connection.close();
                Tasks.ask(Username);
            }else {
                 connection.close();
                 Tasks.show(Username);   
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }
    public static void signup(String Username,String Password){
        String insertQuery_users = "INSERT INTO Users ( Username, Password) VALUES (?, ?)";
        try(PreparedStatement preparedStatement= connection.prepareStatement(insertQuery_users)){
            preparedStatement.setString(1,Username);
            preparedStatement.setString(2,Password);
            preparedStatement.executeUpdate();
            System.out.println("User "+Username+" Created");
            Main.main(new String[]{});
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
