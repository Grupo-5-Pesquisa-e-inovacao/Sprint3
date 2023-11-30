import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;

public class SlackCarlBot {

    //public static void main(String[] args) {
    public void mainSlack(String mensagem){
        String webhookUrl = "https://hooks.slack.com/services/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";
        String channel = "#dashboard";
        String username = "Carl-Bot";
        String text = mensagem;
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

            int responseCode = connection.getResponseCode();
            System.out.println("Resposta do Slack: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}