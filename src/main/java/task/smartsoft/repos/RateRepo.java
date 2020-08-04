package task.smartsoft.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import task.smartsoft.domain.Rate;

import java.sql.Date;
import java.util.List;

public interface RateRepo extends JpaRepository<Rate,Long> {
    List<Rate> findByDate(Date date);
    List<Rate> findByCurrency_ValuteID(String valuteID);
    Rate findByDateAndCurrency_ValuteID(Date date,String valuteID);

}
