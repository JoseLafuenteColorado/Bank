package presentation;

/**
 * Programa que gestiona las cuentas
 */


import business.AccountManager;
import business.CustomerManager;
import business.MovementManager;
import business.TransactionsManager;
import data.DAOManager;
import exceptions.DAOException;
import util.Menu;
import static util.Util.readStr;
import static util.Util.readInt;

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

  private Menu createMenu() {
    return new Menu("Gestión de Banco (Gestión de cuentas)", "Dar de alta", "Dar de baja", "Ingreso en cuenta", 
        "Sacar dinero", "Realizar transferencia", "Mostrar cuenta", "Terminar");
  }

  private void newAccount() throws Exception {
    try {
      String dni = readStr("Introduce el dni ");
      if (accountManager.checkDni(dni) == true) {
        accountManager.add(dni);
      }
      System.out.println("Cuenta dada de alta");
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

  private void withdrawMoney() throws DAOException {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a retirar ");
    String concept = readStr("Introduce el concepto ");
    movementManager.withdraw(numberAccount, amount, concept);
    int balance = movementManager.getBalance(numberAccount);
    System.out.println("El saldo actualizado es :" + balance);
  }

  private void transferMoney() throws DAOException {
    int numberAccount = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a transeferir ");
    int transferAccountNumber = readInt("Introduce el número de cuenta para hacerle la transferencia ");
    String concept = readStr("Introduce el concepto ");
    movementManager.withdraw(numberAccount, amount, concept);
    movementManager.deposit(transferAccountNumber, amount, concept);
    int balance = movementManager.getBalance(numberAccount);
    System.out.println("El saldo actualizado es :" + balance);

  }
}
