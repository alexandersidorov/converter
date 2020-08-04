package task.smartsoft.utils;

import org.springframework.util.StringUtils;
import task.smartsoft.exceptions.BigValueException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ControlInputStr {

    //преобразование входных данных(допускаются только числа и одна запятая)
    //максимум 12 символов целой части (999 999 999 999)
    //только 2 знака после запятой (используем округление)
    public static String control(String input)throws BigValueException{
        boolean findSep = false;

        StringBuilder strBuilder = new StringBuilder();

        for(char c: input.toCharArray()){

            if(Character.isDigit(c))
                strBuilder.append(c);
            else if(c == ',' && !findSep){
                strBuilder.append(c);
                findSep = true;
            }
        }

        String result = strBuilder.toString().replace(",",".");

        if(StringUtils.isEmpty(result))
            return result;

        //проверяем, есть ли запятая
        //если есть, то округляем до 2ух знаков
        int index = result.indexOf(".");
        if (index != -1){
            BigDecimal ddd = new BigDecimal(result);
            ddd = ddd.setScale(2, RoundingMode.HALF_UP);
            result = ddd.toString();
        }

        //проверяем на кол-во символов в целой части(не больше 12)
        int indexStop = index;
        if(indexStop==-1){
            indexStop = result.length()-1;
        }
        String beforeSep = result.substring(0,indexStop);
        if (beforeSep.length()>12)
            throw new BigValueException();


        return result;
    }
}
