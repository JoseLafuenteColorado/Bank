package business;

import java.sql.SQLException;
import data.DAOManager;
import data.MovementDAO;
import exceptions.DAOException;

public class MovementManager {
  private MovementDAO movementDAO;
  
  public MovementManager(DAOManager daoManager) throws DAOException {
    this.movementDAO = daoManager.getMovementDAO();
  }

  public Movement getAll(int numberAccount) throws DAOException {
    return movementDAO.getAll(numberAccount);
  }

  public int balance(int numberAccount) throws DAOException, SQLException {
    return movementDAO.balance(numberAccount);
  }
  
  

}
