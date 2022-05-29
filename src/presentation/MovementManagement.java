package presentation;

import static util.Util.readInt;
import static util.Util.readStr;
import java.sql.SQLException;
import java.util.List;
import business.AccountManager;
import business.CustomerManager;
import business.Movement;
import business.MovementManager;
import business.TransactionsManager;
import data.DAOManager;
import exceptions.DAOException;
import util.Menu;

/**
 * Programa que utiliza un cliente para banca On-line.
 * @author José Lafuente
 *
 */

public class MovementManagement {
  private MovementManager movementManager;
  private TransactionsManager transactionsManager;
  private AccountManager accountManager;
  private CustomerManager customerManager;

  public static void main(String[] args) throws Exception {
    String url = "jdbc:sqlite:/home/jlc/banco.sqlite";

    try {
      MovementManagement program = new MovementManagement(url);
      program.run();
      System.out.println("¡Hasta la próxima! ;-)");
    } catch(DAOException e) {
      System.err.println("Error al conectar con la base de datos.");
    }
  }

  public MovementManagement(String url) throws DAOException {
    accountManager = new AccountManager(new DAOManager(url));
    customerManager = new CustomerManager(new DAOManager(url));
    transactionsManager = new TransactionsManager(new DAOManager(url));
    movementManager = new MovementManager(new DAOManager(url));
  }
  
  public void run() throws Exception {
    Menu menu = createMenu();

    int option;
    do {
      option = menu.choose();
      switch (option) {
        case 1 -> depositMoney();
        case 2 -> withdrawMoney();
        case 3 -> transferMoney();
        case 4 -> showAccount();
        case 5 -> balance();
      }
    } while (option != menu.lastOption());
  }

  private void depositMoney() throws Exception {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a ingresar ");
    String concept = readStr("Introduce el concepto ");
    transactionsManager.deposit(numberAccount, amount, concept);
    //int balance = transactionsManager.balance(numberAccount);
    //System.out.println("El saldo actualizado es :" + balance);
    System.out.println();
  }
  
  private void withdrawMoney() throws Exception {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a retirar ");
    String concept = readStr("Introduce el concepto ");
    transactionsManager.withdraw(numberAccount, amount, concept);
    //int balance = movementManager.balance(numberAccount);
    //System.out.println("El saldo actualizado es :" + balance);
    System.out.println();
  }
  
  private void transferMoney() throws Exception {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a transeferir ");
    int transferAccountNumber = readInt("Introduce el número de cuenta para hacerle la transferencia ");
    String concept = readStr("Introduce el concepto ");
    transactionsManager.transfer(numberAccount, amount, transferAccountNumber, concept);
    //int balance = movementManager.balance(numberAccount);
    //System.out.println("El saldo actualizado es :" + balance);
    System.out.println();
  }
  
  private void showAccount() throws DAOException {
    int numberAccount = readInt("Introduce el número de cuenta ");
    List<Movement> movements = movementManager.list(numberAccount);
    //Movement movements = movementManager.getAll(numberAccount);
    System.out.println(movements);
    System.out.println();
  }
  
  private void balance() throws DAOException, SQLException {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int balance = movementManager.balance(numberAccount);
    System.out.println("Su saldo actual es: " + balance + " denarios");
    System.out.println();
  }
  
  private Menu createMenu() {
    return new Menu("Gestión de Banco (Gestión Online banca personal)", "Ingreso en cuenta", 
        "Sacar dinero", "Realizar transferencia", "Mostrar movimientos", "Saldo", "Terminar");
  }
  
}
