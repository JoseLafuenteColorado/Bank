package business;

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

  public void deposit(int numberAccount, int amount, String concept) throws DAOException {
    movementDAO.deposit(numberAccount, amount, concept);
  }
  
  public void withdraw(int numberAccount, int amount, String concept) throws DAOException {
    movementDAO.withdraw(numberAccount, amount, concept);
  }
  
  public int getBalance(int numberAccount) throws DAOException {
    return movementDAO.getBalance(numberAccount);
  }
  
  

}
