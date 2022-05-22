package business;

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
  
  public Account getAccount(int number) throws DAOException {
    return accountDAO.get(number);
  }
  
  public void add(Customer customer) throws DAOException {
    accountDAO.add(customer);
  }
  
  public void cancelAccount(int number) throws DAOException, SQLException {
    accountDAO.cancelAccount(number);
  }
  
  public Customer getCustomer(int number) throws DAOException {
    return accountDAO.getCustomer(number);
  }
  
  public boolean isActive(int number) throws DAOException {
    return accountDAO.isActive(number);
  }
  

}
