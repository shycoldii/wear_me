package client.validator;

/**
 * Валидация телефонных номеров
 */
public class NumberValidator {
    /**
     * Проверяет, является ли строка номером телефона
     * @param number - номер телефона
     * @return true/false
     */
    public static boolean isNumber(String number){
        String regex = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3,4}\\)?[\\- ]?)?[\\d\\- ]{5,10}$";
        return  number.matches(regex);
    }

    /**
     * Проверяет, является ли строка русским номером телефона
     * @param number - номер телефона
     * @return true/false
     */
    public static boolean isRussianNumber(String number){
        //длина 11
        if (number.length() ==11 & number.charAt(0) == '7'
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
