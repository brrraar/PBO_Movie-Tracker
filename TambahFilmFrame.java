import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Date;
import java.text.ParseException;

public class TambahFilmFrame extends JFrame {

    private final FilmDAO filmDAO = new FilmDAO();
    private String selectedImagePath = null;
    private JLabel imagePreview;

    public TambahFilmFrame(MenuFrame parent, int userId) {
        setTitle("Tambah Film");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        Color bgColor = new Color(18, 18, 18);
        Color cardColor = new Color(28, 28, 28);
        Color accentColor = new Color(255, 102, 0);
        Color textColor = Color.WHITE;
        Color inputBgColor = new Color(45, 45, 45);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        getContentPane().setBackground(bgColor);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(cardColor);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Judul
        JLabel judulLabel = new JLabel("Judul:");
        judulLabel.setForeground(textColor);
        judulLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        panel.add(judulLabel, gbc);

        JTextField judulField = new JTextField(40);
        styleTextField(judulField, inputBgColor, textColor, fieldFont, accentColor);
        gbc.gridx = 1; gbc.weightx = 0.9;
        panel.add(judulField, gbc);

        // Tanggal Rilis
        JLabel rilisLabel = new JLabel("Tanggal Rilis:");
        rilisLabel.setForeground(textColor);
        rilisLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        panel.add(rilisLabel, gbc);

        DateField rilisField = new DateField();
        gbc.gridx = 1; gbc.weightx = 0.9;
        panel.add(rilisField, gbc);

        // Rating
        JLabel ratingLabel = new JLabel("Rating (0 - 10):");
        ratingLabel.setForeground(textColor);
        ratingLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.1;
        panel.add(ratingLabel, gbc);

        RatingField ratingField = new RatingField();
        gbc.gridx = 1; gbc.weightx = 0.9;
        panel.add(ratingField, gbc);

        // Genre
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setForeground(textColor);
        genreLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.1;
        panel.add(genreLabel, gbc);

        JTextField genreField = new JTextField(40);
        styleTextField(genreField, inputBgColor, textColor, fieldFont, accentColor);
        gbc.gridx = 1; gbc.weightx = 0.9;
        panel.add(genreField, gbc);

        // Karakter
        JLabel karakterLabel = new JLabel("Karakter:");
        karakterLabel.setForeground(textColor);
        karakterLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.1;
        panel.add(karakterLabel, gbc);

        JTextField karakterField = new JTextField(40);
        styleTextField(karakterField, inputBgColor, textColor, fieldFont, accentColor);
        gbc.gridx = 1; gbc.weightx = 0.9;
        panel.add(karakterField, gbc);

        // Komentar
        JLabel komentarLabel = new JLabel("Komentar:");
        komentarLabel.setForeground(textColor);
        komentarLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.1;
        panel.add(komentarLabel, gbc);

        JTextArea komentarArea = new JTextArea(8, 40);
        komentarArea.setLineWrap(true);
        komentarArea.setWrapStyleWord(true);
        komentarArea.setBackground(inputBgColor);
        komentarArea.setForeground(textColor);
        komentarArea.setFont(fieldFont);
        komentarArea.setCaretColor(accentColor);
        komentarArea.setBorder(new EmptyBorder(8, 10, 8, 10));
        gbc.gridx = 1; gbc.weightx = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(komentarArea, gbc);

        // Image Preview
        imagePreview = new JLabel();
        imagePreview.setPreferredSize(new Dimension(140, 190));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 6; gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(imagePreview, gbc);

        add(panel, BorderLayout.CENTER);

        // Panel tombol bawah
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        buttonPanel.setBackground(cardColor);

        JButton pilihPosterBtn = new JButton("Pilih Poster");
        styleButton(pilihPosterBtn, accentColor, textColor, buttonFont);

        JButton simpanBtn = new JButton("Simpan");
        styleButton(simpanBtn, accentColor, textColor, buttonFont);

        JButton keluarBtn = new JButton("Keluar");
        styleButton(keluarBtn, accentColor, textColor, buttonFont);

        buttonPanel.add(pilihPosterBtn);
        buttonPanel.add(simpanBtn);
        buttonPanel.add(keluarBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Event tombol Pilih Poster
        pilihPosterBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "bmp"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath = selectedFile.getAbsolutePath();
                ImageIcon icon = new ImageIcon(selectedImagePath);
                Image scaledImg = icon.getImage().getScaledInstance(140, 190, Image.SCALE_SMOOTH);
                imagePreview.setIcon(new ImageIcon(scaledImg));

                // Buka window baru untuk lihat poster full size
                JFrame imageFrame = new JFrame("Preview Poster");
                imageFrame.setSize(400, 550);
                imageFrame.setLocationRelativeTo(this);
                imageFrame.setResizable(false);
                JLabel fullImageLabel = new JLabel();
                ImageIcon fullIcon = new ImageIcon(selectedImagePath);
                Image fullImg = fullIcon.getImage().getScaledInstance(400, 550, Image.SCALE_SMOOTH);
                fullImageLabel.setIcon(new ImageIcon(fullImg));
                imageFrame.add(fullImageLabel);
                imageFrame.setVisible(true);
            }
        });

        // Event tombol Simpan
        simpanBtn.addActionListener(e -> {
            try {
                String judul = judulField.getText().trim();
                if (judul.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Judul harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                rilisField.validateDate();
                Double ratingValue = null;
                if (!ratingField.getText().trim().isEmpty()) {
                    ratingValue = ratingField.getValidRating();
                }

                boolean success = filmDAO.addFilm(
                        userId,
                        judul,
                        Date.valueOf(rilisField.getText().trim()),
                        genreField.getText().trim(),
                        karakterField.getText().trim(),
                        komentarArea.getText().trim(),
                        ratingValue,
                        selectedImagePath
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Film berhasil ditambahkan!");
                    if (parent != null) parent.updateFilmList();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambahkan film.");
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Tanggal rilis harus format yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Event tombol Keluar
        keluarBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void styleTextField(JTextField tf, Color bg, Color fg, Font font, Color caret) {
        tf.setBackground(bg);
        tf.setForeground(fg);
        tf.setFont(font);
        tf.setCaretColor(caret);
        tf.setBorder(new EmptyBorder(8, 10, 8, 10));
    }

    private void styleButton(JButton btn, Color bg, Color fg, Font font) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(font);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.brighter());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
    }

    private static class DateField extends JFormattedTextField {
        private final java.text.DateFormat dateFormat;

        public DateField() {
            super(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd")));
            dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            setColumns(40);
            setBackground(new Color(45, 45, 45));
            setForeground(Color.WHITE);
            setCaretColor(new Color(255, 102, 0));
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        }

        public void validateDate() throws java.text.ParseException {
            String text = getText().trim();
            if (text.isEmpty()) {
                throw new java.text.ParseException("Tanggal tidak boleh kosong", 0);
            }
            dateFormat.parse(text);
        }
    }

    private static class RatingField extends JTextField {
        public RatingField() {
            super(40);
            setBackground(new Color(45, 45, 45));
            setForeground(Color.WHITE);
            setCaretColor(new Color(255, 102, 0));
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        }

        public Double getValidRating() throws Exception {
            String text = getText().trim();
            if (text.isEmpty()) return null;
            double val = Double.parseDouble(text);
            if (val < 0 || val > 10) throw new Exception("Rating harus antara 0 sampai 10");
            return val;
        }
    }
}
