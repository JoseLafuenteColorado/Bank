package business;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import data.CustomerAccessDAO;
import data.DAOManager;
import exceptions.DAOException;

/**
 * Clase que recibe unos parámetros de la Clase CustomerManagement y los pasa al CustomerAccessDAOSql para
 * controlar el acceso de un cliente a la aplicación
 * @author José Lafuente
 *
 */
public class CustomerAccessManager {
  
  private CustomerAccessDAO customerAccessDAO;
  
  public CustomerAccessManager(DAOManager daoManager) throws DAOException {
    this.customerAccessDAO = daoManager.getCustomerAccessDAO();
  }
  
  public void addCustomerAccess(String dni, String password) throws DAOException, NoSuchAlgorithmException, GeneralSecurityException {
    customerAccessDAO.addCustomerAccess(dni, password);
  }
  
  
  public boolean chekAccess(String dni, String password) throws DAOException, NoSuchAlgorithmException, GeneralSecurityException {
    return customerAccessDAO.chekAccess(dni, password);
  }
  
  public void modifyPass(String dni, String oldPassword, String newPassword) throws DAOException, NoSuchAlgorithmException, GeneralSecurityException {
    customerAccessDAO.modifyPass(dni, oldPassword, newPassword);
  }
  

}
