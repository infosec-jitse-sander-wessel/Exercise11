import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sander on 21-9-2016.
 */
public class Controller {

    private List<BigInteger> arguments;

    Controller(String[] args) throws Exception {
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine commandLine = parser.parse(new Options(), args);
            this.arguments = Arrays.stream(commandLine.getArgs())
                    .filter(StringUtils::isNumeric)
                    .map(BigInteger::new)
                    .collect(Collectors.toList());

            if (this.arguments.size() != 3) {
                throw new ParseException("Invalid number of integer arguments.");
            }

        } catch (ParseException e) {
            System.out.println("Incorrect arguments: " + e.getMessage());
            printHelpPage();
            throw new Exception("Incorrect input");
        }
    }

    private void printHelpPage() {
        System.out.println("Usage: <number> <power> <modulo>");
    }

    public void run() {
        System.out.println(arguments.get(0) + "^" + arguments.get(1) + " % " + arguments.get(2));
    }
}
