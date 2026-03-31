# Movie Database Application

A desktop Java application for browsing and analyzing movie data. Built with **Java Swing** for the GUI, **SQLite** for the database, and **Maven** for dependency management. The application allows users to search movies by various criteria and view detailed insights about years, actors, and directors.

---

## Features

### 🔍 Movie Search
- Search movies by **Title**, **Year**, **Rating**, **Votes**, **Director**, or **Star**.
- Results displayed in a sortable table with columns: Title, Year, Rating, Votes, Directors, Stars.
- Dynamic table – scroll through unlimited results.

### 📊 Year Insights
- View detailed statistics for a specific year:
  - Number of films released
  - Average rating and total votes
  - Highest rated film of the year
  - Number of actors and directors born that year
  - Top 5 films (by rating)
  - Top 5 actors (by number of films)
  - Top 5 directors (by number of films)

### 🌟 Star (Actor) Insights
- Select an actor to see:
  - Birth year, career span, total films, average rating
  - Top 5 films (by rating) with director details
  - Top 5 years (by number of films)
  - Favorite director (most collaborations)
  - Favorite co-star (most collaborations)

### 🎬 Director Insights
- Select a director to see:
  - Birth year, career span, total films, average rating
  - Top 5 films (by rating)
  - Top 5 years (by number of films directed)
  - Favorite actor (most collaborations)

### 📈 Database Overview (Optional)
- Total movies, actors, directors, year range
- Highest rated / most voted movies
- Most prolific actor/director

---

## Database Schema

The project uses an SQLite database with the following tables:

```sql
movies(id, title, year)
ratings(movie_id, rating, votes)
people(id, name, birth)
directors(movie_id, person_id)
stars(movie_id, person_id)

