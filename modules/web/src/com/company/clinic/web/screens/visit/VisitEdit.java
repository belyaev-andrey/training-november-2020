package com.company.clinic.web.screens.visit;

import com.company.clinic.entity.Consumable;
import com.company.clinic.entity.Pet;
import com.company.clinic.service.VisitService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Visit;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;
import org.slf4j.Logger;

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

    @Inject
    private Button runReport;
    @Inject
    private DataContext dataContext;
    @Inject
    private DataManager dataManager;

    @Subscribe
    public void onInit(InitEvent event) {
        runReport.setAction(new EditorPrintFormAction(this, null));
    }

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