package com.company.clinic.web.screens.main;

import com.company.clinic.entity.Pet;
import com.company.clinic.entity.Visit;
import com.company.clinic.service.VisitService;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.app.main.MainScreen;

import javax.inject.Inject;
import java.time.LocalDateTime;


@UiController("extMainScreen")
@UiDescriptor("ext-main-screen.xml")
public class ExtMainScreen extends MainScreen {

    @Inject
    private CollectionLoader<Visit> visitsDl;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<Pet> petsDl;
    @Inject
    private VisitService visitService;
    @Inject
    private LookupField<Pet> petSelector;
    @Inject
    private UserSession userSession;
    @Inject
    private DateField<LocalDateTime> dateSelector;
    @Inject
    private CollectionContainer<Visit> visitsDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        visitsDl.load();
        petsDl.load();
    }

    @Subscribe("visitsCalendar")
    public void onVisitsCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {

        final Visit visit = (Visit) event.getEntity();

        final Screen screen = screenBuilders
                .editor(Visit.class, this)
                .editEntity(visit)
                .withOpenMode(OpenMode.DIALOG)
                .build();

        screen.addAfterCloseListener(afterCloseEvent -> {
           if (afterCloseEvent.getCloseAction() == WINDOW_COMMIT_AND_CLOSE_ACTION) {
               visitsDl.load();
           }
        });

        screen.show();

    }

    @Subscribe("schedule")
    public void onSchedule(Action.ActionPerformedEvent event) {
        Visit visit = visitService.scheduleVisit(petSelector.getValue(),
                dateSelector.getValue(), userSession.getCurrentOrSubstitutedUser());

        visitsDc.replaceItem(visit);

        petSelector.clear();
        dateSelector.clear();
    }



    @Subscribe("refresh")
    public void onRefresh(Action.ActionPerformedEvent event) {
        petsDl.load();
        visitsDl.load();
    }



}