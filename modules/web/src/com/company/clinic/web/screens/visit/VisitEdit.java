package com.company.clinic.web.screens.visit;

import com.company.clinic.entity.Consumable;
import com.company.clinic.entity.Pet;
import com.company.clinic.service.VisitService;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.model.CollectionChangeType;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Visit;

import javax.inject.Inject;

@UiController("clinic_Visit.edit")
@UiDescriptor("visit-edit.xml")
@EditedEntityContainer("visitDc")
@LoadDataBeforeShow
public class VisitEdit extends StandardEditor<Visit> {

    @Inject
    private VisitService visitService;

    @Inject
    private CollectionContainer<Pet> petsDc;

    @Inject
    private CollectionLoader<Pet> petsDl;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Visit> event) {
        final Visit visit = event.getEntity();
        petsDl.load();
        visit.setPet(petsDc.getItems().get(0));
    }

    @Subscribe(id = "visitDc", target = Target.DATA_CONTAINER)
    public void onVisitDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Visit> event) {
        if ("hoursSpent".equals(event.getProperty())) {
            refreshAmount();
        }
    }

    @Subscribe(id = "consumablesDc", target = Target.DATA_CONTAINER)
    public void onConsumablesDcCollectionChange(CollectionContainer.CollectionChangeEvent<Consumable> event) {
        if (event.getChangeType() != CollectionChangeType.REFRESH) {
            refreshAmount();
        }
    }

    private void refreshAmount() {
        final Visit visit = getEditedEntity();
        visit.setAmount(visitService.calculateAmount(visit));
    }

}