package com.company.clinic.web.screens.main;

import com.company.clinic.entity.Visit;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
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

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        visitsDl.load();
    }

    @Subscribe("refreshBtn")
    public void onRefreshBtnClick(Button.ClickEvent event) {
        visitsDl.load();
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



}