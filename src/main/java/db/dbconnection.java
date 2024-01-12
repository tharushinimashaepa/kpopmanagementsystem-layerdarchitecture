package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class dbconnection {
        private static dbconnection dbConnection;

        private Connection connection;
        private dbconnection () throws SQLException {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/KpopManagementSystem",
                    "root",
                    "Ijse@1234"
            );
        }
        public static dbconnection getInstance() throws SQLException {
            return (dbConnection==null)?dbConnection = new dbconnection():dbConnection;
        }

        public static dbconnection getDbconnection() {
            return dbconnection;
        }

        public static void setDbconnection(dbconnection dbconnection) {
            dbconnection.dbconnection = dbconnection;
        }

        public Connection getConnection(){
            return connection;
        }
    }


