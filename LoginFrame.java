import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private final UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("Login - Movie Tracker");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Color palette
        Color bgColor = new Color(18, 18, 18);
        Color cardColor = new Color(28, 28, 28);
        Color accentColor = new Color(255, 102, 0);
        Color hoverColor = new Color(255, 140, 0);
        Color inputColor = new Color(45, 45, 45);
        Color textColor = Color.WHITE;

        // Fonts
        Font titleFont = new Font("Segoe UI", Font.BOLD, 28);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        // Background setup
        getContentPane().setBackground(bgColor);
        setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();

        // Title
        JLabel titleLabel = new JLabel("Movie Tracker");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(accentColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Card Panel
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(cardColor);
        cardPanel.setBorder(new CompoundBorder(
                new LineBorder(accentColor, 1, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        // Username Label & Field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        userLabel.setForeground(textColor);
        userLabel.setPreferredSize(new Dimension(100, 25));

        JTextField userField = new JTextField(18);
        userField.setFont(fieldFont);
        userField.setBackground(inputColor);
        userField.setForeground(textColor);
        userField.setCaretColor(accentColor);
        userField.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        // Password Label & Field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        passLabel.setForeground(textColor);
        passLabel.setPreferredSize(new Dimension(100, 25));

        JPasswordField passField = new JPasswordField(18);
        passField.setFont(fieldFont);
        passField.setBackground(inputColor);
        passField.setForeground(textColor);
        passField.setCaretColor(accentColor);
        passField.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        // Tombol Login
        JButton loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.setBackground(accentColor);
        loginButton.setForeground(textColor);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(new EmptyBorder(10, 20, 10, 20));

        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(accentColor);
            }
        });

        // Tombol Register (buka frame register)
        JButton registerButton = new JButton("Register");
        registerButton.setFont(buttonFont);
        registerButton.setBackground(accentColor);
        registerButton.setForeground(textColor);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBorder(new EmptyBorder(10, 20, 10, 20));

        registerButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(accentColor);
            }
        });

        registerButton.addActionListener(e -> {
            // buka frame registrasi user baru
            new RegisterFrame();
        });

        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan Password harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userDAO.validateUser(username, password)) {
                int userId = userDAO.getUserId(username);
                if (userId != -1) {
                    dispose();
                    new MenuFrame(userId);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mendapatkan data user.", "Error", JOptionPane.ERROR_MESSAGE);
                    userField.setText("");
                    passField.setText("");
                    userField.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
                userField.setText("");
                passField.setText("");
                userField.requestFocus();
            }
        });

        // Layout GridBag untuk cardPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        cardPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        cardPanel.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        cardPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        cardPanel.add(passField, gbc);

        // Panel tombol Login & Register di baris ke-2, dua kolom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(cardColor);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        cardPanel.add(buttonPanel, gbc);

        // Tambahkan semua ke frame
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.insets = new Insets(20, 10, 10, 10);
        add(titleLabel, gbcMain);

        gbcMain.gridy = 1;
        add(cardPanel, gbcMain);

        setVisible(true);
    }
}
