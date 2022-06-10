package data;

/**
 * Interfaz para definir los métodos que tendrá el AccountDAOSql
 */


import java.sql.SQLException;
import business.Account;
import business.Customer;
import exceptions.DAOException;

public interface AccountDAO {
  public boolean checkAccount(int numberAccount) throws DAOException;
  public void add(String dni) throws DAOException;
  public void cancelAccount(int number) throws DAOException, SQLException;
  public Account get(int number) throws DAOException;
  public Customer getCustomer(int number) throws DAOException;
  public boolean isActive(int number) throws DAOException, SQLException;
  
  
}
