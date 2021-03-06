package data;
/**
 * Clase que crea los los distintos DAOs a partir de un objeto de la clase Connection
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import exceptions.DAOException;

public class DAOManager {
  private Connection connection;
  
  public DAOManager(Connection connection) {
    this.connection = connection;
  }
  
  public DAOManager(String url) throws DAOException {
    try {
      connection = DriverManager.getConnection(url);
    } catch (SQLException e) {
      throw new DAOException("No se puede conectar a la base de datos");
    }
  }

  public CustomerDAO getCustomerDAO() throws DAOException {
    if(connection != null) {
      return new CustomerDAOSql(connection);
    }
    throw new DAOException("No existe conexión con la Base de Datos.");
  }

  public AccountDAO getAccountDAO() throws DAOException {
    if(connection != null){
      return new AccountDAOSql(connection);
    }
    throw new DAOException("No existe conexión con la Base de Datos.");
  }
  
  public TransactionsDAO getTransactionsDAO() throws DAOException {
    if (connection != null) {
      return new TransactionsDAOSql(connection);
    }
    throw new DAOException("No existe conexión a la base de datos");
  }
  
  public MovementDAO getMovementDAO() throws DAOException {
    if(connection != null) {
      return new MovementDAOSql(connection);
    }
    throw new DAOException("No existe conexión en la base de datos.");
  }
  
  public CustomerAccessDAO getCustomerAccessDAO() throws DAOException {
    if(connection != null) {
      return new CustomerAccessDAOSql(connection);
    }
    throw new DAOException("No existe conexión en la base de datos.");
  }
  
}
