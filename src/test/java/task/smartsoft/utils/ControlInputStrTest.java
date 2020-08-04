package task.smartsoft.utils;

import org.junit.Assert;
import org.junit.Test;
import task.smartsoft.exceptions.BigValueException;

import static org.junit.Assert.fail;

public class ControlInputStrTest {

    @Test
    public void controlTest1() {
        String inputStr = "100sdfsf,009,фывк22";
        String expStr = "100.01";

        try{
            String actString = ControlInputStr.control(inputStr);
            Assert.assertEquals(expStr,actString);
        }catch(BigValueException exception){
            fail();
        }

    }

    @Test
    public void controlTest2() {
        String inputStr = "aasdf0edffgsg|||,11/.khy89";
        String expStr = "0.12";

        try{
            String actString = ControlInputStr.control(inputStr);
            Assert.assertEquals(expStr,actString);
        }catch(BigValueException exception){
            fail();
        }
    }

    @Test
    public void controlTest3() {
        String inputStr = "";
        String expStr = "";

        try{
            String actString = ControlInputStr.control(inputStr);
            Assert.assertEquals(expStr,actString);
        }catch(BigValueException exception){
            fail();
        }
    }

    @Test(expected = NullPointerException.class)
    public void controlTest4() {
        String inputStr = null;
        try{
            String actString = ControlInputStr.control(inputStr);
        }catch(BigValueException exception){
            fail();
        }
    }

    @Test
    public void controlTest5() {
        String inputStr = ",sdfv162";
        String expStr = "0.16";

        try{
            String actString = ControlInputStr.control(inputStr);
            Assert.assertEquals(expStr,actString);
        }catch(BigValueException exception){
            fail();
        }
    }

}