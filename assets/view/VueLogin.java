package view;

import java.util.Scanner;

public class VueLogin {
    public static void printText (String text ){
        System.out.print(text);
    }

    public static String getText (){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    
}
