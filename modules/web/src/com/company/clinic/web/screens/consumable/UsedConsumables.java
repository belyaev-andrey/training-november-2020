package com.company.clinic.web.screens.consumable;

import com.company.clinic.entity.Consumable;
import com.company.clinic.service.ConsumablesService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.List;

@UiController("clinic_UsedConsumables")
@UiDescriptor("used-consumables.xml")
public class UsedConsumables extends Screen {

    @Inject
    private ConsumablesService consumablesService;

    @Inject
    private DataManager dataManager;

    @Install(to = "consumablesDl", target = Target.DATA_LOADER)
    private List<Consumable> consumablesDlLoadDelegate(LoadContext<Consumable> loadContext) {
        loadContext.setQuery(new LoadContext.Query("select distinct c from clinic_Visit v join v.consumables c " +
                "where @between(c.createTs, now-7, now+1, day)")).setView(View.LOCAL);
        return dataManager.loadList(loadContext);
        //return consumablesService.getUsedConsumables();
    }

}