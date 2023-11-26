import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase {
    private static final String LOCAL_DB_URL = "jdbc:mysql://localhost/CamelTech?user=aidmin&password=senhaDificil235813";
    private static final String CLOUD_DB_URL = "jdbc:mysql://containers-us-west-156.railway.app:6470/railway";
    private static final String CLOUD_DB_USER = "root";
    private static final String CLOUD_DB_PASSWORD = "Utjrg0FbyRsc68BFOQC3";

    public Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(LOCAL_DB_URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado.", e);
        }
    }

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

