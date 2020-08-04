package task.smartsoft.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TransferDateToString {
    public static String transfer(Date date,String sep){
        DateFormat dateFormat = new SimpleDateFormat("dd"+sep+"MM"+sep+"yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return dateFormat.format(date);
    }

    public static Date transferToDate(String date,String pattern) throws ParseException {
        java.util.Date dddd = new SimpleDateFormat(pattern).parse(date);
        return new Date(dddd.getTime());
    }
}
