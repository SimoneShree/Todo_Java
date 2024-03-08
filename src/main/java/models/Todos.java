package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Todos {
    private static final String url = "jdbc:sqlite:todo.db";

     List<String> tasks;
     List<String> taskDescription;
     String username;

    public Todos(List<String> tasks, List<String> taskDescription, String username) {
        this.tasks = tasks;
        this.taskDescription = taskDescription;
        this.username = username;
    }

    public void insertTasks() {
        String insertQuery = "INSERT INTO Tasks (Username, Task, Description) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            for (int i = 0; i < tasks.size(); i++) {
                insertStatement.setString(1, username);
                insertStatement.setString(2, tasks.get(i));
                insertStatement.setString(3, taskDescription.get(i));
                insertStatement.executeUpdate();
            }
            System.out.println("Your tasks have been successfully added.");
        } catch (SQLException e) {
            System.out.println("Error occurred during task insertion:");
            e.printStackTrace();
        }
        
    }
}
