package ExtendsLib;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

// Image Render cho cột trong JTable

public class czImgRender extends DefaultTableCellRenderer {

    private static final Map<String, ImageIcon> imageCache = new HashMap<>(); // Cache ảnh

    @Override
    public void setValue(Object value) {
        if (value instanceof String) {
            String imagePath = (String) value;

            // Kiểm tra cache trước
            if (imageCache.containsKey(imagePath)) {
                setIcon(imageCache.get(imagePath));
                setText("");
                return;
            }

            // Kiểm tra file local
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image img = icon.getImage().getScaledInstance(240, 135, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
                imageCache.put(imagePath, icon); // Lưu vào cache
                setIcon(icon);
                setText("");
            } else {
                setIcon(null);
                setText("Ảnh không tồn tại");
            }
        } else {
            setIcon(null);
            setText("Lỗi dữ liệu");
        }
    }
}
