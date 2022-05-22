package presentation;

import business.AccountManager;
import business.Customer;
import business.CustomerManager;
import business.MovementManager;
import data.DAOManager;
import exceptions.DAOException;
import util.Menu;
import static util.Util.readStr;
import static util.Util.readInt;

public class AccountManagement {
  private AccountManager accountManager;
  private CustomerManager customerManager;
  private MovementManager movementManager;

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
    return new Menu("Gestión de Banco (Gestión de cuentas)", "Dar de alta", "Dar de baja", "Ingreso en cuenta (NO OPERATIVO)", 
        "Sacar dinero (NO OPERATIVO)", "Realizar transferencia (NO OPERATIVO)", "Mostrar cuenta", "Terminar");
  }

  private void newAccount() {
    try {
      Customer customer = customerManager.get(readStr("Introduce el DNI del titular"));
      accountManager.add(customer);
      System.out.println("Cuenta dada de alta");
    } catch(DAOException e) {
      System.err.println("No se ha podido dar de alta a la cuenta: " + e.getMessage());
    }
  }

  private void cancelAccount() throws Exception  {
    try {
      int number = readInt("Introduce el número de cuenta a dar de baja");
      accountManager.cancelAccount(number);
      System.out.println("Cuenta dada de baja satisfactoriamente.");
    } catch(DAOException e) {
      System.err.println(e.getMessage());   
    }
  }

  private void showAccount() {
    int number = readInt("Introduce el número de cuenta a mostrar");
    try {
      System.out.println(accountManager.getAccount(number));   
    } catch (DAOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void depositMoney() throws DAOException {
    int number = readInt("Introduce el número de cuenta");
    int amount = readInt("Intr0duce el importe a ingresar");
    movementManager.deposit(number, amount, "");
    int balance = movementManager.getBalance(number);
    System.out.println("El saldo actualizado es :" + balance);
  }

  private void withdrawMoney() throws DAOException {
    int number = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a retirar ");
    movementManager.withdraw(number, amount, "");
    int balance = movementManager.getBalance(number);
    System.out.println("El saldo actualizado es :" + balance);
  }

  private void transferMoney() throws DAOException {
    int number = readInt("Introduce el número de cuenta ");
    int amount = readInt("Introduce el importe a transeferir ");
    int transferAccountNumber = readInt("Introduce el número de cuenta para hacerle la transferencia ");
    String concept = readStr("Introduce el concepto ");
    movementManager.withdraw(number, amount, concept);
    movementManager.deposit(transferAccountNumber, amount, concept);
    int balance = movementManager.getBalance(number);
    System.out.println("El saldo actualizado es :" + balance);
    
  }

}
