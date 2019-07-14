/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tuannnh
 */
public class Validator {
     public static boolean checkEmail(String email){
        return email.matches("\\w+[@]\\w+[.](\\w){2,6}([.](\\w){2,6})?");
    }
    public static boolean checkNumber(String number){
        return Integer.parseInt(number)>0;
    }
      public static boolean checkName(String name){
        return name.matches("[A-Za-z\\s]+");
    }
}
