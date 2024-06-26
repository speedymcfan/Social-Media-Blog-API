package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message addMessage(Message message){
        if(message.getMessage_text().length() <= 0 || message.getMessage_text().length() > 255 || messageDAO.exists(message.getPosted_by()) == false)
            return null;
        return messageDAO.insert(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message retrieveMessage(int messageId){
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessage(int messageId){
        if(messageDAO.getMessageById(messageId) != null){
            return messageDAO.delete(messageId);
        }
        return null;
    }

    public Message updateMessage(int messageId, Message message){
        if(messageDAO.getMessageById(messageId) != null && message.getMessage_text().length() > 0 && message.getMessage_text().length() < 256){
            return messageDAO.update(messageId, message);
        }
        return null;
    }

    public List<Message> getAllMessagesFromAccount(int accountId){
        return messageDAO.getAllMessagesFromAccount(accountId);
    }
}
