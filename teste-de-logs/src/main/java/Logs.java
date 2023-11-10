import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.*;

public class Logs {
    private static final Logger LOGGER = Logger.getLogger(Logs.class.getName());

    public static void main(String[] args) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            String logFileName = "log_" + timestamp + ".txt";

            FileHandler fileHandler = new FileHandler(logFileName);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String time = logTime.format(new Date(record.getMillis()));
                    return time + " : " + record.getLevel() + " : " + record.getMessage() + "\n";
                }
            });

            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new Formatter() {
                SimpleDateFormat consoleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

                @Override
                public String format(LogRecord record) {
                    return consoleDateFormat.format(new Date(record.getMillis())) + " : " + record.getMessage() + "\n";
                }
            });
            LOGGER.addHandler(consoleHandler);
            LOGGER.setUseParentHandlers(false);


            LOGGER.severe("Isso é um log de erro.");
            LOGGER.info("Isso é um log informativo.");
            LOGGER.finest("Aconteceu muuuitas coisas");



           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
