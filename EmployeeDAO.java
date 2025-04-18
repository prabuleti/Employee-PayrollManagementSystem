package com.payroll;

import java.sql.*;
import java.util.*;

public class EmployeeDAO {
    private Connection conn;

    public EmployeeDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public void addEmployee(Employee emp) throws SQLException {
        String query = "INSERT INTO employees (name, email, designation, basic_salary) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, emp.getName());
        ps.setString(2, emp.getEmail());
        ps.setString(3, emp.getDesignation());
        ps.setDouble(4, emp.getBasicSalary());
        ps.executeUpdate();
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String query = "SELECT * FROM employees";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            Employee emp = new Employee(
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("designation"),
                rs.getDouble("basic_salary")
            );
            emp.setId(rs.getInt("emp_id"));
            list.add(emp);
        }
        return list;
    }

    public void updateEmployeeField(int empId, String field, String value) throws SQLException {
        String query = "UPDATE employees SET " + field + " = ? WHERE emp_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, value);
        ps.setInt(2, empId);
        ps.executeUpdate();
    }

    public void updateEmployeeSalary(int empId, double salary) throws SQLException {
        String query = "UPDATE employees SET basic_salary = ? WHERE emp_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setDouble(1, salary);
        ps.setInt(2, empId);
        ps.executeUpdate();
    }

    public void deleteEmployee(int empId) throws SQLException {
        String query = "DELETE FROM employees WHERE emp_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, empId);
        ps.executeUpdate();
    }
}
