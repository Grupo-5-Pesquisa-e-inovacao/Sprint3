import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private String nomeProcessador;
    private Double frequenciaProcessador;
    private String disco;
    private String ram;
    private String rede;

    public List<Integer> obterListaServidores(Connection conexao) throws SQLException {
        List<Integer> servidores = new ArrayList<>();
        String sql = "SELECT idServidor FROM servidor ORDER BY idServidor DESC LIMIT 1";

        try (PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                servidores.add(resultSet.getInt("idServidor"));
            }
        }

        return servidores;
    }



    public String getNomeProcessador() {
        return nomeProcessador;
    }

    public void setNomeProcessador(String nomeProcessador) {
        this.nomeProcessador = nomeProcessador;
    }

    public Double getFrequenciaProcessador() {
        return frequenciaProcessador;
    }

    public void setFrequenciaProcessador(Double frequenciaProcessador) {
        this.frequenciaProcessador = frequenciaProcessador;
    }

    public String getDisco() {
        return disco;
    }

    public void setDisco(String disco) {
        this.disco = disco;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRede() {
        return rede;
    }

    public void setRede(String rede) {
        this.rede = rede;
    }
}
