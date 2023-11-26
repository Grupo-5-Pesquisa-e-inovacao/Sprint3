import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        MemoriaDeDisco disco = new MemoriaDeDisco();
        MemoriaRam ram = new MemoriaRam();
        PlacaDeRede rede = new PlacaDeRede();
        PuxarProcessador processador = new PuxarProcessador();
        DataBase db = new DataBase();

        Scanner scanner = new Scanner(System.in);
        Usuario usuario = login(scanner, db);

        if (usuario != null) {
            System.out.println("Login bem-sucedido. Início do processo de captura:");


            Timer timer = new Timer();

            // Agendamento de tarefas a cada 30 segundos
            timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    LocalDateTime dataHora = LocalDateTime.now();
                    DateTimeFormatter formatadorDeDataHora = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.forLanguageTag("pt-BR"));

                    System.out.println(formatadorDeDataHora.format(dataHora));


                    disco.captarInformacoesDoDisco();
                    ram.captarMemoria();
                    rede.captarPlacaDeRede();
                    rede.calcularVelocidadeDeRede();
                    processador.captarProcessador();

                    try (Connection conexao = db.conectar()) {
                        List<Integer> idsServidores = usuario.obterIdsServidoresAtrelados(conexao);

                        db.inserirDados(conexao, ram.getMemoriaEmUso(), 1, idsServidores, 1);
                        db.inserirDados(conexao, disco.getUsoDisco(), 2, idsServidores, 2);
                        db.inserirDados(conexao, processador.getQtdemUso(), 3, idsServidores, 3);
                        db.inserirDados(conexao, rede.calcularVelocidadeDeRede(), 4, idsServidores, 4);
                    } catch (SQLException ex) {
                        System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
                    }
                }
            }, 0, 30 * 1000); // 0 segundos de atraso, 30 segundos de intervalo entre as tarefas
        } else {
            System.out.println("Login falhou. O programa será encerrado.");
        }
    }

    // Método para realizar o login e retornar um objeto Usuario se o login for bem-sucedido
    private static Usuario login(Scanner scanner, DataBase db) {

        System.out.println("""
                   _____                     _ _______        _    \s
                  / ____|                   | |__   __|      | |   \s
                 | |     __ _ _ __ ___   ___| |  | | ___  ___| |__ \s
                 | |    / _` | '_ ` _ \\ / _ \\ |  | |/ _ \\/ __| '_ \\\s
                 | |____ (_| | | | | | |  __/ |  | |  __/ (__| | | |
                  \\_____\\__,_|_| |_| |_|\\___|_|  |_|\\___|\\___|_| |_|
                                                                   \s
                                                                   \s""");
        System.out.println("""
                |----------------------------------|     
                |                                  |
                | Olá! Seja bem-vindo ao           |
                | sistema Camel, Faça              |
                | login para iniciar o             |
                | monitoramento:                   | 
                |                                  | 
                |----------------------------------|
                """);



        System.out.print("Usuário (Email): ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try (Connection conexao = db.conectar()) {
            String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
            try (PreparedStatement statement = conexao.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, senha);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int idUsuario = resultSet.getInt("idUsuario");
                        String nome = resultSet.getString("nome");
                        String cpf = resultSet.getString("cpf");
                        String emailBanco = resultSet.getString("email");
                        String senhaBanco = resultSet.getString("senha");
                        int fkUnidade = resultSet.getInt("fkUnidade");

                        // Verificar se a unidade associada ao usuário é válida
                        String nomeUnidade = verificarUnidadeValida(conexao, fkUnidade, email);

                        if (nomeUnidade != null) {
                            Usuario usuario = new Usuario(idUsuario, nome, cpf, emailBanco, senhaBanco);
                            System.out.println("Login bem-sucedido. Bem-vindo, " + usuario.getNome() + "!");
                            System.out.println("Unidade associada: " + nomeUnidade);
                            return usuario;
                        } else {
                            System.out.println("Login falhou. Usuário associado a uma unidade inválida.");
                            return null;
                        }
                    } else {
                        System.out.println("Login falhou. Credenciais inválidas.");
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
    // Método para verificar se a unidade associada ao usuário é válida e retornar o nome da unidade
    private static String verificarUnidadeValida(Connection conexao, int fkUnidade, String email) throws SQLException {
        String sql = "SELECT u.nomeUnidade " +
                "FROM unidadeProvedora u " +
                "WHERE u.idUnidadeProvedora = ? AND EXISTS (SELECT 1 FROM usuario WHERE email = ? AND fkUnidade = ?)";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, fkUnidade);
            statement.setString(2, email);
            statement.setInt(3, fkUnidade);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nomeUnidade");
                } else {
                    return null;
                }
            }
        }
    }

}
