package util;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import exceptions.CustomerIllegalArgumentException;



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


  /**
   * LLaves para encriptar y desencriptar contraseñas
   */
  private static final String key = "2ho*wlphu=ri2?opi1otLtRut3owr4c1";
  private static final String salt = "xe20&$eFiSwUwEXi43acHeqechi1Ihoc";

  private static SecretKey secretKeyTemp;

  public void Encrypt() {
    SecretKeyFactory secretKeyFactory;
    KeySpec keySpec = null;

    try {
      secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSH512");
      secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 
   * @param data
   * @return
   * @throws NoSuchAlgorithmException
   * @throws GeneralSecurityException
   */
  public static String getAESEncrypt(String data) throws NoSuchAlgorithmException, GeneralSecurityException {
    byte[] iv = new byte[16];
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
      SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
      return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
    } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String getAESDescrypt(String data) throws NoSuchAlgorithmException, GeneralSecurityException {
    byte[] iv = new byte[16];
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
      SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
      return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
    } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Encriptar con MD5
   * @param input
   * @return
   */
  public static String getMD5(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] messageDigest = md.digest(input.getBytes());
      BigInteger number = new BigInteger(1, messageDigest);
      String hashtext = number.toString(16);

      while (hashtext.length() < 32) {
        hashtext = "0" + hashtext;
      }
      return hashtext;
    }
    catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }


}
