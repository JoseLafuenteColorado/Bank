package business;

import java.sql.SQLException;
import java.util.List;
import data.DAOManager;
import data.MovementDAO;
import exceptions.DAOException;

public class MovementManager {
  private MovementDAO movementDAO;
  
  public MovementManager(DAOManager daoManager) throws DAOException {
    this.movementDAO = daoManager.getMovementDAO();
  }

  public Movement getLast(int numberAccount) throws DAOException {
    return movementDAO.getLast(numberAccount);
  }

  public int balance(int numberAccount) throws DAOException, SQLException {
    return movementDAO.balance(numberAccount);
  }
  
  public List<Movement> list(int numberAccount) throws DAOException {
    return movementDAO.list(numberAccount);
  }
  
  public List<Movement> listBetweenDates(int numberAccount, String firstDate, String lastDate) throws DAOException {
    return movementDAO.listBetweenDates(numberAccount, firstDate, lastDate);
  }
  

}
