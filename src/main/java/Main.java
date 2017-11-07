
import ftl.activity.FTLSubscriber;
import model.CommandLineArgs;
import org.apache.commons.cli.ParseException;
import parser.ArgsParser;

public class Main {
    public static void main(String[] args) throws ParseException {
        CommandLineArgs commandLineArgs = new ArgsParser(args).parse();

        FTLSubscriber subscriber = new FTLSubscriber(commandLineArgs);
        subscriber.receiveMessage();
        subscriber.close();

        //DefaultFTLPublisher publisher = new DefaultFTLPublisher(commandLineArgs);
        //publisher.send("defaultFormat");
        //publisher.close();
        //FTLSubscriberAndReply subscriberAndReply = new FTLSubscriberAndReply(commandLineArgs);
        //subscriberAndReply.receiveMessage();
        //subscriberAndReply.close();
    }
}
