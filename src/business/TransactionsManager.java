package business;



/**
 * Clase que recibe unos parámetros de la clase TransactionsManagement, y se los pasa al TransactionsDAOSql 
 * para que pueda hacer la consulta a la BBDD.
 * 
 * A su vez, recibe los datos extraidos de la base de datos y se los muestra al TransactionsManagement
 * 
 * Es el "intermediario" entra capas para la clase Transactions.
 */


import data.DAOManager;
import data.TransactionsDAO;
import exceptions.DAOException;

public class TransactionsManager {
  
    private TransactionsDAO transactionsDAO;
    
    public TransactionsManager(DAOManager daoManager) throws DAOException {
      this.transactionsDAO = daoManager.getTransactionsDAO();
    }
    
    public void deposit(int numberAccount, int amount, String concept) throws Exception {
      transactionsDAO.deposit(numberAccount, amount, concept);
    }
    
    public void withdraw(int numberAccount, int amount, String concept) throws Exception {
      transactionsDAO.withdraw(numberAccount, amount, concept);
    }
    public void transfer(int numberAccount, int amount, int transferAccountNumber, String concept) throws Exception {
      transactionsDAO.transfer(numberAccount, amount, transferAccountNumber, concept);
    }
    
    
    
    
    
}
