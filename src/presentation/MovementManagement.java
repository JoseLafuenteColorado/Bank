package presentation;

import static util.Util.readInt;
import static util.Util.readStr;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import business.AccountManager;
import business.CustomerAccessManager;
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
  private static CustomerAccessManager customerAccessManager;

  public static void main(String[] args) throws Exception {
    String url = "jdbc:sqlite:/home/jlc/banco.sqlite";

    /* doy de alta a algunos clientes en la aplicación on-line, para ello necesito crear: 
      registerCustomer();
      clientes probados dni:75430449V pass:prueba77  y dni:58714014M  pass: prueba22
    */

    try {
      MovementManagement program = new MovementManagement(url);
      MenuAccess();
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
    customerAccessManager = new CustomerAccessManager(new DAOManager(url));
  }
  
  public static void MenuAccess()
      throws NoSuchAlgorithmException, DAOException, GeneralSecurityException {
    Menu menuAccess = createMenuAccess();
    int option;
    do {
      option = menuAccess.choose();
      switch (option) {
        case 1 -> registerCustomer();
        case 2 -> accessCustomer();
      }
    } while (option != menuAccess.lastOption());
  }

  private static Menu createMenuAccess() {
    return new Menu("Bienvenido a su Banca On-Line", "Registrarse", 
        "Acceso");
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
        case 6 -> showAccountBetweenDates();
        case 7 -> modifyPass();
      }
    } while (option != menu.lastOption());
  }

  private void depositMoney() throws Exception {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a ingresar ");
    String concept = readStr("Introduce el concepto ");
    transactionsManager.deposit(numberAccount, amount, concept);
    int balance = movementManager.balance(numberAccount);
    System.out.println("El saldo actualizado es :" + balance);
    System.out.println();
  }

  private void withdrawMoney() throws Exception {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a retirar ");
    String concept = readStr("Introduce el concepto ");
    transactionsManager.withdraw(numberAccount, amount, concept);
    int balance = movementManager.balance(numberAccount);
    System.out.println("El saldo actualizado es :" + balance);
    System.out.println();
  }

  private void transferMoney() throws Exception {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a transferir ");
    int transferAccountNumber = readInt("Introduce el número de cuenta para hacerle la transferencia ");
    String concept = readStr("Introduce el concepto ");
    transactionsManager.transfer(numberAccount, amount, transferAccountNumber, concept);
    int balance = movementManager.balance(numberAccount);
    System.out.println("El saldo actualizado es :" + balance);
    System.out.println();
  }

  private void showAccount() throws DAOException {
    int numberAccount = readInt("Introduce el número de cuenta ");
    List<Movement> movements = movementManager.list(numberAccount);
    System.out.println(movements);
    System.out.println();
  }

  private void balance() throws DAOException, SQLException {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int balance = movementManager.balance(numberAccount);
    System.out.println("Su saldo actual es: " + balance + " €");
    System.out.println();
  }

  private void showAccountBetweenDates() throws DAOException {
    int numberAccount = readInt("Introduce el número de cuenta");
    System.out.println("Para las fechas utilice el formato dd-mm-yyyy (ej:23-12-2021), por favor");
    String firstDate = readStr("Introduce la primera fecha a mostrar");
    String lastDate = readStr("Introduce la última fecha a mostrar");
    List<Movement> movements = movementManager.listBetweenDates(numberAccount, firstDate, lastDate);
    System.out.println(movements);
    System.out.println();
  }

  private Menu createMenu() {
    return new Menu("Gestión de Banco (Gestión Online banca personal)", "Ingreso en cuenta", 
        "Sacar dinero", "Realizar transferencia", "Mostrar movimientos", "Saldo", "Mostrar movimientos entre fechas", "Modificar contraseña", "Terminar");
  }
  
  private static void registerCustomer() throws NoSuchAlgorithmException, DAOException, GeneralSecurityException {
    String dni = readStr("Introducir dni");
    String password = readStr("Introducir contraseña");
    customerAccessManager.addCustomerAccess(dni, password);
    
    
  }

  private static void accessCustomer() throws NoSuchAlgorithmException, DAOException, GeneralSecurityException {
    System.out.println("-------------------------");
    System.out.println("Acceso a su banca On-Line");
    System.out.println("-------------------------");
    
    String dni = "";
    String password = "";
    do {
      dni = readStr("Introduce tu dni ");
      password = readStr("Introduce tu password ");
      
      } while (customerAccessManager.chekAccess(dni, password) == false);
    System.out.println("Acceso permitido");
    }
  

  private void modifyPass() throws NoSuchAlgorithmException, DAOException, GeneralSecurityException {
    String dni = readStr("Introduce tu  dni");
    String oldPassword = readStr("Introduce tu contraseña");
    String newPassword = readStr("Introduce la nueva contraseña");
    customerAccessManager.modifyPass(dni, oldPassword, newPassword);
  }

  
  
  
  
  }


