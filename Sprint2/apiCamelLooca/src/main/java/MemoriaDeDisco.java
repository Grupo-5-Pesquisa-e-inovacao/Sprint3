import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import java.util.List;

public class MemoriaDeDisco {
    private String nome;
    private Double tamanho;

    private Double usoDisco;

    private Long serial;

    public void captarInformacoesDoDisco() {
        Looca looca = new Looca();
        DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();
        List<Disco> discos = grupoDeDiscos.getDiscos();


        for (Disco disco : discos) {
            nome = disco.getNome();
            tamanho = disco.getTamanho() / 1073741824.0;
            usoDisco = tamanho - (disco.getBytesDeEscritas() / 1073741824.0);

        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getTamanho() {
        return tamanho;
    }

    public void setTamanho(Double tamanho) {
        this.tamanho = tamanho;
    }

    public Double getUsoDisco() {
        return usoDisco;
    }

    public void setUsoDisco(Double usoDisco) {
        this.usoDisco = usoDisco;
    }
}

