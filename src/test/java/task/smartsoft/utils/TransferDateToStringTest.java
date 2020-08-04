package task.smartsoft.utils;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.*;

public class TransferDateToStringTest {

    @Test
    public void transferWithPoint() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,Calendar.AUGUST,1);
        Date date = new Date(calendar.getTimeInMillis());
        String dddd = TransferDateToString.transfer(date,".");
        Assert.assertEquals("01.08.2020",dddd);
    }

    @Test
    public void transferWithSlash() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,Calendar.AUGUST,1);
        Date date = new Date(calendar.getTimeInMillis());
        String dddd = TransferDateToString.transfer(date,"/");
        Assert.assertEquals("01/08/2020",dddd);
    }

    @Test
    public void transferToDateWithPoint() {
        String dateStr = "02.08.2020";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,Calendar.AUGUST,2);
        Date exDate = new Date(calendar.getTimeInMillis());

        Date actDate;
        try {
            actDate = TransferDateToString.transferToDate(dateStr, "dd.MM.yyyy");
            Assert.assertTrue(exDate.toString().equals(actDate.toString()));
        }catch(ParseException ex){
            fail();
        }

    }

    @Test
    public void transferToDateWithSlash() {
        String dateStr = "02/08/2020";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,Calendar.AUGUST,2);
        Date exDate = new Date(calendar.getTimeInMillis());

        Date actDate;
        try {
            actDate = TransferDateToString.transferToDate(dateStr, "dd/MM/yyyy");
            Assert.assertTrue(exDate.toString().equals(actDate.toString()));
        }catch(ParseException ex){
            fail();
        }
    }
}