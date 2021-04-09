package client.validator;

public class NumberValidator {
    public static boolean isNumber(String number){
        String regex = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3,4}\\)?[\\- ]?)?[\\d\\- ]{5,10}$";
        return  number.matches(regex);
    }
    public static boolean isRussianNumber(String number){
        //длина 11
        if (number.length() ==11 & number.charAt(0) == '7' & number.charAt(1) == '9'
        ){
           try{
               Long.parseLong(number.substring(2));
               return true;
           }
           catch(NumberFormatException e){
               return false;
           }
        }
        return false;
    }
}
