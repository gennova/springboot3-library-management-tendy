package com.amazon.test;

import com.amazon.test.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionTest {
    public static void main(String[] args) throws Exception {
        Connection connection = DatabaseConnection.getDataSource().getConnection();
        String query = "select isbn,title,author, count(*) as stock from book group by isbn,title,author";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setInt(1, 452); // Replace with your desired ID
            ResultSet resultSet = statement.executeQuery();

            // Process the results
            while (resultSet.next()) {
                // Access data using column names or indexes
                //System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("ISBN: " + resultSet.getString("isbn"));
                System.out.println("TITLE: " + resultSet.getString("title"));
                System.out.println("AUTHOR: " + resultSet.getString("author"));
                System.out.println("STOCK: " + resultSet.getString("stock"));
                // ... and so on for other columns
            }
        } finally {
            connection.close(); // Return connection to the pool
        }
    }
}
