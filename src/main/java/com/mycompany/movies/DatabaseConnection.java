package com.mycompany.movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:movies.db";
    
    public DatabaseConnection() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
    public List searchMovies(String searchTerm, String searchBy) throws SQLException {
        List movies = new ArrayList();
        String sql = buildSearchQuery(searchBy);
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (!searchTerm.isEmpty()) {
                switch (searchBy) {
                    case "Title":
                        stmt.setString(1, "%" + searchTerm + "%");
                        break;
                    case "Year":
                        try {
                            int year = Integer.parseInt(searchTerm);
                            stmt.setInt(1, year);
                        } catch (NumberFormatException e) {
                            return movies;
                        }
                        break;
                    case "Rating":
                        try {
                            double rating = Double.parseDouble(searchTerm);
                            stmt.setDouble(1, rating);
                        } catch (NumberFormatException e) {
                            return movies;
                        }
                        break;
                    case "Directors":
                        stmt.setString(1, "%" + searchTerm + "%");
                        break;
                    case "Stars":
                        stmt.setString(1, "%" + searchTerm + "%");
                        break;
                    case "Votes":
                        try {
                            int votes = Integer.parseInt(searchTerm);
                            stmt.setInt(1, votes);
                        } catch (NumberFormatException e) {
                            return movies;
                        }
                        break;
                    default:
                        stmt.setString(1, "%" + searchTerm + "%");
                }
            } else {
                stmt.setString(1, "%");
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Movie movie = new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("year"),
                    rs.getDouble("rating"),
                    rs.getInt("votes"),
                    rs.getString("directors"),
                    rs.getString("stars")
                );
                movies.add(movie);
            }
        }
        return movies;
    }
    
    private String buildSearchQuery(String searchBy) {
        String whereClause = "";
        
        switch (searchBy) {
            case "Title": whereClause = "WHERE m.title LIKE ?"; break;
            case "Year": whereClause = "WHERE m.year = ?"; break;
            case "Rating": whereClause = "WHERE r.rating >= ?"; break;
            case "Directors": whereClause = "WHERE d.name LIKE ?"; break;
            case "Stars": whereClause = "WHERE s.name LIKE ?"; break;
            case "Votes": whereClause = "WHERE r.votes >= ?"; break;
            default: whereClause = "WHERE m.title LIKE ?";
        }
        
        return "SELECT m.id, m.title, m.year, r.rating, r.votes, " +
               "GROUP_CONCAT(DISTINCT d.name) as directors, " +
               "GROUP_CONCAT(DISTINCT s.name) as stars " +
               "FROM movies m " +
               "JOIN ratings r ON m.id = r.movie_id " +
               "JOIN directors dir ON m.id = dir.movie_id " +
               "JOIN people d ON dir.person_id = d.id " +
               "JOIN stars st ON m.id = st.movie_id " +
               "JOIN people s ON st.person_id = s.id " +
               whereClause + " " +
               "GROUP BY m.id " +
               "ORDER BY r.rating DESC";
    }
    
    public List getAllMovies() throws SQLException {
        List movies = new ArrayList();
        String sql = "SELECT m.id, m.title, m.year, r.rating, r.votes, " +
                     "GROUP_CONCAT(DISTINCT d.name) as directors, " +
                     "GROUP_CONCAT(DISTINCT s.name) as stars " +
                     "FROM movies m " +
                     "JOIN ratings r ON m.id = r.movie_id " +
                     "JOIN directors dir ON m.id = dir.movie_id " +
                     "JOIN people d ON dir.person_id = d.id " +
                     "JOIN stars st ON m.id = st.movie_id " +
                     "JOIN people s ON st.person_id = s.id " +
                     "GROUP BY m.id " +
                     "ORDER BY r.rating DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Movie movie = new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("year"),
                    rs.getDouble("rating"),
                    rs.getInt("votes"),
                    rs.getString("directors"),
                    rs.getString("stars")
                );
                movies.add(movie);
            }
        }
        return movies;
    }
    
    /*public List<Integer> getDistinctYears() throws SQLException {
        List<Integer> years = new ArrayList<>();
        String sql = "SELECT DISTINCT year FROM movies ORDER BY year DESC";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
        }
        return years;
    }*/
    
    public int getFilmCountByYear(int year) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM movies WHERE year = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }
    
    public double getAverageRatingByYear(int year) throws SQLException {
        String sql = "SELECT AVG(r.rating) as avg_rating " +
                     "FROM movies m JOIN ratings r ON m.id = r.movie_id " +
                     "WHERE m.year = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        }
        return 0.0;
    }
    
    public int getTotalVotesByYear(int year) throws SQLException {
        String sql = "SELECT SUM(r.votes) as total_votes " +
                     "FROM movies m JOIN ratings r ON m.id = r.movie_id " +
                     "WHERE m.year = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total_votes");
            }
        }
        return 0;
    }
    
    public String getHighestRatedFilmByYear(int year) throws SQLException {
        String sql = "SELECT m.title, r.rating " +
                     "FROM movies m JOIN ratings r ON m.id = r.movie_id " +
                     "WHERE m.year = ? " +
                     "ORDER BY r.rating DESC LIMIT 1";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("title") + " (" + rs.getDouble("rating") + ")";
            }
        }
        return null;
    }
    
    public List<Object[]> getTopFilmsByYear(int year, int limit) throws SQLException {
        List<Object[]> films = new ArrayList<>();
        String sql = "SELECT m.title, r.rating, r.votes, " +
                     "GROUP_CONCAT(DISTINCT d.name) as directors " +
                     "FROM movies m " +
                     "JOIN ratings r ON m.id = r.movie_id " +
                     "JOIN directors dir ON m.id = dir.movie_id " +
                     "JOIN people d ON dir.person_id = d.id " +
                     "WHERE m.year = ? " +
                     "GROUP BY m.id " +
                     "ORDER BY r.rating DESC " +
                     "LIMIT ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, year);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                films.add(new Object[]{
                    rs.getString("title"),
                    rs.getDouble("rating"),
                    rs.getInt("votes"),
                    rs.getString("directors")
                });
            }
        }
        return films;
    }
    
    public List<Object[]> getTopActorsByYear(int year, int limit) throws SQLException {
        List<Object[]> actors = new ArrayList<>();
        String sql = "SELECT p.name, COUNT(*) as film_count " +
                     "FROM stars s " +
                     "JOIN movies m ON s.movie_id = m.id " +
                     "JOIN people p ON s.person_id = p.id " +
                     "WHERE m.year = ? " +
                     "GROUP BY p.id " +
                     "ORDER BY film_count DESC " +
                     "LIMIT ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, year);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                actors.add(new Object[]{
                    rs.getString("name"),
                    rs.getInt("film_count")
                });
            }
        }
        return actors;
    }
    
    public List<Object[]> getTopDirectorsByYear(int year, int limit) throws SQLException {
        List<Object[]> directors = new ArrayList<>();
        String sql = "SELECT p.name, COUNT(*) as film_count " +
                     "FROM directors d " +
                     "JOIN movies m ON d.movie_id = m.id " +
                     "JOIN people p ON d.person_id = p.id " +
                     "WHERE m.year = ? " +
                     "GROUP BY p.id " +
                     "ORDER BY film_count DESC " +
                     "LIMIT ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, year);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                directors.add(new Object[]{
                    rs.getString("name"),
                    rs.getInt("film_count")
                });
            }
        }
        return directors;
    }
    
    public int getActorsBornInYear(int year) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT p.id) as count " +
                     "FROM people p " +
                     "JOIN stars s ON p.id = s.person_id " +
                     "WHERE p.birth = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }
    
    public int getDirectorsBornInYear(int year) throws SQLException 
    
    {
        String sql = "SELECT COUNT(DISTINCT p.id) as count " +
                     "FROM people p " +
                     "JOIN directors d ON p.id = d.person_id " +
                     "WHERE p.birth = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }
    public Integer getActorBirthYear(String actorName) throws SQLException {
    String sql = "SELECT birth FROM people WHERE name = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, actorName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("birth");
        }
    }
    return null;
}

    public String getActorCareerSpan(String actorName) throws SQLException {
        String sql = "SELECT MIN(m.year) as first_year, MAX(m.year) as last_year " +
                     "FROM stars s " +
                     "JOIN movies m ON s.movie_id = m.id " +
                     "JOIN people p ON s.person_id = p.id " +
                     "WHERE p.name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, actorName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int firstYear = rs.getInt("first_year");
                int lastYear = rs.getInt("last_year");
                return firstYear + " - " + lastYear;
            }
        }
        return null;
    }

    public int getActorFilmCount(String actorName) throws SQLException {
        String sql = "SELECT COUNT(*) as film_count " +
                     "FROM stars s " +
                     "JOIN people p ON s.person_id = p.id " +
                     "WHERE p.name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, actorName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("film_count");
            }
        }
        return 0;
    }

    public double getActorAverageRating(String actorName) throws SQLException {
        String sql = "SELECT AVG(r.rating) as avg_rating " +
                     "FROM stars s " +
                     "JOIN movies m ON s.movie_id = m.id " +
                     "JOIN ratings r ON m.id = r.movie_id " +
                     "JOIN people p ON s.person_id = p.id " +
                     "WHERE p.name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, actorName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        }
        return 0.0;
    }

    public List<Object[]> getActorTopFilms(String actorName, int limit) throws SQLException {
        List<Object[]> films = new ArrayList<>();
        String sql = "SELECT m.title, m.year, r.rating, " +
                     "GROUP_CONCAT(DISTINCT d.name) as directors " +
                     "FROM stars s " +
                     "JOIN movies m ON s.movie_id = m.id " +
                     "JOIN ratings r ON m.id = r.movie_id " +
                     "JOIN directors dir ON m.id = dir.movie_id " +
                     "JOIN people d ON dir.person_id = d.id " +
                     "JOIN people p ON s.person_id = p.id " +
                     "WHERE p.name = ? " +
                     "GROUP BY m.id " +
                     "ORDER BY r.rating DESC " +
                     "LIMIT ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, actorName);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                films.add(new Object[]{
                    rs.getString("title"),
                    rs.getInt("year"),
                    rs.getDouble("rating"),
                    rs.getString("directors")
                });
            }
        }
        return films;
    }

    public List<Object[]> getActorTopYears(String actorName, int limit) throws SQLException {
        List<Object[]> years = new ArrayList<>();
        String sql = "SELECT m.year, COUNT(*) as film_count " +
                     "FROM stars s " +
                     "JOIN movies m ON s.movie_id = m.id " +
                     "JOIN people p ON s.person_id = p.id " +
                     "WHERE p.name = ? " +
                     "GROUP BY m.year " +
                     "ORDER BY film_count DESC " +
                     "LIMIT ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, actorName);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                years.add(new Object[]{
                    rs.getInt("year"),
                    rs.getInt("film_count")
                });
            }
        }
        return years;
    }

    public String getActorFavoriteDirector(String actorName) throws SQLException {
        String sql = "SELECT d.name, COUNT(*) as times " +
                     "FROM stars s " +
                     "JOIN movies m ON s.movie_id = m.id " +
                     "JOIN directors dir ON m.id = dir.movie_id " +
                     "JOIN people d ON dir.person_id = d.id " +
                     "JOIN people p ON s.person_id = p.id " +
                     "WHERE p.name = ? " +
                     "GROUP BY d.id " +
                     "ORDER BY times DESC " +
                     "LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, actorName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name") + " (" + rs.getInt("times") + " times)";
            }
        }
        return null;
    }

    public String getActorFavoriteCoStar(String actorName) throws SQLException {
        String sql = "SELECT co.name, COUNT(*) as times " +
                     "FROM stars s1 " +
                     "JOIN people p1 ON s1.person_id = p1.id " +
                     "JOIN stars s2 ON s1.movie_id = s2.movie_id AND s1.person_id != s2.person_id " +
                     "JOIN people co ON s2.person_id = co.id " +
                     "WHERE p1.name = ? " +
                     "GROUP BY co.id " +
                     "ORDER BY times DESC " +
                     "LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, actorName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name") + " (" + rs.getInt("times") + " times)";
            }
        }
        return null;
    }
    
    public Integer getDirectorBirthYear(String directorName) throws SQLException {
    String sql = "SELECT birth FROM people WHERE name = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, directorName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("birth");
        }
    }
    return null;
}

public String getDirectorCareerSpan(String directorName) throws SQLException {
    String sql = "SELECT MIN(m.year) as first_year, MAX(m.year) as last_year " +
                 "FROM directors d " +
                 "JOIN movies m ON d.movie_id = m.id " +
                 "JOIN people p ON d.person_id = p.id " +
                 "WHERE p.name = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, directorName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int firstYear = rs.getInt("first_year");
            int lastYear = rs.getInt("last_year");
            return firstYear + " - " + lastYear;
        }
    }
    return null;
}

public int getDirectorFilmCount(String directorName) throws SQLException {
    String sql = "SELECT COUNT(*) as film_count " +
                 "FROM directors d " +
                 "JOIN people p ON d.person_id = p.id " +
                 "WHERE p.name = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, directorName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("film_count");
        }
    }
    return 0;
}

public double getDirectorAverageRating(String directorName) throws SQLException {
    String sql = "SELECT AVG(r.rating) as avg_rating " +
                 "FROM directors d " +
                 "JOIN movies m ON d.movie_id = m.id " +
                 "JOIN ratings r ON m.id = r.movie_id " +
                 "JOIN people p ON d.person_id = p.id " +
                 "WHERE p.name = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, directorName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("avg_rating");
        }
    }
    return 0.0;
}

public List<Object[]> getDirectorTopFilms(String directorName, int limit) throws SQLException {
    List<Object[]> films = new ArrayList<>();
    String sql = "SELECT m.title, m.year, r.rating, r.votes " +
                 "FROM directors d " +
                 "JOIN movies m ON d.movie_id = m.id " +
                 "JOIN ratings r ON m.id = r.movie_id " +
                 "JOIN people p ON d.person_id = p.id " +
                 "WHERE p.name = ? " +
                 "GROUP BY m.id " +
                 "ORDER BY r.rating DESC " +
                 "LIMIT ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, directorName);
        stmt.setInt(2, limit);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            films.add(new Object[]{
                rs.getString("title"),
                rs.getInt("year"),
                rs.getDouble("rating"),
                rs.getInt("votes")
            });
        }
    }
    return films;
}

public List<Object[]> getDirectorTopYears(String directorName, int limit) throws SQLException {
    List<Object[]> years = new ArrayList<>();
    String sql = "SELECT m.year, COUNT(*) as film_count " +
                 "FROM directors d " +
                 "JOIN movies m ON d.movie_id = m.id " +
                 "JOIN people p ON d.person_id = p.id " +
                 "WHERE p.name = ? " +
                 "GROUP BY m.year " +
                 "ORDER BY film_count DESC " +
                 "LIMIT ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, directorName);
        stmt.setInt(2, limit);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            years.add(new Object[]{
                rs.getInt("year"),
                rs.getInt("film_count")
            });
        }
    }
    return years;
}

public String getDirectorFavoriteActor(String directorName) throws SQLException {
    String sql = "SELECT a.name, COUNT(*) as times " +
                 "FROM directors d " +
                 "JOIN movies m ON d.movie_id = m.id " +
                 "JOIN stars s ON m.id = s.movie_id " +
                 "JOIN people a ON s.person_id = a.id " +
                 "JOIN people p ON d.person_id = p.id " +
                 "WHERE p.name = ? " +
                 "GROUP BY a.id " +
                 "ORDER BY times DESC " +
                 "LIMIT 1";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, directorName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("name") + " (" + rs.getInt("times") + " times)";
        }
    }
    return null;
}
}