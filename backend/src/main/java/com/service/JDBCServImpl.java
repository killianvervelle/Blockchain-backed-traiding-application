package com.service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

// Implementation of JDBCService interface
@Service
public class JDBCServImpl implements JDBCService {

    // Method to extract content from a specified column in a database table
    @Override
    public List<String> extractColumnContent(String column) {
        List<String> resultList = new ArrayList<>();
        try {
            Connection connection = DBConnection.DatabaseConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT " + column + " FROM users.customers");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String value = resultSet.getString(column);
                resultList.add(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<List<String>> extractHandledRequestsByIssuer(String supplier) {
        List<List<String>> resultList = new ArrayList<>();
        try {
            Connection connection = DBConnection.DatabaseConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM users.transactions WHERE issuer = ?");
            preparedStatement.setString(1, supplier);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                resultList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    // Method to retrieve user data based on email from the database
    @Override
    public List<List<String>> getUserData(String email) {
        List<List<String>> tableData = new ArrayList<>();
        try {
            Connection connection = DBConnection.DatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * " +
                            " FROM users.customers AS c" +
                            " JOIN users.transactions AS t ON c.tx_id = t.tx_id" +
                            " JOIN users.blocks AS b ON t.hashblock = b.hashcode" +
                            " WHERE c.email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.add(resultSet.getString(i));
                }
                tableData.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableData;
    }

    // Method to print user data in a tabular format to a CSV file
    @Override
    public void printUserDataAsFrame(List<List<String>> tableData) {
        try (FileWriter writer = new FileWriter("output.csv")) {
            for (List<String> row : tableData) {
                for (String cell : row) {
                    writer.append(cell);
                    writer.append(',');
                }
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
