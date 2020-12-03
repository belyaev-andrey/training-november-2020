package com.company.clinic.service;

import com.company.clinic.entity.Consumable;
import com.company.clinic.entity.Pet;
import com.company.clinic.entity.Veterinarian;
import com.company.clinic.entity.Visit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service(VisitService.NAME)
public class VisitServiceBean implements VisitService {

    @Inject
    private DataManager dataManager;

    @Override
    public BigDecimal calculateAmount(Visit visit) {
        BigDecimal result = BigDecimal.ZERO;

        if (visit.getHoursSpent() != null && visit.getVeterinarian() != null) {
            result = result.add(BigDecimal.valueOf(visit.getHoursSpent())
                    .multiply(visit.getVeterinarian().getHourlyRate()));
        }

        if (visit.getConsumables() != null) {
            for (Consumable c : visit.getConsumables()) {
                result = result.add(c.getPrice());
            }
        }

        return result;
    }

    @Override
    public Visit scheduleVisit(Pet pet, LocalDateTime visitDate, User user) {
        Visit newVisit = dataManager.create(Visit.class);

        newVisit.setPet(pet);
        newVisit.setVisitDate(visitDate);
        newVisit.setVeterinarian(findVetByUser(user));
        newVisit.setHoursSpent(1);
        newVisit.setDescription("");

        return dataManager.commit(newVisit);
    }


    private Veterinarian findVetByUser(User user) {
        return dataManager.load(Veterinarian.class)
                .query("select v from clinic_Veterinarian v where v.user.id = :userId")
                .parameter("userId", user.getId())
                .optional()
                .orElseThrow(IllegalArgumentException::new);
    }
}