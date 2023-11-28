import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;

public class ValidacaoAlertas {
    MemoriaDeDisco disco = new MemoriaDeDisco();
    MemoriaRam ram = new MemoriaRam();
    PlacaDeRede rede = new PlacaDeRede();
    PuxarProcessador processador = new PuxarProcessador();
    DataBase db = new DataBase();
    SlackCarlBot slack = new SlackCarlBot();

    Usuario usuario;
    public ValidacaoAlertas(Usuario usuario) {
        this.usuario = usuario;
    }

    public String montarMensagem(Connection conexao, String componente) throws SQLException {
        Integer maxUsoDisco = db.obterMaxUsoDisco(conexao, usuario.getIdUsuario());
        Integer maxUsoRam = db.obterMaxUsoRam(conexao, usuario.getIdUsuario());
        Double frequenciaIdealProcessador = db.obterFrequenciaIdealProcessador(conexao, usuario.getIdUsuario());
        Double obterVelocidadeDeRede = db.obterVelocidadeDeRede(conexao, usuario.getIdUsuario());

        String mensagem = null;

        String query = "SELECT numeroRegistro FROM servidor WHERE fkUsuario = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(query)) {
            preparedStatement.setInt(1, usuario.getIdUsuario());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String numeroRegistro = resultSet.getString("numeroRegistro");

                    if (componente.equals("Ram") && maxUsoRam >= 1) {
                        mensagem = "O servidor de identificação " + numeroRegistro + " está com " + componente + " em estado de alerta";
                        slack.mainSlack(mensagem);
                    }
                    if (componente.equals("Disco") && maxUsoDisco >= 1) {
                        mensagem = "O servidor de identificação " + numeroRegistro + " está com " + componente + " em estado de alerta";
                        slack.mainSlack(mensagem);
                    }
                    if (componente.equals("Frequência") && frequenciaIdealProcessador >= 1) {
                        mensagem = "O servidor de identificação " + numeroRegistro + " está com " + componente + " em estado de alerta";
                        slack.mainSlack(mensagem);
                    }
                    if (componente.equals("Placa de Rede") && obterVelocidadeDeRede <= 1) {
                        mensagem = "O servidor de identificação " + numeroRegistro + " está com " + componente + " em estado de alerta";
                        slack.mainSlack(mensagem);
                    }
                } else {
                    System.out.println("Erro: Não foi possível encontrar o número de registro para o usuário " + usuario.getIdUsuario());
                }
            }
        }

        return mensagem;
    }




}