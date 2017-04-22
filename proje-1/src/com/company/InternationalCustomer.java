package com.company;

public class InternationalCustomer extends Customer{
    private String Country;
    private String City;

    public InternationalCustomer(String country, String city){
        super(0, "", "");
        this.setCountry("");
        this.setCity("");
    }

    public InternationalCustomer(int customerID, String name, String surname, String country, String city){
        super(customerID, name, surname);
        this.setCountry(country);
        this.setCity(city);
    }

    public InternationalCustomer(InternationalCustomer internationalCustomer){
        super(internationalCustomer);
        this.setCountry("");
        this.setCity("");
    }

    @Override
    public String toString(){
        return "i, " + super.toString() + ", " + getCountry() + ", " + getCity();
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
