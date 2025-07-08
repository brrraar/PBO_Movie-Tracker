import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private final UserDAO userDAO = new UserDAO();

    public RegisterFrame() {
        setTitle("Registrasi User Baru");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        Color bgColor = new Color(18, 18, 18);
        Color cardColor = new Color(28, 28, 28);
        Color accentColor = new Color(255, 102, 0);
        Color textColor = Color.WHITE;
        Color inputBgColor = new Color(45, 45, 45);


        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        getContentPane().setBackground(bgColor);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(cardColor);
        panel.setBorder(new EmptyBorder(40, 60, 40, 60)); // padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;


        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(textColor);
        userLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        panel.add(userLabel, gbc);


        JTextField usernameField = new JTextField(30);
        usernameField.setBackground(inputBgColor);
        usernameField.setForeground(textColor);
        usernameField.setFont(fieldFont);
        usernameField.setCaretColor(accentColor);
        usernameField.setBorder(new EmptyBorder(8, 10, 8, 10));
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        panel.add(usernameField, gbc);


        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(textColor);
        passLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        panel.add(passLabel, gbc);


        JPasswordField passwordField = new JPasswordField(30);
        passwordField.setBackground(inputBgColor);
        passwordField.setForeground(textColor);
        passwordField.setFont(fieldFont);
        passwordField.setCaretColor(accentColor);
        passwordField.setBorder(new EmptyBorder(8, 10, 8, 10));
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        panel.add(passwordField, gbc);


        JLabel confirmLabel = new JLabel("Konfirmasi Password:");
        confirmLabel.setForeground(textColor);
        confirmLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        panel.add(confirmLabel, gbc);


        JPasswordField confirmField = new JPasswordField(30);
        confirmField.setBackground(inputBgColor);
        confirmField.setForeground(textColor);
        confirmField.setFont(fieldFont);
        confirmField.setCaretColor(accentColor);
        confirmField.setBorder(new EmptyBorder(8, 10, 8, 10));
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        panel.add(confirmField, gbc);


        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(accentColor);
        registerBtn.setForeground(textColor);
        registerBtn.setFont(buttonFont);
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(new EmptyBorder(10, 30, 10, 30));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.setOpaque(true);


        registerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(accentColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(accentColor);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(30, 10, 0, 10);
        panel.add(registerBtn, gbc);

        add(panel);


        registerBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (username.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong");
                return;
            }
            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Password dan konfirmasi tidak cocok");
                return;
            }
            if (!userDAO.registerUser(username, pass)) {
                JOptionPane.showMessageDialog(this, "Username sudah digunakan atau terjadi kesalahan");
                return;
            }

            JOptionPane.showMessageDialog(this, "Registrasi berhasil!");
            dispose();
        });

        setVisible(true);
    }
}
