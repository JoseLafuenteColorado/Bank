package data;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import exceptions.DAOException;

/**
 * Interfaz para definir los métodos que tendrá el CustomerAccessDAOSql
 */

public interface CustomerAccessDAO {
  
  public void addCustomerAccess(String dni, String password) throws DAOException, NoSuchAlgorithmException, GeneralSecurityException;
  public boolean chekAccess(String dni, String password) throws DAOException, NoSuchAlgorithmException, GeneralSecurityException;
  public void modifyPass(String dni, String oldPassword, String newPassword) throws DAOException, NoSuchAlgorithmException, GeneralSecurityException;
  
}
