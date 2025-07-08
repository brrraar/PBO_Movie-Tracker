import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class DetailFilmFrame extends JFrame {
    private final Film film;

    private final JTextField judulField;
    private final JTextField tanggalRilisField;
    private final JTextField genreField;
    private final JTextField karakterField;
    private final JTextField ratingField;
    private final JTextArea komentarArea;

    private final FilmDAO filmDAO = new FilmDAO();

    private final JButton ubahBtn;
    private final JButton hapusBtn;
    private final JButton keluarBtn;

    private final JLabel posterLabel;

    public DetailFilmFrame(MenuFrame parent, Film film) {
        this.film = film;

        setTitle("Detail Film - " + film.judul);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(18, 18, 18));
        setLayout(new BorderLayout());

        Color bgColor = new Color(18, 18, 18);
        Color cardColor = new Color(28, 28, 28);
        Color inputBgColor = new Color(45, 45, 45);
        Color textColor = Color.WHITE;
        Color accentColor = new Color(255, 102, 0);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        add(mainPanel, BorderLayout.CENTER);

        // Panel form kiri
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(cardColor);
        formPanel.setBorder(new EmptyBorder(0, 0, 0, 30));
        mainPanel.add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Judul
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        JLabel judulLabel = new JLabel("Judul:");
        styleLabel(judulLabel, textColor);
        formPanel.add(judulLabel, gbc);

        judulField = new JTextField(film.judul, 30);
        styleTextField(judulField, inputBgColor, textColor, accentColor);
        judulField.setEditable(false);
        gbc.gridx = 1; gbc.weightx = 0.8;
        formPanel.add(judulField, gbc);

        // Tanggal Rilis
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel tanggalLabel = new JLabel("Tanggal Rilis:");
        styleLabel(tanggalLabel, textColor);
        formPanel.add(tanggalLabel, gbc);

        tanggalRilisField = new JTextField(film.tanggalRilis, 30);
        styleTextField(tanggalRilisField, inputBgColor, textColor, accentColor);
        tanggalRilisField.setEditable(false);
        gbc.gridx = 1;
        formPanel.add(tanggalRilisField, gbc);

        // Genre
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel genreLabel = new JLabel("Genre:");
        styleLabel(genreLabel, textColor);
        formPanel.add(genreLabel, gbc);

        genreField = new JTextField(film.genre, 30);
        styleTextField(genreField, inputBgColor, textColor, accentColor);
        genreField.setEditable(false);
        gbc.gridx = 1;
        formPanel.add(genreField, gbc);

        // Karakter
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel karakterLabel = new JLabel("Karakter:");
        styleLabel(karakterLabel, textColor);
        formPanel.add(karakterLabel, gbc);

        karakterField = new JTextField(film.karakter, 30);
        styleTextField(karakterField, inputBgColor, textColor, accentColor);
        karakterField.setEditable(false);
        gbc.gridx = 1;
        formPanel.add(karakterField, gbc);

        // Rating
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel ratingLabel = new JLabel("Rating (0-10):");
        styleLabel(ratingLabel, textColor);
        formPanel.add(ratingLabel, gbc);

        String ratingText = (film.rating != null) ?
                (film.rating == Math.floor(film.rating) ? String.valueOf(film.rating.intValue()) : String.valueOf(film.rating)) : "";
        ratingField = new JTextField(ratingText, 30);
        styleTextField(ratingField, inputBgColor, textColor, accentColor);
        ratingField.setEditable(false);
        gbc.gridx = 1;
        formPanel.add(ratingField, gbc);

        // Komentar
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel komentarLabel = new JLabel("Komentar:");
        styleLabel(komentarLabel, textColor);
        formPanel.add(komentarLabel, gbc);

        komentarArea = new JTextArea(film.komentar, 7, 30);
        komentarArea.setLineWrap(true);
        komentarArea.setWrapStyleWord(true);
        komentarArea.setBackground(inputBgColor);
        komentarArea.setForeground(textColor);
        komentarArea.setCaretColor(accentColor);
        komentarArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        komentarArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        komentarArea.setEditable(false);
        komentarArea.setFocusable(false);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(komentarArea, gbc);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        ubahBtn = createStyledButton("Ubah", accentColor, textColor);
        hapusBtn = createStyledButton("Hapus", accentColor, textColor);
        keluarBtn = createStyledButton("Keluar", accentColor, textColor);

        buttonPanel.add(ubahBtn);
        buttonPanel.add(hapusBtn);
        buttonPanel.add(keluarBtn);

        ubahBtn.addActionListener(e -> {
            new EditFilmFrame(parent, film, this);
        });

        hapusBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Yakin ingin menghapus film \"" + film.judul + "\"?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = filmDAO.deleteFilm(film.id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Film berhasil dihapus.");
                    parent.updateFilmList();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus film.");
                }
            }
        });

        keluarBtn.addActionListener(e -> dispose());

        // Poster
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(180, 270));
        rightPanel.setBackground(cardColor);
        rightPanel.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(80, 80, 80)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        posterLabel = new JLabel("", SwingConstants.CENTER);
        posterLabel.setPreferredSize(new Dimension(160, 250));
        loadPosterImage();
        rightPanel.add(posterLabel);

        mainPanel.add(rightPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void loadPosterImage() {
        if (film.posterPath != null && !film.posterPath.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(film.posterPath);
                Image img = icon.getImage().getScaledInstance(160, 250, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(img));
                posterLabel.setText("");
            } catch (Exception e) {
                posterLabel.setIcon(null);
                posterLabel.setText("No Image");
                posterLabel.setForeground(Color.LIGHT_GRAY);
                posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
                posterLabel.setVerticalAlignment(SwingConstants.CENTER);
            }
        } else {
            posterLabel.setIcon(null);
            posterLabel.setText("No Image");
            posterLabel.setForeground(Color.LIGHT_GRAY);
            posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            posterLabel.setVerticalAlignment(SwingConstants.CENTER);
        }
    }

    public void refreshData() {
        judulField.setText(film.judul);
        tanggalRilisField.setText(film.tanggalRilis);
        genreField.setText(film.genre);
        karakterField.setText(film.karakter);
        ratingField.setText(film.rating != null ? film.rating.toString() : "");
        komentarArea.setText(film.komentar);
        setTitle("Detail Film - " + film.judul);
        loadPosterImage();
    }

    private void styleLabel(JLabel label, Color fg) {
        label.setForeground(fg);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleTextField(JTextField field, Color bg, Color fg, Color caret) {
        field.setBackground(bg);
        field.setForeground(fg);
        field.setCaretColor(caret);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new EmptyBorder(8, 10, 8, 10));
        field.setOpaque(true);
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.brighter());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }
}
