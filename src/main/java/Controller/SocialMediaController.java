package Controller;


import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);


        //Defining the endpoints
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageTextHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


    //Handler to register user
    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = objectMapper.readValue(ctx.body(), Account.class);
        Account createdAccount = accountService.registerUser(account);
        if(createdAccount != null) {
            ctx.json(objectMapper.writeValueAsString(createdAccount));
        }
        else{
            ctx.status(400);
        }

    }

    //Handler for user login
    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account loginAccount = objectMapper.readValue(ctx.body(), Account.class);
        
        Account authenticatedAccount = accountService.loginUser(loginAccount.getUsername(), loginAccount.getPassword());

        if (authenticatedAccount != null) {
            ctx.json(objectMapper.writeValueAsString(authenticatedAccount));
        } else {
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage != null) {
            ctx.json(objectMapper.writeValueAsString(createdMessage));
        }
        else {
            ctx.status(400);
        }
    }

    
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ObjectMapper objectMapper = new ObjectMapper();
        ctx.json(objectMapper.writeValueAsString(messages));
    }


    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id")); 
        Message message = messageService.getMessageById(messageId);

        ObjectMapper objectMapper = new ObjectMapper();
        if (message != null) {
            ctx.json(objectMapper.writeValueAsString(message)); 
        } else {
            ctx.json("");
        }
    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id")); // Retrieve the message ID from the path
        Message deletedMessage = messageService.deleteMessageById(messageId); // Attempt to delete the message

        ObjectMapper objectMapper = new ObjectMapper();
        if (deletedMessage != null) {
            ctx.json(objectMapper.writeValueAsString(deletedMessage)); // Respond with the deleted message if found
        } else {
            ctx.json(""); // Respond with an empty body if no message was found
        }
    }


        
    private void updateMessageTextHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id")); // Retrieve the message ID from the path
        ObjectMapper objectMapper = new ObjectMapper();
    
        // Read the request body for new message text
        JsonNode jsonNode = objectMapper.readTree(ctx.body());
        String newMessageText = jsonNode.get("message_text").asText();
    
        Message updatedMessage = messageService.updateMessageText(messageId, newMessageText);
    
        if (updatedMessage != null) {
            ctx.json(objectMapper.writeValueAsString(updatedMessage)); // Respond with the updated message
        } else {
            ctx.status(400); // Invalid update
        }
    }
    

    private void getMessagesByUserIdHandler(Context ctx) throws JsonProcessingException {
        int accountId = Integer.parseInt(ctx.pathParam("account_id")); // Retrieve the account ID from the path
        List<Message> messages = messageService.getMessagesByUserId(accountId);
    
        ObjectMapper objectMapper = new ObjectMapper();
        ctx.json(objectMapper.writeValueAsString(messages)); // Respond with the list of messages
    }


}