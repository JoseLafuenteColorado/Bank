package data;

/**
 * /**
 * Clase que conecta con la BBDD, es la encargada de "traer" los datos y pasárselos al TransactionsManager 
 */


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import business.Account;
import business.Customer;
import business.Movement;
import business.Transactions;
import exceptions.DAOException;

public class TransactionsDAOSql implements TransactionsDAO {
  private Connection connection;
  private Customer customer;
  private Account account;
  private Transactions transactions;
  private Movement movement;

  public TransactionsDAOSql(Connection connection) {
    this.connection = connection;
  }

  /** Ejecuta una consulta a la base de datos
   * @param sql
   * @return int si la consulta ha tenido éxito.
   * @throws DAOException
   */

  private int executeUpdate(String sql) throws SQLException, DAOException {
    try {
      Statement statement = connection.createStatement();
      return statement.executeUpdate(sql);  
    } catch (Exception e) {
      throw new DAOException(e);
    }
  }

  @Override
  public void deposit(int numberAccount, int amount, String concept) throws Exception {
    ammountIsNegative(amount);
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String dateTimeString = dateTime.format(formatter);
    String sql = "INSERT INTO movements (numberAcount, amount, LocalDateTime, type, transferAccountNumber, concept)"
        + " VALUES('" + numberAccount + "', '" + amount+ "', '" + dateTimeString + "', ingreso, 0, '" + concept + "')";
    try {
      executeUpdate(sql);
    } catch (DAOException | SQLException e) {
      throw new DAOException("No se ha podido realizar el ingreso");
    }
  }

  private boolean ammountIsNegative(int amount) throws Exception {
    if (amount <= 0) {
      throw new Exception("El importe tiene que ser mayor que 0");
    }
    return false;
  }

  @Override
  public void withdraw(int numberAccount, int amount, String concept) throws Exception {
    if (ammountIsNegative(amount) == false) {
      LocalDateTime dateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
      String dateTimeString = dateTime.format(formatter);
      amount = amount*(-1);
      String sql = "INSERT INTO movements (numberAcount, amount, LocalDateTime, type, transferAccountNumber, concept)"
          + " VALUES('" + numberAccount+ "', '" + amount+ "', '" + dateTimeString + "', ingreso, 0, '" + concept + "')";
      try {
        executeUpdate(sql);
      } catch (DAOException | SQLException e) {
        throw new DAOException("No se ha podido realizar el ingreso");
      }
    }
  }


  @Override
  public void transfer(int numberAccount, int amount, int transferAccountNumber, String concept) {
  // TODO Auto-generated method stub

  }

}
