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

    private CommandLine commandLine;
    private final Options options;

    Controller(String[] args) throws Exception {
        this.options = getOptions();
        CommandLineParser parser = new BasicParser();
        try {
            this.commandLine = parser.parse(this.options, args);
        } catch (ParseException e) {
            System.out.println("Incorrect arguments: " + e.getMessage());
            printHelpPage();
            throw new Exception("Incorrect input");
        }
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Display this help page");
        return options;
    }

    private void printHelpPage() {
        System.out.println("Usage: <number> <power> <modulo>");
    }

    public void run() throws ParseException {
        if (commandLine.hasOption("h")) {
            printHelpPage();
            return;
        }
        List<BigInteger> arguments = Arrays.stream(commandLine.getArgs())
                .filter(StringUtils::isNumeric)
                .map(BigInteger::new)
                .collect(Collectors.toList());

        if (arguments.size() != 3) {
            throw new ParseException("Invalid number of integer arguments.");
        }

        BigInteger base = arguments.get(0);
        BigInteger power = arguments.get(1);
        BigInteger modulo = arguments.get(2);
        List<Boolean> powerBinary = getBigIntegerAsBinaryBooleans(power);
        BigInteger result = calculate(base, base, powerBinary, 1, modulo);
        System.out.println(result);
    }

    private BigInteger calculate(BigInteger base, BigInteger current,
                                 List<Boolean> power, int powerIndex, BigInteger modulo) {
        if (powerIndex == power.size()) {
            return current;
        }
        BigInteger toReturn = current.multiply(current).mod(modulo);

        if (power.get(powerIndex)) {
            toReturn = toReturn.multiply(base).mod(modulo);
        }

        return calculate(base, toReturn, power, ++powerIndex, modulo);
    }

    private List<Boolean> getBigIntegerAsBinaryBooleans(BigInteger value) {
        return value.toString(2)
                .chars()
                .mapToObj(e -> e == '1')
                .collect(Collectors.toList());
    }
}
