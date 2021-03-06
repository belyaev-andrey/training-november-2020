package com.company.clinic.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "CLINIC_VISIT")
@Entity(name = "clinic_Visit")
@NamePattern("%s %s|visitDate,description")
@Listeners("clinic_VisitEntityListener")
@PublishEntityChangedEvents
public class Visit extends StandardEntity {
    private static final long serialVersionUID = -8254124810105648524L;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PET_ID")
    private Pet pet;

    @Column(name = "NUMBER_")
    private Long number;

    @Lookup(type = LookupType.DROPDOWN, actions = {})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VETERINARIAN_ID")
    private Veterinarian veterinarian;

    @NotNull
    @Column(name = "VISIT_DATE", nullable = false)
    private LocalDateTime visitDate;

    @NotNull
    @Column(name = "HOURS_SPENT", nullable = false)
    @PositiveOrZero
    private Integer hoursSpent;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    @PositiveOrZero
    private BigDecimal amount;

    @JoinTable(name = "CLINIC_VISIT_CONSUMABLE_LINK",
            joinColumns = @JoinColumn(name = "VISIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONSUMABLE_ID"))
    @ManyToMany
    private List<Consumable> consumables;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Integer hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public List<Consumable> getConsumables() {
        return consumables;
    }

    public void setConsumables(List<Consumable> consumables) {
        this.consumables = consumables;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public Veterinarian getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(Veterinarian veterinarian) {
        this.veterinarian = veterinarian;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Transient
    @MetaProperty(related = {"visitDate", "hoursSpent"})
    public LocalDateTime getVisitEndDate() {
        if (getVisitDate() == null || getHoursSpent() == null) {
            return null;
        }
        return getVisitDate().plusHours(getHoursSpent());
    }

    @Transient
    @MetaProperty(related = {"pet", "visitDate"})
    public String getVisitCaption() {
        if (getPet() == null || getVisitDate() == null) {
            return "";
        }
        return String.format("%s: %s", getPet().getName(), getVisitDate());
    }

    @PostConstruct
    public void postConstruct() {
        setAmount(BigDecimal.TEN);
    }
}