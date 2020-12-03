package com.company.clinic.web.screens.bp.treatment.start;

import com.company.clinic.entity.Pet;
import com.haulmont.addon.bproc.web.processform.OutputVariable;
import com.haulmont.addon.bproc.web.processform.ProcessForm;
import com.haulmont.addon.bproc.web.processform.ProcessFormContext;
import com.haulmont.addon.bproc.web.processform.ProcessVariable;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;

@UiController("clinic_TreatmentStart")
@UiDescriptor("treatment-start.xml")
@LoadDataBeforeShow
@ProcessForm (
        outputVariables = {
                @OutputVariable(name = "description", type = String.class),
                @OutputVariable(name = "doctor", type = User.class),
                @OutputVariable(name = "nurse", type = User.class),
                @OutputVariable(name = "pet", type = Pet.class)
        }
)
public class TreatmentStart extends Screen {

    @Inject
    private ProcessFormContext processFormContext;

    @Inject
    @ProcessVariable(name = "description")
    private TextArea<String> description;

    @Inject
    @ProcessVariable(name = "doctor")
    private LookupField<User> doctorLookup;

    @Inject
    @ProcessVariable(name = "nurse")
    private LookupField<User> nurseLookup;

    @Inject
    @ProcessVariable(name = "pet")
    private LookupField<Pet> petLookup;

    @Subscribe("startBtn")
    public void onStartBtnClick(Button.ClickEvent event) {
        processFormContext.processStarting().saveInjectedProcessVariables().start();
        closeWithDefaultAction();
    }

}