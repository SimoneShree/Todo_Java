package org.example;

import models.Todos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tasks {
    public static void ask(String Usernname) throws SQLException {
        try (Scanner sc = new Scanner(System.in)) {
            List<String> tasks = new ArrayList<>();
            List<String> task_description = new ArrayList<>();
            System.out.println("How many Tasks do you want to enter?");
            int no_of_tasks = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < no_of_tasks; i++) {
                System.out.println("Title of you Task " + (i + 1));
                String title = sc.nextLine();
                tasks.add(title);
                System.out.println("Description:");
                String desc = sc.nextLine();
                task_description.add(desc);
            }
            Todos t1 = new Todos(tasks, task_description, Usernname);

            t1.insertTasks();
            System.out.println("You want to see your tasks?");
            try (Scanner scan = new Scanner(System.in)) {
                String xyz=scan.nextLine();
                if (xyz.equalsIgnoreCase("Y")) {
                    Tasks.show(Usernname);
                }else{
                    System.out.println("Want to add more tasks?");
                    xyz=scan.nextLine();
                    if (xyz.equalsIgnoreCase("Y")) {
                        Tasks.ask(Usernname);
                    }
                    else{
                        return;
                    }
                }
            }
        }

    }

    public static void show(String Username) throws SQLException {
        String show_query = "SELECT * FROM Tasks WHERE Username = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:todo.db");
                PreparedStatement preparedStatement = connection.prepareStatement(show_query)) {
            preparedStatement.setString(1, Username);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Pending Tasks of user : " + Username);
            while (rs.next()) {
                String taskName = rs.getString("Task");
                String description = rs.getString("Description");
                System.out.print("Task Name: " + taskName + "\t\t\t");
                System.out.println("Description: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            System.out.println("Do You want to add more Tasks? Y/N");
            try (Scanner sc = new Scanner(System.in)) {
                String asking= sc.nextLine();
                if (asking.equalsIgnoreCase("Y")) {
                    Tasks.ask(Username);
                }else{
                    return;
                }
            }

        }
    }

}
