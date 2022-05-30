package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import business.Movement;
import exceptions.DAOException;

public class MovementDAOSql implements MovementDAO{

  private Connection connection;
  private List<Movement> movements = new ArrayList<>();

  public MovementDAOSql(Connection connection) {
    this.connection = connection;
  }

  /**
   * Ejecuta una consulta pasada como argumento a la BBDD
   * @param sql
   * @return número de filas afectadas
   * @throws DAOException
   */
  private int executeUpdate(String sql) throws DAOException {
    try(Statement statement = connection.createStatement()){
      return statement.executeUpdate(sql);
    } catch(SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Crea una Lista con todos los movimientos de una cuenta, trás una consulta previa a la BBDD
   * @param numberAccount
   * @return List<Movements> movements
   * @throws DAOException
   */
  @Override
  public List<Movement> list(int numberAccount) throws DAOException {
    String sql = "SELECT * FROM movements WHERE numberAccount = '" + numberAccount + "'";
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if (!resultSet.next()) {
        throw new DAOException("No existe movimientos para esa cuenta");
      }

      while (resultSet.next()) {
        movements.add(new Movement(resultSet.getInt("numberMovement"),resultSet.getInt("numberAccount"), resultSet.getInt("amount"), resultSet.getString("dateTime"), 
            resultSet.getString("type"), resultSet.getInt("transferAccountNumber"), resultSet.getString("concept")));
      }
      return movements;
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Crea una Lista con todos los movimientos de una cuenta entre fechas, trás una consulta previa a la BBDD
   * @param numberAccount, firstDate, lastDate
   * @return List<Movements> movements
   * @throws DAOException
   */
  
  @Override
  public List<Movement> listBetweenDates(int numberAccount, String firstDate, String lastDate) throws DAOException {
    String sql = "SELECT * FROM movements WHERE numberAccount = '" + numberAccount + "' AND SUBSTR(dateTime,1,10) BETWEEN '"+firstDate+"' AND '"+lastDate+"'";
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if (!resultSet.next()) {
        throw new DAOException("No existe movimientos para esa cuenta");
      }

      while (resultSet.next()) {
        movements.add(new Movement(resultSet.getInt("numberMovement"),resultSet.getInt("numberAccount"), resultSet.getInt("amount"), resultSet.getString("dateTime"), 
            resultSet.getString("type"), resultSet.getInt("transferAccountNumber"), resultSet.getString("concept")));
      }
      return movements;
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  /**
   * Muestra el saldo de una cuenta corriente
   * @param int numberAccount
   * @return int balance
   * @throws DAOException
   */

  @Override
  public int balance(int numberAccount) throws SQLException, DAOException {
    String sql = "Select SUM(amount) AS balance FROM movements WHERE numberAccount = '" + numberAccount + "' GROUP BY numberAccount";
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if (!resultSet.next()) {
        throw new DAOException("No existe la cuenta: " + numberAccount);
      }
      int balance;
      balance = resultSet.getInt("balance");
      return balance;
    }
  }

  /**
   * Muestra un último movimiento de una cuenta
   * @param int numberAccount
   * @return resulSet de la tabla Movements
   * @throws DAOException
   */
  @Override
  public Movement getLast(int numberAccount) throws DAOException {
    String sql = "SELECT * FROM movements WHERE numberAccount = '" + numberAccount + "'";
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if (!resultSet.next()) {
        throw new DAOException("No existe movimientos para esa cuenta");
      }
      return new Movement(resultSet.getInt("numberMovement"),resultSet.getInt("numberAccount"), resultSet.getInt("amount"), resultSet.getString("dateTime"), 
          resultSet.getString("type"), resultSet.getInt("transferAccountNumber"), resultSet.getString("concept"));
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }





}
