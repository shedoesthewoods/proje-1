package com.company;

public class Customer {
    private int CustomerID;
    private String Name;
    private String Surname;

    public Customer(){
        this.setCustomerID(0);
        this.setName("");
        this.setSurname("");
    }

    public Customer(int customerID, String name, String surname){
        this.setCustomerID(customerID);
        this.setName(name);
        this.setSurname(surname);
    }

    public Customer(Customer customer){
        this(0, "", "");
    }

    @Override
    public String toString(){
        return getCustomerID() + ", " + getName() + ", " + getSurname();
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }
}
