package task.smartsoft.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Currency {

     @Id
     private String valuteID;
     private String numCode;
     private String charCode;
     private String name;

     @OneToMany(mappedBy = "currency",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     private Set<Rate> rates;

     public Currency() {}

     public Currency(String valuteID, String numCode, String charCode, String name) {
          this.valuteID = valuteID;
          this.numCode = numCode;
          this.charCode = charCode;
          this.name = name;
     }

     public String getValuteID() {return valuteID;}
     public void setValuteID(String valuteID) {this.valuteID = valuteID;}

     public String getNumCode() {return numCode;}
     public void setNumCode(String numCode) {this.numCode = numCode;}

     public String getCharCode() {return charCode;}
     public void setCharCode(String charCode) {this.charCode = charCode;}

     public String getName() {return name;}
     public void setName(String name) {this.name = name;}

     public Set<Rate> getRates() {return rates;}
     public void setRates(Set<Rate> rates) {this.rates = rates;}

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          Currency currency = (Currency) o;
          return valuteID.equals(currency.valuteID);
     }

     @Override
     public int hashCode() {
          return Objects.hash(valuteID);
     }
}
