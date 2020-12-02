package com.company.clinic.web.screens.owner;

import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Owner;

import javax.inject.Inject;

@UiController("clinic_Owner.edit")
@UiDescriptor("owner-edit.xml")
@EditedEntityContainer("ownerDc")
@LoadDataBeforeShow
public class OwnerEdit extends StandardEditor<Owner> {

    @Inject
    private InstanceContainer<Owner> ownerDc;

    @Install(to = "nameField", subject = "validator")
    private void nameFieldValidator(String name) {
        if (name.contains("1")) throw new ValidationException("Name must not contain '1'");
    }
}