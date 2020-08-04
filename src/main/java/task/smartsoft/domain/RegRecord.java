package task.smartsoft.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class RegRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullCaptionFrom;
    private String fullCaptionTo;
    private double valueFrom;
    private double valueTo;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public RegRecord() {}

    public RegRecord(String fullCaptionFrom, String fullCaptionTo, double valueFrom, double valueTo, Date date) {
        this.fullCaptionFrom = fullCaptionFrom;
        this.fullCaptionTo = fullCaptionTo;
        this.valueFrom = valueFrom;
        this.valueTo = valueTo;
        this.date = date;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getFullCaptionFrom() {return fullCaptionFrom;}
    public void setFullCaptionFrom(String charCodeFrom) {
        this.fullCaptionFrom = charCodeFrom;
    }

    public String getFullCaptionTo() {
        return fullCaptionTo;
    }
    public void setFullCaptionTo(String charCodeTo) {this.fullCaptionTo = charCodeTo;}

    public double getValueFrom() {
        return valueFrom;
    }
    public void setValueFrom(double valueFrom) {
        this.valueFrom = valueFrom;
    }

    public double getValueTo() {
        return valueTo;
    }
    public void setValueTo(double valueTo) {
        this.valueTo = valueTo;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegRecord record = (RegRecord) o;
        return id.equals(record.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
