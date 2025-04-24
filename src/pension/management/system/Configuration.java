/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pension.management.system;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Dell
 */
public class Configuration {

    Connection conn;

    public Configuration() {
        try {

//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/pension_database?zeroDateTimeBehavior=CONVERT_TO_NULL","root","admin@123456789");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-Q1R10TN:1433;databaseName=pension_database;user=sa;password=admin@123456789;encrypt=true;trustServerCertificate=true");

            System.out.println("Successfully connected");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(null, "Database exception occured"+ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);
        }
    }
}
