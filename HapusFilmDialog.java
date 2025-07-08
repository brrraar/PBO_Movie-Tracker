import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HapusFilmDialog extends JDialog {

    public HapusFilmDialog(JDialog detailDialog, MenuFrame menuFrame, int index) {
        super(detailDialog, "Konfirmasi Hapus Film", true);
        setSize(400, 180);
        setLocationRelativeTo(detailDialog);
        setLayout(new BorderLayout());

        Color bgColor = new Color(28, 28, 28);
        Color textColor = Color.WHITE;
        Color accentColor = new Color(255, 102, 0);
        Font messageFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        getContentPane().setBackground(bgColor);

        JLabel messageLabel = new JLabel("Apakah Anda yakin ingin menghapus film ini?");
        messageLabel.setForeground(textColor);
        messageLabel.setFont(messageFont);
        messageLabel.setBorder(new EmptyBorder(20, 20, 20, 20));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(bgColor);

        JButton yesBtn = new JButton("Ya");
        JButton noBtn = new JButton("Tidak");

        for (JButton btn : new JButton[]{yesBtn, noBtn}) {
            btn.setBackground(accentColor);
            btn.setForeground(textColor);
            btn.setFont(buttonFont);
            btn.setFocusPainted(false);
            btn.setBorder(new EmptyBorder(8, 20, 8, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);

            Color hoverColor = accentColor.brighter();
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(hoverColor);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(accentColor);
                }
            });
        }

        yesBtn.addActionListener(e -> {
            // Cek index valid sebelum hapus
            if (index >= 0 && index < FilmStorage.daftarFilm.size()) {
                FilmStorage.daftarFilm.remove(index);
            }

            // Tutup dialog hapus dan dialog detail
            dispose();          // Tutup dialog hapus
            detailDialog.dispose(); // Tutup dialog detail

            // Tampilkan MenuFrame (bila belum tampil atau refresh)
            menuFrame.updateFilmList();
            menuFrame.setVisible(true);
            menuFrame.toFront();
            menuFrame.requestFocus();
        });

        noBtn.addActionListener(e -> dispose());

        buttonPanel.add(yesBtn);
        buttonPanel.add(noBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
