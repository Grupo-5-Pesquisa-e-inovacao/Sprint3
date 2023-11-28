import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    // Construtor
    public Usuario(int idUsuario, String nome, String cpf, String email, String senha) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;

    }

    public List<Integer> obterIdsServidoresAtrelados(Connection conexao) throws SQLException {
        List<Integer> idsServidores = new ArrayList<>();

        String sql = "SELECT idServidor FROM servidor WHERE fkUsuario = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, this.idUsuario);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idServidor = resultSet.getInt("idServidor");
                    idsServidores.add(idServidor);
                }
            }
        }

        return idsServidores;
    }



    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
