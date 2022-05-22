package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import business.Movement;
import business.MovementManager;
import business.MovementType;
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


  /*
  @Override
  public List<Movement> list(String filterA, String filterB) throws DAOException {
    String sql = "SELECT *"
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)){

      List<Movement> list = new ArrayList<>();
      while(resultSet.next()) {
        LocalDateTime dateTime = LocalDateTime.now();
        String day = resultSet.getString("date").substring(0, 4);
        String month;
        String year;
        String hour;
        String minute;
        String second;
        list.add(new Movement(resultSet.getInt("numberAccount"), resultSet.getInt("amount"),  dateTime,
            MovementType.valueOf(resultSet.getString("type")), 
            resultSet.getInt("transferAccount"), resultSet.getString("concept")));
      }
    } catch(SQLException e) {
      throw new DAOException(e);
    }
    return null;
  } */

  @Override
  public int getBalance(int numberAccount) throws DAOException {
    String sql = "SELECT SUM(amount) AS 'balance' FROM movements WHERE numberAccount = '" + numberAccount + "'";
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      int balance = resultSet.getInt("balance");
      statement.executeUpdate(sql);
      return balance;
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  @Override
  public void deposit(int numberAccount, int amount, String concept) throws DAOException {
    LocalDateTime dateTime = LocalDateTime.now();
    if (concept.length() == 0) {
      String conceptNull = "sin concepto";
    }
    String conceptNull = concept;
    int transferAccountNumber = 0;
    String sql = "INSERT INTO movements VALUES(" + numberAccount + ", " + amount + ", " + 
        dateTime.toString() + ", \"INGRESO\", " + transferAccountNumber + ", " + conceptNull + ")";
    executeUpdate(sql);
  }



  @Override
  public void withdraw(int numberAccount, int amount, String concept) throws DAOException {
    LocalDateTime dateTime = LocalDateTime.now();
    int amountNeg = amount * (-1);
    if (concept.length() == 0) {
      String conceptNull = "sin concepto";
    }
    String conceptNull = concept;
    int transferAccountNumber = 0;
    String sql = "INSERT INTO movements VALUES(" + numberAccount + ", " + amount + ", " + 
        dateTime.toString() + ", \"RETIRADA\", " + transferAccountNumber + ", " + conceptNull + ")";
    executeUpdate(sql);
  }

  @Override
  public Movement get(int numberAccount) throws DAOException {
    String sql = "SELECT * FROM movements WHERE numberAccount = '" + numberAccount + "'";
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if (!resultSet.next()) {
        throw new DAOException("No existe movimientos para esa cuenta");
      }
      return new Movement(resultSet.getInt("numberAccount"), resultSet.getInt("amount"), resultSet.getString("dateTime"), 
          resultSet.getString("type"), resultSet.getInt("transferAccountNumber"), resultSet.getString("concept"));
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }



}
