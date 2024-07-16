package com.example.HRApplication.Models;

import jakarta.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "SalaryHistory")
public class SalaryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("salaryHistories")
    private User user;

    @Column(name = "event")
    private String event;

    @Column(name = "effective_from")
    @Temporal(TemporalType.DATE)
    private Date effectiveFrom;

    @Column(name = "field")
    private String field;

    public SalaryHistory(Integer id, User user, String event, Date effectiveFrom, String field, Double changedFrom, Double changedTo, Double percentageChange) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.effectiveFrom = effectiveFrom;
        this.field = field;
        this.changedFrom = changedFrom;
        this.changedTo = changedTo;
        this.percentageChange = percentageChange;
    }

    @Column(name = "changed_from")
    private Double changedFrom;

    @Column(name = "changed_to")
    private Double changedTo;

    @Column(name = "percentage_change")
    private Double percentageChange;

    public SalaryHistory(User user, String baseSalaryChange, Date date, String baseSalary, double oldSalary, double newSalary, double percentageChange) {

    }

    public SalaryHistory() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    public Double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(Double percentageChange) {
        this.percentageChange = percentageChange;
    }

    public Double getChangedFrom() {
        return changedFrom;
    }

    public void setChangedFrom(Double changedFrom) {
        this.changedFrom = changedFrom;
    }

    public Double getChangedTo() {
        return changedTo;
    }

    public void setChangedTo(Double changedTo) {
        this.changedTo = changedTo;
    }

}
