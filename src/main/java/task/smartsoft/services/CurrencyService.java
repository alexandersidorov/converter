package task.smartsoft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import task.smartsoft.domain.Currency;
import task.smartsoft.domain.Rate;
import task.smartsoft.repos.CurrencyRepo;
import task.smartsoft.utils.TransferDateToString;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Value("${captionRUB}")
    private String captionRUB;

    @Autowired
    private CurrencyRepo currencyRepo;

    public boolean addCurrency(Currency currency){

        Currency currencyFromDB = currencyRepo.findByValuteID(currency.getValuteID());
        if(currencyFromDB != null){
            return false;
        }

        currencyRepo.save(currency);
        return true;
    }

    public Currency getCurrencyByCharCode(String charCode){
        return currencyRepo.findByCharCode(charCode);
    }

    public Currency getCurrencyByFullCaption(String caption){
        String charCode = caption.substring(0,3);
        return getCurrencyByCharCode(charCode);
    }

    public Rate getRateByDate(Currency currency, Date date){

        String dateStr = TransferDateToString.transfer(date,".");

        Rate retRate = null;

        for (Rate rate:currency.getRates()){

            String dateFromRate = TransferDateToString.transfer(rate.getDate(),".");
            if(dateStr.equals(dateFromRate)) {
                retRate = rate;
                break;
            }
        }
        return  retRate;
    }

    public List<String> getFullCaptionCurrencies(){
        List<Currency> currencies = currencyRepo.findAll();
        List<String>fullCaptions = currencies
                .stream()
                .map( (curr)->curr.getCharCode()+" ("+curr.getName()+")" )
                .collect(Collectors.toList());
        return fullCaptions;
    }

    public String getCaptionInListByCharCode(String charCode,List<String> captions){
        String retStr = null;
        for(String caption:captions){
            if(caption.contains(charCode))
                retStr = caption;
        }
        return retStr;
    }

    public String getFullCaptionRUB(){
        return captionRUB+" (Российский рубль)";
    }
}
