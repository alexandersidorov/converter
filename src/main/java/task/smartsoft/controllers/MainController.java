package task.smartsoft.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import task.smartsoft.domain.RegRecord;
import task.smartsoft.domain.User;
import task.smartsoft.exceptions.BigValueException;
import task.smartsoft.services.Converter;
import task.smartsoft.services.CurrencyService;
import task.smartsoft.services.RegRecordService;
import task.smartsoft.utils.ControlInputStr;
import task.smartsoft.utils.Today;

import java.sql.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    Converter converter;

    @Autowired
    RegRecordService recordService;

    @Autowired
    CurrencyService currencyService;


    @GetMapping("/")
    public String main(@AuthenticationPrincipal User currUser,
                       Model model) {


        List<String>fullCaptCurr = currencyService.getFullCaptionCurrencies();
        fullCaptCurr.add(currencyService.getFullCaptionRUB());

        String captionFrom = currencyService.getCaptionInListByCharCode("USD",fullCaptCurr);

        model.addAttribute("captions",fullCaptCurr);
        model.addAttribute("captionFrom",captionFrom);
        model.addAttribute("captionTo",currencyService.getFullCaptionRUB());

        return "main";
    }

    @PostMapping("/")
    public String currencyConvert(@AuthenticationPrincipal User currUser,
                                  @RequestParam("fromName")String fromName,
                                  @RequestParam("toName")String toName,
                                  @RequestParam("fromCount")String countFrom,
                                  Model model){

        List<String>fullCaptCurr = currencyService.getFullCaptionCurrencies();
        fullCaptCurr.add(currencyService.getFullCaptionRUB());

        String contrStr = null;
        try{
            contrStr = ControlInputStr.control(countFrom);

        }catch(BigValueException exception){
            model.addAttribute("captions",fullCaptCurr);
            model.addAttribute("captionFrom",fromName);
            model.addAttribute("captionTo",toName);
            model.addAttribute("message","Размер целой части введенной суммы не должен превышать 12 знаков.");
            return "main";
        }

        if(StringUtils.isEmpty(contrStr)){
            return "redirect:/";
        }

        double count = Double.parseDouble(contrStr);

        Date today = Today.get();
        RegRecord record = converter.convert(fromName,toName,count,today);
        record.setUser(currUser);
        recordService.addRegRecord(record);

        model.addAttribute("captions",fullCaptCurr);
        model.addAttribute("captionFrom",fromName);
        model.addAttribute("captionTo",toName);
        model.addAttribute("count",String.valueOf(record.getValueFrom()).replace(".",","));
        model.addAttribute("result",String.valueOf(record.getValueTo()).replace(".",","));

        return "main";

    }

}
