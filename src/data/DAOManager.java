package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

  public AccountDAO getCurrentAccountDAO() throws DAOException {
    if(connection != null){
      return new AccountDAOSql(connection);
    }
    throw new DAOException("No existe conexión con la Base de Datos.");
  }
  
  public MovementDAO getCustomerAccountDAO() throws DAOException {
    if(connection != null) {
      return new MovementDAOSql(connection);
    }
    throw new DAOException("No existe conexión en la base de datos.");
  }
  
  
}
