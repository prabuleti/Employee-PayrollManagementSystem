package com.payroll;

import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;

public class PayrollService {
    private Connection conn;

    public PayrollService() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public void generateSalary(int empId, double bonus, double deductions, String month) throws SQLException {
        String empQuery = "SELECT basic_salary FROM employees WHERE emp_id = ?";
        PreparedStatement empPs = conn.prepareStatement(empQuery);
        empPs.setInt(1, empId);
        ResultSet rs = empPs.executeQuery();

        if (rs.next()) {
            double basic = rs.getDouble("basic_salary");
            double net = basic + bonus - deductions;

            String insert = "INSERT INTO salary_details (emp_id, bonus, deductions, net_salary, salary_month) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insert);
            ps.setInt(1, empId);
            ps.setDouble(2, bonus);
            ps.setDouble(3, deductions);
            ps.setDouble(4, net);
            ps.setString(5, month);
            ps.executeUpdate();

            System.out.println("Salary processed. Net Salary: Rs. " + net);
            exportPayslip(empId, net, month);
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void exportPayslip(int empId, double net, String month) {
        try {
            String filename = "payslips/Payslip_" + empId + "_" + month + ".txt";
            FileWriter writer = new FileWriter(filename);
            writer.write("PAYSLIP\n");
            writer.write("Employee ID: " + empId + "\n");
            writer.write("Month: " + month + "\n");
            writer.write("Net Salary: Rs. " + net + "\n");
            writer.close();
            System.out.println("Payslip saved as " + filename);
        } catch (IOException e) {
            System.out.println("Error saving payslip.");
        }
    }
}
