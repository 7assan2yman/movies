/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.movies;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;
/**
 *
 * @author hasssan
 */
public class DirectorInsights extends javax.swing.JFrame {

    private String directorName;
    private DatabaseConnection db;
    /**
     * Creates new form DirectorInsights
     */
    public DirectorInsights(String directorName) {
        this.directorName = directorName;
        initComponents();
        initializeDatabase();
        setupTables();
        loadDirectorInsights();
    }
    public DirectorInsights() {
        initComponents();
        initializeDatabase();
        setupTables();
        current_director.setText("Director Insights - No Director Selected");
        showInstructionMessage();
    }
    private void showInstructionMessage() {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Please search for a director in the main window and click 'Director Insights'.", 
            "No Director Selected", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void initializeDatabase() {
        try {
            db = new DatabaseConnection();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Database connection failed: " + e.getMessage(), "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupTables() {
        // Setup top 5 films table (Title, Year, Rating, Votes)
        String[] filmColumns = {"Title", "Year", "Rating", "Votes"};
        top_five_films_tbl.setModel(new DefaultTableModel(filmColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // Setup top 5 years table (Year, Film Count)
        String[] yearColumns = {"Year", "Film Count"};
        top_five_years_tbl.setModel(new DefaultTableModel(yearColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void loadDirectorInsights() {
        current_director.setText("Director: " + directorName);
        
        try {
            // Load basic info
            loadBasicInfo(directorName);
            
            // Load top 5 films
            loadTopFiveFilms(directorName);
            
            // Load top 5 years
            loadTopFiveYears(directorName);
            
            // Load favorite actor
            loadFavoriteActor(directorName);
            
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error loading director insights: " + e.getMessage(), "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadBasicInfo(String directorName) throws SQLException {
        // Get birth year
        Integer birthYear = db.getDirectorBirthYear(directorName);
        birth_year_label.setText(birthYear != null ? birthYear.toString() : "N/A");
        
        // Get career span
        String careerSpan = db.getDirectorCareerSpan(directorName);
        first_last_film_label.setText(careerSpan != null ? careerSpan : "N/A");
        
        // Get total film count
        int filmCount = db.getDirectorFilmCount(directorName);
        no_of_films_label.setText(String.valueOf(filmCount));
        
        // Get average rating
        double avgRating = db.getDirectorAverageRating(directorName);
        avg_rating_label.setText(String.format("%.1f", avgRating));
    }

    private void loadTopFiveFilms(String directorName) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) top_five_films_tbl.getModel();
        model.setRowCount(0);
        
        List<Object[]> topFilms = db.getDirectorTopFilms(directorName, 5);
        for (Object[] film : topFilms) {
            model.addRow(film);
        }
    }

    private void loadTopFiveYears(String directorName) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) top_five_years_tbl.getModel();
        model.setRowCount(0);
        
        List<Object[]> topYears = db.getDirectorTopYears(directorName, 5);
        for (Object[] year : topYears) {
            model.addRow(year);
        }
    }

    private void loadFavoriteActor(String directorName) throws SQLException {
        // Get favorite actor
        String favActor = db.getDirectorFavoriteActor(directorName);
        fav_actor_label.setText(favActor != null ? favActor : "N/A");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        current_director = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        top_five_years_tbl = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        top_five_films_tbl = new javax.swing.JTable();
        fav_actor_label = new javax.swing.JLabel();
        avg_rating_label = new javax.swing.JLabel();
        no_of_films_label = new javax.swing.JLabel();
        first_last_film_label = new javax.swing.JLabel();
        birth_year_label = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        current_director.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        current_director.setText("Director");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Birth Year:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("First/Last Film:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("# of films:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Avg rating:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Fav Actor:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Top five films");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Top five years");

        top_five_years_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(top_five_years_tbl);

        top_five_films_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(top_five_films_tbl);

        fav_actor_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        fav_actor_label.setText("X");

        avg_rating_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        avg_rating_label.setText("X");

        no_of_films_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        no_of_films_label.setText("X");

        first_last_film_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        first_last_film_label.setText("X");

        birth_year_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        birth_year_label.setText("X");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(161, 161, 161))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(411, 411, 411)
                        .addComponent(current_director))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(birth_year_label))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(first_last_film_label))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(no_of_films_label))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(avg_rating_label))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fav_actor_label)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(current_director)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(birth_year_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(first_last_film_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(no_of_films_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(avg_rating_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(fav_actor_label))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(DirectorInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DirectorInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DirectorInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DirectorInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DirectorInsights().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avg_rating_label;
    private javax.swing.JLabel birth_year_label;
    private javax.swing.JLabel current_director;
    private javax.swing.JLabel fav_actor_label;
    private javax.swing.JLabel first_last_film_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel no_of_films_label;
    private javax.swing.JTable top_five_films_tbl;
    private javax.swing.JTable top_five_years_tbl;
    // End of variables declaration//GEN-END:variables
}
