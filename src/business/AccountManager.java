package business;

/**
 * Clase que recibe unos parámetros de la clase AccountManagement, y se los pasa al AccountDAOSql 
 * para que pueda hacer la consulta a la BBDD.
 * 
 * A su vez, recibe los datos extraidos de la base de datos y se los muestra al AccountManagement
 * 
 * Es el "intermediario" entra capas para la clase Account.
 */


import java.sql.SQLException;
import data.AccountDAO;
import data.DAOManager;
import exceptions.DAOException;

public class AccountManager {
  
  private AccountDAO accountDAO;
  
  public AccountManager(DAOManager daoManager ) throws DAOException {
    this.accountDAO = daoManager.getAccountDAO(); 
  }
  
  public void set(AccountDAO accountDAO) {
    this.accountDAO = accountDAO;
  }
  
  public Account getAccount(int numberAccount) throws DAOException {
    return accountDAO.get(numberAccount);
  }
  
  public void add(String dni) throws DAOException {
    accountDAO.add(dni);
  }
  
  public boolean checkDni(String dni) throws DAOException {
    return accountDAO.checkDni(dni);
  }
  
  public void cancelAccount(int numberAccount) throws DAOException, SQLException {
    accountDAO.cancelAccount(numberAccount);
  }
  
  public Customer getCustomer(int numberAccount) throws DAOException {
    return accountDAO.getCustomer(numberAccount);
  }
  
  public boolean isActive(int numberAccount) throws DAOException {
    return accountDAO.isActive(numberAccount);
  }
  

}
