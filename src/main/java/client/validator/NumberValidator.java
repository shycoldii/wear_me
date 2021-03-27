package client.validator;

public class NumberValidator {
    public static boolean isNumber(String number){
        String regex = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3,4}\\)?[\\- ]?)?[\\d\\- ]{5,10}$";
        return  number.matches(regex);
    }
}
