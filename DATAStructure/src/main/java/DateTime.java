import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
    public static String now() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
