package Service;


import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    public MessageDAO messageDAO;


    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        // Validation logic
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty() || 
            message.getMessage_text().length() > 255) {
            return null; // Invalid message
        }
        
        
        // If validation passes, create the message
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        return messageDAO.deleteMessageById(messageId); 
    }

    public Message updateMessageText(int messageId, String newMessageText) {
        // Validation logic
        if (newMessageText == null || newMessageText.isEmpty() || newMessageText.length() > 255) {
            return null; // Invalid message text
        }
    
        return messageDAO.updateMessageText(messageId, newMessageText);
    }

    public List<Message> getMessagesByUserId(int userId) {
        return messageDAO.getMessagesByUserId(userId);
    }


    
}
