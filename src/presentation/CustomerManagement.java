package presentation;

import business.Customer;
import business.CustomerManager;
import data.DAOManager;
import exceptions.CustomerIllegalArgumentException;
import exceptions.DAOException;
import util.Menu;
import util.Util;
import static util.Menu.*;
import static util.Util.*;

public class CustomerManagement {
  private CustomerManager customerManager;

  public static void main(String[] args) {
    String url = "jdbc:sqlite:/home/jlc/banco.sqlite";
    
    try {
      CustomerManagement program = new CustomerManagement(url);
      program.run();
      System.out.println("¡Hasta la próxima! ;-)");
    } catch(DAOException e) {
      System.err.println("Error al conectar con la base de datos.");
    }
  }
  
  public CustomerManagement(String url) throws DAOException {
    customerManager = new CustomerManager(new DAOManager(url));
  } 

  private void run() {
    Menu menu = createMenu();

    int option;
    do {
      option = menu.choose();
      switch (option) {
        case 1 -> newCustomer();
        case 2 -> terminateCustomer();
        case 3 -> modifyCustomer();
        case 4 -> showCustomers();
        case 5 -> viewCustomer();
      }
    } while (option != menu.lastOption());
  }
  
  private void newCustomer() {
    try {
      customerManager.add(readStr("dni"), readStr("Nombre"), readStr("Dirección"), 
          readStr("Teléfono"));
      System.out.println("Alta de cliente relizada");
    } catch(CustomerIllegalArgumentException e) {
      System.err.println(e.getMessage());
    } catch(DAOException e) {
      System.err.println(e.getMessage());
    }
  }
  
  private void terminateCustomer() {
    try {
      customerManager.remove(customerManager.get(readStr("Introduce el DNI del cliente a dar de baja")));
      System.out.println("Baja de cliente realizada");
    } catch(DAOException e) {
      System.err.println(e.getMessage());
    }

  }
  
  private void modifyCustomer() {
    try {
      Customer customer = customerManager.get(readStr("DNI del cliente a modificar"));
      customerManager.set(customer.getDni(), readStr("Nombre", customer.getName()), 
          readStr("Dirección", customer.getAddress()),
          readStr("Teléfono", customer.getPhone()));
      System.out.println("modificación realizada");
    } 
    catch(DAOException e) {
      System.err.println("No se ha podido modificar el cliente: " + e.getMessage());
    }
    catch(CustomerIllegalArgumentException e) {
      System.err.println("No se ha podido modificar el cliente: " + e.getMessage());
    }
  }
  
  private void showCustomers() {
    try {
      for(Customer customer: customerManager.getList()) {
        System.out.println(customer);
      }
    } 
    catch(DAOException e) {
      System.err.println("No se han podido mostrar los clientes: " + e.getMessage());
    }
    catch(CustomerIllegalArgumentException e) {
      System.err.println("No se han podido mostrar los clientes: " + e.getMessage());
    }
  }
  
  private void viewCustomer() {
    try {
      String dni = formatDNI(readStr("Introduce el DNI del cliente a mostrar"));
      Customer customer = customerManager.get(dni);
      System.out.println(customer);
    } 
    catch(DAOException e) {
      System.err.println("No se ha podido mostrar el cliente: " + e.getMessage());
    }
    catch(CustomerIllegalArgumentException e) {
      System.err.println("No se ha podido mostrar el cliente: " + e.getMessage());
    }
  }
  
  private static Menu createMenu() {
    return new Menu("\nGestiBank (Gestión de clientes)", "Nuevo cliente", "Dar de baja a un cliente", 
        "Editar cliente", "Ver clientes", "Mostrar a un cliente", "Terminar");
  }

}
