package com.amazon.test.services;

import com.amazon.test.config.DatabaseConnection;
import com.amazon.test.model.Book;
import com.amazon.test.model.BookStock;
import com.amazon.test.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<BookStock> getStock() throws SQLException {
        Connection connection = DatabaseConnection.getDataSource().getConnection();
        List<BookStock> stocks = new ArrayList<>();
        String query = "SELECT b.isbn, b.title, b.author, \n" +
                "       COUNT(*) AS stock, \n" +
                "       (SELECT COUNT(*) FROM borrowbook WHERE borrowbook.isbn = b.isbn) AS borrowed,\n" +
                "       COUNT(*) - (SELECT COUNT(*) FROM borrowbook WHERE borrowbook.isbn = b.isbn) AS available_stock\n" +
                "FROM book b\n" +
                "GROUP BY b.isbn, b.title, b.author;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            // Process the results
            while (resultSet.next()) {
                BookStock bookStock = new BookStock();
                bookStock.setIsbn(resultSet.getString("isbn"));
                bookStock.setTitle(resultSet.getString("title"));
                bookStock.setAuthor(resultSet.getString("author"));
                bookStock.setStock(resultSet.getInt("stock"));
                bookStock.setBorrowed(resultSet.getInt("borrowed"));
                bookStock.setAvailable(resultSet.getInt("available_stock"));
                stocks.add(bookStock);
            }
        } finally {
            connection.close(); // Return connection to the pool
        }
        return stocks;
    }

}
