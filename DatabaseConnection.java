import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/film_tracker?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       // sesuaikan username MySQL kamu
    private static final String PASSWORD = "";       // sesuaikan password MySQL kamu, biasanya kosong jika pakai XAMPP

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
