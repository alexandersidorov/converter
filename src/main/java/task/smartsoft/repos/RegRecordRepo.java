package task.smartsoft.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import task.smartsoft.domain.RegRecord;
import task.smartsoft.domain.User;

import java.sql.Date;
import java.util.List;

public interface RegRecordRepo extends JpaRepository<RegRecord, Long> {
    List<RegRecord> findByUser(User user);
    List<RegRecord> findByUserAndDate(User user, Date date);
    List<RegRecord> findByUserAndDateAndFullCaptionFrom(User user, Date date,String fullCaptionFrom);
    List<RegRecord> findByUserAndDateAndFullCaptionTo(User user, Date date,String fullCaptionTo);
    List<RegRecord> findByUserAndDateAndFullCaptionFromAndFullCaptionTo(User user, Date date,String fullCaptionFrom,String fullCaptionTo);
}
