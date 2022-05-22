package data;

import java.util.List;
import business.Movement;
import exceptions.DAOException;

public interface MovementDAO {
  public List<Movement> list(int numberAccount) throws DAOException;
  public List<Movement> list(String filterA) throws DAOException;
  //public List<Movement> list(int numberAccount, String filterA, String filterB) throws DAOException;
  public int getBalance(int numberAccount) throws DAOException;
  public void deposit(int numberAccount, int amount, String concept) throws DAOException;
  public void withdraw(int numberAccount, int amount, String concept) throws DAOException;
  public Movement get(int numberAccount) throws DAOException;
  //public Movement get(int numberAccount, LocalDateTime dateTime);
  
  

}
