package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static final String url = "jdbc:sqlite:todo.db";
    public static final Connection connection;
    public static final String usertable = "Users";
    public static final String tasks_table = "Tasks";

    static {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            createTables();
        } catch (SQLException e) {
            System.out.println("Failed to create tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createTables() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + usertable
                + "(id  INTEGER PRIMARY KEY NOT NULL,  Username TEXT , Password TEXT)";
        String createTaskTableQuery = "CREATE TABLE IF NOT EXISTS " + tasks_table +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " Username TEXT NOT NULL, " +
                " Task TEXT NOT NULL, " +
                " Description TEXT NOT NULL, " +
                " Time DATE DEFAULT (DATE('now', 'localtime')))";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableQuery);
            System.out.println("Table created: " + usertable);
            try (Statement taskStatement = connection.createStatement()) {
                taskStatement.execute(createTaskTableQuery);
                System.out.println("Task table created: " + tasks_table);
            }
        }
    }
}
