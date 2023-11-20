import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.rede.RedeInterfaceGroup;
import oshi.SystemInfo;

import java.util.List;

public class PlacaDeRede {

    private String hostName;
    private String numIpv4;
    private Long bytesRecebidos;
    private Long bytesEnviados;


    public void captarPlacaDeRede() {
        Looca looca = new Looca();
        Rede rede = looca.getRede();
        RedeInterfaceGroup rede2 =new RedeInterfaceGroup(new SystemInfo());
        hostName = rede.getParametros().getHostName();
        for (int i = 0; i < rede2.getInterfaces().size(); i++) {
            if (rede2.getInterfaces().get(i).getEnderecoIpv4().size() > 0){
                numIpv4 = rede2.getInterfaces().get(i).getEnderecoIpv4().get(0);
                bytesRecebidos = rede2.getInterfaces().get(i).getBytesRecebidos();
                bytesEnviados= rede2.getInterfaces().get(i).getBytesEnviados();

            }
        }
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

    public Long getBytesRecebidos() {
        return bytesRecebidos;
    }

    public void setBytesRecebidos(Long bytesRecebidos) {
        this.bytesRecebidos = bytesRecebidos;
    }

    public Long getBytesEnviados() {
        return bytesEnviados;
    }

    public void setBytesEnviados(Long bytesEnviados) {
        this.bytesEnviados = bytesEnviados;
    }

    public static void main(String[] args) {
        RedeInterfaceGroup rede2 =new RedeInterfaceGroup(new SystemInfo());
        System.out.println(rede2.getInterfaces().get(1).getEnderecoIpv4().size());
    }
}