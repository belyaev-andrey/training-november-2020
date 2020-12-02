package com.company.clinic.web.screens.owner;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Owner;

import javax.inject.Inject;

@UiController("clinic_Owner.browse")
@UiDescriptor("owner-browse.xml")
@LookupComponent("ownersTable")
@LoadDataBeforeShow
public class OwnerBrowse extends StandardLookup<Owner> {

    @Inject
    private GroupTable<Owner> ownersTable;
    @Inject
    private Notifications notifications;

    @Install(to = "ownersTable.greet", subject = "enabledRule")
    private boolean ownersTableGreetEnabledRule() {
        return ownersTable.getSingleSelected() != null;
    }

    @Subscribe("ownersTable.greet")
    public void onOwnersTableGreet(Action.ActionPerformedEvent event) {
        final Owner owner = ownersTable.getSingleSelected();
        if (owner == null) return;
        notifications.create().withCaption(String.format("Hello %s", owner.getName())).show();
    }
}