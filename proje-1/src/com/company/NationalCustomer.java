package com.company;

public class NationalCustomer extends Customer {
    private int LicencePlateNumber;
    private String Occupation;

    public NationalCustomer(int licencePlateNum, String occupation){
        super(0, "", "");
        this.setLicencePlateNumber(1);
        this.setOccupation("");
    }

    public NationalCustomer(int customerID, String name, String surname, int licencePlateNumber, String occupation){
        super(customerID, name, surname);
        this.setLicencePlateNumber(licencePlateNumber);
        this.setOccupation(occupation);
    }

    public NationalCustomer(NationalCustomer nationalCustomer){
        super(nationalCustomer);
        this.setLicencePlateNumber(1);
        this.setOccupation("");
    }

    @Override
    public String toString(){
        return "n, " + super.toString() + ", " + getLicencePlateNumber() + ", " + getOccupation();
    }

    public int getLicencePlateNumber() {
        return LicencePlateNumber;
    }

    public void setLicencePlateNumber(int licencePlateNumber) {
        LicencePlateNumber = licencePlateNumber;
    }

    public String getOccupation() {
        return this.Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }
}
