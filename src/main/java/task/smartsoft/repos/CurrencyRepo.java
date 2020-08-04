package task.smartsoft.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import task.smartsoft.domain.Currency;

public interface CurrencyRepo extends JpaRepository<Currency,Long> {
    Currency findByValuteID(String valuteID);
    Currency findByCharCode(String charCode);

}
