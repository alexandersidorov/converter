package task.smartsoft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.smartsoft.domain.Currency;
import task.smartsoft.domain.Rate;
import task.smartsoft.repos.RateRepo;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;

@Service
public class RateService {

    @Autowired
    RateRepo rateRepo;

    public boolean addRate(Rate rate){

        Rate rateFromDB = findByDateAndCurrency(rate.getDate(), rate.getCurrency());
        if(rateFromDB!=null){
            return false;
        }

        rateRepo.save(rate);
        return true;
    }

    public Rate findByDateAndCurrency(Date date, Currency currency){
        return rateRepo.findByDateAndCurrency_ValuteID(date,currency.getValuteID());
    }

    public List<Rate> findAllByDate(Date date){
        return rateRepo.findByDate(date);
    }

    public Rate findByLastDate(String valuteID){

        List<Rate> rates = rateRepo.findByCurrency_ValuteID(valuteID);
        Rate max = rates.stream().max(Comparator.comparing(Rate::getDate)).get();
        return max;
    }
}
