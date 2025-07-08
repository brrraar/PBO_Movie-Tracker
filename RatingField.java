import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.*;

public class RatingField extends JFormattedTextField {

    public RatingField() {
        super(createFormatter());
        setColumns(30);
        setBackground(new Color(45, 45, 45));
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setCaretColor(new Color(255, 102, 0));
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        // Pasang DocumentFilter custom supaya otomatis clear jika input > 10
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new RatingDocumentFilter());
    }

    private static NumberFormatter createFormatter() {
        NumberFormat format = DecimalFormat.getNumberInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        formatter.setMaximum(10.0);
        formatter.setAllowsInvalid(true);
        formatter.setOverwriteMode(false);
        return formatter;
    }

    public Double getValidRating() throws Exception {
        String text = getText().trim();
        if (text.isEmpty()) return null;
        try {
            double val = Double.parseDouble(text);
            if (val < 0 || val > 10) throw new Exception("Rating harus antara 0 sampai 10.");
            return val;
        } catch (NumberFormatException e) {
            throw new Exception("Rating harus angka.");
        }
    }

    private static class RatingDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.insert(offset, string);

            if (isValidRating(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Clear the field jika input invalid (lebih dari 10)
                fb.remove(0, fb.getDocument().getLength());
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.replace(offset, offset + length, text);

            if (isValidRating(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Clear field jika invalid
                fb.remove(0, fb.getDocument().getLength());
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isValidRating(String text) {
            text = text.trim();
            if (text.isEmpty()) return true; // kosong boleh
            try {
                double val = Double.parseDouble(text);
                return val >= 0 && val <= 10;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
