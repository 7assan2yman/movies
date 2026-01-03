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
public class YearInsights extends javax.swing.JFrame {

    /**
     * Creates new form YearInsights
     */
    private int selectedYear;
    private DatabaseConnection db;
    
    public YearInsights(int year) {
        this.selectedYear = year;
        initComponents();
        initializeDatabase();
        setupTables();
        loadYearInsights();
    }
    
    public YearInsights() {
        initComponents();
        initializeDatabase();
        setupTables();
        selected_year_label.setText("Year Insights - No Year Selected");
        showInstructionMessage();
    }
    private void showInstructionMessage() {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Please search for a year in the main window and click 'Year Insights'.", 
            "No Year Selected", 
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
        String[] filmColumns = {"Title", "Rating", "Votes", "Director"};
        top_films_table.setModel(new DefaultTableModel(filmColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        String[] actorColumns = {"Actor", "Films Count"};
        top_actors_tbl1.setModel(new DefaultTableModel(actorColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        String[] directorColumns = {"Director", "Films Count"};
        top_directors_tbl.setModel(new DefaultTableModel(directorColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void loadYearInsights() {
        selected_year_label.setText("Year: " + selectedYear);
        
        try {
            loadBasicStats(selectedYear);
            loadTopFilms(selectedYear);
            loadTopActors(selectedYear);
            loadTopDirectors(selectedYear);
            loadBirthStats(selectedYear);
            
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error loading year insights: " + e.getMessage(), "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadBasicStats(int year) throws SQLException {
        int filmCount = db.getFilmCountByYear(year);
        no_of_films_label.setText(String.valueOf(filmCount));
        
        double avgRating = db.getAverageRatingByYear(year);
        avg_rating_label.setText(String.format("%.1f", avgRating));
        
        int totalVotes = db.getTotalVotesByYear(year);
        total_votes_label.setText(formatNumber(totalVotes));
        
        String highestRated = db.getHighestRatedFilmByYear(year);
        highest_rated_film.setText(highestRated != null ? highestRated : "N/A");
    }

    private void loadTopFilms(int year) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) top_films_table.getModel();
        model.setRowCount(0);
        
        List<Object[]> topFilms = db.getTopFilmsByYear(year, 5);
        for (Object[] film : topFilms) {
            model.addRow(film);
        }
    }

    private void loadTopActors(int year) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) top_actors_tbl1.getModel();
        model.setRowCount(0);
        
        List<Object[]> topActors = db.getTopActorsByYear(year, 5);
        for (Object[] actor : topActors) {
            model.addRow(actor);
        }
    }

    private void loadTopDirectors(int year) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) top_directors_tbl.getModel();
        model.setRowCount(0);
        
        List<Object[]> topDirectors = db.getTopDirectorsByYear(year, 5);
        for (Object[] director : topDirectors) {
            model.addRow(director);
        }
    }

    private void loadBirthStats(int year) throws SQLException {
        int actorsBorn = db.getActorsBornInYear(year);
        no_of_actors_born_label.setText(String.valueOf(actorsBorn));
        
        int directorsBorn = db.getDirectorsBornInYear(year);
        no_of_directors_born.setText(String.valueOf(directorsBorn));
    }

    private String formatNumber(int number) {
        if (number >= 1000000) {
            return String.format("%.1fM", number / 1000000.0);
        } else if (number >= 1000) {
            return String.format("%.1fK", number / 1000.0);
        }
        return String.valueOf(number);
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
        no_of_films_label = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        avg_rating_label = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        total_votes_label = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        highest_rated_film = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        top_films_table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        top_directors_tbl = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        top_actors_tbl1 = new javax.swing.JTable();
        no_of_actors_born_label = new javax.swing.JLabel();
        no_of_directors_born = new javax.swing.JLabel();
        selected_year_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("# of filmes released:");

        no_of_films_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        no_of_films_label.setText("X");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Avg rating:");

        avg_rating_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        avg_rating_label.setText("X");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Total Votes:");

        total_votes_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        total_votes_label.setText("X");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Hightest Rated Film:");

        highest_rated_film.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        highest_rated_film.setText("X");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Number of actors born:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Number of directors born:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Top five films");

        top_films_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Films"
            }
        ));
        jScrollPane1.setViewportView(top_films_table);

        top_directors_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Actors"
            }
        ));
        jScrollPane2.setViewportView(top_directors_tbl);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Top five actors");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Top five directors");

        top_actors_tbl1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Actors"
            }
        ));
        jScrollPane3.setViewportView(top_actors_tbl1);

        no_of_actors_born_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        no_of_actors_born_label.setText("X");

        no_of_directors_born.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        no_of_directors_born.setText("X");

        selected_year_label.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        selected_year_label.setText("Year");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(no_of_directors_born))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(no_of_actors_born_label))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(highest_rated_film))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(total_votes_label))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(avg_rating_label))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(no_of_films_label)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 296, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(62, 62, 62)))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(58, 58, 58)))
                        .addGap(22, 22, 22))))
            .addGroup(layout.createSequentialGroup()
                .addGap(429, 429, 429)
                .addComponent(selected_year_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(selected_year_label)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(no_of_films_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(avg_rating_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(total_votes_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(highest_rated_film))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(no_of_actors_born_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(no_of_directors_born))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(YearInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YearInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YearInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YearInsights.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new YearInsights().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avg_rating_label;
    private javax.swing.JLabel highest_rated_film;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel no_of_actors_born_label;
    private javax.swing.JLabel no_of_directors_born;
    private javax.swing.JLabel no_of_films_label;
    private javax.swing.JLabel selected_year_label;
    private javax.swing.JTable top_actors_tbl1;
    private javax.swing.JTable top_directors_tbl;
    private javax.swing.JTable top_films_table;
    private javax.swing.JLabel total_votes_label;
    // End of variables declaration//GEN-END:variables
}
