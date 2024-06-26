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
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message retrieveMessage(int messageId){
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessage(int messageId){
        if(messageDAO.getMessageById(messageId) != null){
            messageDAO.delete(messageId);
        }
        return null;
    }

    public Message updateMessage(int messageId, Message message){
        if(messageDAO.getMessageById(messageId) != null){
            messageDAO.update(messageId);
        }
        return null;
    }

    public List<Message> getAllMessagesFromAccount(int accountId){
        return getAllMessagesFromAccount(accountId);
    }
}
