import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class EditFilmFrame extends JFrame {
    private final Film film;

    private final JTextField judulField;
    private final JTextField tanggalRilisField;
    private final JTextField genreField;
    private final JTextField karakterField;
    private final JTextField ratingField;
    private final JTextArea komentarArea;

    private final JLabel posterLabel;

    private final FilmDAO filmDAO = new FilmDAO();

    private final Color bgColor = new Color(18, 18, 18);
    private final Color cardColor = new Color(28, 28, 28);
    private final Color inputBgColor = new Color(45, 45, 45);
    private final Color textColor = Color.WHITE;
    private final Color accentColor = new Color(255, 102, 0);

    public EditFilmFrame(MenuFrame parent, Film film, DetailFilmFrame detailFrame) {
        this.film = film;

        setTitle("Edit Film - " + film.judul);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(bgColor);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        add(mainPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(cardColor);
        inputPanel.setBorder(new EmptyBorder(0, 0, 0, 30));
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        JLabel judulLabel = new JLabel("Judul:");
        styleLabel(judulLabel, textColor);
        inputPanel.add(judulLabel, gbc);

        judulField = new JTextField(film.judul, 30);
        styleTextField(judulField, inputBgColor, textColor, accentColor);
        gbc.gridx = 1; gbc.weightx = 0.8;
        inputPanel.add(judulField, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.2;
        JLabel tanggalLabel = new JLabel("Tanggal Rilis:");
        styleLabel(tanggalLabel, textColor);
        inputPanel.add(tanggalLabel, gbc);

        tanggalRilisField = new JTextField(film.tanggalRilis, 30);
        styleTextField(tanggalRilisField, inputBgColor, textColor, accentColor);
        gbc.gridx = 1; gbc.weightx = 0.8;
        inputPanel.add(tanggalRilisField, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.2;
        JLabel genreLabel = new JLabel("Genre:");
        styleLabel(genreLabel, textColor);
        inputPanel.add(genreLabel, gbc);

        genreField = new JTextField(film.genre, 30);
        styleTextField(genreField, inputBgColor, textColor, accentColor);
        gbc.gridx = 1; gbc.weightx = 0.8;
        inputPanel.add(genreField, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.2;
        JLabel karakterLabel = new JLabel("Karakter:");
        styleLabel(karakterLabel, textColor);
        inputPanel.add(karakterLabel, gbc);

        karakterField = new JTextField(film.karakter, 30);
        styleTextField(karakterField, inputBgColor, textColor, accentColor);
        gbc.gridx = 1; gbc.weightx = 0.8;
        inputPanel.add(karakterField, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.2;
        JLabel ratingLabel = new JLabel("Rating (0 - 10):");
        styleLabel(ratingLabel, textColor);
        inputPanel.add(ratingLabel, gbc);

        ratingField = new JTextField(film.rating != null ? film.rating.toString() : "", 30);
        styleTextField(ratingField, inputBgColor, textColor, accentColor);
        gbc.gridx = 1; gbc.weightx = 0.8;
        inputPanel.add(ratingField, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel komentarLabel = new JLabel("Komentar:");
        styleLabel(komentarLabel, textColor);
        inputPanel.add(komentarLabel, gbc);

        komentarArea = new JTextArea(film.komentar, 7, 30);
        komentarArea.setLineWrap(true);
        komentarArea.setWrapStyleWord(true);
        komentarArea.setBackground(inputBgColor);
        komentarArea.setForeground(textColor);
        komentarArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        komentarArea.setCaretColor(accentColor);
        komentarArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        komentarArea.setOpaque(true);
        gbc.gridx = 1; gbc.weightx = 0.8; gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(komentarArea, gbc);

        gbc.gridy++; gbc.gridx = 1; gbc.weightx = 0; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        buttonPanel.setBackground(cardColor);

        final JButton saveBtn = createStyledButton("Save", accentColor, textColor);
        final JButton batalBtn = createStyledButton("Batal", accentColor, textColor);

        buttonPanel.add(saveBtn);
        buttonPanel.add(batalBtn);

        inputPanel.add(buttonPanel, gbc);

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(180, 320));
        rightPanel.setBackground(cardColor);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(80, 80, 80)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(rightPanel, BorderLayout.EAST);

        posterLabel = new JLabel("", SwingConstants.CENTER);
        posterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        posterLabel.setPreferredSize(new Dimension(160, 250));
        posterLabel.setMaximumSize(new Dimension(160, 250));
        loadPosterImage();
        rightPanel.add(posterLabel);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        final JButton gantiGambarBtn = createStyledButton("Ganti Gambar", accentColor, textColor);
        gantiGambarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        gantiGambarBtn.setMaximumSize(new Dimension(160, 40));
        rightPanel.add(gantiGambarBtn);
        rightPanel.add(Box.createVerticalGlue());

        gantiGambarBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Pilih Gambar Poster");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Image files", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                film.posterPath = selectedFile.getAbsolutePath();
                loadPosterImage();
            }
        });

        saveBtn.addActionListener(e -> {
            if (judulField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Judul harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String ratingText = ratingField.getText().trim();
            Double ratingValue = null;
            if (!ratingText.isEmpty()) {
                try {
                    ratingValue = Double.parseDouble(ratingText);
                    if (ratingValue < 0 || ratingValue > 10) {
                        JOptionPane.showMessageDialog(this, "Rating harus antara 0 sampai 10.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Rating harus angka valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            film.judul = judulField.getText().trim();
            film.tanggalRilis = tanggalRilisField.getText().trim();
            film.genre = genreField.getText().trim();
            film.karakter = karakterField.getText().trim();
            film.rating = ratingValue;
            film.komentar = komentarArea.getText().trim();

            if (filmDAO.updateFilm(film)) {
                JOptionPane.showMessageDialog(this, "Film berhasil disimpan!");
                parent.updateFilmList();
                detailFrame.refreshData();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan film.");
            }
        });

        batalBtn.addActionListener(e -> dispose());

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
