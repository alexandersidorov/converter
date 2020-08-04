package task.smartsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import task.smartsoft.domain.RegRecord;
import task.smartsoft.domain.User;
import task.smartsoft.services.CurrencyService;
import task.smartsoft.services.RegRecordService;
import task.smartsoft.utils.TransferDateToString;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JournalController {

    @Autowired
    RegRecordService recordService;

    @Autowired
    CurrencyService currencyService;


    @GetMapping("/journal/{user}")
    public String show(@AuthenticationPrincipal User user,
                       Model model) {


        List<String> fullCaptCurr = currencyService.getFullCaptionCurrencies();
        fullCaptCurr.add(currencyService.getFullCaptionRUB());
        String empty = "";
        fullCaptCurr.add(empty);

        List<RegRecord> records = recordService.findByUser(user);

        model.addAttribute("records",records);
        model.addAttribute("captions",fullCaptCurr);
        model.addAttribute("captionFrom",empty);
        model.addAttribute("captionTo",empty);

        return "journal";
    }

    @PostMapping("/journal/{user}")
    public String currencyConvert(@AuthenticationPrincipal User user,
                                  @RequestParam("fromName")String fromName,
                                  @RequestParam("toName")String toName,
                                  @RequestParam("date")String dateStr,
                                  Model model){


        List<String> fullCaptCurr = currencyService.getFullCaptionCurrencies();
        fullCaptCurr.add(currencyService.getFullCaptionRUB());
        String empty = "";
        fullCaptCurr.add(empty);

        List<RegRecord> records = new ArrayList<>();
        try{
            Date date = TransferDateToString.transferToDate(dateStr,"yyyy-MM-dd");
            records = recordService.findInJournal(user,date,fromName,toName);
        }catch(ParseException exception){

            model.addAttribute("message","Произошла ошибка при получении даты.");
            model.addAttribute("records",records);
            model.addAttribute("captions",fullCaptCurr);
            model.addAttribute("captionFrom",fromName);
            model.addAttribute("captionTo",toName);
            model.addAttribute("date",dateStr);

            return "redirect:/journal/"+user.getId();
        }

        model.addAttribute("records",records);
        model.addAttribute("captions",fullCaptCurr);
        model.addAttribute("captionFrom",fromName);
        model.addAttribute("captionTo",toName);
        model.addAttribute("date",dateStr);

        return "journal";

    }


}
