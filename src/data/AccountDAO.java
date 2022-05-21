package data;

import java.sql.SQLException;
import business.Account;
import business.Customer;
import exceptions.DAOException;

public interface AccountDAO {
  public void add(Customer customer) throws DAOException;
  public void cancelAccount(int number) throws DAOException, SQLException;
  public void depositMoney(Account account) throws DAOException;
  public void withdrawMoney(Account account) throws DAOException;
  public void transferMoney(Account account) throws DAOException;
  public Account get(int number) throws DAOException;
  public Customer getCustomer(int number) throws DAOException;
  public boolean isActive(int number) throws DAOException;
  
}
