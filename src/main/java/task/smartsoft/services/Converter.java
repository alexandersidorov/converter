package task.smartsoft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import task.smartsoft.domain.Currency;
import task.smartsoft.domain.Rate;
import task.smartsoft.domain.RegRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

@Service
public class Converter {

    @Value("${captionRUB}")
    private String captionRUB;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private RateService rateService;

    @Autowired
    private ExchRatesLoader loader;

    public RegRecord convert(String fullCaptionFrom, String fullCaptionTo, double count, Date date){

        int flag = getFlagForConvert(fullCaptionFrom,fullCaptionTo);

        RegRecord record = null;
        switch(flag){
            case 0:{
                record = new RegRecord(fullCaptionFrom,fullCaptionTo,count,count,date);
                break;
            }
            case 1:{
                record = calcForToFor(fullCaptionFrom,fullCaptionTo,count,date);
                break;
            }
            case 2:{
                record = calcRubToFor(fullCaptionTo,count,date);
                break;
            }
            case 3:{
                record = calcForToRub(fullCaptionFrom,count,date);
                break;
            }
        }
        return record;
    }

    private int getFlagForConvert(String fullCaptionFrom, String fullCaptionTo) {

        //обе валюты - рубль
        int ret = 0;

        //обе валюты иностранные
        if(!fullCaptionFrom.contains(captionRUB) && !fullCaptionTo.contains(captionRUB))
            ret = 1;
        //первая рубль, вторая иностранная
        if(fullCaptionFrom.contains(captionRUB) && !fullCaptionTo.contains(captionRUB))
            ret = 2;
        //первая иностранная, вторая рубль
        if(!fullCaptionFrom.contains(captionRUB) && fullCaptionTo.contains(captionRUB))
            ret = 3;

        return ret;
    }

    private RegRecord calcForToFor(String fullCaptionFrom, String fullCaptionTo, double count, Date date){

        Currency currFrom = currencyService.getCurrencyByFullCaption(fullCaptionFrom);
        Currency currTo = currencyService.getCurrencyByFullCaption(fullCaptionTo);

        Rate rateFrom = currencyService.getRateByDate(currFrom,date);
        Rate rateTo = currencyService.getRateByDate(currTo,date);

        if(rateFrom==null || rateTo == null){
            loader.loadActualExchangeRates();

            rateFrom = rateService.findByLastDate(currFrom.getValuteID());
            rateTo = rateService.findByLastDate(currTo.getValuteID());
        }

        double oneFrom = rateFrom.getValue()/ rateFrom.getNominal();
        double oneTo = rateTo.getValue()/ rateTo.getNominal();
        double result =  oneFrom*count/oneTo;

        BigDecimal ddd = new BigDecimal(result);
        ddd = ddd.setScale(2, RoundingMode.HALF_UP);
        result = ddd.doubleValue();

        return new RegRecord(fullCaptionFrom,fullCaptionTo,count,result,date);
    }

    private RegRecord calcRubToFor(String fullCaptionTo,double count,Date date){

        Currency currTo = currencyService.getCurrencyByFullCaption(fullCaptionTo);

        Rate rateTo = currencyService.getRateByDate(currTo,date);

        if(rateTo == null){
            loader.loadActualExchangeRates();
            rateTo = rateService.findByLastDate(currTo.getValuteID());
        }

        double oneTo = rateTo.getValue()/ rateTo.getNominal();
        double result = count/oneTo;

        BigDecimal ddd = new BigDecimal(result);
        ddd = ddd.setScale(2, RoundingMode.HALF_UP);
        result = ddd.doubleValue();

        return new RegRecord(currencyService.getFullCaptionRUB(),fullCaptionTo,count,result,date);
    }

    private RegRecord calcForToRub(String fullCaptionFrom,double count,Date date){

        Currency currFrom = currencyService.getCurrencyByFullCaption(fullCaptionFrom);

        Rate rateFrom = currencyService.getRateByDate(currFrom,date);

        if(rateFrom==null){
            loader.loadActualExchangeRates();
            rateFrom = rateService.findByLastDate(currFrom.getValuteID());
        }

        double oneFrom = rateFrom.getValue()/ rateFrom.getNominal();
        double result = oneFrom*count;

        BigDecimal ddd = new BigDecimal(result);
        ddd = ddd.setScale(2, RoundingMode.HALF_UP);
        result = ddd.doubleValue();

        return new RegRecord(fullCaptionFrom,currencyService.getFullCaptionRUB(),count,result,date);
    }

}
