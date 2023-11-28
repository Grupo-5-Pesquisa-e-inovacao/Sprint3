import java.sql.Connection;
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

    public String montarMensagem(Connection conexao, String componente, String numIpv4) throws SQLException {
        String maxUsoDisco = db.obterMaxUsoDisco(conexao, usuario.getIdUsuario());
        String maxUsoRam = db.obterMaxUsoRam(conexao, usuario.getIdUsuario());
        String frequenciaIdealProcessador = db.obterFrequenciaIdealProcessador(conexao, usuario.getIdUsuario());
        String obterVelocidadeDeRede = db.obterVelocidadeDeRede(conexao, usuario.getIdUsuario());

        String mensagem = null;
        if (componente.equals("Ram")) {
            if (ram.getMemoriaEmUso() >= 1) { //maxUsoRam
                String NumIpv4 = rede.getNumIpv4();
                mensagem = "O servidor de identificação " + numIpv4 + " está com " + componente + " em estado de alerta";
                slack.mainSlack(mensagem);
            }
        }
        if (componente.equals("Disco")) {
            if (disco.getUsoDisco() >= 1) { //maxUsoDisco
                String NumIpv4 = rede.getNumIpv4();
                mensagem = "O servidor de identificação " + numIpv4 + " está com " + componente + " em estado de alerta";
                slack.mainSlack(mensagem);
            }
        }
        if (componente.equals("Processador")) {
            if (processador.getQtdemUso() >= 1) { //frequenciaIdealProcessador
                String NumIpv4 = rede.getNumIpv4();
                mensagem = "O servidor de identificação " + numIpv4 + " está com " + componente + " em estado de alerta";
                slack.mainSlack(mensagem);
            }
        }
        if (componente.equals("Placa de Rede")) {
            if (rede.getVelocidadeDeRede() <= 1) { //velocidaDeRede
                String NumIpv4 = rede.getNumIpv4();
                mensagem = "O servidor de identificação " + numIpv4 + " está com " + componente + " em estado de alerta";
                slack.mainSlack(mensagem);
            }
        }
        return mensagem;
    }


}