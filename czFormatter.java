package ExtendsLib;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

// VND Formatter | Ex : 15000 -> 15.000 VNĐ

public class czFormatter {

    private static final DecimalFormat vndFormat;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.'); // Đổi dấu phân cách nhóm từ ',' thành '.'

        vndFormat = new DecimalFormat("#,###", symbols);
    }

    public static String format(double d) {
        return vndFormat.format(d) + " VNĐ";
    }

    public static String format(BigDecimal d) {
        return vndFormat.format(d) + " VNĐ";
    }

}
