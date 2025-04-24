/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pension.management.system;

import java.awt.Desktop;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import javax.swing.ComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Dell
 */
public class AddPension extends javax.swing.JFrame {

    /**
     * Creates new form AddPension
     */
    Configuration config;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-y");

    SimpleDateFormat toDataBaseDate = new SimpleDateFormat("yyyy-MM-dd");

    DecimalFormat df = new DecimalFormat("#.00");
    Date date = new Date();
    Date birthDate;
    String dateSearchQuery;
    String ppoNo;
    String todayDate;
//    Date todayDateStringToDate;
    String pensionIssueDate;
    String pensionType;
    String pensionerName;
    String birthDateString;
    String bankName;
    String branchName;
    String accountNo;
    String ifscCode;
    float basicAmount;
    float grossAmount;
    float DA;
    float commutation;
    String issueDate;
    String endDate;
    float commutationNew2;
    String issueDate2;
    String endDate2;
    float otherNew;
    long netPay;
    double oldNetAmount;
    String oldBankAndBranch;

    int age;
    int diff;

    float applyAmt;
    float gross;
    float daFloat;
    float netted;
    float grossToAdd;
    float daToAdd;
    float totalGross;
    float commutationCharges;
    float commutation2;
    float other;
    float netPaySetting;

    DefaultTableModel defaultTableModel;
    DefaultTableModel defaultTableModelCol;
    Statement statementForTableModel;
    Statement insertStmt;
    Statement columnStmt;
    Statement alterStatement;
    String chooseAge;
    ArrayList<String> columnNames;

    String globalSelectQuery = "select * from pension_database.pension_data;";

    public AddPension() {
        initComponents();
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        config = new Configuration();

        AutoCompleteDecorator.decorate(branchSelectTxt);
        fetchData(globalSelectQuery);
        fieldsEnabling(false);
    }

    private void fetchSearchByName(String query) {
        try {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);

            statementForTableModel = config.conn.createStatement();
            ResultSet rs = statementForTableModel.executeQuery(query);
            int rowsTotal = 0;
            while (rs.next()) {
                rowsTotal++;
                int srNo = rowsTotal;
                String ppono = rs.getString("PPONo");
                Date todayDateTable = rs.getDate("TodayDate");
                Date pensionDate = rs.getDate("PensionIssueDate");
                String penType = rs.getString("PensionType");
                String pensionName = rs.getString("PensionerName");
                Date dateOfBirth = rs.getDate("DateOfBirth");
                int age = rs.getInt("Age");
                String bankName = rs.getString("BankName");
                String branch = rs.getString("BankBranch");
                String bankAcc = rs.getString("BankAccountNo");
                String ifsc = rs.getString("BankIFSC");
                float basicAmt = rs.getFloat("BasicAmount");
                float plus80 = rs.getFloat("Age_80_Plus");
                float plus85 = rs.getFloat("Age_85_Plus");
                float plus90 = rs.getFloat("Age_90_Plus");
                float plus95 = rs.getFloat("Age_95_Plus");
                float plus100 = rs.getFloat("Age_100_Plus");
                float gross = rs.getFloat("GrossAmount");
                float da = rs.getFloat("DA");
                float total = rs.getFloat("Total");
                float deducComm = rs.getFloat("DeductionCommutation");
                Date issueDate = rs.getDate("IssueDate");
                Date endDate = rs.getDate("EndDate");
                float deducComm2 = rs.getFloat("DeductionCommutation2");
                Date issueDate2 = rs.getDate("IssueDate2");
                Date endDate2 = rs.getDate("EndDate2");
                float other = rs.getFloat("Other");
                float net = rs.getFloat("NetAmount");

                Object tblData[] = {srNo, ppono, todayDateTable, pensionDate, penType, pensionName, dateOfBirth, age, bankName, branch, bankAcc, ifsc, basicAmt, plus80, plus85, plus90, plus95, plus100, gross, da, total, deducComm, issueDate, endDate, deducComm2, issueDate2, endDate2, other, net};
                defaultTableModel = (DefaultTableModel) dataTable.getModel();
                defaultTableModel.addRow(tblData);

            }

//                JOptionPane.showMessageDialog(null, "No record found", "Pension Record System", JOptionPane.ERROR_MESSAGE);
            ppoSearchTxt.setText("");
            pensionerNameSearchTxt.setText("");
//                fetchData(globalSelectQuery);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, "No record found : " + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);
            fetchData(globalSelectQuery);
            ex.printStackTrace();
        }

    }

    private void fetchSearchData(String query) {
        try {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);

            statementForTableModel = config.conn.createStatement();
            ResultSet rs = statementForTableModel.executeQuery(query);
            int rowsTotal = 0;
            if (rs.next()) {
                rowsTotal++;
                int srNo = rowsTotal;
                String ppono = rs.getString("PPONo");
                Date todayDateTable = rs.getDate("TodayDate");
                Date pensionDate = rs.getDate("PensionIssueDate");
                String penType = rs.getString("PensionType");
                String pensionName = rs.getString("PensionerName");
                Date dateOfBirth = rs.getDate("DateOfBirth");
                int age = rs.getInt("Age");
                String bankName = rs.getString("BankName");
                String branch = rs.getString("BankBranch");
                String bankAcc = rs.getString("BankAccountNo");
                String ifsc = rs.getString("BankIFSC");
                float basicAmt = rs.getFloat("BasicAmount");
                float plus80 = rs.getFloat("Age_80_Plus");
                float plus85 = rs.getFloat("Age_85_Plus");
                float plus90 = rs.getFloat("Age_90_Plus");
                float plus95 = rs.getFloat("Age_95_Plus");
                float plus100 = rs.getFloat("Age_100_Plus");
                float gross = rs.getFloat("GrossAmount");
                float da = rs.getFloat("DA");
                float total = rs.getFloat("Total");
                float deducComm = rs.getFloat("DeductionCommutation");
                Date issueDate = rs.getDate("IssueDate");
                Date endDate = rs.getDate("EndDate");
                float deducComm2 = rs.getFloat("DeductionCommutation2");
                Date issueDate2 = rs.getDate("IssueDate2");
                Date endDate2 = rs.getDate("EndDate2");
                float other = rs.getFloat("Other");
                float net = rs.getFloat("NetAmount");

                Object tblData[] = {srNo, ppono, todayDateTable, pensionDate, penType, pensionName, dateOfBirth, age, bankName, branch, bankAcc, ifsc, basicAmt, plus80, plus85, plus90, plus95, plus100, gross, da, total, deducComm, issueDate, endDate, deducComm2, issueDate2, endDate2, other, net};
                defaultTableModel = (DefaultTableModel) dataTable.getModel();
                defaultTableModel.addRow(tblData);

            } else {
                JOptionPane.showMessageDialog(null, "No record found", "Pension Record System", JOptionPane.ERROR_MESSAGE);
                fetchData(globalSelectQuery);
                ppoSearchTxt.setText("");
                pensionerNameSearchTxt.setText("");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No record found : " + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);

            ex.printStackTrace();
        }

    }

    private long[] fetchSearchByDateData(String query) {
        try {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);

            statementForTableModel = config.conn.createStatement();
            ResultSet rs = statementForTableModel.executeQuery(query);
            selectedExportBtn.setEnabled(false);
            int rowsTotal = 0;
            while (rs.next()) {
                rowsTotal++;

                int srNo = rowsTotal;
                String ppono = rs.getString("PPONo");
                Date todayDateTable = rs.getDate("TodayDate");
                Date pensionDate = rs.getDate("PensionIssueDate");
                String penType = rs.getString("PensionType");
                String pensionName = rs.getString("PensionerName");
                Date dateOfBirth = rs.getDate("DateOfBirth");
                int age = rs.getInt("Age");
                String bankName = rs.getString("BankName");
                String branch = rs.getString("BankBranch");
                String bankAcc = rs.getString("BankAccountNo");
                String ifsc = rs.getString("BankIFSC");
                float basicAmt = rs.getFloat("BasicAmount");
                float plus80 = rs.getFloat("Age_80_Plus");
                float plus85 = rs.getFloat("Age_85_Plus");
                float plus90 = rs.getFloat("Age_90_Plus");
                float plus95 = rs.getFloat("Age_95_Plus");
                float plus100 = rs.getFloat("Age_100_Plus");
                float gross = rs.getFloat("GrossAmount");
                float da = rs.getFloat("DA");
                float total = rs.getFloat("Total");
                float deducComm = rs.getFloat("DeductionCommutation");
                Date issueDate = rs.getDate("IssueDate");
                Date endDate = rs.getDate("EndDate");
                float deducComm2 = rs.getFloat("DeductionCommutation2");
                Date issueDate2 = rs.getDate("IssueDate2");
                Date endDate2 = rs.getDate("EndDate2");
                float other = rs.getFloat("Other");
                float net = rs.getFloat("NetAmount");

                Object tblData[] = {srNo, ppono, todayDateTable, pensionDate, penType, pensionName, dateOfBirth, age, bankName, branch, bankAcc, ifsc, basicAmt, plus80, plus85, plus90, plus95, plus100, gross, da, total, deducComm, issueDate, endDate, deducComm2, issueDate2, endDate2, other, net};
                defaultTableModel = (DefaultTableModel) dataTable.getModel();
                defaultTableModel.addRow(tblData);

                selectedExportBtn.setEnabled(true);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No record found : " + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);
            fetchData(globalSelectQuery);
            ex.printStackTrace();
        }
        long netPaySumDate = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            netPaySumDate = (long) (netPaySumDate + Double.parseDouble(dataTable.getValueAt(i, 28).toString()));
        }
        System.out.println("Net Pay Sum : " + netPaySumDate);

        long basicAmountSumAll = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            basicAmountSumAll = (long) (basicAmountSumAll + Double.parseDouble(dataTable.getValueAt(i, 12).toString()));
        }
        System.out.println("Basic Amount Sum : " + basicAmountSumAll);

        long age80PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age80PlusSum = (long) (age80PlusSum + Double.parseDouble(dataTable.getValueAt(i, 13).toString()));
        }
        System.out.println("age80PlusSum Sum : " + age80PlusSum);

        long age85PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age85PlusSum = (long) (age85PlusSum + Double.parseDouble(dataTable.getValueAt(i, 14).toString()));
        }
        System.out.println("age85PlusSum Sum : " + age85PlusSum);

        long age90PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age90PlusSum = (long) (age90PlusSum + Double.parseDouble(dataTable.getValueAt(i, 15).toString()));
        }
        System.out.println("age90PlusSum Sum : " + age90PlusSum);

        long age95PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age95PlusSum = (long) (age95PlusSum + Double.parseDouble(dataTable.getValueAt(i, 16).toString()));
        }
        System.out.println("age95PlusSum Sum : " + age95PlusSum);

        long age100PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age100PlusSum = (long) (age100PlusSum + Double.parseDouble(dataTable.getValueAt(i, 17).toString()));
        }
        System.out.println("age100PlusSum Sum : " + age100PlusSum);

        long grossAmountSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            grossAmountSum = (long) (grossAmountSum + Double.parseDouble(dataTable.getValueAt(i, 18).toString()));
        }
        System.out.println("grossAmountSum Sum : " + grossAmountSum);

        long DASum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            DASum = (long) (DASum + Double.parseDouble(dataTable.getValueAt(i, 19).toString()));
        }
        System.out.println("DASum Sum : " + DASum);

        long totalSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            totalSum = (long) (totalSum + Double.parseDouble(dataTable.getValueAt(i, 20).toString()));
        }
        System.out.println("totalSum Sum : " + totalSum);

        long commutation1Sum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            commutation1Sum = (long) (commutation1Sum + Double.parseDouble(dataTable.getValueAt(i, 21).toString()));
        }
        System.out.println("commutation1Sum Sum : " + commutation1Sum);

        long commutation2Sum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            commutation2Sum = (long) (commutation2Sum + Double.parseDouble(dataTable.getValueAt(i, 24).toString()));
        }
        System.out.println("commutation2Sum Sum : " + commutation2Sum);

        long otherSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            otherSum = (long) (otherSum + Double.parseDouble(dataTable.getValueAt(i, 27).toString()));
        }
        System.out.println("otherSum Sum : " + otherSum);

        long sums[] = new long[13];
        sums[0] = basicAmountSumAll;
        sums[1] = age80PlusSum;
        sums[2] = age85PlusSum;
        sums[3] = age90PlusSum;
        sums[4] = age95PlusSum;
        sums[5] = age100PlusSum;
        sums[6] = grossAmountSum;
        sums[7] = DASum;
        sums[8] = totalSum;
        sums[9] = commutation1Sum;
        sums[10] = commutation2Sum;
        sums[11] = otherSum;
        sums[12] = netPaySumDate;

        return sums;

    }

    private long[] fetchData(String query) {
        try {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);

            statementForTableModel = config.conn.createStatement();
            ResultSet rs = statementForTableModel.executeQuery(query);
            int rowsTotal = 0;
            while (rs.next()) {
                rowsTotal++;
                int srNo = rowsTotal;
                String ppono = rs.getString("PPONo");
                Date todayDateTable = rs.getDate("TodayDate");
                Date pensionDate = rs.getDate("PensionIssueDate");
                String penType = rs.getString("PensionType");
                String pensionName = rs.getString("PensionerName");
                Date dateOfBirth = rs.getDate("DateOfBirth");
                int age = rs.getInt("Age");
                String bankName = rs.getString("BankName");
                String branch = rs.getString("BankBranch");
                String bankAcc = rs.getString("BankAccountNo");
                String ifsc = rs.getString("BankIFSC");
                float basicAmt = rs.getFloat("BasicAmount");
                float plus80 = rs.getFloat("Age_80_Plus");
                float plus85 = rs.getFloat("Age_85_Plus");
                float plus90 = rs.getFloat("Age_90_Plus");
                float plus95 = rs.getFloat("Age_95_Plus");
                float plus100 = rs.getFloat("Age_100_Plus");
                float gross = rs.getFloat("GrossAmount");
                float da = rs.getFloat("DA");
                float total = rs.getFloat("Total");
                float deducComm = rs.getFloat("DeductionCommutation");
                Date issueDate = rs.getDate("IssueDate");
                Date endDate = rs.getDate("EndDate");
                float deducComm2 = rs.getFloat("DeductionCommutation2");
                Date issueDate2 = rs.getDate("IssueDate2");
                Date endDate2 = rs.getDate("EndDate2");
                float other = rs.getFloat("Other");
                float net = rs.getFloat("NetAmount");

                Object tblData[] = {srNo, ppono, todayDateTable, pensionDate, penType, pensionName, dateOfBirth, age, bankName, branch, bankAcc, ifsc, basicAmt, plus80, plus85, plus90, plus95, plus100, gross, da, total, deducComm, issueDate, endDate, deducComm2, issueDate2, endDate2, other, net};
                defaultTableModel = (DefaultTableModel) dataTable.getModel();
                defaultTableModel.addRow(tblData);

            }

        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, "No record found : "+ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);

            ex.printStackTrace();
        }
        long netPaySumAll = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            netPaySumAll = (long) (netPaySumAll + Double.parseDouble(dataTable.getValueAt(i, 28).toString()));
        }
        System.out.println("Net Pay Sum : " + netPaySumAll);

        long basicAmountSumAll = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            basicAmountSumAll = (long) (basicAmountSumAll + Double.parseDouble(dataTable.getValueAt(i, 12).toString()));
        }
        System.out.println("Basic Amount Sum : " + basicAmountSumAll);

        long age80PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age80PlusSum = (long) (age80PlusSum + Double.parseDouble(dataTable.getValueAt(i, 13).toString()));
        }
        System.out.println("age80PlusSum Sum : " + age80PlusSum);

        long age85PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age85PlusSum = (long) (age85PlusSum + Double.parseDouble(dataTable.getValueAt(i, 14).toString()));
        }
        System.out.println("age85PlusSum Sum : " + age85PlusSum);

        long age90PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age90PlusSum = (long) (age90PlusSum + Double.parseDouble(dataTable.getValueAt(i, 15).toString()));
        }
        System.out.println("age90PlusSum Sum : " + age90PlusSum);

        long age95PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age95PlusSum = (long) (age95PlusSum + Double.parseDouble(dataTable.getValueAt(i, 16).toString()));
        }
        System.out.println("age95PlusSum Sum : " + age95PlusSum);

        long age100PlusSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            age100PlusSum = (long) (age100PlusSum + Double.parseDouble(dataTable.getValueAt(i, 17).toString()));
        }
        System.out.println("age100PlusSum Sum : " + age100PlusSum);

        long grossAmountSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            grossAmountSum = (long) (grossAmountSum + Double.parseDouble(dataTable.getValueAt(i, 18).toString()));
        }
        System.out.println("grossAmountSum Sum : " + grossAmountSum);

        long DASum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            DASum = (long) (DASum + Double.parseDouble(dataTable.getValueAt(i, 19).toString()));
        }
        System.out.println("DASum Sum : " + DASum);

        long totalSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            totalSum = (long) (totalSum + Double.parseDouble(dataTable.getValueAt(i, 20).toString()));
        }
        System.out.println("totalSum Sum : " + totalSum);

        long commutation1Sum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            commutation1Sum = (long) (commutation1Sum + Double.parseDouble(dataTable.getValueAt(i, 21).toString()));
        }
        System.out.println("commutation1Sum Sum : " + commutation1Sum);

        long commutation2Sum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            commutation2Sum = (long) (commutation2Sum + Double.parseDouble(dataTable.getValueAt(i, 24).toString()));
        }
        System.out.println("commutation2Sum Sum : " + commutation2Sum);

        long otherSum = 0;
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            otherSum = (long) (otherSum + Double.parseDouble(dataTable.getValueAt(i, 27).toString()));
        }
        System.out.println("otherSum Sum : " + otherSum);

        long sums[] = new long[13];
        sums[0] = basicAmountSumAll;
        sums[1] = age80PlusSum;
        sums[2] = age85PlusSum;
        sums[3] = age90PlusSum;
        sums[4] = age95PlusSum;
        sums[5] = age100PlusSum;
        sums[6] = grossAmountSum;
        sums[7] = DASum;
        sums[8] = totalSum;
        sums[9] = commutation1Sum;
        sums[10] = commutation2Sum;
        sums[11] = otherSum;
        sums[12] = netPaySumAll;

        return sums;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ppoTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        pensionIssueDateTxt = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        pensionTypeCombo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        pensionerNameTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dateOfBirthTxt = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        accountNoTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ifscTxt = new javax.swing.JTextField();
        bankSelectTxt = new javax.swing.JComboBox<>();
        branchSelectTxt = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        basicAmountTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        grossAmountTxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        daTxt = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        commStartDate1Txt = new com.toedter.calendar.JDateChooser();
        commEndDate1Txt = new com.toedter.calendar.JDateChooser();
        commutation1Txt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        netPayTxt = new javax.swing.JTextField();
        generateNetPayAndEndDateBtn = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        commutation2Txt = new javax.swing.JTextField();
        commStartDate2Txt = new com.toedter.calendar.JDateChooser();
        commEndDate2Txt = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        newBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        clearAllBtn = new javax.swing.JButton();
        closeBtn = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        otherTxt = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        ppoSearchTxt = new javax.swing.JTextField();
        ppoSearchBtn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        pensionerNameSearchTxt = new javax.swing.JTextField();
        pensionerNameSearchBtn = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        fromDateTxt = new com.toedter.calendar.JDateChooser();
        toDateTxt = new com.toedter.calendar.JDateChooser();
        dateSearchBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        selectedExportBtn = new javax.swing.JButton();
        exportAllBtn = new javax.swing.JButton();
        showRecordsBtn = new javax.swing.JButton();
        printRecordsBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RECORD PENSION ");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pension Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Basic Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("PPO No. :");

        ppoTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ppoTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppoTxtActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Pension issue Date :");

        pensionIssueDateTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pensionIssueDateTxt.setMinSelectableDate(new java.util.Date(-62135785701000L));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Pension Type :");

        pensionTypeCombo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pensionTypeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "REG", "FAMILY" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Pensioner Name :");

        pensionerNameTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Date of Birth :");

        dateOfBirthTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ppoTxt)
                            .addComponent(pensionIssueDateTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(pensionTypeCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pensionerNameTxt)
                            .addComponent(dateOfBirthTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ppoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(pensionIssueDateTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pensionTypeCombo))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pensionerNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(dateOfBirthTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Pensioner's Bank Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(359, 258));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Bank Name :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Bank Branch :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Account No. :");

        accountNoTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        accountNoTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                accountNoTxtKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("IFSC Code :");

        ifscTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ifscTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ifscTxtKeyPressed(evt);
            }
        });

        bankSelectTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bankSelectTxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DCC BANK", "IDBI BANK", "BANK OF MAHARASHTRA", "BADODA BANK", "UNION BANK OF INDIA" }));
        bankSelectTxt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bankSelectTxtItemStateChanged(evt);
            }
        });
        bankSelectTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bankSelectTxtActionPerformed(evt);
            }
        });

        branchSelectTxt.setEditable(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(accountNoTxt)
                    .addComponent(ifscTxt)
                    .addComponent(branchSelectTxt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bankSelectTxt, 0, 298, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(bankSelectTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(branchSelectTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(accountNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ifscTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Payment Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(359, 258));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Basic Amount :");

        basicAmountTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        basicAmountTxt.setToolTipText("");
        basicAmountTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                basicAmountTxtKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Gross Amount :");

        grossAmountTxt.setEditable(false);
        grossAmountTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        grossAmountTxt.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("DA(46% of Gross) :");

        daTxt.setEditable(false);
        daTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        daTxt.setEnabled(false);

        jPanel6.setBackground(new java.awt.Color(255, 255, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Commutation 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        commStartDate1Txt.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        commStartDate1Txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                commStartDate1TxtFocusGained(evt);
            }
        });

        commEndDate1Txt.setEnabled(false);
        commEndDate1Txt.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        commutation1Txt.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        commutation1Txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                commutation1TxtFocusGained(evt);
            }
        });
        commutation1Txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                commutation1TxtKeyPressed(evt);
            }
        });

        jLabel13.setText("Commutation 1 :");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(commStartDate1Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(commEndDate1Txt, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addComponent(commutation1Txt))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commutation1Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(commEndDate1Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(commStartDate1Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Net Pay :");

        netPayTxt.setEditable(false);
        netPayTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        netPayTxt.setEnabled(false);

        generateNetPayAndEndDateBtn.setText("Generate Net Pay And End Date");
        generateNetPayAndEndDateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateNetPayAndEndDateBtnActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 204));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Commutation 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        commutation2Txt.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        commStartDate2Txt.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        commEndDate2Txt.setEnabled(false);
        commEndDate2Txt.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        jLabel14.setText("Commutation 2:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(commStartDate2Txt, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(commEndDate2Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(commutation2Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commutation2Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(commStartDate2Txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(commEndDate2Txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(grossAmountTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(basicAmountTxt)
                            .addComponent(daTxt, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(generateNetPayAndEndDateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addComponent(netPayTxt)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(basicAmountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(grossAmountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(daTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(netPayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(generateNetPayAndEndDateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        newBtn.setText("New");
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        saveBtn.setText("Save");
        saveBtn.setToolTipText("");
        saveBtn.setEnabled(false);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        updateBtn.setText("Update");
        updateBtn.setEnabled(false);
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Delete");
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        clearAllBtn.setText("Clear All");
        clearAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllBtnActionPerformed(evt);
            }
        });

        closeBtn.setText("Close");
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Other :");

        otherTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        otherTxt.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(clearAllBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(otherTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(26, 26, 26)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearAllBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(otherTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(204, 255, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Search Pension Record By PPO Number", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Enter PPO No. :");

        ppoSearchTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        ppoSearchBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ppoSearchBtn.setText("Search");
        ppoSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppoSearchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ppoSearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ppoSearchBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(ppoSearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ppoSearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(204, 255, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Search Pension Record By Pensioner Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Enter Pensioner Name :");

        pensionerNameSearchTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        pensionerNameSearchBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pensionerNameSearchBtn.setText("Search");
        pensionerNameSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pensionerNameSearchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pensionerNameSearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pensionerNameSearchBtn)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(pensionerNameSearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pensionerNameSearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(204, 255, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Search By From-To Dates ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("From :");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("To :");

        fromDateTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        toDateTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        dateSearchBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dateSearchBtn.setText("Search");
        dateSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateSearchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fromDateTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(toDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(dateSearchBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateSearchBtn)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fromDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sr. No.", "PPO No.", "Date of record", "Pension Issue Date", "Pension Type", "Pensioner Name", "Date Of Birth", "Age", "Bank Name", "Branch", "Account No", "IFSC", "Basic Amount", "Age_80_Plus", "Age_85_Plus", "Age_90_Plus", "Age_95_Plus", "Age_100_Plus", "Gross Amount", "DA", "Total", "Deduction Commutation", "Issue Date", "End Date", "Deduction Commutation 2", "Issue Date 2", "End Date 2 ", "Other", "Net Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        dataTable.setShowGrid(false);
        dataTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dataTable);

        jPanel10.setBackground(new java.awt.Color(255, 204, 204));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        selectedExportBtn.setText("Export Selected Data Date wise To Excel");
        selectedExportBtn.setEnabled(false);
        selectedExportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectedExportBtnActionPerformed(evt);
            }
        });

        exportAllBtn.setText("Export All Data To Excel");
        exportAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportAllBtnActionPerformed(evt);
            }
        });

        showRecordsBtn.setText("Show All Records");
        showRecordsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showRecordsBtnActionPerformed(evt);
            }
        });

        printRecordsBtn.setText("Print All Records");
        printRecordsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printRecordsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(selectedExportBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                    .addComponent(showRecordsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(printRecordsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(exportAllBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(selectedExportBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(exportAllBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(printRecordsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(showRecordsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 812, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ppoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppoTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppoTxtActionPerformed

    private void fieldsEnabling(boolean flag) {
        ppoTxt.setEnabled(flag);
        pensionIssueDateTxt.setEnabled(flag);
        pensionTypeCombo.setEnabled(flag);
        pensionerNameTxt.setEnabled(flag);
        dateOfBirthTxt.setEnabled(flag);
        bankSelectTxt.setEnabled(flag);
        branchSelectTxt.setEnabled(flag);
        accountNoTxt.setEnabled(flag);
        ifscTxt.setEnabled(flag);
        basicAmountTxt.setEnabled(flag);

        commutation1Txt.setEnabled(flag);
        commStartDate1Txt.setEnabled(flag);
        commutation2Txt.setEnabled(flag);
        commStartDate2Txt.setEnabled(flag);
        otherTxt.setEnabled(flag);
    }

    private void clearAllFields() {
        ppoTxt.setText("");
        pensionIssueDateTxt.setDate(null);
        pensionTypeCombo.setSelectedIndex(0);
        pensionerNameTxt.setText("");
        dateOfBirthTxt.setDate(null);
        bankSelectTxt.setSelectedIndex(0);
//        branchSelectTxt.setSelectedIndex(0);
        accountNoTxt.setText("");
        ifscTxt.setText("");
        basicAmountTxt.setText("");
        grossAmountTxt.setText("");
        daTxt.setText("");
        commutation1Txt.setText("");
        commStartDate1Txt.setDate(null);
        commEndDate1Txt.setDate(null);
        commutation2Txt.setText("");
        commStartDate2Txt.setDate(null);
        commEndDate2Txt.setDate(null);
        otherTxt.setText("0");
        netPayTxt.setText("");
        ppoSearchTxt.setText("");
        fromDateTxt.setDate(null);
        toDateTxt.setDate(null);
        pensionerNameSearchTxt.setText("");

        deleteBtn.setEnabled(false);
        updateBtn.setEnabled(false);
    }


    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        // TODO add your handling code here:
        saveBtn.setEnabled(true);
        updateBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        ppoTxt.setEditable(true);
        ppoTxt.setEnabled(true);
        clearAllFields();
        fieldsEnabling(true);
        fetchData(globalSelectQuery);
    }//GEN-LAST:event_newBtnActionPerformed

//String insertQuery = "INSERT INTO pension_data (PPONo, TodayDate, PensionIssueDate, PensionType, PensionerName, DateOfBirth, BankName, BankBranch, BankAccountNo, BankIFSC, BasicAmount, " + chooseAge + ", GrossAmount, DA, DeductionCommutation, IssueDate, EndDate, NetAmount) VALUES('" + ppoNo + "','" + todayDate + "','" + pensionIssueDate + "','" + pensionType + "','" + pensionerName + "','" + birthDateString + "','" + bankName + "','" + branchName + "','" + accountNo + "','" + ifscCode + "','" + basicAmount + "','" + applyAmt + "','" + grossAmount + "','" + DA + "','" + commutation + "','" + issueDate + "','" + endDate + "','" + netPay + "');";

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:
        try {

            ppoNo = ppoTxt.getText().toString();
            pensionIssueDate = toDataBaseDate.format(pensionIssueDateTxt.getDate());

            todayDate = toDataBaseDate.format(date);

            pensionType = pensionTypeCombo.getSelectedItem().toString();
            pensionerName = pensionerNameTxt.getText().toString();

            birthDateString = toDataBaseDate.format(dateOfBirthTxt.getDate());

            bankName = bankSelectTxt.getSelectedItem().toString();
            branchName = branchSelectTxt.getSelectedItem().toString();
            accountNo = accountNoTxt.getText().toString();
            ifscCode = ifscTxt.getText().toString();

            basicAmount = Float.parseFloat(basicAmountTxt.getText().toString());

            grossAmount = Math.round(Float.parseFloat(grossAmountTxt.getText().toString()));
            DA = Math.round(Float.parseFloat(daTxt.getText().toString()));

            commutation = Math.round(Float.parseFloat(commutation1Txt.getText().toString()));
            issueDate = toDataBaseDate.format(commStartDate1Txt.getDate());
            endDate = toDataBaseDate.format(commEndDate1Txt.getDate());

            commutationNew2 = Math.round(Float.parseFloat(commutation2Txt.getText().toString()));
            issueDate2 = toDataBaseDate.format(commStartDate2Txt.getDate());
            endDate2 = toDataBaseDate.format(commEndDate2Txt.getDate());

            otherNew = Math.round(Float.parseFloat(otherTxt.getText().toString()));

            netPay = Math.round(Double.parseDouble(netPayTxt.getText().toString()));

            String insertQuery = "INSERT INTO pension_database.pension_data (PPONo, TodayDate, PensionIssueDate, PensionType, PensionerName, DateOfBirth,Age, BankName, BankBranch, BankAccountNo, BankIFSC, BasicAmount, " + chooseAge + ", GrossAmount, DA,Total, DeductionCommutation, IssueDate, EndDate, DeductionCommutation2, IssueDate2, EndDate2,Other, NetAmount) VALUES('" + ppoNo + "','" + todayDate + "','" + pensionIssueDate + "','" + pensionType + "','" + pensionerName + "','" + birthDateString + "','" + age + "','" + bankName + "','" + branchName + "','" + accountNo + "','" + ifscCode + "','" + basicAmount + "','" + applyAmt + "','" + grossAmount + "','" + DA + "','" + totalGross + "','" + commutation + "','" + issueDate + "','" + endDate + "','" + commutationNew2 + "','" + issueDate2 + "','" + endDate2 + "','" + otherNew + "','" + netPay + "');";
            System.out.println(insertQuery);
            insertStmt = config.conn.createStatement();
            int insertInt = insertStmt.executeUpdate(insertQuery);

            String insertAPP9 = "INSERT INTO dbo.app9Table(PPONo,DateOfRecord,PensionerName,PensionIssueDate,PensionType,April) VALUES('" + ppoNo + "','"+todayDate+"','" + pensionerName + "','" + pensionIssueDate + "','" + pensionType + "','" + netPay + "');";
            Statement insertAppStmt = config.conn.createStatement();
            int insertAppInt = insertAppStmt.executeUpdate(insertAPP9);
            if (insertInt >= 1 && insertAppInt >= 1) {
                boolean flag = false;
                long amount = 0;
                String bankBranch = bankName + " " + branchName;
                Statement bankStmt = config.conn.createStatement();
                String bankQuery = "SELECT * FROM pension_database.bankbranch_table WHERE BankAndBranch='" + bankBranch + "'";
                ResultSet rsForBank = bankStmt.executeQuery(bankQuery);
                while (rsForBank.next()) {
                    flag = true;
                    amount = rsForBank.getLong("Amount");
                }
                System.out.println(flag);
                if (flag) {
                    amount = (long) (amount + netPay);
                    System.out.println(amount);
                    String updateQuery = "UPDATE pension_database.bankbranch_table SET Amount= '" + amount + "' WHERE BankAndBranch='" + bankBranch + "'";
                    PreparedStatement updateStmt = config.conn.prepareStatement(updateQuery);
                    int updateBank = updateStmt.executeUpdate();
                    System.out.println(updateBank);
                    if (updateBank >= 1) {
                        JOptionPane.showMessageDialog(null, "Data saved successfully(Bank update)", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Some error occured in update bank", "Pension Record System", JOptionPane.ERROR_MESSAGE);

                    }
                } else {
                    String insertBankQuery = "INSERT INTO pension_database.bankbranch_table(BankAndBranch, Amount) VALUES('" + bankBranch + "','" + netPay + "')";
                    Statement bankInsertStmt = config.conn.createStatement();
                    int insertBank = bankInsertStmt.executeUpdate(insertBankQuery);
                    if (insertBank >= 1) {
                        JOptionPane.showMessageDialog(null, "Data saved successfully(Bank Insert)", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(null, "Some error occured in insert bank", "Pension Record System", JOptionPane.ERROR_MESSAGE);

                    }

                }

//                JOptionPane.showMessageDialog(null, "Data saved successfully", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Inserted successfully");
                fetchData(globalSelectQuery);
                clearAllFields();
            } else {
                JOptionPane.showMessageDialog(null, "Record not inserted! Some error occured", "Pension Record System", JOptionPane.ERROR_MESSAGE);
                System.out.println("Inserted unsuccessfully");
            }

            System.out.println(todayDate);
            System.out.println(pensionIssueDate);
            System.out.println(birthDateString);
            System.out.println(issueDate);
            System.out.println(endDate);
            System.out.println(ppoNo + pensionIssueDate + todayDate + pensionType + pensionerName + birthDate + bankName + bankName + branchName);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Exception occured" + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        try {
            // TODO add your handling code here:
            calculateTotalGross();
//            calculateNetPayAndEndDate();
//            int srNo = 0;
            Date todayDateTable = null;
            String ppoNo = ppoTxt.getText().toString();
            String select = "select * from pension_database.pension_data where PPONo='" + ppoNo + "';";
            Statement selectUpDateStmt = config.conn.createStatement();
            ResultSet rsForselectUpDate = selectUpDateStmt.executeQuery(select);
            while (rsForselectUpDate.next()) {
//                srNo = rsForselectUpDate.getInt("SrNo");
                todayDateTable = rsForselectUpDate.getDate("TodayDate");
            }

            Statement deleteStmt = config.conn.createStatement();
            String deletequery = "delete from pension_database.pension_data where PPONo='" + ppoNo + "';";
            deleteStmt.executeUpdate(deletequery);
            System.out.println("Record is deleted from the table successfully..................");
            ppoNo = ppoTxt.getText().toString();
            pensionIssueDate = toDataBaseDate.format(pensionIssueDateTxt.getDate());

//            todayDate = toDataBaseDate.format(date);
            pensionType = pensionTypeCombo.getSelectedItem().toString();
            pensionerName = pensionerNameTxt.getText().toString();

            birthDateString = toDataBaseDate.format(dateOfBirthTxt.getDate());

            bankName = bankSelectTxt.getSelectedItem().toString();
            branchName = branchSelectTxt.getSelectedItem().toString();
            accountNo = accountNoTxt.getText().toString();
            ifscCode = ifscTxt.getText().toString();

            basicAmount = Float.parseFloat(basicAmountTxt.getText().toString());

            grossAmount = Math.round(Float.parseFloat(grossAmountTxt.getText().toString()));
            DA = Math.round(Float.parseFloat(daTxt.getText().toString()));

            commutation = Math.round(Float.parseFloat(commutation1Txt.getText().toString()));
            issueDate = toDataBaseDate.format(commStartDate1Txt.getDate());
            endDate = toDataBaseDate.format(commEndDate1Txt.getDate());

            commutationNew2 = Math.round(Float.parseFloat(commutation2Txt.getText().toString()));
            issueDate2 = toDataBaseDate.format(commStartDate2Txt.getDate());
            endDate2 = toDataBaseDate.format(commEndDate2Txt.getDate());

            otherNew = Math.round(Float.parseFloat(otherTxt.getText().toString()));

            netPay = Math.round(Double.parseDouble(netPayTxt.getText().toString()));

            String updateQuery = "INSERT INTO pension_database.pension_data (PPONo, TodayDate, PensionIssueDate, PensionType, PensionerName, DateOfBirth,Age, BankName, BankBranch, BankAccountNo, BankIFSC, BasicAmount, " + chooseAge + ", GrossAmount, DA,Total, DeductionCommutation, IssueDate, EndDate,DeductionCommutation2, IssueDate2, EndDate2,Other, NetAmount) VALUES('" + ppoNo + "','" + todayDateTable + "','" + pensionIssueDate + "','" + pensionType + "','" + pensionerName + "','" + birthDateString + "','" + age + "','" + bankName + "','" + branchName + "','" + accountNo + "','" + ifscCode + "','" + basicAmount + "','" + applyAmt + "','" + grossAmount + "','" + DA + "','" + totalGross + "','" + commutation + "','" + issueDate + "','" + endDate + "','" + commutationNew2 + "','" + issueDate2 + "','" + endDate2 + "','" + otherNew + "','" + netPay + "');";
            System.out.println(updateQuery);
            Statement updateStmt = config.conn.createStatement();
            int insertInt = updateStmt.executeUpdate(updateQuery);

            String updateApp9 = "UPDATE dbo.app9Table SET DateOfRecord='"+todayDateTable+"', PensionerName='" + pensionerName + "', PensionIssueDate='" + pensionIssueDate + "', PensionType='" + pensionType + "', April='" + netPay + "' WHERE PPONo='" + ppoNo + "';";
            PreparedStatement updateApp9Stmt = config.conn.prepareStatement(updateApp9);
            int a = updateApp9Stmt.executeUpdate();
            System.out.println(a);
            if (insertInt >= 1) {
                String oldBankQuery = "SELECT * FROM pension_database.bankbranch_table WHERE BankAndBranch='" + oldBankAndBranch + "'";
                Statement oldBankStmt = config.conn.createStatement();
                ResultSet oldBankRs = oldBankStmt.executeQuery(oldBankQuery);
                long oldBankAmount = 0;
                while (oldBankRs.next()) {
                    oldBankAmount = (long) (oldBankRs.getLong("Amount") - oldNetAmount);
                }
                String updateOldBankQuery = "UPDATE pension_database.bankbranch_table SET Amount='" + oldBankAmount + "' WHERE BankAndBranch='" + oldBankAndBranch + "';";
                PreparedStatement updateoldBankStmt = config.conn.prepareStatement(updateOldBankQuery);
                updateoldBankStmt.executeUpdate();

                boolean flag = false;
                long updateAmount = 0;
                String bankBranch = bankName + " " + branchName;
                Statement bankStmt = config.conn.createStatement();
                String bankQuery = "SELECT * FROM pension_database.bankbranch_table WHERE BankAndBranch='" + bankBranch + "'";
                ResultSet rsForBank = bankStmt.executeQuery(bankQuery);
                while (rsForBank.next()) {

                    updateAmount = Math.abs(rsForBank.getLong("Amount"));
                    flag = true;
                }
//                if (oldBankAndBranch != bankBranch) {
//                    double oldUpdateAmount = 0;
//                    Statement bankOldStmt = config.conn.createStatement();
//                    String bankOldQuery = "SELECT * FROM bankbranch_table WHERE BankAndBranch='" + oldBankAndBranch + "'";
//                    ResultSet rsForOldBank = bankOldStmt.executeQuery(bankOldQuery);
//                    while (rsForOldBank.next()) {
//
//                        oldUpdateAmount = rsForOldBank.getDouble("Amount") - oldNetAmount;
//
//                    }
//                    String updateBeforeUpdateQuery = "UPDATE bankbranch_table SET Amount='" + oldUpdateAmount + "' WHERE BankAndBranch='" + oldBankAndBranch + "';";
//                    PreparedStatement updateOldBankStmt = config.conn.prepareStatement(updateBeforeUpdateQuery);
//                    int updateOldNetBank = updateOldBankStmt.executeUpdate();
//                }
//                    if (flag) {
////                    
//
//                        double totalNet = updateAmount + netPay;
//                        String updateBankQuery = "UPDATE bankbranch_table SET Amount= '" + totalNet + "' WHERE BankAndBranch='" + bankBranch + "'";
//                        PreparedStatement updateBankStmt = config.conn.prepareStatement(updateBankQuery);
//                        int updateNetBank = updateBankStmt.executeUpdate();
//                        if (updateNetBank >= 1) {
//                            JOptionPane.showMessageDialog(null, "Data Updated successfully(Bank update)", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);
//                        } else {
//                            JOptionPane.showMessageDialog(null, "Some error occured in update(update) bank", "Pension Record System", JOptionPane.ERROR_MESSAGE);
//
//                        }
//                    } else {
//                        String insertBankQuery = "INSERT INTO bankbranch_table(BankAndBranch, Amount) VALUES('" + bankBranch + "','" + netPay + "')";
//                        Statement bankInsertStmt = config.conn.createStatement();
//                        int insertBank = bankInsertStmt.executeUpdate(insertBankQuery);
//                        if (insertBank >= 1) {
//                            JOptionPane.showMessageDialog(null, "Data updated successfully(Bank Insert)", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);
//
//                        } else {
//                            JOptionPane.showMessageDialog(null, "Some error occured in update(insert) bank", "Pension Record System", JOptionPane.ERROR_MESSAGE);
//
//                        }
//                    }
//                }
                long totalNet = 0;
                if (flag) {

                    totalNet = (long) (updateAmount + netPay);

                    String updateBankQuery = "UPDATE pension_database.bankbranch_table SET Amount= '" + totalNet + "' WHERE BankAndBranch='" + bankBranch + "'";
                    PreparedStatement updateBankStmt = config.conn.prepareStatement(updateBankQuery);
                    int updateNetBank = updateBankStmt.executeUpdate();
                    if (updateNetBank >= 1) {
                        JOptionPane.showMessageDialog(null, "Data Updated successfully(Bank update)\n Total minus bank amount : " + updateAmount + ", Real Total amount added : " + totalNet + ", Current netPay to be added : " + netPay, "Pension Record System", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Some error occured in update(update) bank", "Pension Record System", JOptionPane.ERROR_MESSAGE);

                    }
                } else {
                    String insertBankQuery = "INSERT INTO pension_database.bankbranch_table(BankAndBranch, Amount) VALUES('" + bankBranch + "','" + netPay + "')";
                    Statement bankInsertStmt = config.conn.createStatement();
                    int insertBank = bankInsertStmt.executeUpdate(insertBankQuery);
                    if (insertBank >= 1) {
                        JOptionPane.showMessageDialog(null, "Data updated successfully(Bank Insert)", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(null, "Some error occured in update(insert) bank", "Pension Record System", JOptionPane.ERROR_MESSAGE);

                    }
                }

//                JOptionPane.showMessageDialog(null, "Data Updated successfully", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Updated successfully");
                fetchData(globalSelectQuery);
                saveBtn.setEnabled(false);
                updateBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                clearAllFields();
                fieldsEnabling(false);
            } else {
                JOptionPane.showMessageDialog(null, "Record not updated! Some error occured", "Pension Record System", JOptionPane.ERROR_MESSAGE);
                System.out.println("Inserted unsuccessfully");
                fetchData(globalSelectQuery);
                saveBtn.setEnabled(false);
                updateBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                clearAllFields();
                fieldsEnabling(false);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        try {
            // TODO add your handling code here:
            int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete the pension record?", "Pension Record System", JOptionPane.YES_OPTION);
            if (result == 0) {
                ppoNo = ppoTxt.getText().toString();
                Statement deleteStatement = config.conn.createStatement();
//                Statement alterStatement = config.conn.createStatement();
                String deletequery = "delete from  pension_database.pension_data where PPONo='" + ppoNo + "';";
                deleteStatement.executeUpdate(deletequery);

                String deleteApp9 = "delete from dbo.app9Table where PPONo='" + ppoNo + "';";
                Statement deleteApp9Stmt = config.conn.createStatement();
                deleteApp9Stmt.executeUpdate(deleteApp9);

                String selectAfterDelete = "SELECT * FROM pension_database.bankbranch_table WHERE BankAndBranch='" + oldBankAndBranch + "';";
                Statement selectAfterDeleteStmt = config.conn.createStatement();
                ResultSet selectAfterDeleteRs = selectAfterDeleteStmt.executeQuery(selectAfterDelete);
                long totalNetAmount = 0;
                while (selectAfterDeleteRs.next()) {
                    totalNetAmount = selectAfterDeleteRs.getLong("Amount");
                }
                long updatedNetAmount = totalNetAmount - Math.round(Double.parseDouble(netPayTxt.getText().toString()));
                String updateAfterDeleteQuery = "UPDATE pension_database.bankbranch_table SET Amount= '" + updatedNetAmount + "' WHERE BankAndBranch='" + oldBankAndBranch + "'";
                PreparedStatement updateAfterDeleteStmt = config.conn.prepareStatement(updateAfterDeleteQuery);
                int updateAfterDeleteBank = updateAfterDeleteStmt.executeUpdate();
                if (updateAfterDeleteBank >= 1) {
                    JOptionPane.showMessageDialog(null, "Record deleted successfully", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Some error occured in update bank", "Pension Record System", JOptionPane.ERROR_MESSAGE);

                }

//                String alterTable = "ALTER TABLE pension_data AUTO_INCREMENT=1;";
//                alterStatement.executeUpdate(alterTable);
//                JOptionPane.showMessageDialog(null, "Record deleted successfully", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);
                fetchData(globalSelectQuery);
                clearAllFields();
                ppoTxt.setEditable(true);
                ppoTxt.setEnabled(true);
                deleteBtn.setEnabled(false);
                updateBtn.setEnabled(false);
                fieldsEnabling(false);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void clearAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllBtnActionPerformed
        // TODO add your handling code here:
        clearAllFields();
    }//GEN-LAST:event_clearAllBtnActionPerformed

    private void basicAmountTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_basicAmountTxtKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            basicAmountTxt.setEditable(false);

            basicAmountTxt.setToolTipText("Please enter number only");
        } else {
            basicAmountTxt.setEditable(true);
        }

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {

            basicAmountTxt.setEditable(true);

        } else {
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE || evt.getExtendedKeyCode() == KeyEvent.VK_DECIMAL) {
                basicAmountTxt.setEditable(true);
            } else {
                basicAmountTxt.setEditable(false);
            }
        }


    }//GEN-LAST:event_basicAmountTxtKeyPressed

    private void commutation1TxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_commutation1TxtFocusGained
        // TODO add your handling code here:
        calculateTotalGross();

    }//GEN-LAST:event_commutation1TxtFocusGained

    private void calculateTotalGross() {
        try {
            birthDate = dateOfBirthTxt.getDate();
            age = date.getYear() - birthDate.getYear();
            System.out.println("Hello gained" + age);
            basicAmount = Float.parseFloat(basicAmountTxt.getText().toString());

            if (age <= 79) {
                applyAmt = 0;
                gross = basicAmount;
                daFloat = (float) Math.round((gross * 0.46));
//                 System.out.println("Hello gross"+gross);
//                 System.out.println("Hello gross"+daFloat);

                daTxt.setText(Float.toString(daFloat));
                grossAmountTxt.setText(Float.toString(gross));
                chooseAge = "Age_80_Plus";
            }

            if (age >= 80 && age <= 85) {
//            System.out.println("Hello basic"+basicAmount);

                applyAmt = (float) Math.round(basicAmount * 0.2);

//                   System.out.println("Hello applyAmt"+applyAmt);
                gross = basicAmount + applyAmt;
                daFloat = (float) Math.round(gross * 0.46);
//                 System.out.println("Hello gross"+gross);
//                 System.out.println("Hello gross"+daFloat);

                daTxt.setText(Float.toString(daFloat));
                grossAmountTxt.setText(Float.toString(gross));
                chooseAge = "Age_80_Plus";
            }

            if (age >= 86 && age <= 90) {
//            System.out.println("Hello basic"+basicAmount);

                applyAmt = (float) Math.round(basicAmount * 0.3);

//                   System.out.println("Hello applyAmt"+applyAmt);
                gross = basicAmount + applyAmt;
                daFloat = (float) Math.round(gross * 0.46);
//                 System.out.println("Hello gross"+gross);
//                 System.out.println("Hello gross"+daFloat);

                daTxt.setText(Float.toString(daFloat));
                grossAmountTxt.setText(Float.toString(gross));
                chooseAge = "Age_85_Plus";
            }

            if (age >= 91 && age <= 95) {
//            System.out.println("Hello basic"+basicAmount);

                applyAmt = (float) Math.round(basicAmount * 0.4);

//                   System.out.println("Hello applyAmt"+applyAmt);
                gross = basicAmount + applyAmt;
                daFloat = (float) Math.round(gross * 0.46);
//                 System.out.println("Hello gross"+gross);
//                 System.out.println("Hello gross"+daFloat);

                daTxt.setText(Float.toString(daFloat));
                grossAmountTxt.setText(Float.toString(gross));
                chooseAge = "Age_90_Plus";
            }

            if (age >= 96 && age <= 100) {
//            System.out.println("Hello basic"+basicAmount);

                applyAmt = (float) Math.round(basicAmount * 0.5);

//                   System.out.println("Hello applyAmt"+applyAmt);
                gross = basicAmount + applyAmt;
                daFloat = (float) Math.round(gross * 0.46);
//                 System.out.println("Hello gross"+gross);
//                 System.out.println("Hello gross"+daFloat);

                daTxt.setText(Float.toString(daFloat));
                grossAmountTxt.setText(Float.toString(gross));
                chooseAge = "Age_95_Plus";
            }

            if (age > 100) {
//            System.out.println("Hello basic"+basicAmount);

                applyAmt = (float) Math.round(basicAmount * 1);

//                   System.out.println("Hello applyAmt"+applyAmt);
                gross = basicAmount + applyAmt;
                daFloat = (float) Math.round(gross * 0.46);
//                 System.out.println("Hello gross"+gross);
//                 System.out.println("Hello gross"+daFloat);

                daTxt.setText(Float.toString(daFloat));
                grossAmountTxt.setText(Float.toString(gross));
                chooseAge = "Age_100_Plus";
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

//        if (basicAmountTxt.getText().toString() == "") {
//            grossAmountTxt.setText("");
//            daTxt.setText("");
//        }
        grossToAdd = Float.parseFloat(grossAmountTxt.getText().toString());
        daToAdd = Float.parseFloat(daTxt.getText().toString());

        totalGross = grossToAdd + daToAdd;

    }

    private void commutation1TxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_commutation1TxtKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            commutation1Txt.setEditable(false);

            commutation1Txt.setToolTipText("Please enter number only");
        } else {
            commutation1Txt.setEditable(true);
        }

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {

            commutation1Txt.setEditable(true);

        } else {
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE || evt.getExtendedKeyCode() == KeyEvent.VK_DECIMAL) {
                commutation1Txt.setEditable(true);
            } else {
                commutation1Txt.setEditable(false);
            }

        }

    }//GEN-LAST:event_commutation1TxtKeyPressed

    private void commStartDate1TxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_commStartDate1TxtFocusGained
        // TODO add your handling code here:


    }//GEN-LAST:event_commStartDate1TxtFocusGained

    private void calculateNetPayAndEndDate() {
        try {
            calculateTotalGross();
            Date issued = commStartDate1Txt.getDate();
            todayDate = formatter.format(date);
            System.out.println("todayDate" + todayDate);
            int dateIssue = issued.getDate();
            int monthIssue = issued.getMonth();

            String monthIssueString;
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getMonths();
            monthIssueString = months[monthIssue];

            int yearIssue = issued.getYear();
            int yearCodedIssued = yearIssue + 1900;
            System.out.println("Year Issue" + yearCodedIssued);
            int endYear = yearCodedIssued + 15;
//        int monthIssueReal = monthIssue + 1;
            String endDateIssued = dateIssue + "-" + monthIssueString + "-" + endYear;
            System.out.println("End date issue" + endDateIssued);
            Date endDatePrint;

            endDatePrint = formatter.parse(endDateIssued);
            System.out.println(endDatePrint);
            commEndDate1Txt.setDate(endDatePrint);

            if (endDatePrint.compareTo(date) <= 0) {
                JOptionPane.showConfirmDialog(null, "The issued date 1 is expired, the coummutation 1 date will be set to 0", "Pension Record System", JOptionPane.YES_OPTION);
                commutation1Txt.setText("0.0");
            }

            Date issued2 = commStartDate2Txt.getDate();
            int dateIssue2 = issued2.getDate();
            int monthIssue2 = issued2.getMonth();

            String monthIssue2String;
            DateFormatSymbols dfs2 = new DateFormatSymbols();
            String[] months2 = dfs2.getMonths();
            monthIssue2String = months2[monthIssue2];

            int yearIssue2 = issued2.getYear();
            int yearCodedIssued2 = yearIssue2 + 1900;
            System.out.println("Year Issue" + yearCodedIssued2);
            int endYear2 = yearCodedIssued2 + 15;

            String endDateIssued2 = dateIssue2 + "-" + monthIssue2String + "-" + endYear2;
            System.out.println("End date issue 2 " + endDateIssued2);
            Date endDatePrint2;

            endDatePrint2 = formatter.parse(endDateIssued2);
            System.out.println(endDatePrint2);
            commEndDate2Txt.setDate(endDatePrint2);

            if (endDatePrint2.compareTo(date) <= 0) {
                JOptionPane.showConfirmDialog(null, "The issued commutation 2 date is expired, the coummutation 2 date will be set to 0", "Pension Record System", JOptionPane.YES_OPTION);
                commutation2Txt.setText("0.0");
            }

//            Date end=formatter.parse(dateIssued);
//            endDateTxt.setDate(end);
            System.out.println("Total Gross : " + totalGross);
            commutationCharges = Float.parseFloat(commutation1Txt.getText().toString());
            commutation2 = Float.parseFloat(commutation2Txt.getText().toString());
            other = Float.parseFloat(otherTxt.getText().toString());

            netPaySetting = Math.round(totalGross - (commutationCharges + commutation2 + other));
            System.out.println("Total Net Pay : " + netPaySetting);
            netPayTxt.setText(Float.toString(netPaySetting));
//            saveBtn.setEnabled(true);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(null, "Basic amount, commutation charges and issue date must be filled : " + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);

        }
    }
    private void generateNetPayAndEndDateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateNetPayAndEndDateBtnActionPerformed

        // TODO add your handling code here:
        calculateNetPayAndEndDate();


    }//GEN-LAST:event_generateNetPayAndEndDateBtnActionPerformed

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Exception occured : " + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void exportSelectedToExcel(JTable jt) {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(jt);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("pension_records_date_wise");
                Row rowCol = sheet.createRow(0);
                XSSFFont font = ((XSSFWorkbook) wb).createFont();
                font.setBold(true);
                CellStyle style = wb.createCellStyle();
                style.setFont(font);
                for (int i = 0; i < jt.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellStyle(style);
                    cell.setCellValue(jt.getColumnName(i));

                }

                for (int j = 0; j < jt.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < jt.getColumnCount(); k++) {
                        Cell cell = cell = row.createCell(k);
                        if (jt.getValueAt(j, k) != null) {
                            cell.setCellValue(jt.getValueAt(j, k).toString());

                        }

                    }
                }

                Row row = sheet.createRow(jt.getRowCount() + 1);
                Cell cell1 = row.createCell(jt.getColumnCount() - 1);
                long netPaySumAll[] = fetchSearchByDateData(dateSearchQuery);
                cell1.setCellStyle(style);
                cell1.setCellValue(Long.toString(netPaySumAll[12]));

                Cell cell2 = row.createCell(0);
                cell2.setCellValue("Total");
                cell2.setCellStyle(style);

                Cell cellBasic = row.createCell(12);
                cellBasic.setCellStyle(style);
                cellBasic.setCellValue(Long.toString(netPaySumAll[0]));

                Cell plus80 = row.createCell(13);
                plus80.setCellValue(Long.toString(netPaySumAll[1]));
                plus80.setCellStyle(style);

                Cell plus85 = row.createCell(14);
                plus85.setCellValue(Long.toString(netPaySumAll[2]));
                plus85.setCellStyle(style);

                Cell plus90 = row.createCell(15);
                plus90.setCellValue(Long.toString(netPaySumAll[3]));
                plus90.setCellStyle(style);

                Cell plus95 = row.createCell(16);
                plus95.setCellValue(Long.toString(netPaySumAll[4]));
                plus95.setCellStyle(style);

                Cell plus100 = row.createCell(17);
                plus100.setCellValue(Long.toString(netPaySumAll[5]));
                plus100.setCellStyle(style);

                Cell grossAmt = row.createCell(18);
                grossAmt.setCellValue(Long.toString(netPaySumAll[6]));
                grossAmt.setCellStyle(style);

                Cell DAAmt = row.createCell(19);
                DAAmt.setCellValue(Long.toString(netPaySumAll[7]));
                DAAmt.setCellStyle(style);

                Cell totalAmt = row.createCell(20);
                totalAmt.setCellValue(Long.toString(netPaySumAll[8]));
                totalAmt.setCellStyle(style);

                Cell comm1 = row.createCell(21);
                comm1.setCellValue(Long.toString(netPaySumAll[9]));
                comm1.setCellStyle(style);

                Cell comm2 = row.createCell(24);
                comm2.setCellValue(Long.toString(netPaySumAll[10]));
                comm2.setCellStyle(style);

                Cell otherAmt = row.createCell(27);
                otherAmt.setCellValue(Long.toString(netPaySumAll[11]));
                otherAmt.setCellStyle(style);

                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                openFile(saveFile.toString());

            } else {
                JOptionPane.showMessageDialog(null, "Operation Cancelled", "Pension Record System", JOptionPane.ERROR_MESSAGE);

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Exception occured : " + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void exportToExcel(JTable jt) {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(jt);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("pension_records");
                Row rowCol = sheet.createRow(0);
                XSSFFont font = ((XSSFWorkbook) wb).createFont();
                font.setBold(true);
                CellStyle style = wb.createCellStyle();
                style.setFont(font);
                for (int i = 0; i < jt.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellStyle(style);
                    cell.setCellValue(jt.getColumnName(i));

                }
                Cell cell = null;
                for (int j = 0; j < jt.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < jt.getColumnCount(); k++) {
                        cell = row.createCell(k);
                        if (jt.getValueAt(j, k) != null) {
                            cell.setCellValue(jt.getValueAt(j, k).toString());

                        }

                    }
                }

//                 Row rowTotal = sheet.createRow(jt.getRowCount() + 1);
//                Cell cellTotal = rowTotal.createCell(jt.getColumnCount());
//                cellTotal.setCellStyle(style);
//                cellTotal.setCellValue("Total");
                Row row = sheet.createRow(jt.getRowCount() + 1);
                Cell cell1 = row.createCell(jt.getColumnCount() - 1);
                long netPaySumAll[] = fetchData(globalSelectQuery);
                cell1.setCellStyle(style);
                cell1.setCellValue(Long.toString(netPaySumAll[12]));

                Cell cell2 = row.createCell(0);
                cell2.setCellValue("Total");
                cell2.setCellStyle(style);

                Cell cellBasic = row.createCell(12);
                cellBasic.setCellStyle(style);
                cellBasic.setCellValue(Long.toString(netPaySumAll[0]));

                Cell plus80 = row.createCell(13);
                plus80.setCellValue(Long.toString(netPaySumAll[1]));
                plus80.setCellStyle(style);

                Cell plus85 = row.createCell(14);
                plus85.setCellValue(Long.toString(netPaySumAll[2]));
                plus85.setCellStyle(style);

                Cell plus90 = row.createCell(15);
                plus90.setCellValue(Long.toString(netPaySumAll[3]));
                plus90.setCellStyle(style);

                Cell plus95 = row.createCell(16);
                plus95.setCellValue(Long.toString(netPaySumAll[4]));
                plus95.setCellStyle(style);

                Cell plus100 = row.createCell(17);
                plus100.setCellValue(Long.toString(netPaySumAll[5]));
                plus100.setCellStyle(style);

                Cell grossAmt = row.createCell(18);
                grossAmt.setCellValue(Long.toString(netPaySumAll[6]));
                grossAmt.setCellStyle(style);

                Cell DAAmt = row.createCell(19);
                DAAmt.setCellValue(Long.toString(netPaySumAll[7]));
                DAAmt.setCellStyle(style);

                Cell totalAmt = row.createCell(20);
                totalAmt.setCellValue(Long.toString(netPaySumAll[8]));
                totalAmt.setCellStyle(style);

                Cell comm1 = row.createCell(21);
                comm1.setCellValue(Long.toString(netPaySumAll[9]));
                comm1.setCellStyle(style);

                Cell comm2 = row.createCell(24);
                comm2.setCellValue(Long.toString(netPaySumAll[10]));
                comm2.setCellStyle(style);

                Cell otherAmt = row.createCell(27);
                otherAmt.setCellValue(Long.toString(netPaySumAll[11]));
                otherAmt.setCellStyle(style);

                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                openFile(saveFile.toString());

            } else {
                JOptionPane.showMessageDialog(null, "Operation Cancelled", "Pension Record System", JOptionPane.ERROR_MESSAGE);

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Exception occured : " + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void exportAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportAllBtnActionPerformed
        // TODO add your handling code here:

        fetchData(globalSelectQuery);

        exportToExcel(dataTable);

    }//GEN-LAST:event_exportAllBtnActionPerformed

    private void dataTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataTableMouseClicked
        try {
            // TODO add your handling code here:
            DefaultTableModel fetchTableModel = (DefaultTableModel) dataTable.getModel();

            String ppoNOStr = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 1).toString();
            Date penDate = new SimpleDateFormat("yyyy-MM-dd").parse(fetchTableModel.getValueAt(dataTable.getSelectedRow(), 3).toString());
            String penType = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 4).toString();
            String penName = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 5).toString();
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(fetchTableModel.getValueAt(dataTable.getSelectedRow(), 6).toString());

            String bnkName = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 8).toString();
            String branch = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 9).toString();
            String accountNumber = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 10).toString();
            String ifscNo = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 11).toString();
            String basicAmt = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 12).toString();
            String gross = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 18).toString();
            String da = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 19).toString();
            String total = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 20).toString();
            String deducComm = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 21).toString();
            Date issuDate = new SimpleDateFormat("yyyy-MM-dd").parse(fetchTableModel.getValueAt(dataTable.getSelectedRow(), 22).toString());
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(fetchTableModel.getValueAt(dataTable.getSelectedRow(), 23).toString());
            String deducComm2 = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 24).toString();
            Date issuDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(fetchTableModel.getValueAt(dataTable.getSelectedRow(), 25).toString());
            Date endDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(fetchTableModel.getValueAt(dataTable.getSelectedRow(), 26).toString());
            String other = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 27).toString();
            String netAmt = fetchTableModel.getValueAt(dataTable.getSelectedRow(), 28).toString();
            oldNetAmount = Math.round(Double.parseDouble(netAmt));
            oldBankAndBranch = bnkName + " " + branch;
//            JOptionPane.showMessageDialog(null, oldBankAndBranch + " " + oldNetAmount, "Pension Record System", JOptionPane.ERROR_MESSAGE);

            ppoTxt.setText(ppoNOStr);
            pensionIssueDateTxt.setDate(penDate);
            pensionTypeCombo.setSelectedItem(penType);
            pensionerNameTxt.setText(penName);
            dateOfBirthTxt.setDate(dob);
            bankSelectTxt.setSelectedItem(bnkName);
            branchSelectTxt.setSelectedItem(branch);
            accountNoTxt.setText(accountNumber);
            ifscTxt.setText(ifscNo);
            basicAmountTxt.setText(basicAmt);
            grossAmountTxt.setText(gross);
            daTxt.setText(da);

            commutation1Txt.setText(deducComm);
            commStartDate1Txt.setDate(issuDate);
            commEndDate1Txt.setDate(endDate);
            commutation2Txt.setText(deducComm2);
            commStartDate2Txt.setDate(issuDate2);
            commEndDate2Txt.setDate(endDate2);
            otherTxt.setText(other);
            netPayTxt.setText(netAmt);

            fieldsEnabling(true);
            ppoTxt.setEditable(false);
            ppoTxt.setEnabled(false);

            updateBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
            saveBtn.setEnabled(false);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Exception occured" + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);

        }


    }//GEN-LAST:event_dataTableMouseClicked

    private void ppoSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppoSearchBtnActionPerformed
        // TODO add your handling code here:
        String ppoNoSearch = ppoSearchTxt.getText().toString();
        String ppoSearchQuery = "select * from pension_database.pension_data where PPONo='" + ppoNoSearch + "';";
        fetchSearchData(ppoSearchQuery);

    }//GEN-LAST:event_ppoSearchBtnActionPerformed

    private void dateSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateSearchBtnActionPerformed
        // TODO add your handling code here:

        String fromDate = toDataBaseDate.format(fromDateTxt.getDate());
        String toDate = toDataBaseDate.format(toDateTxt.getDate());
        dateSearchQuery = "SELECT * FROM pension_database.pension_data WHERE TodayDate BETWEEN '" + fromDate + "' AND '" + toDate + "';";
        fetchSearchByDateData(dateSearchQuery);
    }//GEN-LAST:event_dateSearchBtnActionPerformed

    private void pensionerNameSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pensionerNameSearchBtnActionPerformed
        // TODO add your handling code here:
        String pensionNameSearch = pensionerNameSearchTxt.getText().toString();
        String pensionNameSearchQuery = "select * from pension_database.pension_data where PensionerName='" + pensionNameSearch + "';";
        fetchSearchByName(pensionNameSearchQuery);
    }//GEN-LAST:event_pensionerNameSearchBtnActionPerformed

    private void showRecordsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showRecordsBtnActionPerformed
        // TODO add your handling code here:
        fetchData(globalSelectQuery);
        selectedExportBtn.setEnabled(false);
    }//GEN-LAST:event_showRecordsBtnActionPerformed

    private void printRecordsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printRecordsBtnActionPerformed
        // TODO add your handling code here:
        fetchData(globalSelectQuery);
        MessageFormat header = new MessageFormat("Pension Records");
        MessageFormat footer = new MessageFormat("Pension Records");
        try {
//            PrintRequestAttributeSet set=new HashPrintRequestAttributeSet();
//            set.add(OrientationRequested.LANDSCAPE);
            boolean flag = dataTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            if (flag) {
                JOptionPane.showMessageDialog(null, "Printed Successfully", "Print Data", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Operation Cancelled", "Print Data", JOptionPane.ERROR_MESSAGE);

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed", "Print Data", JOptionPane.ERROR);

        }

    }//GEN-LAST:event_printRecordsBtnActionPerformed

    private void selectedExportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectedExportBtnActionPerformed
        // TODO add your handling code here:
        exportSelectedToExcel(dataTable);
    }//GEN-LAST:event_selectedExportBtnActionPerformed

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        // TODO add your handling code here:
        this.dispose();
//        Dashboard window = new Dashboard();
//        window.setVisible(true);
//        window.setExtendedState(MAXIMIZED_BOTH);

    }//GEN-LAST:event_closeBtnActionPerformed

    private void accountNoTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accountNoTxtKeyPressed
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            accountNoTxt.setEditable(false);

            accountNoTxt.setToolTipText("Please enter number only");
        } else {
            accountNoTxt.setEditable(true);
        }

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {

            accountNoTxt.setEditable(true);

        } else {
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                accountNoTxt.setEditable(true);
            } else {
                accountNoTxt.setEditable(false);
            }
        }


    }//GEN-LAST:event_accountNoTxtKeyPressed

    private void ifscTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ifscTxtKeyPressed
        // TODO add your handling code here:
        DocumentFilter f = new UppercaseJTextField();
        AbstractDocument doc = (AbstractDocument) ifscTxt.getDocument();
        doc.setDocumentFilter(f);
    }//GEN-LAST:event_ifscTxtKeyPressed

    private void bankSelectTxtItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bankSelectTxtItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_bankSelectTxtItemStateChanged


    private void bankSelectTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bankSelectTxtActionPerformed
        // TODO add your handling code here:
        if (bankSelectTxt.getSelectedItem().equals("DCC BANK")) {

            branchSelectTxt.removeAllItems();
            branchSelectTxt.setSelectedItem(null);
            branchSelectTxt.addItem("BRANCH ZP SATARA");
            branchSelectTxt.addItem("BRANCH SHIVAJI CIRCLE");
            branchSelectTxt.addItem("BRANCH SATARA CITY");
            branchSelectTxt.addItem("BRANCH RAJWADA");
            branchSelectTxt.addItem("BRANCH ARALE");
            branchSelectTxt.addItem("BRANCH PARALI");
            branchSelectTxt.addItem("BRANCH ATIT");
            branchSelectTxt.addItem("BRANCH NAGTHANE");
            branchSelectTxt.addItem("BRANCH SHAHUNAGAR SHENDRE");
            branchSelectTxt.addItem("BRANCH ANGAPUR");
            branchSelectTxt.addItem("BRANCH PADALI");
            branchSelectTxt.addItem("BRANCH GOVE");
            branchSelectTxt.addItem("BRANCH KASHIL");
            branchSelectTxt.addItem("BRANCH MARKET YARDE");
            branchSelectTxt.addItem("BRANCH KODOLI SATARA");
            branchSelectTxt.addItem("BRANCH KRISHNAGAR");
            branchSelectTxt.addItem("BRANCH SHAHUPURI");
            branchSelectTxt.addItem("BRANCH MANGARLWA TALE");
            branchSelectTxt.addItem("BRANCH VENNANAGER");
            branchSelectTxt.addItem("BRANCH LIMB");
            branchSelectTxt.addItem("BRANCH SADAR BAZAR");
            branchSelectTxt.addItem("BRANCH APASHINGE");
            branchSelectTxt.addItem("BRANCH TASGAON");
            branchSelectTxt.addItem("BRANCH GODOLI SATARA");
            branchSelectTxt.addItem("BRANCH NELE");
            branchSelectTxt.addItem("BRANCH KSHETRA MAHULI");
            branchSelectTxt.addItem("BRANCH KOPARDE");
            branchSelectTxt.addItem("BRANCH PATHAKEL");
            branchSelectTxt.addItem("BRANCH KANHER");
            branchSelectTxt.addItem("BRANCH KAMERI");
            branchSelectTxt.addItem("BRANCH THOSEGHAR");
            branchSelectTxt.addItem("BRANCH CHINCHANER VANDAN");
            branchSelectTxt.addItem("BRANCH JIHE TAL SATARA");
            branchSelectTxt.addItem("BRANCH KONDAVE");
            branchSelectTxt.addItem("BRANCH SATARA ROAD KOREGAON");
            branchSelectTxt.addItem("BRANCH VIKASNAGR");
            branchSelectTxt.addItem("BRANCH SASPADE");
            branchSelectTxt.addItem("BRANCH BHATMARALI");
            branchSelectTxt.addItem("BRANCH DEGAON");
            branchSelectTxt.addItem("BRANCH KUDAL");
            branchSelectTxt.addItem("BRANCH KOREGAON");
            branchSelectTxt.addItem("BRANCH MEDHA");
            branchSelectTxt.addItem("BRANCH SHIWATHAR");
            branchSelectTxt.addItem("BRANCH ANAEWADI");
            branchSelectTxt.addItem("BRANCH CAMP SATARA");
            branchSelectTxt.addItem("BRANCH UDTARE");
            branchSelectTxt.addItem("BRANCH MARKET YARD KARAD");
            branchSelectTxt.addItem("BRANCH PANCHWAD");
            branchSelectTxt.addItem("BRANCH NANDGIRIL KHED KOREGAON");
            branchSelectTxt.addItem("BRANCH PIMPODE BK. KOREGAON");
            branchSelectTxt.addItem("BRANCH WATHAR STATION KOREGAON");
            branchSelectTxt.addItem("BRANCH VARNE");
            branchSelectTxt.addItem("BRANCH KHOJEWADI");
            branchSelectTxt.addItem("BRANCH MALHARPETH SATARA");

        }
        if (bankSelectTxt.getSelectedItem().equals("IDBI BANK")) {
            branchSelectTxt.removeAllItems();
            branchSelectTxt.setSelectedItem(null);
            branchSelectTxt.addItem("SATARA CITY");
            branchSelectTxt.addItem("LOKMANYANAGAR PUNE");
            branchSelectTxt.addItem("POWAI NAKA SATARA");
            branchSelectTxt.addItem("KARANJE SATARA");
            branchSelectTxt.addItem("WARNA VSAHAT-EXT");
            branchSelectTxt.addItem("ASHTA SANGALI");
            branchSelectTxt.addItem("MANDYA KARNATAKA");
            branchSelectTxt.addItem("KOREGAON SATARA");
            branchSelectTxt.addItem("KOTHRUD PUNE");

        }
        if (bankSelectTxt.getSelectedItem().equals("BANK OF MAHARASHTRA")) {
            branchSelectTxt.removeAllItems();
            branchSelectTxt.setSelectedItem(null);
            branchSelectTxt.addItem("SATARA CITY");
            branchSelectTxt.addItem("POWAI NAKA");
            branchSelectTxt.addItem("KODOLI");
            branchSelectTxt.addItem("KANER TAL SATARA");
            branchSelectTxt.addItem("NAGTHANE TAL SATARA");
            branchSelectTxt.addItem("SANGLI");
            branchSelectTxt.addItem("PADMAWATI PUNE");
            branchSelectTxt.addItem("TILIK ROAD PUNE");
            branchSelectTxt.addItem("LAYOUT BANGLORE");
            branchSelectTxt.addItem("KANDAWALIE WEAST");
            branchSelectTxt.addItem("MYSORE");
            branchSelectTxt.addItem("CHAMRAJ PETH BANGLORE");
            branchSelectTxt.addItem("NAGPUR NANDANWAN");
            branchSelectTxt.addItem("VADGAON PUNE");
            branchSelectTxt.addItem("BHOR");
            branchSelectTxt.addItem("KAGAL");
            branchSelectTxt.addItem("ASHTA SANGALI");
            branchSelectTxt.addItem("MIRAJ");
            branchSelectTxt.addItem("KEM-TAL KARMALA");
            branchSelectTxt.addItem("VILE PARLE (EAST)");
            branchSelectTxt.addItem("WADE DIS.PUNE");
            branchSelectTxt.addItem("BR.KARANJE SATARA");
            branchSelectTxt.addItem("VADGAON B.PUNE");
            branchSelectTxt.addItem("BR.SHAHUPURI");
            branchSelectTxt.addItem("BR.MANJIREWADI SOLAPUR");
            branchSelectTxt.addItem("BR.PARALI");
            branchSelectTxt.addItem("BR.KRISHNANAGAR");
            branchSelectTxt.addItem("BR.KUDAL");

        }
        if (bankSelectTxt.getSelectedItem().equals("BADODA BANK")) {
            branchSelectTxt.removeAllItems();
            branchSelectTxt.setSelectedItem(null);
            branchSelectTxt.addItem("SATARA CITY");
            branchSelectTxt.addItem("RAMGANG(UP)");
            branchSelectTxt.addItem("SHAHUPURI");
            branchSelectTxt.addItem("SADAR BAZAR");

        }
        if (bankSelectTxt.getSelectedItem().equals("UNION BANK OF INDIA")) {
            branchSelectTxt.removeAllItems();
            branchSelectTxt.setSelectedItem(null);
            branchSelectTxt.addItem("ALL");

        }


    }//GEN-LAST:event_bankSelectTxtActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddPension.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPension.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPension.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPension.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddPension().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accountNoTxt;
    private javax.swing.JComboBox<String> bankSelectTxt;
    private javax.swing.JTextField basicAmountTxt;
    private javax.swing.JComboBox<String> branchSelectTxt;
    private javax.swing.JButton clearAllBtn;
    private javax.swing.JButton closeBtn;
    private com.toedter.calendar.JDateChooser commEndDate1Txt;
    private com.toedter.calendar.JDateChooser commEndDate2Txt;
    private com.toedter.calendar.JDateChooser commStartDate1Txt;
    private com.toedter.calendar.JDateChooser commStartDate2Txt;
    private javax.swing.JTextField commutation1Txt;
    private javax.swing.JTextField commutation2Txt;
    private javax.swing.JTextField daTxt;
    private javax.swing.JTable dataTable;
    private com.toedter.calendar.JDateChooser dateOfBirthTxt;
    private javax.swing.JButton dateSearchBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton exportAllBtn;
    private com.toedter.calendar.JDateChooser fromDateTxt;
    private javax.swing.JButton generateNetPayAndEndDateBtn;
    private javax.swing.JTextField grossAmountTxt;
    private javax.swing.JTextField ifscTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField netPayTxt;
    private javax.swing.JButton newBtn;
    private javax.swing.JTextField otherTxt;
    private com.toedter.calendar.JDateChooser pensionIssueDateTxt;
    private javax.swing.JComboBox<String> pensionTypeCombo;
    private javax.swing.JButton pensionerNameSearchBtn;
    private javax.swing.JTextField pensionerNameSearchTxt;
    private javax.swing.JTextField pensionerNameTxt;
    private javax.swing.JButton ppoSearchBtn;
    private javax.swing.JTextField ppoSearchTxt;
    private javax.swing.JTextField ppoTxt;
    private javax.swing.JButton printRecordsBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton selectedExportBtn;
    private javax.swing.JButton showRecordsBtn;
    private com.toedter.calendar.JDateChooser toDateTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables

}

class UppercaseJTextField extends DocumentFilter {

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        fb.insertString(offset, text.toUpperCase(), attr);
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        fb.replace(offset, length, text.toUpperCase(), attrs);
    }
}
