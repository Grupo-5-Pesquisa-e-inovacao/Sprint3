package Log;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.*;

public class LogGerador {
    private static final Logger LOGGER = Logger.getLogger(LogGerador.class.getName());

    public void mainLog(){}
    public void mainLog(String status, String resposta) {
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
                    return time + ": " + record.getLevel() + ": " + record.getMessage() + "\n";
                }
            });

            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new Formatter() {
                SimpleDateFormat consoleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

                @Override
                public String format(LogRecord record) {
                    return consoleDateFormat.format(new Date(record.getMillis())) + ": " + record.getMessage() + "\n";
                }
            });
            LOGGER.addHandler(consoleHandler);
            LOGGER.setUseParentHandlers(false);


            if (status.equals("Erro")){
                LOGGER.severe("Status de Captura: " + status);
                LOGGER.finest("Resposta de Captura: "+ resposta+"\n");
            }else {
                LOGGER.info("Status: "+ status);
                LOGGER.finest("Resposta: "+ resposta+"\n");
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LogLogin(String email, String senha, String resposta, String status) {
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
                    return time + ": " + record.getLevel() + ": " + record.getMessage() + "\n";
                }
            });

            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new Formatter() {
                SimpleDateFormat consoleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

                @Override
                public String format(LogRecord record) {
                    return consoleDateFormat.format(new Date(record.getMillis())) + ": " + record.getMessage() + "\n";
                }
            });
            LOGGER.addHandler(consoleHandler);
            LOGGER.setUseParentHandlers(false);


            if (status.equals("Erro")){
                LOGGER.severe("Tentativa de login falha!\n Credenciais:\n Email: "+ email+" Senha: "+ senha);
                LOGGER.finest("Resposta: "+ resposta+"\n");
            }else {
                LOGGER.info("Login realizado!\n Usuario de Credenciais:\n Email: "+ email+" Senha: "+ senha);
                LOGGER.finest("Resposta: "+ resposta+"\n");
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LogConexao(String conexao, String resposta, String status) {
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
                    return time + ": " + record.getLevel() + ": " + record.getMessage() + "\n";
                }
            });

            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new Formatter() {
                SimpleDateFormat consoleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

                @Override
                public String format(LogRecord record) {
                    return consoleDateFormat.format(new Date(record.getMillis())) + ": " + record.getMessage() + "\n";
                }
            });
            LOGGER.addHandler(consoleHandler);
            LOGGER.setUseParentHandlers(false);


            if (status.equals("Erro")){
                LOGGER.severe("Tentativa de conexao falha!\n Credenciais:\n Conexão: "+ conexao);
                LOGGER.finest("Resposta: "+ resposta);
            }else {
                LOGGER.info("Tentativa de conexao bem sucedida!\n Credenciais:\n Conexão: "+ conexao);
                LOGGER.finest("Resposta: "+ resposta);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}