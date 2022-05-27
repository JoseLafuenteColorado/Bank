package data;

import java.sql.SQLException;
import java.util.List;
import business.Movement;
import exceptions.DAOException;

public interface MovementDAO {
  public List<Movement> list(int numberAccount) throws DAOException;
  public List<Movement> list(String filterA) throws DAOException;
  public int balance(int numberAccount) throws DAOException, SQLException;
  public Movement get(int numberAccount) throws DAOException;
  
  

}
