package util;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

public class PruebaEncriptar {

  public static void main(String[] args) throws NoSuchAlgorithmException, GeneralSecurityException {
    System.out.println(util.Util.getAESEncrypt("Hola Mundo"));
    System.out.println();
  }

}
