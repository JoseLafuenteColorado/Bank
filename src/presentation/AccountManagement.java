package presentation;

import static util.Util.readInt;
import static util.Util.readStr;
import java.util.List;

/**
 * Programa que gestiona las cuentas, utilizado por un empleado de alto nivel
 */


import business.AccountManager;
import business.CustomerManager;
import business.Movement;
import business.MovementManager;
import business.TransactionsManager;
import data.DAOManager;
import exceptions.DAOException;
import util.Menu;

public class AccountManagement {
  private AccountManager accountManager;
  private CustomerManager customerManager;
  private MovementManager movementManager;
  private TransactionsManager transactionsManager;


  public static void main(String[] args) throws Exception {
    String url = "jdbc:sqlite:/home/jlc/banco.sqlite";

    try {
      AccountManagement program = new AccountManagement(url);
      program.run();
      System.out.println("¡Hasta la próxima! ;-)");
    } catch(DAOException e) {
      System.err.println("Error al conectar con la base de datos.");
    }
  }

  public AccountManagement(String url) throws DAOException {
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
        case 1 -> newAccount();
        case 2 -> cancelAccount();
        case 3 -> depositMoney();
        case 4 -> withdrawMoney();
        case 5 -> transferMoney();
        case 6 -> showAccount();
      }
    } while (option != menu.lastOption());
  }

  private void newAccount() throws Exception {
    try {
      String dni = readStr("Introduce el dni ");
      if (customerManager.checkCustomer(dni) == 1) {
        accountManager.add(dni);
      }
      
    } catch(DAOException e) {
      System.err.println("No se ha podido dar de alta a la cuenta: " + e.getMessage());
      System.out.println("Hay que dar primero al cliente de alta");
    }
  }

  private void cancelAccount() throws Exception  {
    try {
      int numberAccount = readInt("Introduce el número de cuenta a dar de baja");
      accountManager.cancelAccount(numberAccount);
      System.out.println("Cuenta dada de baja satisfactoriamente.");
    } catch(DAOException e) {
      System.err.println(e.getMessage());   
    }
  }

  private void showAccount() {
    int numberAccount = readInt("Introduce el número de cuenta a mostrar");
    try {
      System.out.println("Cuenta de : " + accountOwer(numberAccount));
      System.out.println(accountManager.getAccount(numberAccount)); 
      List<Movement> movements = movementManager.list(numberAccount);
      System.out.println(movements);
    } catch (DAOException e) {
      System.err.println(e.getMessage());
    }
  }

  private String accountOwer(int numberAccount) throws DAOException {
    String dni = accountManager.getAccount(numberAccount).getDni();
    String name = customerManager.get(dni).getName();
    return name;

  }

  private void depositMoney() throws Exception {
    int numberAccount = readInt("Introduce el número de cuenta");
    int amount = readInt("Introduce el importe a ingresar");
    String concept = readStr("Introduce el concepto ");
    transactionsManager.deposit(numberAccount, amount, concept);
    //int balance = transactionsManager.balance(numberAccount);
    //System.out.println("El saldo actualizado es :" + balance);
  }

  private void withdrawMoney() throws Exception {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a retirar ");
    String concept = readStr("Introduce el concepto ");
    transactionsManager.withdraw(numberAccount, amount, concept);
    //int balance = movementManager.balance(numberAccount);
    //System.out.println("El saldo actualizado es :" + balance);
  }

  private void transferMoney() throws Exception {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a transeferir ");
    int transferAccountNumber = readInt("Introduce el número de cuenta para hacerle la transferencia ");
    String concept = readStr("Introduce el concepto ");
    transactionsManager.transfer(numberAccount, amount, transferAccountNumber, concept);
    //int balance = movementManager.balance(numberAccount);
    //System.out.println("El saldo actualizado es :" + balance);

  }
  
  private Menu createMenu() {
    return new Menu("Gestión de Banco (Gestión de cuentas)", "Dar de alta", "Dar de baja", "Ingreso en cuenta", 
        "Sacar dinero", "Realizar transferencia", "Mostrar cuenta", "Terminar");
  }
  
  
}
