import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MenuFrame extends JFrame {
    private final DefaultListModel<Film> model;
    private final JList<Film> filmList;
    private final FilmDAO filmDAO = new FilmDAO();
    private final int userId;

    public MenuFrame(int userId) {
        this.userId = userId;

        setTitle("Your Films");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(18, 18, 18));
        setLayout(new BorderLayout());

        Color background = new Color(18, 18, 18);
        Color listBackground = new Color(28, 28, 28);
        Color cardBackground = new Color(38, 38, 38);
        Color accent = new Color(255, 102, 0);
        Color text = Color.WHITE;

        JLabel title = new JLabel("Your Films", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(accent);
        title.setBorder(new EmptyBorder(20, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);
        mainPanel.setBorder(new EmptyBorder(0, 15, 15, 15));

        model = new DefaultListModel<>();
        filmList = new JList<>(model);
        filmList.setBackground(listBackground);
        filmList.setForeground(text);
        filmList.setSelectionBackground(accent.darker());
        filmList.setSelectionForeground(Color.WHITE);
        filmList.setCellRenderer(new FilmListCellRenderer(cardBackground));
        updateFilmList();

        JScrollPane scrollPane = new JScrollPane(filmList);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(listBackground);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton tambahBtn = createStyledButton("Tambah Film", accent);
        JButton keluarBtn = createStyledButton("Keluar", accent);

        tambahBtn.addActionListener(e -> new TambahFilmFrame(this, userId));
        keluarBtn.addActionListener(e -> System.exit(0));

        filmList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && filmList.getSelectedIndex() != -1) {
                    Film selected = filmList.getSelectedValue();
                    new DetailFilmFrame(MenuFrame.this, selected);
                }
            }
        });

        JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        tombolPanel.setBackground(background);
        tombolPanel.add(tambahBtn);
        tombolPanel.add(keluarBtn);
        mainPanel.add(tombolPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void updateFilmList() {
        model.clear();
        List<Film> films = filmDAO.getFilmsByUser(userId);
        for (Film f : films) {
            model.addElement(f);
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
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

    private static class FilmListCellRenderer extends JPanel implements ListCellRenderer<Film> {
        private final JLabel judulLabel = new JLabel();
        private final JLabel rilisLabel = new JLabel();
        private final JLabel ratingLabel = new JLabel();
        private final JLabel imageLabel = new JLabel();

        public FilmListCellRenderer(Color background) {
            setLayout(new BorderLayout(10, 0));
            setOpaque(true);
            setBorder(new CompoundBorder(
                    new MatteBorder(0, 0, 1, 0, new Color(60, 60, 60)),
                    new EmptyBorder(10, 10, 10, 10)
            ));
            setBackground(background);

            // Setup image label
            imageLabel.setPreferredSize(new Dimension(70, 100));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);

            // Panel teks sebelah kanan gambar
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setOpaque(false);

            judulLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            judulLabel.setForeground(Color.WHITE);
            rilisLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            rilisLabel.setForeground(Color.LIGHT_GRAY);
            ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            ratingLabel.setForeground(Color.LIGHT_GRAY);

            // Agar jarak antar label lebih rapih
            judulLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            rilisLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            textPanel.add(judulLabel);
            textPanel.add(Box.createVerticalStrut(5));  // space
            textPanel.add(rilisLabel);
            textPanel.add(Box.createVerticalStrut(3));  // space
            textPanel.add(ratingLabel);

            add(imageLabel, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Film> list, Film value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            judulLabel.setText(value.judul);
            rilisLabel.setText("Rilis: " + value.tanggalRilis);
            ratingLabel.setText("Rating: " + (value.rating != null ? String.format("%.1f", value.rating) : "-"));

            if (value.posterPath != null && !value.posterPath.isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(value.posterPath);
                    Image scaledImg = icon.getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImg));
                } catch (Exception e) {
                    imageLabel.setIcon(null);
                }
            } else {
                imageLabel.setIcon(null);
            }

            if (isSelected) {
                setBackground(new Color(80, 80, 80));
                judulLabel.setForeground(Color.WHITE);
                rilisLabel.setForeground(Color.WHITE);
                ratingLabel.setForeground(Color.WHITE);
            } else {
                setBackground(new Color(38, 38, 38));
                judulLabel.setForeground(Color.WHITE);
                rilisLabel.setForeground(Color.LIGHT_GRAY);
                ratingLabel.setForeground(Color.LIGHT_GRAY);
            }

            return this;
        }
    }
}
