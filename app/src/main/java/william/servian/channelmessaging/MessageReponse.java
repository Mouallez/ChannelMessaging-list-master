package william.servian.channelmessaging;

import java.util.ArrayList;

/**
 * Created by sacquind on 06/03/2018.
 */
public class MessageReponse {

    public ArrayList<Message> getMessages() { return messages;}

    public ArrayList<Message> messages;

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
