package data;

/**
 * Clase que conecta con la BBDD, es la encargada de "traer" los datos y pasárselos al AccountManager 
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import business.Account;
import business.Customer;
import exceptions.DAOException;

public class AccountDAOSql implements AccountDAO{
  private Connection connection;

  public AccountDAOSql(Connection connection) {
    this.connection = connection;
  }

  /** Ejecuta una consulta a la base de datos
   * @param sql
   * @return int si la consulta ha tenido éxito.
   * @throws DAOException
   */

  private int executeUpdate(String sql) throws SQLException, DAOException {
    try (Statement statement = connection.createStatement()) {
      return statement.executeUpdate(sql);  
    } catch (Exception e) {
      throw new DAOException(e);
    }
  }

  /**
   * Comprueba en la BBDD que existe una cuenta cuando le pasamos un número de cuenta
   * @throws SQLException 
   * 
   */
  @Override
  public boolean checkAccount(int numberAccount) throws DAOException {
    String sql = "SELECT numberAccount FROM Accounts WHERE numberAccont = '" + numberAccount + "'";
    try {
      executeUpdate(sql);
      if (executeUpdate(sql) != 0) {
        return true;
      }

    } catch (DAOException | SQLException e) {
    }
    return false;
  }


  /**
   * Añade una cuenta pasándole el dni
   */
  @Override
  public void add(String dni) throws DAOException {
    String sql = "INSERT INTO accounts (dni, stage) VALUES('" + dni + "', 1)";
    try {
      executeUpdate(sql);
      System.out.println("Cuenta añadida");
    } catch (SQLException | DAOException e) {
      System.out.println("NO se ha podido añadir la cuenta");
    }
  }

  /**
   * Desactiva una cuenta, NUNCA LA ELIMINA.  
   */
  @Override
  public void cancelAccount(int numberAccount) throws SQLException, DAOException  {
    String sql = "UPDATE accounts SET stage = 0 WHERE numberAccount = '" + numberAccount + "'";
    try {
      executeUpdate(sql);
    } catch (Exception e) {
      throw new DAOException("No podido dar de baja la cuenta " + numberAccount);
    }


  }


  /**
   * Muestra una cuenta cuando le pasamos el número de ésta.
   */
  @Override
  public Account get(int numberAccount) throws DAOException {
    String sql = "SELECT * FROM accounts WHERE numberAccount = '" + numberAccount + "'";
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if (!resultSet.next()) {
        throw new DAOException("No existe la cuenta: " + numberAccount);
      }

      return new Account(resultSet.getInt("numberAccount"), resultSet.getString("dni"), 
          resultSet.getInt("stage"));
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Muestra el cliente propietario de la cuenta pasada por parámetro.
   */

  @Override
  public Customer getCustomer(int numberAccount) throws DAOException {
    String sql = "SELECT * FROM accounts CROSS JOIN customers ON accounts.dni= customers.dni WHERE customer.dni= '" + numberAccount + "'";
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if(!resultSet.next()) {
        throw new DAOException("No existe la cuenta: " + numberAccount);
      }

      Customer customer = new Customer(resultSet.getString("dni"), 
          resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("phone"));

      return customer;
    } catch(SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Nos dice si la cuenta está activa
   * @throws SQLException 
   */
  @Override
  public boolean isActive(int numberAccount) throws DAOException, SQLException {
    String sql = "SELECT stage FROM accounts WHERE numberAccount = '" + numberAccount + "'";
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if (resultSet.getInt("stage") == 1) {
        return true;
      }
      System.out.println("La cuenta no está activa");
      return false;

    } catch (SQLException e) {
      throw new DAOException("No se podido comprobar el estado de la cuenta");
    }

  } 



}
