package ftl.activity;

import com.tibco.ftl.EventQueue;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.Inbox;
import com.tibco.ftl.Message;
import com.tibco.ftl.MessageFieldRef;
import com.tibco.ftl.MessageIterator;
import com.tibco.ftl.Publisher;
import model.CommandLineArgs;
import java.util.List;

public class FTLSubscriberAndReply extends FTLSubscriber {
    private int received = 0;

    protected Publisher replyPublisher;
    protected Message   replyMsg;

    public FTLSubscriberAndReply(CommandLineArgs args) {
        super(args);
        try {
            replyPublisher = realm.createPublisher(args.getFtlEndPoint());
        } catch (FTLException e) {
            e.printStackTrace();
        }

        replyMsg = realm.createMessage("Format-1");
    }

    public void close() {
        super.close();
        try {
            replyPublisher.close();
            replyMsg.destroy();
        } catch (FTLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messagesReceived(List<Message> messages, EventQueue eventQueue) {
        Inbox inbox = null;
        Message msg;
        String fieldName;
        int fieldType;
        MessageIterator iter;

        try {
            received++;
            System.out.printf("received request %d \n", received);
            msg = messages.get(0);

            // use a message iterator to print fields that have been set in the message
            iter = msg.iterator();
            while (iter.hasNext()) {
                // retrieve the field reference from the iterator to access fields.
                MessageFieldRef ref = iter.next();
                fieldName = ref.getFieldName();
                fieldType = msg.getFieldType(fieldName);

                System.out.println("Name: " + fieldName);
                System.out.println("    Type: " + msg.getFieldTypeString(fieldType));
                System.out.print  ("    Value:  ");

                switch (fieldType) {
                    case Message.TIB_FIELD_TYPE_INBOX:
                        inbox = msg.getInbox(ref);
                        System.out.println(inbox);
                        break;
                    default:
                        System.out.println("Unhandled type");
                        break;
                }
            }
            iter.destroy();

            // set by name since performance is not demonstrated here
            replyMsg.setString("reply", "Reply from java Subs&Reply");

            System.out.printf("sending reply # %d \n", received);
            replyPublisher.sendToInbox(inbox, replyMsg);
        } catch(FTLException exp) {
            exp.printStackTrace();
        }
    }
}
