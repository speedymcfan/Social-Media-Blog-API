package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import static org.mockito.ArgumentMatchers.nullable;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = accountService.addAccount(account);
        if(newAccount != null) {
            context.json(mapper.writeValueAsString(newAccount));
        }
        else    
            context.status(400);
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        if(accountService.exists(account)) {
            Account retrieved = accountService.retrieveAccount(account.getUsername());
            context.json(mapper.writeValueAsString(retrieved));
        }
        else    
            context.status(401);
    }

    private void postMessagesHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.addMessage(message);
        if(newMessage != null) {
            context.json(mapper.writeValueAsString(newMessage));
        }
        else
            context.status(400);

    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        if(messages != null)
            context.json(messages);
    }

    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.valueOf(context.pathParam("message_id"));
        Message target = messageService.retrieveMessage(messageId);
        if(target != null)
            context.json(target);
    }

    private void deleteMessageByIdHandler(Context context) {
        int messageId = Integer.valueOf(context.pathParam("message_id"));
        Message target = messageService.deleteMessage(messageId);
        if(target != null)
            context.json(target);
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.valueOf(context.pathParam("message_id"));
        Message message = mapper.readValue(context.body(), Message.class);
        Message target = messageService.updateMessage(messageId, message);
        if(target != null)
            context.json(target);
        else
            context.status(400);
    }

    private void getAllMessagesByUserHandler(Context context) {
        int accountId = Integer.valueOf(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromAccount(accountId);
        if(messages != null)
            context.json(messages);
    }
}