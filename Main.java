package com.payroll;

import java.util.*;
import java.sql.*;

public class Main {
    public static boolean login(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection conn = DBConnection.getConnection();
            if (!login(conn, sc)) {
                System.out.println("Invalid credentials. Exiting...");
                return;
            }

            EmployeeDAO empDao = new EmployeeDAO();
            PayrollService payroll = new PayrollService();

            while (true) {
                System.out.println("\nPayroll Management System");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Generate Salary");
                System.out.println("4. Update Employee");
                System.out.println("5. Delete Employee");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        sc.nextLine();
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Designation: ");
                        String desig = sc.nextLine();
                        System.out.print("Basic Salary: ");
                        double salary = sc.nextDouble();
                        empDao.addEmployee(new Employee(name, email, desig, salary));
                        System.out.println("Employee added successfully.");
                        break;

                    case 2:
                        List<Employee> list = empDao.getAllEmployees();
                        for (Employee e : list) {
                            System.out.println(e.getId() + ". " + e.getName() + " - " + e.getDesignation());
                        }
                        break;

                    case 3:
                        System.out.print("Employee ID: ");
                        int empId = sc.nextInt();
                        System.out.print("Bonus: ");
                        double bonus = sc.nextDouble();
                        System.out.print("Deductions: ");
                        double deduc = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Salary Month: ");
                        String month = sc.nextLine();
                        payroll.generateSalary(empId, bonus, deduc, month);
                        break;

                    case 4:
                        System.out.print("Enter Employee ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Choose field to update:");
                        System.out.println("1. Name");
                        System.out.println("2. Email");
                        System.out.println("3. Designation");
                        System.out.println("4. Basic Salary");
                        int fieldChoice = sc.nextInt();
                        sc.nextLine();
                        String field = "";
                        String newValue = "";
                        double newSalary = 0.0;

                        switch (fieldChoice) {
                            case 1:
                                field = "name";
                                System.out.print("Enter new name: ");
                                newValue = sc.nextLine();
                                empDao.updateEmployeeField(updateId, field, newValue);
                                break;
                            case 2:
                                field = "email";
                                System.out.print("Enter new email: ");
                                newValue = sc.nextLine();
                                empDao.updateEmployeeField(updateId, field, newValue);
                                break;
                            case 3:
                                field = "designation";
                                System.out.print("Enter new designation: ");
                                newValue = sc.nextLine();
                                empDao.updateEmployeeField(updateId, field, newValue);
                                break;
                            case 4:
                                field = "basic_salary";
                                System.out.print("Enter new basic salary: ");
                                newSalary = sc.nextDouble();
                                empDao.updateEmployeeSalary(updateId, newSalary);
                                break;
                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                        System.out.println("Employee updated successfully.");
                        break;

                    case 5:
                        System.out.print("Employee ID to Delete: ");
                        int deleteId = sc.nextInt();
                        empDao.deleteEmployee(deleteId);
                        System.out.println("Employee deleted successfully.");
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
