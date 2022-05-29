package data;

import java.sql.SQLException;
import exceptions.DAOException;

public interface TransactionsDAO {
  
  public void deposit(int numberAccount, int amount, String concept) throws SQLException, DAOException, Exception ;
  public void withdraw(int numberAccount, int amount, String concept) throws DAOException, SQLException, Exception;
  public void transfer(int numberAccount, int amount, int transferAccountNumber, String concept) throws Exception;

}
