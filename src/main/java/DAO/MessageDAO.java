package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public Message createMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                message.setMessage_id(rs.getInt(1)); // set the generated message_id
            }
            return message;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();


        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public Message getMessageById(int messageId) {
        Connection conn = ConnectionUtil.getConnection();
        Message message = null;

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, messageId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } catch (Exception e) {
      
        }
        return message;
    }


    public Message deleteMessageById(int messageId) {
        Connection conn = ConnectionUtil.getConnection();
        Message deletedMessage = null;

        deletedMessage = getMessageById(messageId);

        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, messageId);

            preparedStatement.executeUpdate();
           
        } catch (Exception e) {
      
        }
        return deletedMessage;
    }


    public Message updateMessageText(int messageId, String newMessageText) {
        Connection conn = ConnectionUtil.getConnection();
        Message updatedMessage = null;
    
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
    
            preparedStatement.setString(1, newMessageText);
            preparedStatement.setInt(2, messageId);
    
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                updatedMessage = getMessageById(messageId); // Retrieve the updated message
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedMessage;
    }

    public List<Message> getMessagesByUserId(int userId) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
    
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
    
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
    
    
}
