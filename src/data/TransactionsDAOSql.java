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
import exceptions.DAOException;

public class TransactionsDAOSql implements TransactionsDAO {
  private Connection connection;
  private AccountDAOSql accountDAOSql;
  private MovementDAOSql movementDAOSql;


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

  /**
   * Realiza un depósito al número de cuenta, con el importe y concepto, pasados por parámetros.
   * @param int numberAccount, int amount, String concept
   * @throws DAOException, SQLException
   * 
   */
  @Override
  public void deposit(int numberAccount, int amount, String concept) throws Exception {
    ammountIsNegative(amount);
    accountDAOSql.isActive(numberAccount);
    insufficientMoney(numberAccount, amount);
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String dateTimeString = dateTime.format(formatter);
    String sql = "INSERT INTO movements VALUES(null, " +numberAccount+ ", " +amount+ ", '" +dateTimeString+ "', 'ingreso', 0, '" +concept+ "')";
    try {
      executeUpdate(sql);
    } catch (DAOException | SQLException e) {
      throw new DAOException("No se ha podido realizar el ingreso");
    }
  }

  /**
   * Comprueba que el importe pasado no sea negativo
   * @param amount
   * @return false
   * @throws Exception
   */
  private boolean ammountIsNegative(int amount) throws Exception {
    if (amount <= 0) {
      throw new Exception("El importe tiene que ser mayor que 0");
    }
    return false;
  }
  
  /**
   * Realiza una retirada al número de cuenta, con el importe y concepto, pasados por parámetros.
   * @param int numberAccount, int amount, String concept
   * @throws DAOException, SQLException
   * 
   */
  @Override
  public void withdraw(int numberAccount, int amount, String concept) throws Exception {
    ammountIsNegative(amount);
    accountDAOSql.isActive(numberAccount);
    insufficientMoney(numberAccount, amount);
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String dateTimeString = dateTime.format(formatter);
    amount = amount*(-1);
    String sql = "INSERT INTO movements VALUES(null, " +numberAccount+ ", " +amount+ ", '" +dateTimeString+ "', 'retirada', 0, '" +concept+ "')";
    try {
      executeUpdate(sql);
    } catch (DAOException | SQLException e) {
      throw new DAOException("No se ha podido realizar la retirada");
    }

  }

  /**
   * Comprueba si el saldo de la cuenta es mayor que el importe pasado
   * @param numberAccount, amount
   * @return boolean
   * @throws DAOException, SQLEXception
   */
  private boolean insufficientMoney(int numberAccount, int amount) throws SQLException, DAOException {
    if (movementDAOSql.balance(numberAccount) < amount) {
      throw new DAOException("saldo insuficiente para ese importe");
    }
    return true;
  }
  
/** Realiza una transferencia desde una cuenta a otra , con un importe y concepto dado.
 * @param numberAccount, amount, transferAccountNumber, concept
 * @throws DAOException, SQLException
 * 
 */
  @Override
  public void transfer(int numberAccount, int amount, int transferAccountNumber, String concept) throws Exception {
    ammountIsNegative(amount);
    accountDAOSql.isActive(numberAccount);
    insufficientMoney(numberAccount, amount);
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String dateTimeString = dateTime.format(formatter);
    int amountWhitdraw = amount*(-1);
    
    String sql = "INSERT INTO movements VALUES(null, " +numberAccount+ ", " +amountWhitdraw+ ", '" +dateTimeString+ "', 'transferencia realizada', "+transferAccountNumber+", '" +concept+ "')";
    try {
      executeUpdate(sql);
    } catch (DAOException | SQLException e) {
      throw new DAOException("No se ha podido realizar la retirada para la transferencia");
    }
    
    String sql2 = "INSERT INTO movements VALUES(null, " +transferAccountNumber+ ", " +amount+ ", '" +dateTimeString+ "', 'transferencia recibida', "+numberAccount+", '" +concept+ "')";
    try {
      executeUpdate(sql2);
    } catch (DAOException | SQLException e) {
      throw new DAOException("No se ha podido realizar el ingreso de la transferencia");
    }
  }

  
  
}
