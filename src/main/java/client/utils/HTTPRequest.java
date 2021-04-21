package client.utils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Класс для работы с http-запросами
 */
public class HTTPRequest {
    /**
     * Реализует GET-обращение к серверу
     * @param urlString - адрес
     * @return String - полученные данные
     */
    public static String Get(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            {
                StringBuilder sb = new StringBuilder();
                InputStream is = new BufferedInputStream(conn.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                return sb.toString();
            }

        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Реализует POST-обращение к серверу
     * @param link - адрес
     * @param jsonObject - объект
     * @return String -  полученные данные
     * @throws IOException - может возникнуть при отсутствии подключения
     */
    public static String Post(String link, JSONObject jsonObject) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        try(OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return execResponse(httpURLConnection);
    }
    /**
     * Реализует PUT-обращение к серверу
     * @param link - адрес
     * @param jsonObject - объект
     * @return String -  полученные данные
     * @throws IOException - может возникнуть при отсутствии подключения/успешного завершения
     */
    public static String Put(String link, JSONObject jsonObject) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("PUT");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
        try(OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return execResponse(httpURLConnection);
    }

    /**
     * Реализует DELETE-обращение к серверу
     * @param link - адрес
     * @throws IOException -  может возникнуть при отсутствии подключения/успешного завершения
     * @return String - полученные данные
     */
    public static String Delete(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("DELETE");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
        return execResponse(httpURLConnection);
    }

    /**
     * Считывает полученные данные
     * @param httpURLConnection - http-соединение
     * @return String - полученные данные
     * @throws IOException - может возникнуть при отсутствии подключения/успешного завершения
     */
    private static String execResponse(HttpURLConnection httpURLConnection) throws IOException {
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

}
