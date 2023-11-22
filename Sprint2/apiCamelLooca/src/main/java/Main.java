import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        MemoriaDeDisco disco = new MemoriaDeDisco();
        MemoriaRam ram = new MemoriaRam();
        PlacaDeRede rede = new PlacaDeRede();
        PuxarProcessador processador = new PuxarProcessador();

        DataBase db = new DataBase();
        Timer timer = new Timer();

        System.out.println("Inicio do processo de captura:");

        // Agendando tarefas a cada 30 segundos
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                LocalDateTime dataHora = LocalDateTime.now();
                DateTimeFormatter formatadorDeDataHora = DateTimeFormatter.ofPattern("hh:mm:ss", Locale.forLanguageTag("pt-BR"));

                System.out.println(formatadorDeDataHora.format(dataHora));
                disco.captarInformacoesDoDisco();
                ram.captarMemoria();
                rede.captarPlacaDeRede();
                processador.captarProcessador();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try (Connection conexao = db.conectar()) {
                            try {
                                String sqlConsulta1 = "SELECT c.idConfiguracao\n" +
                                        "FROM configuracao c\n" +
                                        "JOIN servidor s ON c.fkServidor = s.idServidor\n" +
                                        "WHERE c.fktipoComponente = 1";

                                try (PreparedStatement consulta1 = conexao.prepareStatement(sqlConsulta1)) {
                                    try (ResultSet resultado1 = consulta1.executeQuery()) {
                                        if (resultado1.next()) {
                                            db.inserirDados(conexao, ram.getMemoriaEmUso(), 1, 1);
                                        }
                                    }
                                }

                                String sqlConsulta2 = "SELECT c.idConfiguracao\n" +
                                        "FROM configuracao c\n" +
                                        "JOIN servidor s ON c.fkServidor = s.idServidor\n" +
                                        "WHERE c.fktipoComponente = 2";

                                try (PreparedStatement consulta2 = conexao.prepareStatement(sqlConsulta2)) {
                                    try (ResultSet resultado2 = consulta2.executeQuery()) {
                                        if (resultado2.next()) {
                                            db.inserirDados(conexao, disco.getUsoDisco(), 2, 2);
                                        }
                                    }
                                }

                                String sqlConsulta3 = "SELECT c.idConfiguracao\n" +
                                        "FROM configuracao c\n" +
                                        "JOIN servidor s ON c.fkServidor = s.idServidor\n" +
                                        "WHERE c.fktipoComponente = 3";

                                try (PreparedStatement consulta3 = conexao.prepareStatement(sqlConsulta3)) {
                                    try (ResultSet resultado3 = consulta3.executeQuery()) {
                                        if (resultado3.next()) {
                                            db.inserirDados(conexao, processador.getQtdemUso(), 3, 3);
                                        }
                                    }
                                }

                                String sqlConsulta4 = "SELECT c.idConfiguracao\n" +
                                        "FROM configuracao c\n" +
                                        "JOIN servidor s ON c.fkServidor = s.idServidor\n" +
                                        "WHERE c.fktipoComponente = 4";

                                try (PreparedStatement consulta4 = conexao.prepareStatement(sqlConsulta4)) {
                                    try (ResultSet resultado4 = consulta4.executeQuery()) {
                                        if (resultado4.next()) {
                                            db.inserirDados(conexao, rede.getBytesEnviados(), 4, 4);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Erro ao inserir dados no banco de dados: " + e.getMessage());
                            }
                        } catch (Exception ex) {
                            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
                        }
                    }
                } ,0, 30 * 1000); // 0 segundos de atraso, 30 segundos de intervalo entre as tarefas
            }
        },0, 30 * 1000); // 0 segundos de atraso, 30 segundos de intervalo entre as tarefas
    }

 }