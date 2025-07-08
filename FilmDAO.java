import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilmDAO {

    private static final Logger LOGGER = Logger.getLogger(FilmDAO.class.getName());

    // Tambah film baru dengan posterPath
    public boolean addFilm(int userId, String judul, Date tanggalRilis, String genre, String karakter, String komentar, Double rating, String posterPath) {
        String sql = "INSERT INTO films (user_id, judul, tanggal_rilis, genre, karakter, komentar, rating, poster_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, judul);
            stmt.setDate(3, tanggalRilis);
            stmt.setString(4, genre);
            stmt.setString(5, karakter);
            stmt.setString(6, komentar);
            if (rating != null) {
                stmt.setDouble(7, rating);
            } else {
                stmt.setNull(7, Types.DOUBLE);
            }
            if (posterPath != null && !posterPath.isEmpty()) {
                stmt.setString(8, posterPath);
            } else {
                stmt.setNull(8, Types.VARCHAR);
            }

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal menambahkan film", e);
            return false;
        }
    }

    // Ambil semua film milik user tertentu, lengkap dengan posterPath
    public List<Film> getFilmsByUser(int userId) {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM films WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Film film = new Film();
                    film.id = rs.getInt("id");
                    film.userId = rs.getInt("user_id");
                    film.judul = rs.getString("judul");
                    film.tanggalRilis = rs.getDate("tanggal_rilis").toString();
                    film.genre = rs.getString("genre");
                    film.karakter = rs.getString("karakter");
                    film.komentar = rs.getString("komentar");
                    double ratingVal = rs.getDouble("rating");
                    film.rating = rs.wasNull() ? null : ratingVal;
                    film.posterPath = rs.getString("poster_path");
                    films.add(film);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengambil daftar film", e);
        }
        return films;
    }

    // Delete film by id
    public boolean deleteFilm(int filmId) {
        String sql = "DELETE FROM films WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filmId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal menghapus film dengan ID: " + filmId, e);
            return false;
        }
    }

    // Update film lengkap dengan posterPath
    public boolean updateFilm(Film film) {
        String sql = "UPDATE films SET judul = ?, tanggal_rilis = ?, genre = ?, karakter = ?, komentar = ?, rating = ?, poster_path = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, film.judul);
            stmt.setDate(2, Date.valueOf(film.tanggalRilis));
            stmt.setString(3, film.genre);
            stmt.setString(4, film.karakter);
            stmt.setString(5, film.komentar);
            if (film.rating != null) {
                stmt.setDouble(6, film.rating);
            } else {
                stmt.setNull(6, Types.DOUBLE);
            }
            if (film.posterPath != null && !film.posterPath.isEmpty()) {
                stmt.setString(7, film.posterPath);
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }
            stmt.setInt(8, film.id);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal memperbarui film dengan ID: " + film.id, e);
            return false;
        }
    }
}
