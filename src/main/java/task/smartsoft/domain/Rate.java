package task.smartsoft.domain;

import task.smartsoft.utils.TransferDateToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double value;
    private Date date;
    private int nominal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency")
    private Currency currency;

    public Rate() {}

    public Rate(Date date, double value, int nominal, Currency currency) {
        this.value = value;
        this.date = date;
        this.nominal = nominal;
        this.currency = currency;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public double getValue() {return value;}
    public void setValue(double value) {this.value = value;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public int getNominal() {return nominal;}
    public void setNominal(int nominal) {this.nominal = nominal;}

    public Currency getCurrency() {return currency;}
    public void setCurrency(Currency currency) {this.currency = currency;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return TransferDateToString.transfer(date,".").equals(TransferDateToString.transfer(rate.date,".")) &&
                currency.equals(rate.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TransferDateToString.transfer(date,"."), currency);
    }
}
