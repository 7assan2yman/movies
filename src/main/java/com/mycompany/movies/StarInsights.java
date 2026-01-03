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
public class StarInsights extends javax.swing.JFrame {

    private String actorName;
    private DatabaseConnection db;
    /**
     * Creates new form StarInsights
     */
    public StarInsights(String actorName) {
        this.actorName = actorName;
        initComponents();
        initializeDatabase();
        setupTables();
        loadStarInsights();
    }
    public StarInsights() {
        initComponents();
        initializeDatabase();
        setupTables();
        current_star_label.setText("Star Insights - No Actor Selected");
        showInstructionMessage();
    }
    private void showInstructionMessage() {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Please search for an actor in the main window and click 'Star Insights'.", 
            "No Actor Selected", 
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
        // Setup top 5 films table
        String[] filmColumns = {"Title", "Year", "Rating", "Director"};
        top_five_films_tbl.setModel(new DefaultTableModel(filmColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // Setup top 5 years table
        String[] yearColumns = {"Year", "Film Count"};
        top_five_years_tbl.setModel(new DefaultTableModel(yearColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
    private void loadStarInsights() {
        current_star_label.setText("Actor: " + actorName);
        
        try {
            // Load basic info
            loadBasicInfo(actorName);
            
            // Load top 5 films
            loadTopFiveFilms(actorName);
            
            // Load top 5 years
            loadTopFiveYears(actorName);
            
            // Load collaborations
            loadCollaborations(actorName);
            
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error loading star insights: " + e.getMessage(), "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadBasicInfo(String actorName) throws SQLException {
        // Get birth year
        Integer birthYear = db.getActorBirthYear(actorName);
        birth_year_label.setText(birthYear != null ? birthYear.toString() : "N/A");
        
        // Get career span
        String careerSpan = db.getActorCareerSpan(actorName);
        first_last_film_label.setText(careerSpan != null ? careerSpan : "N/A");
        
        // Get total film count
        int filmCount = db.getActorFilmCount(actorName);
        no_of_films_label.setText(String.valueOf(filmCount));
        
        // Get average rating
        double avgRating = db.getActorAverageRating(actorName);
        avg_rating_label.setText(String.format("%.1f", avgRating));
    }

    private void loadTopFiveFilms(String actorName) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) top_five_films_tbl.getModel();
        model.setRowCount(0);
        
        List<Object[]> topFilms = db.getActorTopFilms(actorName, 5);
        for (Object[] film : topFilms) {
            model.addRow(film);
        }
    }

    private void loadTopFiveYears(String actorName) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) top_five_years_tbl.getModel();
        model.setRowCount(0);
        
        List<Object[]> topYears = db.getActorTopYears(actorName, 5);
        for (Object[] year : topYears) {
            model.addRow(year);
        }
    }

    private void loadCollaborations(String actorName) throws SQLException {
        // Get favorite director
        String favDirector = db.getActorFavoriteDirector(actorName);
        fav_director_label.setText(favDirector != null ? favDirector : "N/A");
        
        // Get favorite co-star
        String favCoStar = db.getActorFavoriteCoStar(actorName);
        fav_costar_label.setText(favCoStar != null ? favCoStar : "N/A");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        current_star_label = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        top_five_years_tbl = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        top_five_films_tbl = new javax.swing.JTable();
        birth_year_label = new javax.swing.JLabel();
        first_last_film_label = new javax.swing.JLabel();
        no_of_films_label = new javax.swing.JLabel();
        avg_rating_label = new javax.swing.JLabel();
        fav_director_label = new javax.swing.JLabel();
        fav_costar_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Birth Year:");

        current_star_label.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        current_star_label.setText("Star");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("First/Last Film:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("# of films:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Avg rating:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Fav Director:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Fav Co-star:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Top five years");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Top five films");

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
        jScrollPane1.setViewportView(top_five_years_tbl);

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
        jScrollPane2.setViewportView(top_five_films_tbl);

        birth_year_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        birth_year_label.setText("X");

        first_last_film_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        first_last_film_label.setText("X");

        no_of_films_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        no_of_films_label.setText("X");

        avg_rating_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        avg_rating_label.setText("X");

        fav_director_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        fav_director_label.setText("X");

        fav_costar_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        fav_costar_label.setText("X");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(145, 145, 145))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(first_last_film_label))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(birth_year_label))
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
                                .addComponent(fav_director_label))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fav_costar_label))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(392, 392, 392)
                        .addComponent(current_star_label)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 21, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(birth_year_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(first_last_film_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(fav_director_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(fav_costar_label))
                .addGap(56, 56, 56)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(current_star_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
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
            java.util.logging.Logger.getLogger(StarInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StarInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StarInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StarInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StarInsights().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avg_rating_label;
    private javax.swing.JLabel birth_year_label;
    private javax.swing.JLabel current_star_label;
    private javax.swing.JLabel fav_costar_label;
    private javax.swing.JLabel fav_director_label;
    private javax.swing.JLabel first_last_film_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel no_of_films_label;
    private javax.swing.JTable top_five_films_tbl;
    private javax.swing.JTable top_five_years_tbl;
    // End of variables declaration//GEN-END:variables
}
