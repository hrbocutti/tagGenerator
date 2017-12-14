package br.com.ifsolutions.model;

import java.io.File;
import java.sql.*;

public class SqliteModel {
    public boolean verifySqlite(String path) {
        File sqlitefile = new File(path);
        return sqlitefile.exists() && !sqlitefile.isDirectory();
    }

    public boolean createDataBase(String url) {
        try(Connection conn = DriverManager.getConnection(url)){
            Class.forName("org.sqlite.JDBC");
            if(conn != null){
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created");
                return true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createTable(String url, String tableName) {
        // SQLite connection string
        String db = "jdbc:sqlite:" + url;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(id INTEGER PRIMARY KEY, name VARCHAR(255) NOT NULL, value VARCHAR(255) NOT NULL)";

        try (Connection conn = DriverManager.getConnection(db);
             Statement stmt = conn.createStatement()) {
            Class.forName("org.sqlite.JDBC");
            // create a new table
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
