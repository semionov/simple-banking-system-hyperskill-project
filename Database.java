package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static int id = 1;

    public static void connection(String dataBaseName) {
        String url = "jdbc:sqlite:" + dataBaseName;// + ".db";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {

            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER," +
                    "number TEXT," +
                    "pin TEXT," +
                    "balance INTEGER)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void save(String cardNumber, String pin, String dataBaseName) {

        String url = "jdbc:sqlite:" + dataBaseName;// + ".db";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {

            try (Statement statement = con.createStatement()) {
                String sql = "INSERT INTO card" +
                    " VALUES(" + id + ",'" + cardNumber + "','" + pin + "', 0)";
                statement.executeUpdate(sql);
                id++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void delete(int loggedid, String dataBaseName) {

        String url = "jdbc:sqlite:" + dataBaseName;// + ".db";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {

            try (Statement statement = con.createStatement()) {
                String sql = "DELETE FROM card\n" +
                    "WHERE rowid = " + loggedid;
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int logIn(String cardNumber, String pin, String dataBaseName) {

        String url = "jdbc:sqlite:" + dataBaseName;// + ".db";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        int loggedId = 0;

        try (Connection con = dataSource.getConnection()) {

            try (Statement statement = con.createStatement()) {
                String sql = "SELECT rowid\n" +
                    "FROM card\n" +
                    "WHERE  pin  =  '" + pin + "' AND number = '" + cardNumber + "'";
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    loggedId = rs.getInt("rowid");
                }
                id++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return loggedId;
    }

    public static int getBalance(int loggedId, String dataBaseName) {
        String url = "jdbc:sqlite:" + dataBaseName;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        int balance = 0;

        try (Connection con = dataSource.getConnection()) {

            try (Statement statement = con.createStatement()) {
                String sql = "SELECT balance\n" +
                    "FROM card\n" +
                    "WHERE  rowid  =  '" + loggedId + "'";
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    balance = rs.getInt("balance");
                }
                id++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return balance;
    }

    public static int enterIncome(int loggedId, String dataBaseName, int income) {
        String url = "jdbc:sqlite:" + dataBaseName;
        int result = 0;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {

            try (Statement statement = con.createStatement()) {
                String sql = "UPDATE card\n" +
                    "SET balance = balance + " + income + "\n" +
                    "WHERE  rowid  =  '" + loggedId + "'";
                result = statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static int findCard(String dataBaseName, String cardNumber) {
        String url = "jdbc:sqlite:" + dataBaseName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        int id = 0;

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                String sql = "SELECT rowid\n" +
                    "FROM card\n" +
                    "WHERE  number  LIKE  '" + cardNumber + "'";
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    id = rs.getInt("rowid");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public static int subtractIncome(int loggedId, String dataBaseName, int income) {
        String url = "jdbc:sqlite:" + dataBaseName;
        int result = 0;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {

            try (Statement statement = con.createStatement()) {
                String sql = "UPDATE card\n" +
                    "SET balance = balance - " + income + "\n" +
                    "WHERE  rowid  =  '" + loggedId + "'";
                result = statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}