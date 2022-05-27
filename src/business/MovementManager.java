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

  public void MovementDAO(DAOManager daoManager) throws DAOException {
    this.movementDAO = daoManager.getMovementDAO();
  }

  public void setMovementDAO(MovementDAO movementDAO) {
    this.movementDAO = movementDAO;
  }
  
  public Movement get(int numberAccount) throws DAOException {
    return movementDAO.get(numberAccount);
  }

  public int balance(int numberAccount) throws DAOException, SQLException {
    return movementDAO.balance(numberAccount);
  }
  
  

}
