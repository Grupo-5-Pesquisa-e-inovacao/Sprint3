import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.rede.RedeInterfaceGroup;
import oshi.SystemInfo;

public class PlacaDeRede {

    private String hostName;
    private String numIpv4;
    private Long bytesRecebidosAntes;
    private Long bytesEnviadosAntes;

    public void captarPlacaDeRede() {
        Looca looca = new Looca();
        Rede rede = looca.getRede();
        RedeInterfaceGroup redeInterfaceGroup = new RedeInterfaceGroup(new SystemInfo());
        hostName = rede.getParametros().getHostName();

        for (int i = 0; i < redeInterfaceGroup.getInterfaces().size(); i++) {
            if (redeInterfaceGroup.getInterfaces().get(i).getEnderecoIpv4().size() > 0) {
                numIpv4 = redeInterfaceGroup.getInterfaces().get(i).getEnderecoIpv4().get(0);
                bytesRecebidosAntes = redeInterfaceGroup.getInterfaces().get(i).getBytesRecebidos();
                bytesEnviadosAntes = redeInterfaceGroup.getInterfaces().get(i).getBytesEnviados();
            }
        }
    }

    public Long calcularVelocidadeDeRede() {
        Looca looca = new Looca();
        RedeInterfaceGroup redeInterfaceGroup = new RedeInterfaceGroup(new SystemInfo());

        for (int i = 0; i < redeInterfaceGroup.getInterfaces().size(); i++) {
            if (redeInterfaceGroup.getInterfaces().get(i).getEnderecoIpv4().size() > 0) {
                Long bytesRecebidosAgora = redeInterfaceGroup.getInterfaces().get(i).getBytesRecebidos();
                Long bytesEnviadosAgora = redeInterfaceGroup.getInterfaces().get(i).getBytesEnviados();

                Long velocidadeRecebida = bytesRecebidosAgora - bytesRecebidosAntes;
                Long velocidadeEnviada = bytesEnviadosAgora - bytesEnviadosAntes;

                // Atualizar os valores anteriores para o próximo cálculo
                bytesRecebidosAntes = bytesRecebidosAgora;
                bytesEnviadosAntes = bytesEnviadosAgora;

                return velocidadeRecebida + velocidadeEnviada;
            }
        }

        return null;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getNumIpv4() {
        return numIpv4;
    }

    public void setNumIpv4(String numIpv4) {
        this.numIpv4 = numIpv4;
    }

    public Long getBytesRecebidosAntes() {
        return bytesRecebidosAntes;
    }

    public void setBytesRecebidosAntes(Long bytesRecebidosAntes) {
        this.bytesRecebidosAntes = bytesRecebidosAntes;
    }

    public Long getBytesEnviadosAntes() {
        return bytesEnviadosAntes;
    }

    public void setBytesEnviadosAntes(Long bytesEnviadosAntes) {
        this.bytesEnviadosAntes = bytesEnviadosAntes;
    }

    public static void main(String[] args) {
        RedeInterfaceGroup redeInterfaceGroup = new RedeInterfaceGroup(new SystemInfo());
        System.out.println(redeInterfaceGroup.getInterfaces().get(1).getEnderecoIpv4().size());
    }
}
