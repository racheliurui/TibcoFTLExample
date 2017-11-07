package parser;

import lombok.AllArgsConstructor;
import model.CommandLineArgs;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@AllArgsConstructor
public class ArgsParser {
    private String args[];

    public CommandLineArgs parse() throws ParseException {
        Options options = new Options();
        options.addOption("realm", true, "Realm Server link");
        options.addOption("appName", true, "FTL Application name");
        options.addOption("endPoint", true, "FTL Publisher or Subscriber endPoint");
        CommandLineParser parser = new BasicParser();
        CommandLine commandLine = parser.parse(options, args);
        return CommandLineArgs.builder()
                .ftlRealm(commandLine.getOptionValue("realm"))
                .ftlAppName(commandLine.getOptionValue("appName"))
                .ftlEndPoint(commandLine.getOptionValue("endPoint"))
                .build();
    }
}
