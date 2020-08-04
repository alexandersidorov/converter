package task.smartsoft.utils;

import java.sql.Date;
import java.util.Calendar;

public class Today {
    public static Date get(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date retDate = new Date(calendar.getTimeInMillis());
        return retDate;
    }

}
