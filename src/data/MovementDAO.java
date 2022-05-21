package data;

import java.time.LocalDateTime;
import java.util.List;
import business.Movement;
import exceptions.DAOException;

public interface MovementDAO {
  public List<Movement> list(int number) throws DAOException;
  public List<Movement> list(String filter) throws DAOException;
  public int getBalance(int number) throws DAOException;
  public void deposit(int amount, int number, String concept) throws DAOException;
  public void withdraw();
  public Movement get(int number, LocalDateTime dateTime);
  

}
