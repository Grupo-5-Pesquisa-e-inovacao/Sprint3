import java.sql.Connection;
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

                try (Connection conexao = db.conectar()) {
                    try {
                        // db.inserirDados(conexao, ram.getMemoriaEmUso(), 1);
                        // db.inserirDados(conexao, disco.getUsoDisco(), 2);
                         // db.inserirDados(conexao, processador.getQtdemUso(), 3);
                        // db.inserirDados(conexao, rede.getBytesEnviados(), 4);

                    } catch (Exception e) {
                        System.out.println("Erro ao inserir dados no banco de dados: " + e.getMessage());
                    }
                } catch (Exception ex) {
                    System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
                }
            }
        }, 0, 30 * 1000); // 0 segundos de atraso, 30 segundos de intervalo entre as tarefas
    }
}
