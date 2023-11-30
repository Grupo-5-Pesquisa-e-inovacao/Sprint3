package Log;

import com.mysql.cj.log.Log;

import java.sql.SQLException;

public class LogParameters {
    LogGerador log = new LogGerador();

    public void logLogin(String email, String senha, String status, String resultado){
        log.LogLogin(email, senha, status, resultado);
    }
    public void logParametros(String status, String resposta){
        log.mainLog(status,resposta);
    }

    public void logConexao(String conexao, String resposta, String status){
        log.LogConexao(conexao, resposta, status);
    }

}
