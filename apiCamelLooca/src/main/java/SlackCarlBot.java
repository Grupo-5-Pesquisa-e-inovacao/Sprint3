import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SlackCarlBot {

    public static void main(String[] args) {
        String webhookUrl = "https://hooks.slack.com/services/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";
        String channel = "#dashboard";
        String username = "Carl-Bot";
        String text = "O servidor 104 está em estado de alerta";
        String iconUrl = "carl-bot.jpeg";

        try {
            String payload = String.format("{\"channel\": \"%s\", \"username\": \"%s\", \"text\": \"%s\", \"icon_url\": \"%s\"}", channel, username, text, iconUrl);

            // Criar a URL
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            // Enviar dados
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obter a resposta (opcional)
            int responseCode = connection.getResponseCode();
            System.out.println("Resposta do Slack: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
