package util;

import java.util.Scanner;

public class Util {
  
  private static Scanner in = new Scanner(System.in);

  public static String readStr(String prompt) {
    System.out.print(prompt + ": ");
    return in.nextLine();
  }
  
  public static String readStr(String prompt, String end) {
    String str = readStr(prompt);
    System.out.print(end);
    return str;
  }

  public static String read(String prompt, String defaultValue) {
    String returnValue = readStr(prompt + " [" + defaultValue + "]");
    return returnValue.isEmpty() ? defaultValue : returnValue;
  }

  
  public static int readInt(String prompt) {
    System.out.print(prompt + ": ");
    int n = in.nextInt();
    in.nextLine();  // sacamos \n del buffer de teclado
    return n;
  }
  
  public static int readInt(String prompt, String end) {
    int n = readInt(prompt);
    System.out.print(end);
    return n;
  }
  
  public static double readDouble(String prompt) {
    System.out.print(prompt + ": ");
    double n = in.nextDouble();
    in.nextLine();  // sacamos \n del buffer de teclado
    return n;
  }
  
  public static double readDouble(String prompt, String end) {
    double n = readDouble(prompt);
    System.out.print(end);
    return n;
  }
  
  public static void cleanBuffer() {
    in.nextLine();  // sacamos \n del buffer de teclado
  }
  
  public static boolean confirm(String prompt) {
    String resp;
    for (;;) {
      resp = Util.readStr(prompt).toUpperCase();
      if (resp.equals("S") || resp.equals("N")) {
        break;
      }
      System.out.println("Respuesta incorrecta, pulse S o N y después Intro");
    }
    return resp.equals("S");
  }
  
  public static boolean confirm() {
    return confirm("¿Está seguro/a? (S/N)");
  }

  public static int randomInt(int min, int max) {
    return min + (int) (Math.random() * (max-min+1));
  }
  
  public static boolean dniIsOk(String dni) {
    if(dni.length() != 9) {
      throwDNIException();
    }
    if(!dniIsDigit(dni)) {
      throwDNIException();
    }
    if(!dniLetter(dni).equals(dni.substring(8).toUpperCase())) {
      throwDNIException();
    }
    return true;
  }
  
  private static String dniLetter(String dni) {
    String dniLetter = "TRWAGMYFPDXBNJZSQVHLCKE";
    int dniNumber = Integer.parseInt(dni.substring(0,8));
    return String.valueOf(dniLetter.charAt(dniNumber % 23));
  }
  

  private static boolean dniIsDigit(String dni) {
    for(int i = 0; i < dni.length()-1; i++) {
      if(!(Character.isDigit(dni.charAt(i)))){
        return false;
      }
    }
    return true;
  }
  private static void throwDNIException() {
    throw new CustomerIllegalArgumentException("El DNI no es válido");
  }
  
  public static String formatDni(String dni) {
    String dniNumber = dni.substring(0,8);
    String dniLetter = dni.substring(8).toUpperCase();
    return dniNumber + dniLetter;
  }
  
  public static boolean stringIsOk(String prompt) {
    if(prompt.isBlank() || prompt.isEmpty()) {
      return false;
    }
    return true;
  }
  
  public static boolean phoneNumberIsOk(int phoneNumber) {
    if(phoneNumber > 600000000 || phoneNumber < 999999999 ) {
      return true;
    }
    
    throw new CustomerIllegalArgumentException("El teléfono introducido no es válido.");
  }

}
