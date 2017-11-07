package ftl.activity;

import com.tibco.ftl.EventQueue;
import com.tibco.ftl.FTL;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.Message;
import com.tibco.ftl.Realm;
import com.tibco.ftl.Subscriber;
import com.tibco.ftl.SubscriberListener;
import model.CommandLineArgs;
import java.util.List;

public class FTLSubscriber implements SubscriberListener {

    private boolean messageReceived = false;

    protected Realm realm;
    protected Subscriber sub;
    protected EventQueue queue;

    public FTLSubscriber(CommandLineArgs args) {
        try {
            realm = FTL.connectToRealmServer(args.getFtlRealm(), args.getFtlAppName(), null);

            sub = realm.createSubscriber(args.getFtlEndPoint(), null, null);

            queue = realm.createEventQueue();
            queue.addSubscriber(sub, this);
        } catch (FTLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            queue.removeSubscriber(sub);
            queue.destroy();
            sub.close();
            realm.close();
        } catch (FTLException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage() {
        System.out.printf("waiting for message(s)\n");
        while (messageReceived == false) {
            try {
                queue.dispatch();
            } catch (FTLException e) {
                e.printStackTrace();
            }
        }
        messageReceived = false;
    }

    public void messagesReceived(List<Message> messages, EventQueue eventQueue) {
        for (int i = 0; i < messages.size(); i++) {
            System.out.println(" " + messages.get(i));
            messageReceived = true;
        }
    }
}
