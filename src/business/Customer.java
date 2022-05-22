package business;

import static util.Util.dniIsOk;
import static util.Util.formatDni;
import static util.Util.stringIsOk;
import java.util.Objects;
import exceptions.CustomerIllegalArgumentException;

public class Customer {
  private String dni;
  private String name;
  private String address;
  private String phone;
  
  public Customer(String dni, String name, String address, String phone) {
    setDni(dni);
    setName(name);
    setAddress(address);
    setPhone(phone);
        
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    if(dniIsOk(dni)) {
      this.dni = formatDni(dni);
    } else {
      throw new CustomerIllegalArgumentException("Ya existe un cliente con ese DNI.");
    }
  }
    
  public String getName() {
    return name;
  }

  public void setName(String name) {
    if(stringIsOk(name)) {
      this.name = name.toUpperCase();
    } else {
      throw new CustomerIllegalArgumentException("El nombre introducido no es válido.");
    }
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    if(stringIsOk(address)) {
      this.address = address.toUpperCase();
    } else {
      throw new CustomerIllegalArgumentException("La dirección no es correcta.");
    }
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    if(phone == null || !phone.matches("[679]\\d{8}")){
      throw new CustomerIllegalArgumentException("El teléfono introducido no es válido.");
    }
    this.phone = phone;
  }

  @Override
  public int hashCode() {
    return Objects.hash(dni);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Customer other = (Customer) obj;
    return Objects.equals(dni, other.dni);
  }

  @Override
  public String toString() {
    return "Customer [dni=" + dni + ", name=" + name + ", address=" + address + ", phone=" + phone
        + "]";
  }
  
  
  
  
  

}
