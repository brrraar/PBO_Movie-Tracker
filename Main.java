public class Main {
    public static void main(String[] args) {
        try {
            // Uji koneksi ke database
            if (DatabaseConnection.getConnection() != null) {
                System.out.println("Berhasil terhubung ke database.");
            }
        } catch (Exception e) {
            System.out.println("Gagal terhubung ke database: " + e.getMessage());
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Tidak dapat terhubung ke database:\n" + e.getMessage(),
                    "Koneksi Gagal", javax.swing.JOptionPane.ERROR_MESSAGE);
            return; // keluar agar LoginFrame tidak muncul jika koneksi gagal
        }

        javax.swing.SwingUtilities.invokeLater(LoginFrame::new);
    }
}
