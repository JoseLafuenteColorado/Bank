package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import business.Movement;
import exceptions.DAOException;

public class MovementDAOSql implements MovementDAO{

  private Connection connection;

  public MovementDAOSql(Connection connection) {
    this.connection = connection;
  }

  private int executeUpdate(String sql) throws DAOException {
    try(Statement statement = connection.createStatement()){
      return statement.executeUpdate(sql);
    } catch(SQLException e) {
      throw new DAOException(e);
    }
  }

  @Override
  public List<Movement> list(int numberAccount) throws DAOException {
    return list("SELECT * FROM movements WHERE numberAccount = '" + numberAccount + "'");
  }

  @Override
  public List<Movement> list(String filterA) throws DAOException {
    // TODO Auto-generated method stub
    return null;
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
   * Muestra todos los movimientos de una cuenta
   * @param int numberAccount
   * @return resulSet de la tabla Movements
   * @throws DAOException
   */
  @Override
  public Movement getAll(int numberAccount) throws DAOException {
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
