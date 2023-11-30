import Log.LogGerador;
import Log.LogParameters;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase {
    LogParameters logParameters = new LogParameters();
    private static final String LOCAL_DB_URL = "jdbc:mysql://localhost/CamelTech?user=aidmin&password=senhaDificil235813";
  /*
    private static final String CLOUD_DB_URL = "jdbc:mysql://containers-us-west-156.railway.app:6470/railway";
    private static final String CLOUD_DB_USER = "root";
    private static final String CLOUD_DB_PASSWORD = "Utjrg0FbyRsc68BFOQC3";
*/
    String SQL_SERVER_URL = "jdbc:sqlserver://3.233.52.99:1433;databaseName=camelTech;encrypt=false;trustServerCertificate=true";
    String SQL_SERVER_USER = "sa";
    String SQL_SERVER_PASSWORD = "SASenha123";

     public Connection ConectarSQLServer() throws SQLException {
         try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             return DriverManager.getConnection(SQL_SERVER_URL, SQL_SERVER_USER, SQL_SERVER_PASSWORD);
         } catch (ClassNotFoundException e) {
             throw new SQLException("Driver JDBC do SQL Server não encontrado.", e);
         }
     }

     /*
     public Connection conectar() throws SQLException {
         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             return DriverManager.getConnection(LOCAL_DB_URL);
         } catch (ClassNotFoundException e) {
             throw new SQLException("Driver JDBC do MySQL não encontrado.", e);
         }
     }
*/
    public List<Integer> obterConfiguracoesPorTipo(Connection conexao, int tipoComponente) throws SQLException {
        List<Integer> configuracoes = new ArrayList<>();


        String sql = "SELECT c.idConfiguracao " +
                "FROM configuracao c " +
                "JOIN servidor s ON c.fkServidor = s.idServidor " +
                "WHERE c.fktipoComponente = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, tipoComponente);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    configuracoes.add(resultSet.getInt("idConfiguracao"));
                }
            }
        }

        return configuracoes;
    }



    public void inserirDados(Connection conexao, Number dadosCapturados, int fkTipoDado, List<Integer> servidoresIds, int tipoComponente) throws SQLException {
        Date dataHoraAtual = new Date();
        Timestamp timestamp = new Timestamp(dataHoraAtual.getTime());


        List<Integer> configuracoes = obterConfiguracoesPorTipo(conexao, tipoComponente);


        for (Integer servidorId : servidoresIds) {

            for (Integer fkConfiguracao : configuracoes) {

                if (configuracaoPertenceAoServidor(conexao, fkConfiguracao, servidorId)) {
                    String insercao = "INSERT INTO dadosCapturados (dadoCapturado, dtHora, fkConfiguracao, fkTipoDado) VALUES (?,?,?,?)";

                    try (PreparedStatement ps = conexao.prepareStatement(insercao)) {
                        ps.setObject(1, dadosCapturados);
                        ps.setTimestamp(2, timestamp);
                        ps.setInt(3, fkConfiguracao);
                        ps.setInt(4, fkTipoDado);
                        ps.executeUpdate();

                    } catch (SQLException e) {
                        System.out.println("Erro ao inserir dados no banco de dados: " + e.getMessage());
                    }
                }
            }

        }
    }
    private boolean configuracaoPertenceAoServidor(Connection conexao, int configuracao, int servidorId) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM configuracao WHERE idConfiguracao = ? AND fkServidor = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, configuracao);
            statement.setInt(2, servidorId);

            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;


    }


    public Integer obterMaxUsoRam(Connection conexao,  int fkUsuario) throws SQLException {
        Integer maxUsoRam = 0;
        String sql = "select maxUsoRam from servidor where fkUsuario = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, fkUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    maxUsoRam = resultSet.getInt("maxUsoRam");
                }
            }
        }
        return maxUsoRam;
    }
    public Integer obterMaxUsoDisco(Connection conexao, int fkUsuario) throws SQLException {
        Integer maxUsoDisco = 0;
        String sql = "select maxUsoDisco from servidor where fkUsuario = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, fkUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    maxUsoDisco = resultSet.getInt("maxUsoDisco");
                }
            }
        }
        return maxUsoDisco;
    }
    public Double obterFrequenciaIdealProcessador(Connection conexao,  int fkUsuario) throws SQLException {
        Double frequenciaIdealProcessador = 0.0;
        String sql = "select frequenciaIdealProcessador from servidor where fkUsuario = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, fkUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    frequenciaIdealProcessador = resultSet.getDouble("frequenciaIdealProcessador");
                }
            }
        }
        return frequenciaIdealProcessador;
    }
    public Double obterVelocidadeDeRede(Connection conexao,  int fkUsuario) throws SQLException {
        Double velocidadeDeRede = 0.0;
        String sql = "select velocidadeDeRede from servidor where fkUsuario = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, fkUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    velocidadeDeRede = resultSet.getDouble("velocidadeDeRede");
                }
            }
        }
        return velocidadeDeRede;
    }
}

    /*
    public Connection conectarNuvem() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(CLOUD_DB_URL, CLOUD_DB_USER, CLOUD_DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado.", e);
   }
 }
*/


