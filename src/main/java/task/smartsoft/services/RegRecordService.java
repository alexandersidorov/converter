package task.smartsoft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import task.smartsoft.domain.RegRecord;
import task.smartsoft.domain.User;
import task.smartsoft.repos.RegRecordRepo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RegRecordService {

    @Autowired
    private RegRecordRepo regRecordRepo;

    public boolean addRegRecord(RegRecord record){
        regRecordRepo.save(record);
        return true;
    }

    public List<RegRecord> findByUser(User user){
        List<RegRecord> records = regRecordRepo.findByUser(user);
        records.sort(Comparator.comparing(RegRecord::getDate));
        return records;
    }

    public List<RegRecord> findInJournal(User user, Date date,String fullCaptionFrom,String fullCaptionTo){
        List<RegRecord> retList = new ArrayList<>();
        int index = getSearchIndex(user,date,fullCaptionFrom,fullCaptionTo);
        switch (index){
            case 0:{
                retList = regRecordRepo.findByUserAndDate(user, date);
                break;
            }
            case 1:{
                retList = regRecordRepo.findByUserAndDateAndFullCaptionFrom(user, date, fullCaptionFrom);
                break;
            }
            case 2:{
                retList = regRecordRepo.findByUserAndDateAndFullCaptionTo(user, date, fullCaptionTo);
                break;
            }
            case 3:{
                retList = regRecordRepo.findByUserAndDateAndFullCaptionFromAndFullCaptionTo(user, date,
                        fullCaptionFrom, fullCaptionTo);
                break;
            }
        }
        return retList;
    }

    private int getSearchIndex(User user, Date date,String fullCaptionFrom,String fullCaptionTo){
        int index = -1;

        if(StringUtils.isEmpty(fullCaptionFrom) && StringUtils.isEmpty(fullCaptionTo) )
            index = 0;
        if(!StringUtils.isEmpty(fullCaptionFrom) && StringUtils.isEmpty(fullCaptionTo) )
            index = 1;
        if(StringUtils.isEmpty(fullCaptionFrom) && !StringUtils.isEmpty(fullCaptionTo) )
            index = 2;
        if(!StringUtils.isEmpty(fullCaptionFrom) && !StringUtils.isEmpty(fullCaptionTo) )
            index = 3;

        return index;
    }
}
