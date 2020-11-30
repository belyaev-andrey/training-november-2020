package com.company.clinic.service;

import com.company.clinic.entity.Consumable;
import com.company.clinic.entity.Visit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service(VisitService.NAME)
public class VisitServiceBean implements VisitService {

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

}