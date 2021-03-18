package client.validator;

public class WordValidator {
    public static boolean check(String field){
        return !field.contains("/") & !field.contains("\\") & !field.contains("&") & !field.contains(":");
    }
}
