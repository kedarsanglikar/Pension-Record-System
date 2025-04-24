/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pension.management.system;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.sql.SQLException;

/**
 *
 * @author Dell
 */
public class APP9 extends javax.swing.JFrame {

    /**
     * Creates new form APP9
     */
    Configuration config;
    DefaultTableModel defaultTableModel;
    String globalSelectQuery = "SELECT * FROM dbo.app9Table";
    SimpleDateFormat toDataBaseDate = new SimpleDateFormat("yyyy-MM-dd");
    String dateSearchQuery;

    public APP9() {
        initComponents();
        config = new Configuration();
        fetchData(globalSelectQuery);
    }

    private void fetchData(String query) {
        try {
            DefaultTableModel model = (DefaultTableModel) appTable.getModel();
            model.setRowCount(0);
            Statement stmt = config.conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int rows = 0;
            while (rs.next()) {
                rows++;
                int srNo = rows;
                Date recordDate = rs.getDate("DateOfRecord");
                String ppoNo = rs.getString("PPONo");
                String pensionerName = rs.getString("PensionerName");
                String pensionIssueDate = rs.getString("PensionIssueDate");
                String pensionType = rs.getString("PensionType");
                String year = rs.getString("Year");
                float april = rs.getFloat("April");
                float may = rs.getFloat("May");
                float june = rs.getFloat("June");
                float july = rs.getFloat("July");
                float august = rs.getFloat("August");
                float september = rs.getFloat("September");
                float october = rs.getFloat("October");
                float november = rs.getFloat("November");
                float december = rs.getFloat("December");
                float january = rs.getFloat("January");
                float february = rs.getFloat("February");
                float march = rs.getFloat("March");
                long total = 0;
                total = (long) (april + may + june + july + august + september + october + november + december + january + february + march);

                Object tblData[] = {srNo, ppoNo, recordDate, pensionerName, pensionIssueDate, pensionType, year, april, may, june, july, august, september, october, november, december, january, february, march, total};
                defaultTableModel = (DefaultTableModel) appTable.getModel();
                defaultTableModel.addRow(tblData);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database exception occured" + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    private void fetchSearchByDateData(String query) {
        try {
            DefaultTableModel model = (DefaultTableModel) appTable.getModel();
            model.setRowCount(0);
            Statement stmt = config.conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int rows = 0;
            while (rs.next()) {
                rows++;
                int srNo = rows;
                String ppoNo = rs.getString("PPONo");
                Date recordDate = rs.getDate("DateOfRecord");
                String pensionerName = rs.getString("PensionerName");
                String pensionIssueDate = rs.getString("PensionIssueDate");
                String pensionType = rs.getString("PensionType");
                String year = rs.getString("Year");
                float april = rs.getFloat("April");
                float may = rs.getFloat("May");
                float june = rs.getFloat("June");
                float july = rs.getFloat("July");
                float august = rs.getFloat("August");
                float september = rs.getFloat("September");
                float october = rs.getFloat("October");
                float november = rs.getFloat("November");
                float december = rs.getFloat("December");
                float january = rs.getFloat("January");
                float february = rs.getFloat("February");
                float march = rs.getFloat("March");
                long total = 0;
                total = (long) (april + may + june + july + august + september + october + november + december + january + february + march);
                Object tblData[] = {srNo, ppoNo, recordDate, pensionerName, pensionIssueDate, pensionType, year, april, may, june, july, august, september, october, november, december, january, february, march,total};
                defaultTableModel = (DefaultTableModel) appTable.getModel();
                defaultTableModel.addRow(tblData);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database exception occured" + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void fetchSearchData(String query) {
        try {
            DefaultTableModel model = (DefaultTableModel) appTable.getModel();
            model.setRowCount(0);
            Statement stmt = config.conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int rows = 0;
            if (rs.next()) {
                rows++;
                int srNo = rows;
                String ppoNo = rs.getString("PPONo");
                Date recordDate = rs.getDate("DateOfRecord");
                String pensionerName = rs.getString("PensionerName");
                String pensionIssueDate = rs.getString("PensionIssueDate");
                String pensionType = rs.getString("PensionType");
                String year = rs.getString("Year");
                float april = rs.getFloat("April");
                float may = rs.getFloat("May");
                float june = rs.getFloat("June");
                float july = rs.getFloat("July");
                float august = rs.getFloat("August");
                float september = rs.getFloat("September");
                float october = rs.getFloat("October");
                float november = rs.getFloat("November");
                float december = rs.getFloat("December");
                float january = rs.getFloat("January");
                float february = rs.getFloat("February");
                float march = rs.getFloat("March");
                long total = 0;
                total = (long) (april + may + june + july + august + september + october + november + december + january + february + march);
                Object tblData[] = {srNo, ppoNo, recordDate, pensionerName, pensionIssueDate, pensionType, year, april, may, june, july, august, september, october, november, december, january, february, march,total};
                defaultTableModel = (DefaultTableModel) appTable.getModel();
                defaultTableModel.addRow(tblData);
                JOptionPane.showMessageDialog(null, "Record found", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "No record found", "Pension Record System", JOptionPane.ERROR_MESSAGE);
                fetchData(globalSelectQuery);
                searchByPPOTxt.setText("");
                searchByNameTxt.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database exception occured" + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

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
        jScrollPane1 = new javax.swing.JScrollPane();
        appTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        searchByPPOTxt = new javax.swing.JTextField();
        searchByPPOBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        fromDate = new com.toedter.calendar.JDateChooser();
        toDate = new com.toedter.calendar.JDateChooser();
        searchByDateBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        searchByNameTxt = new javax.swing.JTextField();
        searchByNameBtn = new javax.swing.JButton();
        reloadData = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        exportAll = new javax.swing.JButton();
        exportSelected = new javax.swing.JButton();
        closeBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("APP9");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        appTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        appTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Sr. No.", "PPO No.", "Date of Record", "Pensioner Name", "Pension issue Date", "Pension Type", "Year", "April", "May", "June", "July", "August", "September", "October", "November", "December", "January", "February", "March", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        appTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(appTable);

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Search By PPO No.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        searchByPPOTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        searchByPPOBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchByPPOBtn.setText("Search");
        searchByPPOBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByPPOBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchByPPOTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(searchByPPOBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchByPPOTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchByPPOBtn))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Search From Date - To Date ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        fromDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        toDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        searchByDateBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchByDateBtn.setText("Search");
        searchByDateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByDateBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("From :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("To :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(searchByDateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchByDateBtn)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
        );

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Search By Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        searchByNameTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        searchByNameBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchByNameBtn.setText("Search");
        searchByNameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByNameBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchByNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(searchByNameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchByNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchByNameBtn))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        reloadData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        reloadData.setText("Reload Data");
        reloadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadDataActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(0, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        saveBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        exportAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        exportAll.setText("Export All Data to Excel");
        exportAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportAllActionPerformed(evt);
            }
        });

        exportSelected.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        exportSelected.setText("Export Selected Data Date wise To Excel");
        exportSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportSelectedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(saveBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportSelected, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(exportAll, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exportSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        closeBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        closeBtn.setText("Close");
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Note* : Edit the data of months in the below table itself and then click on save button.");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 51, 51));
        jLabel4.setText("*Don't forget to click on save button after editing the data or making any changes in the records itself.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 763, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(reloadData, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reloadData, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void searchByPPOBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByPPOBtnActionPerformed
        // TODO add your handling code here:
        String ppoNoSearch = searchByPPOTxt.getText().toString();
        String ppoSearchQuery = "select * from dbo.app9Table where PPONo='" + ppoNoSearch + "';";
        fetchSearchData(ppoSearchQuery);
    }//GEN-LAST:event_searchByPPOBtnActionPerformed

    private void reloadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadDataActionPerformed
        // TODO add your handling code here:
        fetchData(globalSelectQuery);
        searchByNameTxt.setText("");
        searchByPPOTxt.setText("");
        fromDate.setDate(null);
        toDate.setDate(null);
    }//GEN-LAST:event_reloadDataActionPerformed

    private void searchByDateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByDateBtnActionPerformed
        // TODO add your handling code here:
        String fromDateString = toDataBaseDate.format(fromDate.getDate());
        String toDateString = toDataBaseDate.format(toDate.getDate());
        dateSearchQuery = "SELECT * FROM dbo.app9Table WHERE DateOfRecord BETWEEN '" + fromDateString + "' AND '" + toDateString + "';";
        fetchSearchByDateData(dateSearchQuery);

    }//GEN-LAST:event_searchByDateBtnActionPerformed

    private void searchByNameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByNameBtnActionPerformed
        // TODO add your handling code here:
        String pensionNameSearch = searchByNameTxt.getText().toString();
        String pensionNameSearchQuery = "select * from dbo.app9Table where PensionerName='" + pensionNameSearch + "';";
        fetchData(pensionNameSearchQuery);
    }//GEN-LAST:event_searchByNameBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:

        Statement stmtForSave;
        DefaultTableModel model = (DefaultTableModel) appTable.getModel();
        try {

            stmtForSave = config.conn.createStatement();
            for (int i = 0; i < model.getRowCount(); i++) {
                String ppoNo = model.getValueAt(i, 1).toString();
                String year = model.getValueAt(i, 6).toString();
                float april = Float.parseFloat(model.getValueAt(i, 7).toString());
                float may = Float.parseFloat(model.getValueAt(i, 8).toString());
                float june = Float.parseFloat(model.getValueAt(i, 9).toString());
                float july = Float.parseFloat(model.getValueAt(i, 10).toString());
                float august = Float.parseFloat(model.getValueAt(i, 11).toString());
                float september = Float.parseFloat(model.getValueAt(i, 12).toString());
                float october = Float.parseFloat(model.getValueAt(i, 13).toString());
                float november = Float.parseFloat(model.getValueAt(i, 14).toString());
                float december = Float.parseFloat(model.getValueAt(i, 15).toString());
                float january = Float.parseFloat(model.getValueAt(i, 16).toString());
                float february = Float.parseFloat(model.getValueAt(i, 17).toString());
                float march = Float.parseFloat(model.getValueAt(i, 18).toString());

                String updateQuery = "UPDATE dbo.app9Table SET Year='" + year + "',April='" + april + "',May='" + may + "',June='" + june + "',July='" + july + "',August='" + august + "',September='" + september + "',October='" + october + "',November='" + november + "',December='" + december + "',January='" + january + "',February='" + february + "',March='" + march + "' WHERE PPONo='" + ppoNo + "';";

                stmtForSave.addBatch(updateQuery);

            }
            int updateRow[] = stmtForSave.executeBatch();
            if (updateRow.length >= 0) {
                JOptionPane.showMessageDialog(null, "Data Saved successfully", "Pension Record System", JOptionPane.INFORMATION_MESSAGE);
                fetchData(globalSelectQuery);
            } else {
                JOptionPane.showMessageDialog(null, "Data not saved, some error occured!", "Pension Record System", JOptionPane.ERROR_MESSAGE);
                fetchData(globalSelectQuery);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Exception occured : " + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            fetchData(globalSelectQuery);
        } catch (NullPointerException ex2) {
            JOptionPane.showMessageDialog(null, "Please enter the year and click Enter!", "Pension Record System", JOptionPane.ERROR_MESSAGE);
            ex2.printStackTrace();
            fetchData(globalSelectQuery);
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeBtnActionPerformed

    private void exportAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportAllActionPerformed
        // TODO add your handling code here:
        fetchData(globalSelectQuery);
        exportToExcel(appTable);

    }//GEN-LAST:event_exportAllActionPerformed

    private void exportSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportSelectedActionPerformed
        // TODO add your handling code here:
        exportToExcel(appTable);
    }//GEN-LAST:event_exportSelectedActionPerformed

    private void exportToExcel(JTable jt) {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(jt);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("APP9");
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

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Exception occured : " + ex.toString(), "Pension Record System", JOptionPane.ERROR_MESSAGE);

        }
    }

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
            java.util.logging.Logger.getLogger(APP9.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(APP9.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(APP9.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(APP9.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new APP9().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable appTable;
    private javax.swing.JButton closeBtn;
    private javax.swing.JButton exportAll;
    private javax.swing.JButton exportSelected;
    private com.toedter.calendar.JDateChooser fromDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton reloadData;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton searchByDateBtn;
    private javax.swing.JButton searchByNameBtn;
    private javax.swing.JTextField searchByNameTxt;
    private javax.swing.JButton searchByPPOBtn;
    private javax.swing.JTextField searchByPPOTxt;
    private com.toedter.calendar.JDateChooser toDate;
    // End of variables declaration//GEN-END:variables

}
