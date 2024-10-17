package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account createAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();

        try {
            //SQL Logic
            String sql = "INSERT INTO account(username, password) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
            account.setAccount_id(generatedKeys.getInt(1)); // Set the generated account ID
        }
      
            return account;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



    public Account getAccountByUsername(String username) {
        Connection conn = ConnectionUtil.getConnection();
       
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no account found
    }
    

    public Account getAccount(String username, String password) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if account not found
    }


    public Account getAccountByID(Integer id) {
        Connection conn = ConnectionUtil.getConnection();
       
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no account found
    }



}
