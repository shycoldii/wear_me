package client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignInService {
    public SignInService(){};

    public static boolean is_email(String email) throws IOException {
        URL url = new URL("http://localhost:8080/shop/login/"+ URLEncoder.encode(email));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try {
            String result = new BufferedReader(new InputStreamReader(conn.getInputStream())).
                    readLine();
            return Boolean.parseBoolean(result);
        }
        catch (ConnectException e){
            //TODO: сделать 404 и 500
            return false;
        }

    }
    public static Long is_password(String email,String password) throws IOException {
        URL url = new URL("http://localhost:8080/shop/login/"+URLEncoder.encode(email)+"/"+URLEncoder.encode(password));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try{
            String result = new BufferedReader(new InputStreamReader(conn.getInputStream()))
                    .readLine();
            if(result == null){
                return null;
            }
            return Long.parseLong(result);
        }
        catch (ConnectException e){
            //TODO: сделать 404 и обработку 500
            return null;
        }
    }
}
