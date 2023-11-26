import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processador.Processador;
public class PuxarProcessador {
    Looca looca = new Looca();
    Processador processador = looca.getProcessador();
    private String fabricante;
    private String nome;
    private Integer numCpuFisica;
    private Integer numCpuLogica;
    private Double qtdemUso;
    private Long frequencia ;
    public void captarProcessador(){
        fabricante = processador.getFabricante();
        nome = processador.getNome();
        numCpuFisica = processador.getNumeroCpusFisicas();
        numCpuLogica = processador.getNumeroCpusLogicas();
        qtdemUso = processador.getUso();
        frequencia = processador.getFrequencia();
    }
    public Looca getLooca() {
        return looca;
    }
    public Processador getProcessador() {
        return processador;
    }
    public String getFabricante() {
        return fabricante;
    }
    public String getNome() {
        return nome;
    }
    public Integer getNumCpuFisica() {
        return numCpuFisica;
    }
    public Integer getNumCpuLogica() {
        return numCpuLogica;
    }
    public Double getQtdemUso() {
        return qtdemUso;
    }
    public Long getFrequencia() {
        return frequencia;
    }
}