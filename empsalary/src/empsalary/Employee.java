package empsalary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Employee {
    private static final String URL = "jdbc:mysql://localhost:3306/employe";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "@Yash7417";
    private static final String getALL = "SELECT * FROM Employee";
    private static final String getById = "SELECT * FROM Employee WHERE id = ?";
    private static final String insert = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?)";
    private static final String update = "UPDATE Employee SET bsal = ?, totSal = ? WHERE id = ?";
    private static final String delete = "DELETE FROM Employee WHERE id = ?";

    public static void main(String[] args) throws Exception {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            PreparedStatement ps1 = connection.prepareStatement(getALL);
            PreparedStatement ps2 = connection.prepareStatement(getById);
            PreparedStatement ps3 = connection.prepareStatement(insert);
            PreparedStatement ps4 = connection.prepareStatement(update);
            PreparedStatement ps5 = connection.prepareStatement(delete);

            while (true) {
                System.out.println("\n\n1. View All Employees");
                System.out.println("2. View Employee By Id");
                System.out.println("3. Add New Employee");
                System.out.println("4. Update Employee Salary");
                System.out.println("5. Delete Employee By Id");
                System.out.println("6. Exit\n\n");
                System.out.println("Enter Your Choice: ");
                String opt = br.readLine();
                switch (opt) {
                    case "1": {
                        ResultSet rs = ps1.executeQuery();
                        System.out.printf("%-10s %-20s %-20s %-10s %-10s%n", "ID", "Name", "Designation", "Basic Salary", "Total Salary");
                        while (rs.next()) {
                            System.out.printf("%-10d %-20s %-20s %-10.2f %-10.2f%n", 
                                rs.getInt("id"), rs.getString("name"), rs.getString("desg"), rs.getDouble("bsal"), rs.getDouble("totSal"));
                        }
                        break;
                    }
                    case "2": {
                        System.out.println("Enter Employee Id: ");
                        int id = Integer.parseInt(br.readLine());
                        ps2.setInt(1, id);
                        ResultSet rs = ps2.executeQuery();
                        if (rs.next()) {
                            System.out.printf("%-10d %-20s %-20s %-10.2f %-10.2f%n", 
                                rs.getInt("id"), rs.getString("name"), rs.getString("desg"), rs.getDouble("bsal"), rs.getDouble("totSal"));
                        } else {
                            System.err.println("Record Not Found");
                        }
                        break;
                    }
                    case "3": {
                        System.out.println("Enter Employee Id: ");
                        int id = Integer.parseInt(br.readLine());
                        System.out.println("Enter Employee Name: ");
                        String name = br.readLine();
                        System.out.println("Enter Employee Designation: ");
                        String desg = br.readLine();
                        System.out.println("Enter Employee Basic Salary: ");
                        double bsal = Double.parseDouble(br.readLine());
                        
                        double hra = 0.93 * bsal;
                        double da = 0.61 * bsal;
                        double totSal = bsal + hra + da;
                        
                        ps3.setInt(1, id);
                        ps3.setString(2, name);
                        ps3.setString(3, desg);
                        ps3.setDouble(4, bsal);
                        ps3.setDouble(5, totSal);
                        
                        int k = ps3.executeUpdate();
                        if (k > 0) {
                            System.out.println("Employee Added Successfully");
                        } else {
                            System.err.println("Failed to Add Employee");
                        }
                        break;
                    }
                    case "4": {
                        System.out.println("Enter Employee Id: ");
                        int id = Integer.parseInt(br.readLine());
                        ps2.setInt(1, id);
                        ResultSet rs = ps2.executeQuery();
                        if (rs.next()) {
                            System.out.println("Old Basic Salary: " + rs.getDouble("bsal"));
                            System.out.println("Enter New Basic Salary: ");
                            double bsal = Double.parseDouble(br.readLine());
                            
                            double hra = 0.93 * bsal;
                            double da = 0.61 * bsal;
                            double totSal = bsal + hra + da;
                            
                            ps4.setDouble(1, bsal);
                            ps4.setDouble(2, totSal);
                            ps4.setInt(3, id);
                            
                            int k = ps4.executeUpdate();
                            if (k > 0) {
                                System.out.println("Employee Salary Updated Successfully");
                            } else {
                                System.err.println("Failed to Update Employee Salary");
                            }
                        } else {
                            System.err.println("Invalid Employee Id");
                        }
                        break;
                    }
                    case "5": {
                        System.out.println("Enter Employee Id: ");
                        int id = Integer.parseInt(br.readLine());
                        ps2.setInt(1, id);
                        ResultSet rs = ps2.executeQuery();
                        if (rs.next()) {
                            ps5.setInt(1, id);
                            int k = ps5.executeUpdate();
                            if (k > 0) {
                                System.out.println("Employee Deleted Successfully");
                            } else {
                                System.err.println("Failed to Delete Employee");
                            }
                        } else {
                            System.err.println("Record Not Found");
                        }
                        break;
                    }
                    case "6": {
                        System.out.println("Good Bye");
                        System.exit(0);
                    }
                    default: {
                        System.err.println("Invalid Option Selected");
                    }
                }
            }
        }
    }
}
