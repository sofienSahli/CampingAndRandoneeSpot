package soft.dot.com.campingandrandoneespot.com.dot.soft.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    public static String getTimeFromLong(long mills) {
        Date date = new Date(mills);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public static String calculAverageSpeed(long time, float distance) {
        long t = time / 60000;
        return  distance / t + "M/Min";
    }
}
