import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
public class MemoriaRam {
    private Double memoriaTotal;
    private Double memoriaEmUso;
    private Double memoriaDisponivel;
    public void captarMemoria() {
        Looca looca = new Looca();
        Memoria memoria = looca.getMemoria();
        memoriaTotal = memoria.getTotal() / 1073741824.0;
        memoriaEmUso = memoria.getEmUso() / 1073741824.0;
        memoriaDisponivel = memoria.getDisponivel() / 1073741824.0;
    }
    public Double getMemoriaTotal() {
        return memoriaTotal;
    }
    public Double getMemoriaEmUso() {
        return memoriaEmUso;
    }
    public Double getMemoriaDisponivel() {
        return memoriaDisponivel;
    }
}


