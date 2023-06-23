package com.example.attendence_mangement_system;


import java.sql.*;

public class Jdbc {

        // Replace below database url, username and password with your actual database credentials
        private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/attendence_management_system?useSSL=false&allowPublicKeyRetrieval=true";
        private static final String DATABASE_USERNAME = "root";
        private static final String DATABASE_PASSWORD = "Ritik@1234";
        private static final String INSERT_QUERY = "drop table login";



        public static Connection connect() throws SQLException {

            // Step 1: Establishing a Connection and
            // try-with-resource statement will auto close the connection.
            try {
                Connection connection = DriverManager
                        .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

                return connection;

            } catch (SQLException e) {
                // print SQL exception information
                printSQLException(e);
                return null;
            }

        }

    private static final String SELECT_QUERY = "SELECT * FROM login WHERE username = ? and password = ?";

    public static boolean validate(String username, String password) throws SQLException {

            // Step 1: Establishing a Connection and
            // try-with-resource statement will auto close the connection.
            try (Connection connection = DriverManager
                    .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                System.out.println(preparedStatement);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }


            } catch (SQLException e) {
                // print SQL exception information
                printSQLException(e);
            }
            return false;
        }
        public static void printSQLException(SQLException ex) {
            for (Throwable e : ex) {
                if (e instanceof SQLException) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                    System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }
