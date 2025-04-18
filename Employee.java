package com.payroll;

public class Employee {
    private int id;
    private String name;
    private String email;
    private String designation;
    private double basicSalary;

    public Employee(String name, String email, String designation, double basicSalary) {
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.basicSalary = basicSalary;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDesignation() { return designation; }
    public double getBasicSalary() { return basicSalary; }
}
